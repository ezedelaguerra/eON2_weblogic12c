package com.freshremix.selenium.webtest.customer;

import javax.annotation.Resource;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.freshremix.common.test.AbstractWebTest;
import com.freshremix.common.test.SeleniumTestUtil;
import com.freshremix.dao.setup.TOSWebDBSetup;
import com.freshremix.model.User;
import com.freshremix.model.UsersTOS;
import com.freshremix.selenium.pages.common.LoginPage;
import com.freshremix.selenium.pages.common.TermsOfServiceDisplayPage;
import com.freshremix.selenium.pages.common.TermsOfServicePopUp;
import com.freshremix.selenium.pages.common.UserHomePage;

@TransactionConfiguration(defaultRollback = false)
@ContextConfiguration(locations = { "classpath:applicationContextDaoTest.xml" })
public class TermsOfServiceUserWebTest extends AbstractWebTest {

	@Resource(name = "tosWebDBSetup")
	private TOSWebDBSetup tosWebDBSetup;

	private UserHomePage hp;

	@Override
	@BeforeClass
	public void beforeClass(){
		super.beforeClass();
		tosWebDBSetup.prepareTOSData();
	}
	
	@Override
	@AfterClass
	public void afterClass() {
		super.afterClass();
		tosWebDBSetup.tearDownAllTOSData();
	}
	
	@BeforeMethod
	public void beforeMethod(){
		lp = new LoginPage(driver);
	}

	@AfterMethod
	public void afterMethod(){
		if (hp != null){
			hp.logout();
		}
	}
	

	@DataProvider(name = "userList")
	private Object[][] createUserList() {
		return new Object[][] { { "s_ca01", true }, { "sa_ca01", false },
				{ "b_ca02", false }, { "ba_ca01", true } };
	}

	@DataProvider(name = "agreedUsersList")
	private Object[][] createAgreedUsersList() {
		return new Object[][] { { "s_ca01" },
		{ "ba_ca01" } };
	}

	@DataProvider(name = "disagreedUsersList")
	private Object[][] createDisAgreedUsersList() {
		return new Object[][] { { "sa_ca01", true }, { "b_ca02", false }, };
	}

	@Test(dataProvider = "userList")
	public void termsOfServiceDisplayOnLoginAgreeTest(String username,
			boolean isAgree) {
		lp.loginUser(username);
		TermsOfServiceDisplayPage tosPage = new TermsOfServiceDisplayPage(
				driver);
		SeleniumTestUtil.waitForSeconds(5);
		String content = tosPage.getTOSDIV().getText();
		tosPage.termsOfServiceResponse(isAgree);

		User user = tosWebDBSetup.getUserDao().getUserByUsername(username);
		UsersTOS usersTOSRecord = tosWebDBSetup.getUsersTOSDao().getEntity(user.getUserId());
		String flag = "0";
		if (isAgree) {
			flag = "1";
		}
		Assert.assertNotNull(content);
		Assert.assertEquals(usersTOSRecord.getFlag(), flag);

	}

	@Test(dataProvider = "agreedUsersList", dependsOnMethods = "termsOfServiceDisplayOnLoginAgreeTest")
	public void termsOfServicePopUpAgreedUsersTest(String username) {
		lp.loginUser(username);
		hp = new UserHomePage(driver);
		TermsOfServicePopUp pop = hp.displayTOSPopUp();
		String content = pop.getContentDiv().getText();
		String isBTNVisible = pop.getAgreeBtn().getCssValue("visibility");

		pop.closePopUp();
		Assert.assertEquals(isBTNVisible, "hidden");
		Assert.assertNotNull(content);
	}

	@Test(dataProvider = "disagreedUsersList", dependsOnMethods = "termsOfServicePopUpAgreedUsersTest")
	public void termsOfServicePopUpDisAgreedUsersTest(String username,
			boolean isAgree) {
		lp.loginUser(username);
		hp = new UserHomePage(driver);
		TermsOfServicePopUp pop = hp.displayTOSPopUp();
		SeleniumTestUtil.waitForSeconds(5);
		String content = pop.getContentDiv().getText();
		String isBTNVisible = pop.getAgreeBtn().getCssValue("visibility");

		pop.respond(isAgree);

		User user = tosWebDBSetup.getUserDao().getUserByUsername(username);
		SeleniumTestUtil.waitForSeconds(3);
		UsersTOS usersTOSRecord = tosWebDBSetup.getUsersTOSDao().getEntity(user.getUserId());
		Assert.assertEquals(isBTNVisible, "visible");

		Assert.assertNotNull(content);

		if (isAgree) {
			Assert.assertEquals(usersTOSRecord.getFlag(), "1");
		} else {
			Assert.assertEquals(usersTOSRecord.getFlag(), "0");
		}

	}

	@Test(dependsOnMethods = "termsOfServicePopUpDisAgreedUsersTest")
	public void TermsOfServiceLabelTestNew() throws Exception {

		lp.loginUser("s_ca01");
		hp = new UserHomePage(driver);
		SeleniumTestUtil.waitForSeconds(5);
		String isLabelVisible = hp.getTermsOfServiceLabel().getCssValue(
				"visibility");
		Assert.assertEquals(isLabelVisible, "visible");
	}

	@Test(dependsOnMethods = "TermsOfServiceLabelTestNew")
	public void TermsOfServiceLabelTestOld() throws Exception {

		tosWebDBSetup.prepareTOSLabelData(-31);
		LoginPage lp = new LoginPage(driver);
		lp.loginUser("s_ca01");
		hp = new UserHomePage(driver);
		SeleniumTestUtil.waitForSeconds(5);
		String isLabelVisible = hp.getTermsOfServiceLabel().getCssValue(
				"visibility");

		Assert.assertEquals(isLabelVisible, "hidden");
	}

	@Test(dependsOnMethods = "TermsOfServiceLabelTestNew")
	public void TermsOfServiceLabelTestOld30() throws Exception {
		tosWebDBSetup.prepareTOSLabelData(-30);
		lp.loginUser("s_ca01");
		hp = new UserHomePage(driver);
		SeleniumTestUtil.waitForSeconds(5);
		String isLabelVisible = hp.getTermsOfServiceLabel().getCssValue(
				"visibility");

		Assert.assertEquals(isLabelVisible, "visible");
	}

	@Test(dependsOnMethods = "TermsOfServiceLabelTestOld")
	public void test30daysExpiration() throws Exception {

		String username = "b_ca02";
		tosWebDBSetup.prepareUserExpiration(username, -31);
		lp.loginUser(username);
		hp=null;

		SeleniumTestUtil.waitUntil(driver, ExpectedConditions.alertIsPresent());
		String actual = driver.switchTo().alert().getText();
		driver.switchTo().alert().accept();
		Assert.assertEquals(
				actual,
				"You have failed to agree with the Terms of Service for 30 days. Please contact the administrator to use eON again");
	}

}
