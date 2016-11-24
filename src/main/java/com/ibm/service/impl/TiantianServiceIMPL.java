package com.ibm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ibm.service.ITiantianTranService;
import com.ibm.util.SnapshotUtil;

public class TiantianServiceIMPL implements ITiantianTranService {

	@Override
	public Map<String, Object> login(WebDriver driver, String loginName, String loginPass) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
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
		try {
			(new WebDriverWait(driver, 20)).until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver d) {
					return !d.findElement(By.id("all_value")).getText().equals("--");
				}
			});
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
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
		} catch (Exception e) {
			resultMap.put("status", false);
			resultMap.put("message", "登录失败！");
			e.printStackTrace();
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> hold(WebDriver driver) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 持仓明细
		driver.get("https://trade6.1234567.com.cn/MyAssets/hold?spm=L");
		// 等待加载
		try {
			(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver d) {
					return d.findElement(By.id("XjbAssetValue")) != null;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", false);
			resultMap.put("message", e.getMessage());
			return resultMap;
		}
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
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
		if (result.size() > 0) {
			resultMap.put("status", true);
			resultMap.put("message", "获取成功！");
			resultMap.put("body", result);
			return resultMap;
		}
		resultMap.put("status", false);
		resultMap.put("message", "获取失败！");
		resultMap.put("body", result);
		return resultMap;
	}

	@Override
	public Map<String, Object> account(WebDriver driver) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 持仓明细
		driver.get("https://trade6.1234567.com.cn/MyAssets/default?spm=L");
		try {
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
		} catch (Exception e) {
			resultMap.put("status", false);
			resultMap.put("message", e.getMessage());
			e.printStackTrace();
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> buyjj(WebDriver driver, String money, String fundCode, String tarnsPass) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
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
			resultMap.put("status", false);
			resultMap.put("message", e.getMessage());
			return resultMap;
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
		// driver.findElement(By.id("ctl00_body_rblBanks_0")).click();
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
			resultMap.put("status", false);
			resultMap.put("message", e.getMessage());
			return resultMap;
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
					if (d.findElements(By.className("h1")) != null) {
						return d.findElements(By.className("h1")).size() > 0;
					} else {
						return false;
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", false);
			resultMap.put("message", e.getMessage());
			return resultMap;
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String result = driver.findElements(By.className("h1")).get(0).getText();
		if ("申请受理成功".equals(result)) {
			resultMap.put("status", true);
			resultMap.put("message", result);
		} else {
			resultMap.put("status", false);
			resultMap.put("message", result);
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> salejj(WebDriver driver, String fundCode, String transPass) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
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
			resultMap.put("status", false);
			resultMap.put("message", e.getMessage());
			return resultMap;
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
			resultMap.put("status", false);
			resultMap.put("message", e.getMessage());
			return resultMap;
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String fundMsg = null;
		try {
			fundMsg = driver.findElements(By.className("fw")).get(0).getText().split("\\[")[0];
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (fundMsg != null && fundMsg.indexOf(fundCode) > 0) {
			// 选择资金到活期宝
			driver.findElement(By.id("ctl00_body_rblBanks_0")).click();
			// 普通（T+3日确认后可用）
			driver.findElement(By.id("common")).click();
			// 极速（T+1日确认后可用）
			driver.findElement(By.id("ctl00_body_faster")).click();
			// 卖掉全部份额
			driver.findElement(By.id("allinput")).click();
			try {
				// 等待5秒 js加载
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 提交跳转页面
			driver.findElement(By.id("ctl00_body_btnSp2")).click();
			// 卖出截图
			SnapshotUtil.snapshot((TakesScreenshot) driver, "./", "sale2.png");
		} else {
			resultMap.put("status", false);
			resultMap.put("message", "交易不正确！");
			return resultMap;
		}
		// 等待加载
		try {
			(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver d) {
					if (d.findElements(By.className("fw")) == null
							|| d.findElements(By.className("fw")).get(0) == null) {
						return false;
					}
					String fundsName = d.findElements(By.className("fw")).get(0).getText();
					return fundsName.indexOf(fundCode) > 0;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", false);
			resultMap.put("message", e.getMessage());
			return resultMap;
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
					if (d.findElements(By.className("h1")) != null) {
						return d.findElements(By.className("h1")).size() > 0;
					} else {
						return false;
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", false);
			resultMap.put("message", e.getMessage());
			return resultMap;
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String result = driver.findElements(By.className("h1")).get(0).getText();
		if ("申请受理成功".equals(result)) {
			resultMap.put("status", true);
			resultMap.put("message", result);
		} else {
			resultMap.put("status", false);
			resultMap.put("message", result);
		}
		return resultMap;
	}

}
