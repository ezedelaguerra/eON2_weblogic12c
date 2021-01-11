package com.freshremix.selenium.pages.admin;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.freshremix.common.test.SeleniumTestUtil;
import com.freshremix.selenium.pages.common.AbstractPageObject;


public class TermsOfServiceMngtPage extends AbstractPageObject{

	public TermsOfServiceMngtPage( WebDriver driverParam){
		super(driverParam);
		// wait for X seconds for ajax call to completely load the data
		SeleniumTestUtil.waitForSeconds(3);
		SeleniumTestUtil.waitUntil(driver, By.id("linb.UI.Input-INPUT:a:"));
	}
	

	public WebElement getTOSTextArea() {
		By locator = By.id("linb.UI.Input-INPUT:a:");
		SeleniumTestUtil.waitUntil(driver, locator);
		return driver.findElement(locator);
	}

	public WebElement getEmailListTextArea() {
		By locator = By.id("linb.UI.Input-INPUT:b:");
		SeleniumTestUtil.waitUntil(driver, locator);
		return driver.findElement(locator);
	}

	public WebElement getTOSSubmitBtn() {
		By locator = By.className("app.caption.BTN_TOS_SUBMIT");
		SeleniumTestUtil.waitUntil(driver, locator);
		return driver.findElement(locator);
	}
	
	public String getTOSContent() {
		By locator = By.id("linb.UI.Input-INPUT:a:");
		SeleniumTestUtil.waitUntil(driver, locator);
		return driver.findElement(locator)
				.getAttribute("value");
	}

	
	public String getEmailList() {
		By locator = By.id("linb.UI.Input-INPUT:b:");
		SeleniumTestUtil.waitUntil(driver, locator);
		return driver.findElement(locator).getAttribute("value");
	}	
	
	
	public WebElement getUsernameTxt()
	{		
		By locator = By.id("TXT_USERNAME");
		SeleniumTestUtil.waitUntil(driver, locator);
		return driver.findElement(locator).findElement(By.xpath(".//input"));
		
	}
	
	public WebElement getPasswordTxt()
	{		
		By locator = By.id("TXT_PASS");
		SeleniumTestUtil.waitUntil(driver, locator);
		return driver.findElement(locator).findElement(By.xpath(".//input"));
		
	}
	public WebElement getSubmitBTN()
	{		
		By locator = By.id("BTNSUBMIT");
		SeleniumTestUtil.waitUntil(driver, locator);
		return driver.findElement(locator);
		
	}
	public void saveTermsOfService(String newContent, String email)
	{
		getTOSTextArea().clear();
		SeleniumTestUtil.waitUntil(driver, new ExpectedCondition<Boolean>() {
	        public Boolean apply(WebDriver driver) {                
	            String text = driver.findElement(By.id(getTOSTextArea().getAttribute("id"))).getAttribute("value");
	            return text.equals("");
	        }
	    });

		getTOSTextArea().sendKeys(newContent);
		getEmailListTextArea().clear();
		getEmailListTextArea().sendKeys(email);
		getTOSSubmitBtn().click();
	}
	

	
	
	
	
	public String resetAllTOS(String username, String password)
	{
		driver.findElement(By.id("RESET_BTN")).click();
		SeleniumTestUtil.waitForSeconds(2);
		getUsernameTxt().sendKeys(username);
		getPasswordTxt().sendKeys(password);
		getSubmitBTN().click();
		
		SeleniumTestUtil.waitUntil(driver, ExpectedConditions.alertIsPresent());
		String str = driver.switchTo().alert().getText();
		driver.switchTo().alert().accept();
		return str.trim();
	}
}

