package com.ibm.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/test")
public class TestCtl {
	@RequestMapping(value="/sendTest",method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> sendTest(@RequestBody HashMap<String,Object> hashMap){
		System.out.println(hashMap.get("name"));
		return hashMap;
	}
}
