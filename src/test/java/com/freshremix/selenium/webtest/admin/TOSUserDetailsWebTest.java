package com.freshremix.selenium.webtest.admin;

import javax.annotation.Resource;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.freshremix.common.test.AbstractWebTest;
import com.freshremix.common.test.SeleniumTestUtil;
import com.freshremix.dao.setup.TOSWebDBSetup;
import com.freshremix.model.AdminUsers;
import com.freshremix.model.User;
import com.freshremix.model.UsersTOS;
import com.freshremix.selenium.pages.admin.CompanyMaintenancePage;
import com.freshremix.selenium.pages.admin.UserDetailsForm;
import com.freshremix.selenium.pages.common.KenHomePage;
import com.freshremix.selenium.pages.common.LoginPage;


@ContextConfiguration(locations = { "classpath:applicationContextDaoTest.xml"})
public class TOSUserDetailsWebTest  extends AbstractWebTest {

	private static final String TOS2 = "tos2";
	private static final String TOS1 = "tos1";
	private static final String SELLER = "SELLER";
	private static final String AGREE = "1";
	private static final String DISAGREE = "0";
	private static final String USER_SUCCESFULLY_UPDATED = "User succesfully updated.";
	private static final String NEW_USER_SUCCESFULLY_SAVED = "New user succesfully saved.";
	private static final String USER_SUCCESFULLY_DELETED = "User succesfully deleted.";

	private static final String company = "FRASC_S_CA";
	
	@Resource(name = "tosWebDBSetup")
	private TOSWebDBSetup tosWebDBSetup;

	private KenHomePage hp;

	@BeforeMethod
	public void beforeMethod(){
		lp = new LoginPage(driver);
		hp = lp.loginKen();
		tosWebDBSetup.deleteUser(TOS1);
		tosWebDBSetup.deleteUser(TOS2);
	}

	@AfterMethod
	public void afterMethod(){
		if (hp != null){
			hp.logout();
		}
		tosWebDBSetup.deleteUser(TOS1);
		tosWebDBSetup.deleteUser(TOS2);
	}
	
	private AdminUsers createAdminUser(String username) {
		AdminUsers user = new AdminUsers();
		user.setName(username);
		user.setShortName(username);
		user.setUserName(username);
		user.setPassword(AGREE);
		user.setRoleName(SELLER);
		return user;
	}

	private String createNewUser(String username, String tosFlag) {
		AdminUsers user = createAdminUser(username);
		user.setTosFlag(tosFlag);

		CompanyMaintenancePage comMngt = hp.loadCompanyMngt();
		comMngt.searchForCompany(company);
		SeleniumTestUtil.waitForSeconds(2);
		comMngt.clickAddUser();
		
		UserDetailsForm form = new UserDetailsForm(driver, true);
		form.addNewUser(user);
		
		SeleniumTestUtil.waitUntil(driver, ExpectedConditions.alertIsPresent());
		String actual = driver.switchTo().alert().getText();
		driver.switchTo().alert().accept();
		return actual;
	}
	
	@DataProvider(name="newUserDataProvider")
	public Object[][] newUserDataProvider(){
		return new Object[][]{
				{TOS1, AGREE},	
				{TOS2, DISAGREE},	
		};
	}
	
	@Test(groups="AddNewUserTOS", dataProvider="newUserDataProvider")
	public void userDetailsNewUser(String username, String tosFlag) throws Exception {
		String actualAlertMessage = createNewUser(username, tosFlag);
		UsersTOS userTOS = tosWebDBSetup.getUsersTOS(username);
		Assert.assertEquals(actualAlertMessage, NEW_USER_SUCCESFULLY_SAVED);
		if (tosFlag.equals(AGREE)){
			Assert.assertNotNull(userTOS);
			Assert.assertEquals(userTOS.getFlag(), tosFlag);
		} else {
			Assert.assertNull(userTOS);
		}
	}

	
	private String editNewUser(String username, String tosFlag) {
		String actualAlertMessage = createNewUser(username, tosFlag );
		Assert.assertEquals(actualAlertMessage, NEW_USER_SUCCESFULLY_SAVED);
	
		CompanyMaintenancePage comMngt = hp.loadCompanyMngt();
		comMngt.searchForCompany(company);
		SeleniumTestUtil.waitForSeconds(3);
		comMngt.loadEditPageForUser(username);
		SeleniumTestUtil.waitForSeconds(2);
		UserDetailsForm form = new UserDetailsForm(driver, false);
		form.getTOSCheckBox().click();
		form.getSaveUserBtn().click();
	
		SeleniumTestUtil.waitUntil(driver, ExpectedConditions.alertIsPresent());
	
		String actual = driver.switchTo().alert().getText();
		driver.switchTo().alert().accept();
		return actual;
	}
	
	@DataProvider(name="editUserDataProvider")
	public Object[][] editUserDataProvider(){
		return new Object[][]{
				{TOS2, DISAGREE, AGREE},
				{TOS1, AGREE, DISAGREE}
		};
	}

	@Test(groups = "EditNewUserTOS", dataProvider = "editUserDataProvider")
	public void userDetailsEditUser(String username,
			String tosFlag, String expectedTosFlag) throws Exception {

		String actual = editNewUser(username, tosFlag);
		UsersTOS userTOS = tosWebDBSetup.getUsersTOS(username);
		Assert.assertNotNull(userTOS);
		Assert.assertEquals(userTOS.getFlag(), expectedTosFlag);
		Assert.assertEquals(actual, USER_SUCCESFULLY_UPDATED);
	}
	
	@DataProvider(name="deleteUserDataProvider")
	public Object[][] deleteUserDataProvider(){
		return new Object[][]{
				{TOS1, AGREE},
				{TOS2, DISAGREE}
		};
	}
	
	@Test(groups="DeleteNewUserTOS", dataProvider="deleteUserDataProvider")
	public void userDetailsDeleteUser(String username,
			String tosFlag) throws Exception {
		
		String actualAlertMessage = createNewUser(username, tosFlag);
		Assert.assertEquals(actualAlertMessage, NEW_USER_SUCCESFULLY_SAVED);
		User originalUserRecord = tosWebDBSetup.getUserDao().getUserByUsername(username);

		CompanyMaintenancePage comMngt = hp.loadCompanyMngt();
		comMngt.searchForCompany(company);
		SeleniumTestUtil.waitForSeconds(2);
		comMngt.loadEditPageForUser(username);
		SeleniumTestUtil.waitForSeconds(2);
		comMngt.deleteUser(username);

		SeleniumTestUtil.waitUntil(driver, ExpectedConditions.alertIsPresent());

		driver.switchTo().alert().accept();
		SeleniumTestUtil.waitUntil(driver, ExpectedConditions.alertIsPresent());

		String actual = driver.switchTo().alert().getText();
		driver.switchTo().alert().accept();

		User user = tosWebDBSetup.getUserDao().getUserByUsername(username);
		UsersTOS userTOS = tosWebDBSetup.getUsersTOSDao().getEntity(originalUserRecord.getUserId());

		Assert.assertNull(user);
		Assert.assertNull(userTOS);
		Assert.assertEquals(actual, USER_SUCCESFULLY_DELETED);
	}

}
