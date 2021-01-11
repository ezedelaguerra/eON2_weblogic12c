package com.freshremix.selenium.pages.common;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.freshremix.common.test.SeleniumTestUtil;

public class HomePage extends AbstractPageObject{
	
	
	private static final String LOGOUT_CLASS="app.caption.logout";
	
	public HomePage(WebDriver driverParam) throws IllegalStateException {
		super(driverParam);
		SeleniumTestUtil.waitUntil(driver, By.className("app.caption.logout"));

	}	
	
	public WebElement getLogoutLink()
	{
		return driver.findElement(By.className(LOGOUT_CLASS));
	}
	
	public void logout() {
		SeleniumTestUtil.waitUntil(driver, By.className(LOGOUT_CLASS));

		getLogoutLink().click();
	}	
	

}
