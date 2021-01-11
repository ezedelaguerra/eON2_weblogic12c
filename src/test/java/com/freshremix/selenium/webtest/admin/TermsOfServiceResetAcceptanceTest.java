package com.freshremix.selenium.webtest.admin;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.freshremix.common.test.AbstractWebTest;
import com.freshremix.dao.setup.TOSWebDBSetup;
import com.freshremix.selenium.pages.admin.TermsOfServiceMngtPage;
import com.freshremix.selenium.pages.common.KenHomePage;
import com.freshremix.selenium.pages.common.LoginPage;
import com.freshremix.service.MessageI18NService;
@TransactionConfiguration(defaultRollback = false)

@ContextConfiguration(locations = { "classpath:applicationContextDaoTest.xml",
"classpath:applicationContext-i18n.xml" })
public class TermsOfServiceResetAcceptanceTest extends AbstractWebTest{
	@Resource(name = "tosWebDBSetup")
	private TOSWebDBSetup tosWebDBSetup;
	@Resource(name="messageI18NService")
	private MessageI18NService messageI18NService;
	private KenHomePage hp;
	private TermsOfServiceMngtPage tosMngtPage;

	@Override
	@BeforeClass
	public void beforeClass(){
		super.beforeClass();
		String[][] users = new String[][] { 
				{"s_ca01", "1"}, 
				{ "b_ca02","1"}, 
				{ "sa_ca01","1"}, 
				{ "ba_ca01","0"}
		};
		for (int i = 0; i < users.length; i++) {
			tosWebDBSetup.prepareResetTOSData(users[i][0],users[i][1]);
		}
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
		hp = lp.loginKen();
		tosMngtPage = hp.loadTOSMngt();
	}

	@AfterMethod
	public void afterMethod(){
		if (hp != null){
			hp.logout();
		}
	}
	
	

	@Test
	public void testResetAllHappyFlow() throws Exception{
		
		
		int i =tosWebDBSetup.getUsersTOSDao().countAcceptanceFlag("1");
		Assert.assertEquals(i, 1);

		
		String msg = tosMngtPage.resetAllTOS("Ken", "1");
		

		Assert.assertEquals(msg, "Reset Terms of Service Acceptance for all users successful");
		i =tosWebDBSetup.getUsersTOSDao().countAcceptanceFlag("1");
		Assert.assertEquals(i, 0);
	}
	
	@Test
	public void testResetMissingFieldTest() throws Exception{

		String msg = tosMngtPage.resetAllTOS("", "1");

		Assert.assertEquals(msg, messageI18NService.getPropertyMessage("login.invalid.username.password"));
	}
	
	@Test
	public void testIncorrectUserTest() throws Exception{
		

		String msg = tosMngtPage.resetAllTOS("aaaa", "1");
		Assert.assertEquals(msg, messageI18NService.getPropertyMessage("login.invalid.username.password"));
	}

}
