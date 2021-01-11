package com.freshremix.selenium.pages.admin;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.freshremix.common.test.SeleniumTestUtil;
import com.freshremix.model.AdminUsers;
import com.freshremix.selenium.pages.common.AbstractPageObject;

public class UserDetailsForm extends AbstractPageObject {

	private boolean isNew;

	public UserDetailsForm(WebDriver driverParam, boolean isNew) {
		super(driverParam);
		this.isNew = isNew;
		SeleniumTestUtil.waitUntil(driver, By.id(getFirstNameTxtID()));
	}

	public String getFirstNameTxtID() {
		if (isNew) {
			return "iptNewFirstName";
		}
		return "iptEditName";
	}

	public WebElement getFirstNameTxt() {

		WebElement el = driver.findElement(By.id(getFirstNameTxtID()));
		return el.findElement(By.xpath(".//input"));
	}

	public String getShortNameTxtID() {
		if (isNew) {
			return "iptNewShortName";
		}
		return "iptEditShortName";
	}

	public WebElement getShortNameTxt() {

		WebElement el = driver.findElement(By.id(getShortNameTxtID()));
		return el.findElement(By.xpath(".//input"));
	}

	public String getRoleInputID() {
		if (isNew) {
			return "cbiNewRole";
		}
		return "cbiEditRole";
	}

	public WebElement getRoleInput() {
		WebElement el = driver.findElement(By.id(getRoleInputID()));
		return el.findElement(By.xpath(".//input"));
	}

	public String getUsernameTxtID() {
		if (isNew) {
			return "iptNewUserId";
		}
		return "iptNewUserId";
	}

	public WebElement getUsernameTxt() {

		WebElement el = driver.findElement(By.id(getUsernameTxtID()));
		return el.findElement(By.xpath(".//input"));
	}

	public String getPasswordTxtID() {
		if (isNew) {
			return "iptNewPassword";
		}
		return "iptEditPassword";
	}

	public WebElement getPasswordTxt() {

		WebElement el = driver.findElement(By.id(getPasswordTxtID()));
		return el.findElement(By.xpath(".//input"));

	}

	public String getTOSCheckBoxID() {
		if (isNew) {
			return "tosNewUserChk";
		}
		return "tosChk";
	}

	public WebElement getTOSCheckBox() {
		String xPath = "//span[@id='" + getTOSCheckBoxID()
				+ "']/descendant::span[contains(@class, 'linb-checkbox-mark')]";
		return driver.findElement(By.xpath(xPath));
	}

	public String getCategoryBoxID() {
		if (isNew) {
			return "lstNewCategory";
		}
		return "lstEditCategory";
	}

	public WebElement getCategoryBox() {

		return driver.findElement(By.id(getCategoryBoxID()));
	}

	public String getSaveUserBtnID() {
		if (isNew) {
			return "btnSaveNewRole";
		}
		return "btnSaveEditRole";
	}

	public WebElement getSaveUserBtn() {

		return driver.findElement(By.id(getSaveUserBtnID()));
	}

	public void selectRole(String roleName) {
		driver.findElement(By.id("linb.UI.ComboInput-BTN:b:")).click();
		SeleniumTestUtil.waitForSeconds(2);
		List<WebElement> catList = driver
				.findElements(By
						.xpath("//div[contains(@id,'linb.UI.List:l:')]/descendant::span[contains(@class,'linb-list-item')]"));
		for (WebElement webElement : catList) {
			if (webElement.getText().trim().equals(roleName)) {
				webElement.click();
				return;
			}
		}
	}

	public void selectCategory(String catName) {
		List<WebElement> catList = driver
				.findElements(By
						.xpath("//div[contains(@id,'lstNewCategory')]/descendant::span[contains(@class,'linb-list-item')]"));
		for (WebElement webElement : catList) {
			if (webElement.getText().trim().equals(catName)) {
				webElement.click();
				return;
			}
		}

	}

	public void addNewUser(AdminUsers user) {
		getFirstNameTxt().sendKeys(user.getName());
		getShortNameTxt().sendKeys(user.getShortName());
		selectRole(user.getRoleName());
		selectCategory("Meat");
		getUsernameTxt().sendKeys(user.getUserName());
		getPasswordTxt().sendKeys(user.getPassword());

		if (user.getTosFlag() != null && user.getTosFlag().equals("1")) {
			getTOSCheckBox().click();
		}

		getSaveUserBtn().click();
	}
}
