package com.freshremix.selenium.pages.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.freshremix.common.test.SeleniumTestUtil;


public class TermsOfServiceDisplayPage extends AbstractPageObject{ 
	
	public TermsOfServiceDisplayPage(WebDriver driverParam) throws IllegalStateException {
		super(driverParam);
		SeleniumTestUtil.waitUntil(driver, By.id("BTN_AGREE"));
	}
	
	
	public WebElement getTOSDIV()
	{
		return driver.findElement(By.id("DIVCONTENT"));
	}
		
	public WebElement getTOSAgreeBtn()
	{
		return driver.findElement(By.id("BTN_AGREE"));
	}
	
	public WebElement getTOSDisAgreeBtn()
	{
		return driver.findElement(By.id("BTN_DISAGREE"));
	}
	
	public UserHomePage termsOfServiceResponse(boolean isAgree) {
		if (isAgree) {
			getTOSAgreeBtn().click();
		} else {
			getTOSDisAgreeBtn().click();
		}

		return new UserHomePage(driver);
	}
	
}
