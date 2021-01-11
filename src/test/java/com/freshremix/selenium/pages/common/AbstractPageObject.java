package com.freshremix.selenium.pages.common;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

public abstract class AbstractPageObject {

	protected WebDriver driver;

	public AbstractPageObject(WebDriver driverParam){
		this.driver = driverParam;
		this.driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}
}
