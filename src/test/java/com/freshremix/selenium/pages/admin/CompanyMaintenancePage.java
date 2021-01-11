package com.freshremix.selenium.pages.admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.freshremix.common.test.SeleniumTestUtil;
import com.freshremix.selenium.pages.common.AbstractPageObject;

public class CompanyMaintenancePage extends AbstractPageObject{

	public CompanyMaintenancePage( WebDriver driverParam){
		super(driverParam);
		SeleniumTestUtil.waitUntil(driver, By.id("linb.UI.Group-PANEL:a:"));
	}
	
	public void searchForCompany(String company){
		SeleniumTestUtil.waitForSeconds(3);
		By searchBoxLocator = By.xpath("//span[@id='iptSearchCompName']/descendant-or-self::input");
		SeleniumTestUtil.waitUntil(driver, searchBoxLocator);
		
		//Input search box for company name
		WebElement companyNameInputElement = driver.findElement(searchBoxLocator);
		companyNameInputElement.sendKeys(company);
		
		//search button beside input box
		WebElement searchButton = driver.findElement(By.id("mainSearchButton"));
		searchButton.click();

		//result popup list
		WebElement companyRowEntry = driver.findElement(By.xpath("//div[@id='companySearchResultTreeGrid']/descendant-or-self::span[text()='"+company+"']"));
		companyRowEntry.click();

		//search button in the popup
		WebElement searchPopupButton = driver.findElement(By.id("popupSearchButton"));
		searchPopupButton.click();
		
	}
	
	
	public void loadEditPageForUser(String userName){

		//wait for the userlist to load
		SeleniumTestUtil.waitUntil(driver, By.className("linb-treegrid-cells"));
		
		//find the corresponding button for the user name
		WebElement userNameButton = driver.findElement(By.xpath("//span[text()='"+userName+"']/ancestor::div[contains(@class,'linb-treegrid-cells')]/descendant-or-self::button"));
		userNameButton.click();
	}
	
	public void clickAddUser(){
		WebElement addUserButton = driver.findElement(By.className("app.caption.adduser"));
		addUserButton.click();
	}

	public void deleteUser(String userName){

		//wait for the userlist to load
		SeleniumTestUtil.waitUntil(driver, By.className("linb-treegrid-cells"));
		
		//find the corresponding button for the user name
		WebElement userNameButton = driver.findElement(By.xpath("//span[text()='"+userName+"']/ancestor::div[contains(@class,'linb-treegrid-cells')]/descendant-or-self::button"));
		userNameButton.click();

		WebElement deleteUserButton = driver.findElement(By.className("app.caption.deletes"));
		deleteUserButton.click();
	}
	
	
	public void downloadUserData() {
		SeleniumTestUtil.waitUntil(driver, By.id("tosUsrDwnld"));

		WebElement downloadBtn = driver.findElement(By.id("tosUsrDwnld"));
		downloadBtn.click();
		SeleniumTestUtil.waitUntil(driver, ExpectedConditions.alertIsPresent());
		driver.switchTo().alert().accept();
	}

}
