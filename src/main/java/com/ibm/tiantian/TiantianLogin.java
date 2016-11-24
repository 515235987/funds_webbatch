package com.ibm.tiantian;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ibm.util.SnapshotUtil;

/**
 * 登录天天基金网
 * @author Admin
 *
 */
public class TiantianLogin {

	public static Map<String,Object> login(WebDriver driver,String loginName,String loginPass){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		// 登录天天基金网
		driver.get("https://login.1234567.com.cn/login");
		try {
			(new WebDriverWait(driver, 20)).until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver d) {
					return d.findElement(By.id("tbname")) != null;
				}
			});
		} catch (Exception e) {
			resultMap.put("status", false);
			resultMap.put("message", "无法打开登录页面！");
			e.printStackTrace();
			return resultMap;
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebElement tbname = driver.findElement(By.id("tbname"));
		WebElement tbpwd = driver.findElement(By.id("tbpwd"));
		tbname.sendKeys(loginName);
		tbpwd.sendKeys(loginPass);
		List<WebElement> bottons = driver.findElements(By.className("submit"));
		bottons.get(0).click();
		// 登录截图
		SnapshotUtil.snapshot((TakesScreenshot) driver, "./", "login.png");
		// 等待登录成功
		try{
			(new WebDriverWait(driver, 20)).until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver d) {
					return !d.findElement(By.id("all_value")).getText().equals("--");
				}
			});
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 总额
			resultMap.put("total", driver.findElement(By.id("all_value")).getText());
			// 可用余额 
			resultMap.put("balance", driver.findElement(By.id("TiantianCashBag_value")).getText());
			// 基金总值
			resultMap.put("fundsTotal", driver.findElement(By.id("fundProductTotalAsset")).getText());
			// 持仓收益
			resultMap.put("fundsIncome", driver.findElement(By.id("fundProductTotalAssetProfit")).getText());
			
			resultMap.put("status", true);
			resultMap.put("message", "登录成功！");
		}catch(Exception e){
			resultMap.put("status", false);
			resultMap.put("message", "登录失败！");
			e.printStackTrace();
		}
		return resultMap;
	}
}
