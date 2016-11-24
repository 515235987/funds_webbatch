package com.ibm.tiantian;

import org.openqa.selenium.By;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ibm.util.SnapshotUtil;

public class TiantianBuy {

	public static String buyFunds(WebDriver driver,String money,final String fundCode,String tarnsPass) {
		driver.get("https://trade6.1234567.com.cn/FundtradePage/default2.aspx?fc=" + fundCode);
		// 等待加载
		try {
			(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver d) {
					return d.findElement(By.id("jjmc")) != null;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			return "加载购买基金页面失败！";
		}
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 购入购买金额
		driver.findElement(By.id("ctl00_body_amount")).sendKeys(money);
		// 活期宝
		driver.findElement(By.id("ctl00_body_rblUsableBanks_0")).click();
		// 银行卡
//		driver.findElement(By.id("ctl00_body_rblBanks_0")).click();
		// 购买截图
		SnapshotUtil.snapshot((TakesScreenshot) driver, "./", "buy1.png");
		
		// 提交到确认页面
		driver.findElement(By.id("ctl00_body_btnSp1")).click();
		// 等待加载
		try {
			(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver d) {
					return d.findElement(By.id("ctl00_body_fundcode")).getText().equals(fundCode);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			return "加载购买基金确认页面失败！";
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 购买确认截图
		SnapshotUtil.snapshot((TakesScreenshot) driver, "./", "buy2.png");
		// 输入交易密码
		driver.findElement(By.id("ctl00_body_txtPaypwd")).sendKeys(tarnsPass);
		driver.findElement(By.id("ctl00_body_btnSp2")).click();
		
		// 等待加载
		try {
			(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver d) {
					if(d.findElements(By.className("h1")) != null){
						return d.findElements(By.className("h1")).size() > 0;
					}else{
						return false;
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			return "提交购买基金失败失败！";
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return driver.findElements(By.className("h1")).get(0).getText();
	}

}
