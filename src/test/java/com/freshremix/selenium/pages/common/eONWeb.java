package com.freshremix.selenium.pages.common;



import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.freshremix.common.test.SeleniumTestUtil;



public class eONWeb {
	public static final String CHROME_WEB_DRIVER = "src/test/resources/chromedriver.exe";
	private WebDriver driver;


	

	public WebDriver setUpWebDriver()  {
		
		/*
		 * ChromeOptions options = new ChromeOptions();
		 * options.addArguments("start-maximized");
		 * System.setProperty("webdriver.chrome.driver"
		 * ,eONWeb.CHROME_WEB_DRIVER);
		 * 
		 * DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		 * capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		 * Map<String, String> chromePrefs = new HashMap<String,String>();
		 * chromePrefs.put("download.default_directory", "C:\\testDownloads");
		 * capabilities.setCapability("chrome.prefs", chromePrefs); this.driver
		 * = new ChromeDriver(capabilities);
		 */
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		 System.setProperty("webdriver.chrome.driver",eONWeb.CHROME_WEB_DRIVER);
		 this.driver = new ChromeDriver(options);
		 
		return driver;

	}

	public void teardown() {
		driver.quit();
	}
	
	
	public String getDownloadFilePath(String filename) {
		driver.get("chrome://downloads/");

		WebElement fileLink = driver.findElement(By.xpath("//a[contains(text(),'"
				+ filename + "')]"));
		if (fileLink == null) {
			return null;
		}

		return fileLink.getAttribute("href");
	}
	
	public void clearDownloads()
	{
		driver.get("chrome://downloads/");
		SeleniumTestUtil.waitUntil(driver, ExpectedConditions.presenceOfElementLocated(By.id("clear-all")));
		driver.findElement(By.id("clear-all")).click();
		
	}
	
	public int getDownloadCount() {
		driver.get("chrome://downloads/");
		List<WebElement> list = driver.findElements(By.className("download"));
		if (list == null) {
			return 0;
		}
		return list.size();
	}
}
