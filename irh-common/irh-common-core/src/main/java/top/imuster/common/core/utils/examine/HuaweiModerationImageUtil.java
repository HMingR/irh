package top.imuster.common.core.utils.examine;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huawei.ais.sdk.AisAccess;
import com.huawei.ais.sdk.util.HttpClientUtils;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;

/**
 * @ClassName: HuaweiModerationImageUtil
 * @Description: 华为云的图片内容审核
 * @author: hmr
 * @date: 2020/5/21 9:55
 */
@Component
public class HuaweiModerationImageUtil {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private AisAccess service;

    public HuaweiModerationImageUtil() {

        // 1. 配置好访问图像内容批量检测服务的基本信息,生成对应的一个客户端连接对象
        service = HwaweiServiceAccessBuilder.builder()
                .ak("Q5PZO3ODORLPOCUMB26C")                       // your ak
                .sk("vEF6RtvBcakWb2Q4wPFXqBItzBZ5tobTvnRdbIaB")                       // your sk
                .region("cn-north-4")               // 内容审核服务目前支持华北-北京(cn-north-4)以及亚太-香港(ap-southeast-1)
                .connectionTimeout(5000)            // 连接目标url超时限制
                .connectionRequestTimeout(1000)     // 连接池获取可用连接超时限制
                .socketTimeout(20000)               // 获取服务器响应数据超时限制
                .retryTimes(3)                      // 请求异常时的重试次数
                .build();
    }

    public String imageContentBatchCheck(String[] urls) throws IOException {
        try {
            for (int i = 0; i < urls.length; i++) {
                urls[i] = "http://39.105.0.169:8080/" + urls[i];
            }

            //
            // 2.构建访问图像内容批量检测服务需要的参数
            //
            String uri = "/v1.0/moderation/image/batch";


            LinkedMultiValueMap<String, String> header = new LinkedMultiValueMap<>();

            //定义body
            LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("urls", urls);
            body.add("categories", new String[] {"politics", "terrorism", "porn", "ad"});
            body.add("threshold", 0);

            HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(body, header);

            JSONObject json = new JSONObject();

            // api请求参数说明可参考：https://support.huaweicloud.com/api-moderation/moderation_03_0036.html
            json.put("urls", urls);
            json.put("categories", new String[] {"politics", "terrorism", "porn", "ad"}); //检测内容
            json.put("threshold", 0);

            StringEntity stringEntity = new StringEntity(json.toJSONString(), "utf-8");

            // 3.传入图像内容批量检测服务对应的uri参数, 传入图像内容批量检测服务需要的参数，
            // 该参数主要通过JSON对象的方式传入, 使用POST方法调用服务
            HttpResponse response = service.post(uri, stringEntity);

            // 4.验证服务调用返回的状态是否成功，如果为200, 为成功, 否则失败。
            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode != 200) return "NULL";

            StringBuffer res = new StringBuffer();
            // 5.处理服务返回的字符流，输出识别结果。
            JSONObject jsonObject = JSONObject.parseObject(HttpClientUtils.convertStreamToString(response.getEntity().getContent()));
            JSONArray result = jsonObject.getJSONArray("result" );
            for(int i = 0; i < result.size(); i++){
                JSONObject jsonObject1 = result.getJSONObject(i);
                String suggestion = jsonObject1.getString("suggestion");
                if("block".equalsIgnoreCase(suggestion)){
                    res.append("第").append(i+1).append("张图片审核不通过; ");
                }
            }
            return res.toString();
        } catch (Exception e) {
            log.error("------->使用华为云检测图片出现异常,异常信息为{}",e.getMessage());
            return null;
        } finally {
            // 6.使用完毕，关闭服务的客户端连接
            service.close();
        }

    }

}
