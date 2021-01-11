package com.freshremix.selenium.pages.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.freshremix.common.test.SeleniumTestUtil;

public class LoginPage extends AbstractPageObject {

	private static final String COMMON_PASSWORD = "1";
	private final String LOGIN_URL = "http://localhost/eON";
	private final String USERNAMETXT_ID = "j_username";
	private final String PASSWORDTXT_ID = "j_password";

	public LoginPage(WebDriver driverParam) throws IllegalStateException {
		super(driverParam);

		driver.get(LOGIN_URL);
		SeleniumTestUtil.waitUntil(driver, By.id(USERNAMETXT_ID));
	}

	public WebElement getUsernameTxt() {
		return driver.findElement(By.id(USERNAMETXT_ID));
	}

	public WebElement getPasswordTxt() {
		return driver.findElement(By.id(PASSWORDTXT_ID));
	}

	public void login(String username, String password, int roleId) {
		getUsernameTxt().sendKeys(username);
		getPasswordTxt().sendKeys(password);
		WebElement submitBtn = driver.findElement(By
				.xpath("//input[@type='submit']"));
		submitBtn.click();
	}

	public void loginUser(String username) {
		getUsernameTxt().sendKeys(username);
		getPasswordTxt().sendKeys(COMMON_PASSWORD);
		WebElement submitBtn = driver.findElement(By
				.xpath("//input[@type='submit']"));
		submitBtn.click();
	}
	
	public KenHomePage loginKen(){
		login("Ken", COMMON_PASSWORD, 10);
		return  new KenHomePage(driver);
	}
}
