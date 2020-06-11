package top.imuster.auth.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.imuster.auth.exception.CustomSecurityException;
import top.imuster.auth.service.UserAuthenInfoService;
import top.imuster.auth.service.UserAuthenRecordInfoService;
import top.imuster.auth.service.UserAuthenService;
import top.imuster.auth.service.UserRoleRelService;
import top.imuster.auth.utils.Base64Util;
import top.imuster.auth.utils.HttpUtil;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.dto.rabbitMq.SendUserCenterDto;
import top.imuster.common.core.utils.DateUtil;
import top.imuster.common.core.utils.GenerateSendMessageService;
import top.imuster.file.api.service.FileServiceFeignApi;
import top.imuster.security.api.dto.UserAuthenDto;
import top.imuster.security.api.pojo.UserAuthenInfo;
import top.imuster.security.api.pojo.UserAuthenRecordInfo;
import top.imuster.security.api.pojo.UserRoleRel;
import top.imuster.user.api.dto.UserAuthenResultDto;
import top.imuster.user.api.service.UserServiceFeignApi;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @ClassName: UserAuthenServiceImpl
 * @Description: UserAuthenServiceImpl
 * @author: hmr
 * @date: 2020/3/27 15:07
 */
@Service("userAuthenService")
public class UserAuthenServiceImpl implements UserAuthenService {
    protected  final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${baidu.accessToken}")
    private String bdAccessToken;

    @Value("${baidu.templateSign}")
    private String bdTemplateSign;

    @Value("${baidu.bdRecogniseUrl}")
    private String bdRecogniseUrl;

    @Value("${file.urlPrefix}")
    private String urlPrefix;

    @Value("${baidu.bdRealNameUrl}")
    private String bdRealNameUrl;

    @Autowired
    private GenerateSendMessageService generateSendMessageService;

    @Autowired
    FileServiceFeignApi fileServiceFeignApi;

    @Autowired
    UserServiceFeignApi userServiceFeignApi;

    @Resource
    UserRoleRelService userRoleRelService;

    @Resource
    UserAuthenRecordInfoService userAuthenRecordInfoService;

    @Resource
    UserAuthenInfoService userAuthenInfoService;

    private static final String failContent = "我们已经收到您提交的审核,但是由于图片不清晰导致AI无法快速识别,请等待管理员审核，感谢您的配合";
    private static final String successContent = "恭喜您已经成功通过实名认证,您现在可以使用irh平台全部功能,感谢您的配合";

    public Message<String> realNameAuthentication(UserAuthenDto userAuthenDto) {
        boolean res = generate(userAuthenDto);
        saveAuthen2DB(userAuthenDto, res ? 2 : 1,2);
        return Message.createBySuccess(res ? successContent : failContent);
    }

    private boolean generate(UserAuthenDto userAuthenDto){
        String inputCardNo = userAuthenDto.getInputCardNo();
        String inputName = userAuthenDto.getInputName();
        String fileUri = userAuthenDto.getFileUri();
        // 请求url
        try {
            // 本地文件路径
            String imgStr = Base64Util.encode(getFileByUri(fileUri));
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");
            String param = "id_card_side=" + "front" + "&image=" + imgParam;
            String result = HttpUtil.post(bdRealNameUrl, bdAccessToken, param);
            JSONObject jsonObject = JSONObject.parseObject(result);
            JSONObject wordsResult = jsonObject.getJSONObject("words_result");
            JSONObject realNameObject = wordsResult.getJSONObject("姓名");
            String realName = (String)realNameObject.get("words");
            JSONObject numObject = wordsResult.getJSONObject("公民身份号码");
            String num = (String) numObject.get("words");
            if(StringUtils.equals(inputCardNo, num) && StringUtils.equals(inputName, realName)){
                return true;
            }
        } catch (Exception e) {
            log.error("------------>百度云识别身份证请求失败,错误信息为{}", e.getMessage());
        }
        return false;
    }

    /**
     * @Author hmr
     * @Description 根据上传的图片地址获得图片信息
     * @Date: 2020/5/13 14:03
     * @param
     * @reture: java.io.File
     **/
    private byte[] getFileByUri(String suffix) throws IOException {
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(urlPrefix + suffix)
                .build();
        Response response = httpClient.newCall(request).execute();
        return response.body().bytes();
    }

