package top.imuster.auth.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.imuster.auth.service.UserAuthenRecordInfoService;
import top.imuster.auth.utils.HttpUtils;
import top.imuster.common.base.wrapper.Message;
import top.imuster.file.api.service.FileServiceFeignApi;
import top.imuster.security.pojo.UserAuthenRecordInfo;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: UserAuthenService
 * @Description: UserAuthenService
 * @author: hmr
 * @date: 2020/3/27 15:07
 */
@Slf4j
@Service("userAuthenService")
public class UserAuthenService {

    @Value("aliyun.realNameAuth.host")
    private String host;

    @Value("aliyun.realNameAuth.path")
    private String path;

    @Value("aliyun.realNameAuth.appCode")
    private String appCode;

    @Resource
    UserAuthenRecordInfoService userAuthenRecordInfoService;

    @Autowired
    FileServiceFeignApi fileServiceFeignApi;

    /**
     * @Author hmr
     * @Description 用户的实名认证
     * @Date: 2020/3/27 14:57
     * @param userId 当前用户
     * @param picUri 服务器中图片的uri
     * @param inputName 输入的名字
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    public Message<String> realNameAuthentication(Long userId, String picUri, String inputName) {
        boolean res = generate(picUri, inputName);
        if(!res){
            UserAuthenRecordInfo condition = new UserAuthenRecordInfo();
            condition.setUserId(userId);
            condition.setInputName(inputName);
            condition.setPicuri(picUri);
            condition.setType(2);
            userAuthenRecordInfoService.insertEntry(condition);
        }else{
            //删除服务器上的身份证照片
            fileServiceFeignApi.deleteByName(picUri);
        }
        return Message.createBySuccess();
    }

    private boolean generate(String picUri, String inputName){
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
           /* requestObj.put("image", imgBase64);
            if(config_str.length() > 0) {
                requestObj.put("configure", config_str);
            }*/
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String bodys = requestObj.toString();

        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
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


    /*
     * 获取参数的json对象
     */
    private static JSONObject getParam(int type, String dataValue) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("dataType", type);
            obj.put("dataValue", dataValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * @Author hmr
     * @Description 一卡通认证
     * @Date: 2020/3/27 16:03
     * @param picUri
     * @param inputName
     * @param userId
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    public Message<String> oneCardSolution(String picUri, String inputName, Long userId, String inputCardNo) {
        UserAuthenRecordInfo condition = new UserAuthenRecordInfo();
        condition.setPicuri(picUri);
        condition.setUserId(userId);
        condition.setInputCardNo(inputCardNo);
        condition.setInputName(inputName);
        userAuthenRecordInfoService.insertEntry(condition);
        return Message.createBySuccess();
    }
}
