package com.freshremix.service;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.googlecode.flyway.core.Flyway;
import com.googlecode.flyway.core.api.MigrationInfo;
import com.googlecode.flyway.core.api.MigrationInfoService;

/**
 * This class performs as audit of the database. It checks if the schema_version
 * table is up to date with the db.migration scripts. It throws a runtime
 * exception to halt the starup process.
 * 
 * This class is executed on ContextRefreshedEvent of Spring so that datasource
 * is loaded first.
 * 
 * @author michael
 * 
 */
public class StartupDBAudit implements ApplicationListener {

	private static final Logger LOGGER = Logger.getLogger(StartupDBAudit.class);
	private DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * Event handler method
	 */
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof ContextRefreshedEvent) {
			checkMigration();
		}
	}

	public void checkMigration() {
		String url="null";
		String userName = "null";
		try {
			DatabaseMetaData metaData = dataSource.getConnection().getMetaData();
			url = metaData.getURL();
			userName = metaData.getUserName();
		} catch (SQLException e) {
			throw new RuntimeException("Unable to retrieve datasource details", e);
		}
		LOGGER.info(String.format("Flyway checking DB schema version... url:%s username:%s", url, userName));
		Flyway fly = new Flyway();
		fly.setDataSource(dataSource);
		MigrationInfoService flyinfo = fly.info();
		MigrationInfo[] result = flyinfo.pending();
		if (result.length > 0) {
			StringBuilder pending = new StringBuilder();
			for (int i = 0; i < result.length; i++)
				pending.append("\t" + result[i].getScript() + "\n");
			StringBuilder failMsg = new StringBuilder(
					"The Database is not up to date. Please run the following scripts together with their corresponding schema version records.\nList of Pending scripts: \n");
			failMsg.append(pending);
			LOGGER.error(failMsg.toString());
			throw new RuntimeException(failMsg.toString());

		}
		LOGGER.info("Flyway checking DB schema version... completed");
	}

}
