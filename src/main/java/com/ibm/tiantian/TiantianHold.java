package com.ibm.tiantian;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TiantianHold {

	public static List<String> hold(WebDriver driver){
		// 持仓明细
		driver.get("https://trade6.1234567.com.cn/MyAssets/hold?spm=L");
		// 等待加载
		try{
			(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver d) {
					return d.findElement(By.id("XjbAssetValue")) != null;
				}
			});
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<String> result = new ArrayList<String>();
		List<WebElement> tableList = driver.findElements(By.className("table-hold")).get(0)
				.findElements(By.tagName("tbody")).get(0).findElements(By.tagName("tr"));
		
		for (WebElement tr : tableList) {
			StringBuffer sbr = new StringBuffer();
			List<WebElement> tds = tr.findElements(By.tagName("td"));
			for (int i = 0; i < tds.size(); i++) {
				// System.out.println(tds.get(i).getText());
				if (i == 0) {
					String fundCode = tds.get(i).getText().split("（")[1].split("）")[0];
					sbr.append(fundCode);
				} else if (i == 1) {
					sbr.append(",");
					sbr.append(tds.get(i).getText());
				} else if (i == 2) {
					String[] yingkui = tds.get(i).getText().split("\\n");
					sbr.append(",");
					sbr.append(yingkui[0]);
					sbr.append(",");
					sbr.append(yingkui[1]);
				}
			}
			result.add(sbr.toString());
		}
		if(result.size() > 0){
			return result;
		}
		return null;
	}
}
