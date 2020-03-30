package top.imuster.auth.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.imuster.auth.exception.CustomSecurityException;
import top.imuster.auth.service.UserAuthenService;
import top.imuster.auth.utils.Base64Util;
import top.imuster.auth.utils.HttpUtil;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.dto.SendAuthenRecordDto;
import top.imuster.common.core.utils.GenerateSendMessageService;
import top.imuster.file.api.service.FileServiceFeignApi;
import top.imuster.user.api.service.UserServiceFeignApi;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: UserAuthenServiceImpl
 * @Description: UserAuthenServiceImpl
 * @author: hmr
 * @date: 2020/3/27 15:07
 */
@Slf4j
@Service("userAuthenService")
public class UserAuthenServiceImpl implements UserAuthenService {

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

    @Autowired
    private GenerateSendMessageService generateSendMessageService;

    @Autowired
    FileServiceFeignApi fileServiceFeignApi;

    @Autowired
    UserServiceFeignApi userServiceFeignApi;

    public Message<String> realNameAuthentication(Long userId, MultipartFile file, String inputName) {
        boolean res = generate(file, inputName);
        if(!res){
            Message<String> upload = fileServiceFeignApi.upload(file);
            String fileUri = upload.getText();
            saveAuthen2DB(fileUri, inputName, userId);
            userServiceFeignApi.updateUserState(userId, 50);
        }else{
            //删除服务器上的身份证照片
            userServiceFeignApi.updateUserState(userId, 40);
        }
        return Message.createBySuccess();
    }

    private boolean generate(MultipartFile file, String inputName){
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
            requestObj.put("image", Base64.encodeBase64(file.getBytes()));
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
            if(inputName.equals(realName)){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public Message<String> oneCardSolution(MultipartFile file, String inputName, Long userId, String inputCardNo) throws Exception {
        if(file.isEmpty()) return Message.createByError("文件为空");
        byte[] bytes = file.getBytes();
        String imgStr = Base64Util.encode(bytes);
        String recogniseParams = new StringBuilder().append("templateSign=").append(bdTemplateSign).append("&image=").append(URLEncoder.encode(imgStr, "UTF-8")).toString();
        String result = HttpUtil.post(bdRecogniseUrl, bdAccessToken, recogniseParams);
        JSONObject out = JSON.parseObject(result);
        Integer errorCode = (Integer) out.get("error_code");
        if(errorCode != 0){
            //验证失败
            saveAuthen2DB(file, inputName, userId);
            return Message.createBySuccess("图片验证失败,AI无法识别图片中的信息,您可以选择等待管理员审核或者重新上传清晰的图片");
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
        if(!name.equals(inputName)){
            saveAuthen2DB(file, inputName, userId);
            return Message.createByError(new StringBuilder().append("图片验证失败,AI识别的姓名为").append(name).append(",请检查输入或者更换清晰的图片").toString());
        }
        return Message.createBySuccess();
    }

    /**
     * @Author hmr
     * @Description 使用AI认证失败之后将信息保存到数据库，通过人工识别
     * @Date: 2020/3/29 15:45
     * @param file
     * @param inputName
     * @param userId
     * @reture: void
     **/
    private void saveAuthen2DB(MultipartFile file, String inputName, Long userId){
        Message<String> upload = fileServiceFeignApi.upload(file);
        if(upload.getCode() != 200){
            log.error("上传用户认证的一卡通图片到服务器失败");
            throw new CustomSecurityException();
        }
        String fileUri = upload.getText();
        saveAuthen2DB(fileUri, inputName, userId);
    }

    /**
     * @Author hmr
     * @Description 重载方法，直接存储图片的uri
     * @Date: 2020/3/29 11:22
     * @param picUri
     * @param inputName
     * @param userId
     * @reture: void
     **/
    private void saveAuthen2DB(String picUri, String inputName, Long userId){
        SendAuthenRecordDto condition = new SendAuthenRecordDto();
        condition.setInputName(inputName);
        condition.setPicUri(picUri);
        condition.setUserId(userId);
        generateSendMessageService.sendToMq(condition);
    }
}
