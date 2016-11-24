package com.ibm.util;


import java.io.IOException;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpUtil {

	public static void postMethod(String url,String jsn) throws ClientProtocolException, IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		int status = 0;
		CloseableHttpResponse response;
		
		HttpPost postEntity = new HttpPost(url);
		postEntity.addHeader("Content-Type", "application/json");
		StringEntity se = new StringEntity(jsn,"utf-8");
		postEntity.setEntity(se);
		
		response = httpclient.execute(postEntity);
		status = response.getStatusLine().getStatusCode();
		System.out.println(status);
		System.out.println(response);
		
	}
	
	
	public static String getMethod(String url,String charSet){
		CloseableHttpClient httpclient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();//设置请求和传输超时时间
		CloseableHttpResponse response = null;
		HttpGet getEntity = new HttpGet(url);
		getEntity.setConfig(requestConfig);
		getEntity.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.94 Safari/537.36");
		try {
			response = httpclient.execute(getEntity);
			int statusCode = response.getStatusLine().getStatusCode();
			if(statusCode == 200){
				return EntityUtils.toString(response.getEntity(),charSet);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
}
