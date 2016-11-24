package com.ibm.tiantian;

import org.openqa.selenium.By;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ibm.util.SnapshotUtil;

public class TiantianSale {
	public static String sale(WebDriver driver,final String fundCode,String transPass){
		// 跳转到卖出列表页面
		driver.get("https://trade6.1234567.com.cn/FundtradePage/redemption?fc=" + fundCode);
		// 等待加载
		try {
			(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver d) {
					return d.findElement(By.id("ctl00_body_rpList_ctl00_btnSp1")) != null;
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
		// 跳转到填写卖出信息
		driver.findElement(By.id("ctl00_body_rpList_ctl00_btnSp1")).click();
		// 卖出截图
		SnapshotUtil.snapshot((TakesScreenshot) driver, "./", "sale1.png");
		// 等待加载
		try {
			(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver d) {
					return d.findElement(By.id("ctl00_body_p2llshare")) != null;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			return "加载购买基金页面失败！";
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String fundMsg  = null ;
		try{
			fundMsg = driver.findElements(By.className("fw")).get(0).getText().split("\\[")[0];
		}catch(Exception e){
			e.printStackTrace();
		}
		if(fundMsg != null && fundMsg.indexOf(fundCode) > 0){
			// 选择资金到活期宝
			driver.findElement(By.id("ctl00_body_rblBanks_0")).click();
			// 普通（T+3日确认后可用）
			driver.findElement(By.id("common")).click();
			// 极速（T+1日确认后可用）
			driver.findElement(By.id("ctl00_body_faster")).click();
			// 卖掉全部份额
			driver.findElement(By.id("allinput")).click();
			try {
				//等待5秒 js加载
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 提交跳转页面
			driver.findElement(By.id("ctl00_body_btnSp2")).click();
			// 卖出截图
			SnapshotUtil.snapshot((TakesScreenshot) driver, "./", "sale2.png");
		}else{
			return "交易基金不正确！";
		}
		// 等待加载
		try {
			(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver d) {
					if(d.findElements(By.className("fw")) == null || d.findElements(By.className("fw")).get(0) == null){
						return false;
					}
					String fundsName = d.findElements(By.className("fw")).get(0).getText();
					return fundsName.indexOf(fundCode) > 0;
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
		driver.findElement(By.id("ctl00_body_txtPaypwd")).sendKeys(transPass);
		driver.findElement(By.id("ctl00_body_btnSp3")).click();
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
