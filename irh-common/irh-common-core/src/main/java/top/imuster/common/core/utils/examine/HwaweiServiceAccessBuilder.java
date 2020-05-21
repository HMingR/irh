package top.imuster.common.core.utils.examine;

import com.huawei.ais.common.AuthInfo;
import com.huawei.ais.common.ProxyHostInfo;
import com.huawei.ais.sdk.AisAccess;
import com.huawei.ais.sdk.AisAccessWithProxy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 此处为服务入口的构建函数，主要用于初始化Service Access的一些通用信息:
 * 
 * 包括 Endpoint(服务端点), Region(区域)，Access key(接入码) / Secret access key(安全接入码),
 * 以及http 请求相关超时参数
 *
 */
public class HwaweiServiceAccessBuilder {


	private static Map<String, String> endponitMap = new ConcurrentHashMap<>();
	static {
		/*  内容审核服务的区域和终端节点信息可以从如下地址查询
		 *  http://developer.huaweicloud.com/dev/endpoint
		 * */
		endponitMap.put("cn-north-1", "https://moderation.cn-north-1.myhuaweicloud.com");
		endponitMap.put("cn-north-4", "https://moderation.cn-north-4.myhuaweicloud.com");
		endponitMap.put("ap-southeast-1", "https://moderation.ap-southeast-1.myhuaweicloud.com");
		endponitMap.put("cn-east-3", "https://moderation.cn-east-3.myhuaweicloud.com");
	}

	private String region;

	private String endpoint;

	private String ak;

	private String sk;

	private ProxyHostInfo proxy = null;

	private int connectionTimeout = 5000;

	private int connectionRequestTimeout = 1000;

	private int socketTimeout = 5000;

	private int retryTimes = 3;

	public static HwaweiServiceAccessBuilder builder() {
		return new HwaweiServiceAccessBuilder();
	}

	public AisAccess build() {
		if (proxy == null) {
			return new AisAccess(new AuthInfo(endpoint, region, ak, sk), connectionTimeout, connectionRequestTimeout, socketTimeout, retryTimes);
		} else {
			return new AisAccessWithProxy(new AuthInfo(endpoint, region, ak, sk), proxy, connectionTimeout, connectionRequestTimeout, socketTimeout, retryTimes);
		}
	}

	public  HwaweiServiceAccessBuilder ak(String ak) {
		this.ak = ak;
		return this;
	}

	public  HwaweiServiceAccessBuilder sk(String sk) {
		this.sk = sk;
		return this;
	}

	public  HwaweiServiceAccessBuilder region(String region) {
		this.region = region;
		this.endpoint = getCurrentEndpoint(region);
		return this;
	}

	public  HwaweiServiceAccessBuilder proxy(ProxyHostInfo proxy) {
		this.proxy = proxy;
		return this;
	}

	public HwaweiServiceAccessBuilder connectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
		return this;
	}

	public HwaweiServiceAccessBuilder connectionRequestTimeout(int connectionRequestTimeout) {
		this.connectionRequestTimeout = connectionRequestTimeout;
		return this;
	}

	public HwaweiServiceAccessBuilder socketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
		return this;
	}

	public HwaweiServiceAccessBuilder retryTimes(int retryTimes) {
		this.retryTimes = retryTimes;
		return this;
	}

	/**
	 * 用于支持使用代理模式访问网络， 此时使用的代理主机配置信息
	 */
	public static ProxyHostInfo getProxyHost() {

		return new ProxyHostInfo("proxycn2.***.com", /* 代理主机信息 */
				8080,        /* 代理主机的端口 */
				"china/***", /* 代理的用户名 */
				"***"        /* 代理用户对应的密码 */
				);
	}

	/**
	 * 用于根据服务的区域信息获取服务域名
	 */
	public static String getCurrentEndpoint(String region){
		return endponitMap.get(region);
	}


}