    private UserAuthenResultDto execOneCard(UserAuthenDto userAuthenDto){
        UserAuthenResultDto resultDto = new UserAuthenResultDto();
        Integer errorCode = 0;
        JSONObject out = null;
        String fileUri = userAuthenDto.getFileUri();
        String inputCardNo = userAuthenDto.getInputCardNo();
        String inputName = userAuthenDto.getInputName();

        try{
            String imgStr = Base64Util.encode(getFileByUri(fileUri));
            String recogniseParams = new StringBuilder().append("templateSign=").append(bdTemplateSign).append("&image=").append(URLEncoder.encode(imgStr, "UTF-8")).toString();
            String result = HttpUtil.post(bdRecogniseUrl, bdAccessToken, recogniseParams);
            out = JSON.parseObject(result);
            errorCode = (Integer) out.get("error_code");
        }catch (Exception e){
            throw new CustomSecurityException("认证服务器暂时停止工作,请稍后重试");
        }
        if(errorCode != 0){
            //验证失败
            resultDto.setSuccess(false);
        }
        String data = out.getString("data");
        JSONObject jsonData = JSON.parseObject(data);
        HashMap<String, String> resMap = new HashMap<>();
        JSONArray ret = jsonData.getJSONArray("ret");
        for (int i = 0; i < ret.size(); i++) {
            JSONObject msg = ret.getJSONObject(i);
            String key = msg.getString("word_name");
            String value = msg.getString("word");
            resMap.put(key, value);
        }
        String name = resMap.get("realName");
        String id = resMap.get("certificateNum");
        if(!name.equals(inputName) || !id.equals(inputCardNo)){
            resultDto.setSuccess(false);
        }else{
            resultDto.setRealName(name);
            resultDto.setSuccess(true);
            resultDto.setAcademyName(resMap.get("academyName"));
            resultDto.setCertificateNum(id);
        }
        return resultDto;
    }

    public Message<String> oneCardSolution(UserAuthenDto userAuthenDto){
        UserAuthenResultDto result = execOneCard(userAuthenDto);
        saveAuthen2DB(userAuthenDto, result.isSuccess() ?  2 : 1, 1);
        return Message.createBySuccess(result.isSuccess() ? successContent : failContent);
    }

    /**
     * @Author hmr
     * @Description 保存认证记录
     * @Date: 2020/5/14 12:26
     * @param userAuthenDto
     * @param result
     * @param type
     * @reture: void
     **/
    private void saveAuthen2DB(UserAuthenDto userAuthenDto, Integer result, Integer type){
        String inputNum = userAuthenDto.getInputCardNo();
        String inputName = userAuthenDto.getInputName();
        String picUri = userAuthenDto.getFileUri();
        Long userId = userAuthenDto.getUserId();
        UserAuthenRecordInfo recordInfo = new UserAuthenRecordInfo();
        //认证成功
        if(result == 2){
            UserAuthenResultDto resultDto = new UserAuthenResultDto();
            resultDto.setUserId(userId);
            resultDto.setCertificateNum(inputNum);
            resultDto.setRealName(inputName);
            userServiceFeignApi.userAuthenSuccess(resultDto);
            //实名认证的角色id固定为1L
            UserRoleRel userRoleRel = new UserRoleRel();
            userRoleRel.setRoleId(1L);
            userRoleRel.setStaffId(userId);
            userRoleRelService.insertEntry(userRoleRel);

            recordInfo.setState(2);

            UserAuthenInfo userAuthenInfo = new UserAuthenInfo();
            userAuthenInfo.setType(type);
            userAuthenInfo.setRealName(inputName);
            userAuthenInfo.setCertificateNum(inputNum);
            userAuthenInfo.setUserId(userId);
            userAuthenInfoService.insertEntry(userAuthenInfo);
        }


        /*SendAuthenRecordDto condition = new SendAuthenRecordDto();
        condition.setInputName(inputName);
        condition.setPicUri(picUri);
        condition.setUserId(userId);
        condition.setInputNum(inputNum);
        condition.setResult(result);
        condition.setAuthenType(type);
        generateSendMessageService.sendToMq(condition);*/
        recordInfo.setUserId(userId);
        recordInfo.setInputCardNo(inputNum);
        recordInfo.setInputName(inputName);
        recordInfo.setType(type);
        recordInfo.setPicuri(picUri);
        userAuthenRecordInfoService.insertEntry(recordInfo);


        //发送到消息中心
        SendUserCenterDto sendUserCenterDto = new SendUserCenterDto();
        sendUserCenterDto.setContent(result == 2 ? successContent : failContent);
        sendUserCenterDto.setNewsType(70);
        sendUserCenterDto.setFromId(-1L);
        sendUserCenterDto.setDate(DateUtil.now());
        sendUserCenterDto.setToId(userId);
        generateSendMessageService.sendToMq(sendUserCenterDto);
    }
}
