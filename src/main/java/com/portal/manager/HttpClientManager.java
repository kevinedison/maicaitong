package com.portal.manager;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 
 * httpclient的通用封装，httpClientUtil是一个单例，在这个单例中控制超时时间以及网络调用的线程池
 * 
 * example: get: HttpGet httpGet = HttpClientUtil.getGetRequest();
 * httpGet.setURI(new URI("")); httpGet.setHeader("user_id", "");
 * httpGet.setHeader("user_key", ""); httpGet.setHeader("baseid", "");
 * httpGet.setHeader("client_sys_name", ""); String codeStr =
 * HttpClientUtil.executeAndGet(httpGet);
 * 
 * post: HttpPost httpPost = HttpClientUtil.getPostRequest();
 * httpPost.setURI(new URI("")); httpPost.setHeader("Accept",
 * "application/json"); httpPost.setHeader("user_id", "");
 * httpPost.setHeader("user_key", ""); httpPost.setHeader("baseid", "");
 * httpPost.setHeader("client_sys_name", ""); String json =
 * SerializationUtil.gson2String(null); httpPost.setEntity(new
 * StringEntity(json, ContentType.APPLICATION_JSON)); String resJsonStr =
 * HttpClientUtil.executeAndGet(httpPost); Type type = new
 * TypeToken<GeneralModel<Integer>>() {}.getType(); GeneralModel<Integer> model
 * = SerializationUtil.gson2Object(resJsonStr, type);
 *
 *
 * upload HttpPost httppost = new HttpPost(url); FileBody bin = new FileBody(new
 * File(filepath + File.separator + filename1)); comment = new
 * StringBody(filename1); MultipartEntity reqEntity = new MultipartEntity();
 * reqEntity.addPart("file1", bin);//file1为请求后台的File upload;属性
 * reqEntity.addPart("filename1", comment);//filename1为请求后台的普通参数;属性
 * httppost.setEntity(reqEntity); HttpClientUtil.executeAndGet(httppost);
 * 
 */

@Component
@Scope("singleton")
public class HttpClientManager {

	private CloseableHttpClient httpClient;

	private final int TIMEOUT_SECONDS = 60;

	private final int POOL_SIZE = 60;

	private static Logger LOGGER = LoggerFactory.getLogger(HttpClientManager.class);

	@PostConstruct
	public void init() {
		initApacheHttpClient();
	}

	@PreDestroy
	public void destroy() {
		destroyApacheHttpClient();
	}

	// 创建包含connection pool与超时设置的client
	private void initApacheHttpClient() {
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(TIMEOUT_SECONDS * 1000)
				.setConnectTimeout(TIMEOUT_SECONDS * 1000).setConnectionRequestTimeout(TIMEOUT_SECONDS * 1000)
				.setStaleConnectionCheckEnabled(true).build();

		httpClient = HttpClientBuilder.create().setMaxConnTotal(POOL_SIZE).setMaxConnPerRoute(POOL_SIZE)
				.setDefaultRequestConfig(requestConfig).build();

	}

	private void destroyApacheHttpClient() {
		try {
			if (httpClient != null) {
				httpClient.close();
				httpClient = null;
			}
		} catch (IOException e) {
			LOGGER.error("httpclient close fail", e);
		}
	}

	public static HttpGet getGetRequest() {
		return new HttpGet();
	}

	public static HttpPost getPostRequest() {
		return new HttpPost();
	}

	public static HttpPut getPutRequest() {
		return new HttpPut();
	}

	public static HttpDelete getDeleteRequest() {
		return new HttpDelete();
	}

	public String executeAndGet(HttpRequestBase httpRequestBase) throws Exception {
		HttpResponse response;

		String entiStr = "";
		Long start = System.currentTimeMillis();

		try {
			response = httpClient.execute(httpRequestBase);

			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				LOGGER.error("request url：" + httpRequestBase.getURI() + ", method：" + httpRequestBase.getMethod()
						+ ",status code = " + response.getStatusLine().getStatusCode());
				throw new Exception("response status code : " + response.getStatusLine().getStatusCode());
			} else {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					entiStr = EntityUtils.toString(entity, Consts.UTF_8);
				} else {
					throw new Exception("response entity is null");
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			Long end = System.currentTimeMillis();
			LOGGER.info("url:" + httpRequestBase.getURI().toString() + ",cost time:" + (end - start) / 1000 + "s");
		}
		return entiStr;
	}

}