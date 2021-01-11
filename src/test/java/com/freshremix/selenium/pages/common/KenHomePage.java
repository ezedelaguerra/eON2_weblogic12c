package com.freshremix.selenium.pages.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.freshremix.common.test.SeleniumTestUtil;
import com.freshremix.selenium.pages.admin.CompanyMaintenancePage;
import com.freshremix.selenium.pages.admin.ProxyLoginPage;
import com.freshremix.selenium.pages.admin.TermsOfServiceMngtPage;

public class KenHomePage extends HomePage{
	
	private static final String PROXYLOGINBTN_CLASS = "app.caption.menubtnproxy";
	private static final String COMPANYBTN_CLASS = "app.caption.menubtncompany";
	private static final String TOSBTN_CLASS = "app.caption.menubtntos";

	
	public KenHomePage(WebDriver driverParam) {
		super(driverParam);
		SeleniumTestUtil.waitUntil(driver, By.className("app.caption.menubtntos"));
	}
	
	public WebElement getCompanyBTN()
	{
		return driver.findElement(By.className(COMPANYBTN_CLASS));
	}
	
	public WebElement getTOSBTN()
	{
		return driver.findElement(By.className(TOSBTN_CLASS));
	}

	public TermsOfServiceMngtPage loadTOSMngt() {
		getTOSBTN().click();
		return new TermsOfServiceMngtPage(driver);
	}
	
	public CompanyMaintenancePage loadCompanyMngt(){
		SeleniumTestUtil.waitUntil(driver, By.className(COMPANYBTN_CLASS));
		getCompanyBTN().click();
		return new CompanyMaintenancePage(driver);
	}
	
	public ProxyLoginPage  loadProxyLoginPage(){
		SeleniumTestUtil.waitUntil(driver, By.className(PROXYLOGINBTN_CLASS));
		getProxyLoginBTN().click();
		return new ProxyLoginPage(driver);
	}

	private WebElement getProxyLoginBTN() {
		return driver.findElement(By.className(PROXYLOGINBTN_CLASS));
	}

}
