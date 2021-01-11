package com.freshremix.selenium.pages.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.freshremix.common.test.SeleniumTestUtil;



public class UserHomePage extends HomePage{
	
	
	private final String lnkTOSID = "lnkTos";
	private final String lblTOSID = "tosNewLabel";
	public UserHomePage(WebDriver driverParam) throws IllegalStateException {
		super(driverParam);
		SeleniumTestUtil.waitUntil(driver, By.className("app.caption.logout"));
	}
	
	public WebElement getTermsOfServiceLink()
	{
		return driver.findElement(By.id(lnkTOSID));
	}
	
	public WebElement getTermsOfServiceLabel()
	{
		return driver.findElement(By.id(lblTOSID));
	}
	
	
	public TermsOfServicePopUp displayTOSPopUp() {
		SeleniumTestUtil.waitUntil(driver, 20, By.id(lnkTOSID));
		getTermsOfServiceLink().click();
		return new TermsOfServicePopUp(driver);
	}		

	
}
