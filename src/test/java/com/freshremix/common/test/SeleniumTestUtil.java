package com.freshremix.common.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumTestUtil {

	private static final long DEFAULT_WAIT_IN_SECS = 20l;

	private SeleniumTestUtil(){
		
	}
	
	public static void waitForSeconds(int secs){
		long millis = secs * 1000;
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			throw new RuntimeException("Sleep interrupted", e);
		}
	}
	public static void waitUntil(WebDriver driver, long timeOutInSeconds,
			By byLocator) {
		ExpectedCondition<?> presenceOfElementLocated = ExpectedConditions
				.presenceOfElementLocated(byLocator);
		waitUntil(driver, timeOutInSeconds, presenceOfElementLocated);
	}

	public static void waitUntil(WebDriver driver, By byLocator) {
		long timeOutInSeconds = DEFAULT_WAIT_IN_SECS;
		ExpectedCondition<?> presenceOfElementLocated = ExpectedConditions
				.presenceOfElementLocated(byLocator);
		waitUntil(driver, timeOutInSeconds, presenceOfElementLocated);
	}
	
	public static void waitUntil(WebDriver driver, 
			ExpectedCondition<?> expectedCondition) {
		long timeOutInSeconds = DEFAULT_WAIT_IN_SECS;
		waitUntil(driver, timeOutInSeconds, expectedCondition);
	}
	
	public static void waitUntil(WebDriver driver, long timeOutInSeconds,
			ExpectedCondition<?> expectedCondition) {
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(expectedCondition);
	}

}
