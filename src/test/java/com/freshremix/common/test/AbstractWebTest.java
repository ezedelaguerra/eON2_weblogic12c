package com.freshremix.common.test;

import java.io.IOException;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.openqa.selenium.WebDriver;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.freshremix.selenium.pages.common.LoginPage;
import com.freshremix.selenium.pages.common.eONWeb;

/**
 * 
 * Intentionally set with default rollback false so as to commit transactions.
 * 
 */
@TransactionConfiguration(defaultRollback = false)
public abstract class AbstractWebTest extends
		AbstractTransactionalTestNGSpringContextTests {

	/**
	 * Eon driver setup.
	 */
	protected eONWeb eon;
	
	/**
	 * Web driver.
	 */
	protected WebDriver driver;
	
	/**
	 * Login page object.
	 */
	protected LoginPage lp;

	@Override
	@Resource(name = "dataSourceDriverManager")
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	/**
	 * Sets up the web driver.
	 * 
	 * @throws IOException
	 */
	@BeforeClass
	public void beforeClass() {
		eon = new eONWeb();
		driver = eon.setUpWebDriver();
	}

	@AfterClass
	public void afterClass() {
		eon.teardown();
	}

}
