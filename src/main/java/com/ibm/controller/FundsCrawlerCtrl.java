package com.ibm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibm.service.impl.FundCrawlerServiceIMPL;
import com.ibm.service.impl.FundsServiceIMPL;

@Controller
@RequestMapping(value="/funds_crawler")
public class FundsCrawlerCtrl {
	
	@Autowired
	private FundCrawlerServiceIMPL fundCrawlerService;
	
	@Autowired
	private FundsServiceIMPL fundsService;
	
	@RequestMapping(value="/everyDayCrawler",method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> everyDayCrawler(@RequestBody HashMap<String,Object> reqMap){
		Map<String,Object> respMap = new HashMap<>();
		respMap = fundCrawlerService.fundsEveryDayCrawler("http://fund.eastmoney.com/Data/Fund_JJJZ_Data.aspx?t=1&lx=1&letter=&gsid=&text=&sort=bzdm,asc&page=1,9999&dt=");
		System.out.println(respMap.get("result"));
		return respMap;
	}
	
	@RequestMapping(value="/historyCrawler",method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> historyCrawler(@RequestBody HashMap<String,Object> reqMap){
		Map<String,Object> respMap = new HashMap<>();
		if(!reqMap.containsKey("dateTime")){
			respMap.put("status", false);
			respMap.put("message", "参数不足！");
			return respMap;
		}
		List<String> fundsLst = fundsService.getFundCode();
		List<String> result = new ArrayList<String>();
//		int i = 0;
		for(String fundCode : fundsLst){
			Map<String,Object> tempResult = fundCrawlerService.fundsHistoryCrawler("http://fund.eastmoney.com/f10/F10DataApi.aspx?type=lsjz&code=", fundCode.substring(1), (String)reqMap.get("dateTime"));
			if(tempResult.containsKey("status") && (boolean)tempResult.get("status")){
				result.addAll((List<String>)tempResult.get("result"));
			}
//			if(i>2){
//				break;
//			}
//			i++;
		}
		Integer count = fundsService.addHistoryFunds(result);
		respMap.put("status", true);
		respMap.put("result", result);
		respMap.put("count", count.toString());
		return respMap;
	}
}
