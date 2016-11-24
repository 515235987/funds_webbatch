package com.ibm.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ibm.service.IFundCrawlerService;

public class FundCrawlerServiceIMPL implements IFundCrawlerService {
	private static String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.94 Safari/537.36";
	private static String charSet = "utf-8";

	@Override
	public Map<String, Object> fundsEveryDayCrawler(String URL) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<>();
		CloseableHttpClient httpclient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();// 设置请求和传输超时时间
		CloseableHttpResponse response = null;
		HttpGet getEntity = new HttpGet(URL + System.currentTimeMillis());
		getEntity.setConfig(requestConfig);
		getEntity.addHeader("User-Agent", userAgent);
		try {
			response = httpclient.execute(getEntity);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				result.put("status", true);
				String html = EntityUtils.toString(response.getEntity(), charSet);
				String string = html.split(",datas:")[1].split(",count:")[0];
				String dateStr = html.split(",showday:")[1].split("}")[0];
				JSONArray dateJson = JSON.parseArray(dateStr);
				JSONArray jsonArr = JSON.parseArray(string);
				jsonArr.getJSONArray(0);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				List<String> resultList = new ArrayList<>();
				for (int i = 0; i < jsonArr.size(); i++) {
					StringBuffer sb = new StringBuffer();
					JSONArray jsonSubarr = jsonArr.getJSONArray(i);
					sb.append("\"");
					sb.append("1" + jsonSubarr.getString(0));
					sb.append("\"");
					sb.append(",");
					sb.append("\"");
					try {
						sb.append(String.valueOf(sdf.parse(dateJson.getString(0).replaceAll("\"", "")).getTime()));
					} catch (java.text.ParseException e) {
						// TODO Auto-generated catch block
						sb.append("0");
					}
					sb.append("\"");
					sb.append(",");
					sb.append("\"");
					sb.append(jsonSubarr.getString(3));
					sb.append("\"");
					sb.append(",");
					sb.append("\"");
					sb.append(jsonSubarr.getString(4));
					sb.append("\"");
					sb.append(",");
					sb.append("\"");
					sb.append(jsonSubarr.getString(7));
					sb.append("\"");
					sb.append(",");
					sb.append("\"");
					sb.append(jsonSubarr.getString(8));
					sb.append("\"");
					sb.append(",");
					sb.append("\"");
					sb.append(jsonSubarr.getString(9));
					sb.append("\"");
					sb.append(",");
					sb.append("\"");
					sb.append(jsonSubarr.getString(10));
					sb.append("\"");
					resultList.add(sb.toString());
				}
				result.put("result", resultList);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("status", false);
			result.put("message", e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("status", false);
			result.put("message", e.getMessage());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("status", false);
			result.put("message", e.getMessage());
		}
		return result;
	}

	@Override
	public Map<String, Object> fundsHistoryCrawler(String URL, String fundCode, String daily) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<>();
		CloseableHttpClient httpclient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();// 设置请求和传输超时时间
		CloseableHttpResponse response = null;
		try {
			Thread.sleep((long)Math.random() * 8000);
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		HttpGet getEntity = new HttpGet(URL + fundCode + "&page=1&per=" + daily);
		getEntity.setConfig(requestConfig);
		getEntity.addHeader("User-Agent", userAgent);
		try {
			response = httpclient.execute(getEntity);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				result.put("status", true);
				boolean flag = false;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				try {
					sdf.parse(daily);
				} catch (java.text.ParseException e1) {
					flag = true;
				}
				String html = EntityUtils.toString(response.getEntity(), charSet);
				String string = html.split("content:\"")[1].split("\",records:")[0];

				// 使用后HTML Parser 控件

				Parser myParser;

				NodeList nodeList = null;

				myParser = Parser.createParser(string, "utf-8");

				NodeFilter tableFilter = new NodeClassFilter(TableTag.class);

				OrFilter lastFilter = new OrFilter();

				lastFilter.setPredicates(new NodeFilter[] { tableFilter });

				// 获取标签为table的节点列表

				nodeList = myParser.parse(lastFilter);
				
				List<String> resultList = new ArrayList<>();

				// 循环读取每个table

				for (int i = 0; i <= nodeList.size(); i++) {

					if (nodeList.elementAt(i) instanceof TableTag) {

						TableTag tag = (TableTag) nodeList.elementAt(i);

						TableRow[] rows = tag.getRows();

						// 循环读取每一行

						boolean dailyFlag = false;
						for (int j = 0; j < rows.length; j++) {

							TableRow tr = (TableRow) rows[j];
							TableColumn[] td = tr.getColumns();
							// 读取每行的单元格内容
							if (!"".equals(tr)) {
								StringBuffer sb = new StringBuffer();
								if(td.length < 5){
									continue;
								}
								sb.append("\"");
								sb.append("1"+fundCode);
								sb.append("\"");
								sb.append(",");
								for (int k = 0; k < td.length; k++) {
									if(k==0){
										try {
											if(!flag && daily.equals(td[k].getStringText())){
												dailyFlag = true;
											}
											Long time = sdf.parse(td[k].getStringText()).getTime();
											sb.append("\"");
											sb.append(time.toString());
											sb.append("\"");
										} catch (java.text.ParseException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
											sb.append("\"");
											sb.append("0");
											sb.append("\"");
										}
									}else if(k == 3){
										sb.append(",");
										sb.append("\"");
										sb.append(td[k].getStringText().replaceAll("%", ""));
										sb.append("\"");
									}else if(k > 5){
										continue;
									}else{
										sb.append(",");
										sb.append("\"");
										sb.append(td[k].getStringText());
										sb.append("\"");
									}
								}
								if(dailyFlag){
									resultList.add(sb.toString());
									dailyFlag = false;
								}else if(sb.length() > 0 && flag){
									resultList.add(sb.toString());
								}
							}
						}
					}

				}
				result.put("result", resultList);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("status", false);
			result.put("message", e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("status", false);
			result.put("message", e.getMessage());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("status", false);
			result.put("message", e.getMessage());
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("status", false);
			result.put("message", e.getMessage());
		}
		return result;
	}

}
