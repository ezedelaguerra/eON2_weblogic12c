package com.freshremix.common.test;

import javax.sql.DataSource;

import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.ext.oracle.Oracle10DataTypeFactory;

public class CustomDatabaseTester extends DataSourceDatabaseTester {

	
	public CustomDatabaseTester(DataSource dataSource, String schema) {
		super(dataSource, schema);
	}

	@Override
	public IDatabaseConnection getConnection() throws Exception {
		IDatabaseConnection databaseConnection = super.getConnection();

		DatabaseConfig databaseConfig = databaseConnection.getConfig();

		databaseConfig.setProperty(
				DatabaseConfig.PROPERTY_DATATYPE_FACTORY,
				new Oracle10DataTypeFactory());

		return databaseConnection;
	}
	
}
