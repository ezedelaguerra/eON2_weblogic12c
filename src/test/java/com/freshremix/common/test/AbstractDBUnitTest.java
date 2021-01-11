package com.freshremix.common.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.lang.ArrayUtils;
import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import com.googlecode.flyway.core.Flyway;
import com.googlecode.flyway.core.api.MigrationInfo;
import com.googlecode.flyway.core.api.MigrationInfoService;
@TransactionConfiguration(defaultRollback = true)
public abstract class AbstractDBUnitTest extends AbstractTransactionalTestNGSpringContextTests
 {

	@Retention(RetentionPolicy.RUNTIME)
	public @interface FlatXMLDataSet {

		public String[] locations();
	}

	@Target(value = ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface NoSetUp {
		public String[] excludeLocations() default {};
	}

	@Autowired
	private IDatabaseTester databaseTester;

	private DataSource ds;

	@Override
	@Resource(name="dataSource")
	public void setDataSource(DataSource dataSource) {
		this.ds=dataSource;
		super.setDataSource(dataSource);
	}

	
	@BeforeClass
	public void checkMigration() {
		String url = "null";
		String userName = "null";
		try {
			DatabaseMetaData metaData = ds.getConnection().getMetaData();
			url = metaData.getURL();
			userName = metaData.getUserName();
		} catch (SQLException e) {
			throw new RuntimeException("Unable to retrieve datasource details",
					e);
		}
		Flyway fly = new Flyway();
		fly.setDataSource(ds);
		MigrationInfoService flyinfo = fly.info();
		MigrationInfo[] result = flyinfo.pending();
		if (result.length > 0) {
			StringBuilder pending = new StringBuilder();
			for (int i = 0; i < result.length; i++)
				pending.append("\t" + result[i].getScript() + "\n");
			StringBuilder failMsg = new StringBuilder(
					"Test Database is not up to date. \n");
			failMsg.append("Please run flyway migration before testing.\n");
			failMsg.append("connection url:").append(url)
					.append("\n connection username:").append(userName);
			failMsg.append("\nList of Pending scripts: \n");
			failMsg.append(pending);
			Assert.fail(failMsg.toString());

		}
	}

	
	@BeforeMethod
	public void setUp(Method method) throws Exception {

		NoSetUp noSetup = null;
		String[] excludeLocations = null;
		
		if (method.isAnnotationPresent(NoSetUp.class)) {
			noSetup = method.getAnnotation(NoSetUp.class);
			excludeLocations = noSetup.excludeLocations();
		}

		if (!method.isAnnotationPresent(NoSetUp.class) 
				|| !ArrayUtils.isEmpty(excludeLocations)) {
			
			
			IDataSet dataSet = getDataSet(excludeLocations);
			databaseTester.setDataSet(dataSet);
			databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
			databaseTester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
			
			databaseTester.onSetup();
		}

	}

	private IDataSet getDataSet(String[] excludeLocations) {
		List<org.springframework.core.io.Resource> flatXMLDatasets = getFlatXMLDatasetResources();

		List<IDataSet> dataSetList = new ArrayList<IDataSet>();
		for (org.springframework.core.io.Resource dataSetLocation : flatXMLDatasets) {
			/* skip the file that was declared excluded in the @NoSetup method */
			if (ArrayUtils.contains(excludeLocations, dataSetLocation.getFilename())) {
				continue;
			}
			try {

				FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
				builder.setColumnSensing(true);
				IDataSet dataSet = builder.build(dataSetLocation.getFile());
				dataSetList.add(dataSet);
			} catch (Exception e) {
				logger.warn("Could not load flat xml dataset "
						+ dataSetLocation.getFilename() + ", ignoring", e);
			}
		}
		try {

			return new CompositeDataSet(
					dataSetList.toArray(new IDataSet[dataSetList.size()]));
		} catch (DataSetException e) {
			logger.warn("Could not create composite dataset");
			throw new RuntimeException("Could not create composite dataset", e);
		}

	}

	@AfterTransaction
	public void tearDown() throws Exception {
		databaseTester.onTearDown();
	}

	private List<org.springframework.core.io.Resource> getFlatXMLDatasetResources() {
		List<org.springframework.core.io.Resource> datasetLocations = new ArrayList<org.springframework.core.io.Resource>();
		FlatXMLDataSet dataSet = getClass().getAnnotation(FlatXMLDataSet.class);

		if (dataSet != null && dataSet.locations().length > 0) {
			for (String location : dataSet.locations()) {
				datasetLocations
						.add(new org.springframework.core.io.ClassPathResource(
								location));
			}
		}
		return datasetLocations;
	}

}
