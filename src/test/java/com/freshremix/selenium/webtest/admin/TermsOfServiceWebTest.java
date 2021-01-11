package com.freshremix.selenium.webtest.admin;

import javax.annotation.Resource;

import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.freshremix.common.test.AbstractWebTest;
import com.freshremix.common.test.SeleniumTestUtil;
import com.freshremix.dao.setup.TOSWebDBSetup;
import com.freshremix.model.TermsOfService;
import com.freshremix.selenium.pages.admin.TermsOfServiceMngtPage;
import com.freshremix.selenium.pages.common.KenHomePage;
import com.freshremix.selenium.pages.common.LoginPage;
import com.freshremix.util.MessageUtil;

@ContextConfiguration(locations = { "classpath:applicationContextDaoTest.xml",
		"classpath:applicationContext-i18n.xml" })
public class TermsOfServiceWebTest extends AbstractWebTest{

	@Resource(name = "tosWebDBSetup")
	private TOSWebDBSetup tosWebDBSetup;
	
	private KenHomePage hp;
	private TermsOfServiceMngtPage tosMngtPage;

	@Override
	@AfterClass(groups="TermsOfServiceWebTestAfterClass")
	public void afterClass() {
		super.afterClass();
	}
	
	@BeforeMethod
	public void beforeMethod(){
		lp = new LoginPage(driver);
		hp = lp.loginKen();
		tosMngtPage = hp.loadTOSMngt();
		tosWebDBSetup.getTosDao().deleteAll();
	}

	@AfterMethod
	public void afterMethod(){
		if (hp != null){
			hp.logout();
		}
		tosWebDBSetup.getTosDao().deleteAll();
	}
	
	
	@Test(groups="createTOS")
	public void TermsOfServiceTestEmptyTable() throws Exception {

		String content = tosMngtPage.getTOSContent();
		String email = tosMngtPage.getEmailList();

		Assert.assertEquals(content, "");
		Assert.assertEquals(email, "");
	}
	
	@DataProvider(name = "contentAndEmailProvider")
	public Object[][] contentAndEmailProvider() {
		return new Object[][] { 
				{ "just a content", "melissa@djfakdf.com" },
				{ "just another content", "melissa@farmind.com" } };
	}

	@Test(groups="createTOS", dataProvider="contentAndEmailProvider")
	public void TermsOfServiceTestSave(String content, String email) throws Exception {
	
		tosMngtPage.saveTermsOfService(content, email);
		SeleniumTestUtil.waitUntil(driver, ExpectedConditions.alertIsPresent());

		driver.switchTo().alert().accept();

		TermsOfService tos = tosWebDBSetup.getTosDao().getLatestTOS();
		Assert.assertEquals(tos.getContent(), content);
		Assert.assertEquals(tos.getEmailList(), email);
	}

	private void testWithError(String errorCode, String content, String email) {
		TermsOfService tosBefore = tosWebDBSetup.getTosDao().getLatestTOS();
	
		tosMngtPage.saveTermsOfService(content, email);
	
		SeleniumTestUtil.waitUntil(driver, ExpectedConditions.alertIsPresent());
	
		String actual = driver.switchTo().alert().getText();
		driver.switchTo().alert().accept();
	
		String message = MessageUtil.getPropertyMessage(errorCode);
		Assert.assertEquals(actual, message);
		
		TermsOfService tosAfter = tosWebDBSetup.getTosDao().getLatestTOS();
		Assert.assertEquals(tosBefore, tosAfter);
	}

	@DataProvider(name = "emptyEmailLListData")
	private Object[][] createEmptyEmailListTOS()  throws Exception {
		return new Object[][] { { "" }, { ";" }, { " " } };
	}

	@Test(groups="TOStestingWErrors", dataProvider = "emptyEmailLListData")
	public void TermsOfServiceTestSaveEmptyEmail(String email)  throws Exception {

		String content = "just another content with email error";
		String errorCode = "tos.message.error.emailListEmpty";
		testWithError(errorCode, content, email);
	}

	@DataProvider(name = "invalidTOSContentListData")
	private Object[][] createInvalidTOSContentList() {
		return new Object[][] { { "" }, { "   " }, { Keys.RETURN.toString() },
				{ Keys.TAB.toString() } };
	}

	@Test(groups="TOStestingWErrors", dataProvider = "invalidTOSContentListData")
	public void TermsOfServiceTestSaveEmptyContent(String newContent)  throws Exception {

		String errorCode = "tos.message.error.tosContentNotFound";
		String email = "melissa@contentTest.com";
		testWithError(errorCode, newContent, email);
	}

	@DataProvider(name = "invalidEmailFormatListData")
	private Object[][] createInvalidEmailFormatList() {
		return new Object[][] { { "testNoATSign.com" },
				{ "@nothignBeforeTheAtSign" }, { "@" } };
	}

	@Test(groups="TOStestingWErrors", dataProvider = "invalidEmailFormatListData")
	public void TermsOfServiceTestSaveInvalidEmail(String email)  throws Exception {
	
		String newContent = "invalid email content test";
		String errorCode = "tos.message.error.emailListInvalid";
		testWithError(errorCode, newContent, email);
	}

}
