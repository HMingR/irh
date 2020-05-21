package top.imuster.common.core.utils.examine;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.huawei.ais.sdk.AisAccess;
import com.huawei.ais.sdk.util.HttpClientUtils;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.springframework.stereotype.Component;
import top.imuster.common.core.dto.ExamineResultDto;

import java.io.IOException;

/**
 *  文本内容检测服务的使用示例类
 */
@Component
public class HuaweiModerationTextContentUtil {

	private AisAccess service;

	public HuaweiModerationTextContentUtil(){

		// 1. 配置好访问文本内容检测服务的基本信息,生成对应的一个客户端连接对象
		service = HwaweiServiceAccessBuilder.builder()
				.ak("Q5PZO3ODORLPOCUMB26C")                       // your ak
				.sk("vEF6RtvBcakWb2Q4wPFXqBItzBZ5tobTvnRdbIaB")                       // your sk
				.region("cn-north-4")               // 内容审核服务目前支持华北-北京(cn-north-4)、华东上海一(cn-east-3)以及亚太-香港(ap-southeast-1)
				.connectionTimeout(5000)            // 连接目标url超时限制
				.connectionRequestTimeout(1000)     // 连接池获取可用连接超时限制
				.socketTimeout(20000)               // 获取服务器响应数据超时限制
				.retryTimes(3)                      // 请求异常时的重试次数
				.build();
	}

	public ExamineResultDto moderationTextContent(String content) throws IOException {

		try {
			//
			// 2.构建访问文本内容检测服务需要的参数
			//
			String uri = "/v1.0/moderation/text";

			// api请求参数说明可参考: https://support.huaweicloud.com/api-moderation/moderation_03_0018.html
			JSONObject json = new JSONObject();
			// 注：检测场景支持默认场景和自定义词库场景
			// 自定义词库配置使用可参考:https://support.huaweicloud.com/api-moderation/moderation_03_0027.html
			json.put("categories", new String[] {"porn","politics", "ad", "abuse", "contraband", "flood"});

			JSONObject text = new JSONObject();
			text.put("text", content);
			text.put("type", "content");
			
			JSONArray items = new JSONArray();
			items.add(text);
			
			json.put("items", items);
			
			StringEntity stringEntity = new StringEntity(json.toJSONString(), "utf-8");

			// 3.传入文本内容检测服务对应的uri参数, 传入文本内容检测服务需要的参数，
			// 该参数主要通过JSON对象的方式传入, 使用POST方法调用服务
			HttpResponse response = service.post(uri, stringEntity);

			// 4.验证服务调用返回的状态是否成功，如果为200, 为成功, 否则失败。

			int statusCode = response.getStatusLine().getStatusCode();
			if(statusCode != 200) return null;


			// 5.处理服务返回的字符流，输出识别结果。
			JSONObject jsonObject = JSON.parseObject(HttpClientUtils.convertStreamToString(response.getEntity().getContent()));
			JSONObject result = jsonObject.getJSONObject("result" );
			String res = (String)result.get("suggestion");
			if("block".equalsIgnoreCase(res)){
				String str = JSON.toJSONString(result, SerializerFeature.PrettyFormat);
				ExamineResultDto examineResultDto = JSONObject.parseObject(str, ExamineResultDto.class);
				return examineResultDto;
			}else {
				ExamineResultDto examineResultDto = new ExamineResultDto();
				examineResultDto.setSuggestion("pass");
				return examineResultDto;
			}
		} catch (Exception e) {
			return null;
		} finally {
			// 6.使用完毕，关闭服务的客户端连接
			service.close();
		}
	}
}
