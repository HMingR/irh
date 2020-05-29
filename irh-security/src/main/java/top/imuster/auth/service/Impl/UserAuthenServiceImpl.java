package top.imuster.auth.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.imuster.auth.exception.CustomSecurityException;
import top.imuster.auth.service.UserAuthenService;
import top.imuster.auth.service.UserRoleRelService;
import top.imuster.auth.utils.Base64Util;
import top.imuster.auth.utils.HttpUtil;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.dto.rabbitMq.SendAuthenRecordDto;
import top.imuster.common.core.dto.rabbitMq.SendUserCenterDto;
import top.imuster.common.core.utils.DateUtil;
import top.imuster.common.core.utils.GenerateSendMessageService;
import top.imuster.file.api.service.FileServiceFeignApi;
import top.imuster.security.api.dto.UserAuthenDto;
import top.imuster.security.api.pojo.UserRoleRel;
import top.imuster.user.api.service.UserServiceFeignApi;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: UserAuthenServiceImpl
 * @Description: UserAuthenServiceImpl
 * @author: hmr
 * @date: 2020/3/27 15:07
 */
@Service("userAuthenService")
public class UserAuthenServiceImpl implements UserAuthenService {
    protected  final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${aliyun.realNameAuth.host}")
    private String host;

    @Value("${aliyun.realNameAuth.path}")
    private String path;

    @Value("${aliyun.realNameAuth.appCode}")
    private String appCode;

    @Value("${baidu.accessToken}")
    private String bdAccessToken;

    @Value("${baidu.templateSign}")
    private String bdTemplateSign;

    @Value("${baidu.bdRecogniseUrl}")
    private String bdRecogniseUrl;

    @Value("${file.urlPrefix}")
    private String urlPrefix;

    @Autowired
    private GenerateSendMessageService generateSendMessageService;

    @Autowired
    FileServiceFeignApi fileServiceFeignApi;

    @Autowired
    UserServiceFeignApi userServiceFeignApi;

    @Resource
    UserRoleRelService userRoleRelService;


    private static final String failContent = "我们已经收到您提交的审核,但是由于图片不清晰导致AI无法快速识别,请等待管理员审核，感谢您的配合";
    private static final String successContent = "恭喜您已经成功通过实名认证,您现在可以使用irh平台全部功能,感谢您的配合";

    public Message<String> realNameAuthentication(UserAuthenDto userAuthenDto) {
        boolean res = generate(userAuthenDto);
        saveAuthen2DB(userAuthenDto, res ? 1 : 0,2);
        return Message.createBySuccess(res ? successContent : failContent);
    }

    private boolean generate(UserAuthenDto userAuthenDto){
        String inputCardNo = userAuthenDto.getInputCardNo();
        String inputName = userAuthenDto.getInputName();

        //请根据线上文档修改configure字段
        JSONObject configObj = new JSONObject();
        configObj.put("side", "face");
        String config_str = configObj.toString();

        String method = "POST";
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "APPCODE " + appCode);
        Map<String, String> querys = new HashMap<>();

        // 拼装请求body的json字符串
        JSONObject requestObj = new JSONObject();
        try {
            requestObj.put("image", Base64.encodeBase64(getFileByUri(userAuthenDto.getFileUri())));
            if(config_str.length() > 0) {
                requestObj.put("configure", config_str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String bodys = requestObj.toString();

        try {
            HttpResponse response = HttpUtil.doPost(host, path, method, headers, querys, bodys);
            int stat = response.getStatusLine().getStatusCode();
            if(stat != 200){
                log.error("------->根据身份证图片获得身份证信息失败,状态码为{},http header error msg为{},http body error msg为{}", stat, response.getFirstHeader("X-Ca-Error-Message"), EntityUtils.toString(response.getEntity()));
                return false;
            }
            String res = EntityUtils.toString(response.getEntity());
            JSONObject res_obj = JSON.parseObject(res);
            JSONArray outputArray = res_obj.getJSONArray("outputs");
            String output = outputArray.getJSONObject(0).getJSONObject("outputValue").getString("dataValue");
            JSONObject out = JSON.parseObject(output);
            Object realName = out.get("name");
            Object n = out.get("num");
            if(inputName.equals(realName) && inputCardNo.equals(n)){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    private boolean execOneCard(UserAuthenDto userAuthenDto){
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
            return false;
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
        String name = resMap.get("name");
        String id = resMap.get("idCard");
        if(!name.equals(inputName) || !id.equals(inputCardNo)){
            return false;
        }
        return true;
    }

    public Message<String> oneCardSolution(UserAuthenDto userAuthenDto){
        boolean result = execOneCard(userAuthenDto);
        saveAuthen2DB(userAuthenDto, result ?  1 : 0, 2);
        return Message.createBySuccess(result ? successContent : failContent);
    }

    /**
     * @Author hmr
     * @Description TODO
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

        if(result == 1){
            userServiceFeignApi.updateUserState(userId, 50);
            UserRoleRel userRoleRel = new UserRoleRel();
            userRoleRel.setRoleId(1L);
            userRoleRel.setStaffId(userId);
            userRoleRelService.insertEntry(userRoleRel);
        }

        SendAuthenRecordDto condition = new SendAuthenRecordDto();
        condition.setInputName(inputName);
        condition.setPicUri(picUri);
        condition.setUserId(userId);
        condition.setInputNum(inputNum);
        condition.setResult(result);
        condition.setAuthenType(type);
        generateSendMessageService.sendToMq(condition);

        //发送到消息中心
        SendUserCenterDto sendUserCenterDto = new SendUserCenterDto();
        sendUserCenterDto.setContent(result == 1 ? successContent : failContent);
        sendUserCenterDto.setNewsType(70);
        sendUserCenterDto.setFromId(-1L);
        sendUserCenterDto.setDate(DateUtil.now());
        sendUserCenterDto.setToId(userId);
        generateSendMessageService.sendToMq(sendUserCenterDto);
    }
}
