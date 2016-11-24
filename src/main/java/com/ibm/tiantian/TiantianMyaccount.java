package com.ibm.tiantian;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
/**
 * 查询账户余额
 * @author Admin
 *
 */
public class TiantianMyaccount {
	
	public static Map<String,Object> account(WebDriver driver){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		// 持仓明细
		driver.get("https://trade6.1234567.com.cn/MyAssets/default?spm=L");
		
		try{
			(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
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
			resultMap.put("message", "查询成功！");
		}catch(Exception e){
			resultMap.put("status", false);
			resultMap.put("message", "查询失败！");
			e.printStackTrace();
		}
		return resultMap;
	}
}
