package com.ibm.service;

import java.util.Map;

import org.openqa.selenium.WebDriver;

public interface ITiantianTranService {

	/**
	 * 登录天天基金网
	 * @param driver
	 * @param loginName
	 * @param loginPass
	 * @return
	 */
	public Map<String,Object> login(WebDriver driver,String loginName,String loginPass);
	/**
	 * 查询持仓基金列表
	 * @param driver
	 * @return
	 */
	public Map<String,Object> hold(WebDriver driver);
	/**
	 * 查询账户（资产）
	 * @param driver
	 * @return
	 */
	public Map<String,Object> account(WebDriver driver);
	/**
	 * 买基金（每次一只）
	 * @param driver
	 * @param money
	 * @param fundCode
	 * @param tarnsPass
	 * @return
	 */
	public Map<String,Object> buyjj(WebDriver driver,String money,final String fundCode,String tarnsPass);
	/**
	 * 卖基金（每次一只）
	 * @param driver
	 * @param fundCode
	 * @param transPass
	 * @return
	 */
	public Map<String,Object> salejj(WebDriver driver,final String fundCode,String transPass);
}
