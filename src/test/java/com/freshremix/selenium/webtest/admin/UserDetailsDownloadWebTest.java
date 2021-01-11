package com.freshremix.selenium.webtest.admin;

import java.io.File;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.freshremix.common.test.AbstractWebTest;
import com.freshremix.common.test.CSVTestUtil;
import com.freshremix.common.test.SeleniumTestUtil;
import com.freshremix.selenium.pages.admin.CompanyMaintenancePage;
import com.freshremix.selenium.pages.common.KenHomePage;
import com.freshremix.selenium.pages.common.LoginPage;

@ContextConfiguration(locations = { "classpath:applicationContextDaoTest.xml"})
public class UserDetailsDownloadWebTest  extends AbstractWebTest{


	@Test
	public void userDetailsDownload() throws Exception {
		eon.clearDownloads();
		Assert.assertEquals(eon.getDownloadCount(), 0);
		LoginPage lp = new LoginPage(driver);
		lp.loginKen();
		KenHomePage hp = new KenHomePage(driver);
		CompanyMaintenancePage comMngt = hp.loadCompanyMngt();

		comMngt.downloadUserData();
		SeleniumTestUtil.waitForSeconds(10);
		hp.logout();
		Assert.assertEquals(eon.getDownloadCount(), 1);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		String formattedDate = simpleDateFormat.format(new Date());
		String downloadFilePath = eon.getDownloadFilePath("UserData_"+formattedDate);
		File file = new File(new URI(downloadFilePath));
		int countLines = CSVTestUtil.countLines(file);
		CSVTestUtil.deleteFile(file);
		Assert.assertEquals(countLines, 41);
		eon.clearDownloads();
	}
}
