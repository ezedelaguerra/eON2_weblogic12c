package com.freshremix.selenium.pages.admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.freshremix.common.test.SeleniumTestUtil;
import com.freshremix.selenium.pages.common.AbstractPageObject;

public class ProxyLoginPage extends AbstractPageObject{

	public ProxyLoginPage( WebDriver driverParam){
		super(driverParam);
		SeleniumTestUtil.waitUntil(driver, By.className("app.caption.LOGIN_BTN"));
	}
	
	public void loginUser(String userName){
		WebElement inputBox = driver.findElement(By
				.xpath("//span[@id='TXT_USERNAME']/descendant-or-self::input"));
		
		inputBox.sendKeys(userName);
		
		WebElement loginButton = driver.findElement(By
				.id("btnLogin"));
		loginButton.click();
	}

}
