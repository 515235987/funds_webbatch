package com.ibm.service;

import java.util.Map;

public interface IFundCrawlerService {
	
	public Map<String,Object> fundsEveryDayCrawler(String URL);

	public Map<String,Object> fundsHistoryCrawler(String URL,String fundCode,String daily);
}
