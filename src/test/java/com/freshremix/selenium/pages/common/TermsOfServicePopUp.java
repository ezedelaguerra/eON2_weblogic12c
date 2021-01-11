package com.freshremix.selenium.pages.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.freshremix.common.test.SeleniumTestUtil;

public class TermsOfServicePopUp extends AbstractPageObject {

	private static final String LINB_DIALOG_CLOSE = "linb-dialog-close";
	private static final String CONTENTID = "DIVCONTENT";
	private static final String CLOSEBTNID= "linb.UI.Dialog-CLOSE:c:";
	private static final String AGREEBTNID ="BTN_AGREE";
	private static final String DISAGREEBTNID ="BTN_DISAGREE";

	
	public TermsOfServicePopUp(WebDriver driverParam) throws IllegalStateException {
		super(driverParam);
		SeleniumTestUtil.waitForSeconds(3);
		SeleniumTestUtil.waitUntil(driver, By.id(CONTENTID));
	}

	public WebElement getContentDiv()
	{
		return driver.findElement(By.id(CONTENTID));
	}
	
	public WebElement getCloseBtn()
	{
		return driver.findElement(By.xpath("//span[@id ='tosDialog']/descendant-or-self::span[contains(@class, 'linb-dialog-close')]"));
	}
	
	public WebElement getAgreeBtn()
	{
		return driver.findElement(By.id(AGREEBTNID));
	}
	
	public WebElement getDisAgreeBtn()
	{
		return driver.findElement(By.id(DISAGREEBTNID));
	}
	
	public void closePopUp() {
		getCloseBtn().click();
	}	
	
	public void respond(boolean isAgree) {
		if (isAgree) {
			getAgreeBtn().click();
		} else {
			getCloseBtn().click();
		}

	}	
	
}
