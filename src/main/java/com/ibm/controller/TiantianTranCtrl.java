package com.ibm.controller;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibm.service.impl.TiantianServiceIMPL;

@Controller
@RequestMapping(value="/tiantian")
public class TiantianTranCtrl {
	public static final String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36"
			+ " (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36";
	@Autowired
	private TiantianServiceIMPL tiantianService;
	
	
	@RequestMapping(value="/transation",method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> transation(@RequestBody HashMap<String,Object> reqMap){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(!reqMap.containsKey("loginName") || !reqMap.containsKey("loginPass")){
			resultMap.put("status", false);
			resultMap.put("message", "请加入用户名、密码！");
			return resultMap;
		}
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX + "userAgent", userAgent);
		WebDriver driver = new PhantomJSDriver(caps);
		resultMap = tiantianService.login(driver, (String) reqMap.get("loginName"), (String) reqMap.get("loginPass"));
		System.out.println(resultMap);
		resultMap = tiantianService.hold(driver);
		driver.close();
		return resultMap;
	}
}
