package com.freshremix.selenium.webtest.admin;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.freshremix.common.test.AbstractWebTest;
import com.freshremix.common.test.SeleniumTestUtil;
import com.freshremix.selenium.pages.admin.ProxyLoginPage;
import com.freshremix.selenium.pages.common.KenHomePage;
import com.freshremix.selenium.pages.common.LoginPage;

@ContextConfiguration(locations = { "classpath:applicationContextDaoTest.xml"})
public class ProxyLoginWebTest extends AbstractWebTest {


	private KenHomePage hp;
	private ProxyLoginPage proxyLoginPage;

	@BeforeMethod
	public void beforeMethod(){
		lp = new LoginPage(driver);
		hp = lp.loginKen();
		proxyLoginPage = hp.loadProxyLoginPage();
	}

	@AfterMethod
	public void afterMethod(){
		if(hp!=null){
			hp.logout();
		}
	}
	
	@Test
	public void testProxyLogin(){
		proxyLoginPage.loginUser("s_ca01");
		driver.findElement(By.xpath("//span[contains(text(),'s_ca01 SELLER')]"));
	}

	@DataProvider(name="invalidEntryDataProvider")
	public Object[][] invalidEntryDataProvider(){
		return new Object[][]{
				{"invalidUser", "User does not exist."},
				{"Ken", "Admin Username is not allowed for proxy login."}
		};
	}
	
	@Test(dataProvider="invalidEntryDataProvider")
	public void testUserDoesNotExist(String userName, String expectedText){
		proxyLoginPage.loginUser(userName);
		SeleniumTestUtil.waitUntil(driver, ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		String alertText = alert.getText();
		Assert.assertEquals(alertText, expectedText);
		alert.accept();
	}
}
