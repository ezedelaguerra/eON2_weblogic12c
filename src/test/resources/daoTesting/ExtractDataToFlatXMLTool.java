package daoTesting;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;

/**
 * This is a helper class that exports data to a flat xml file for DBUnit
 * Testing
 * 
 * Taken from http://www.dbunit.org/faq.html#extract
 * 
 * To run this stand alone app, you need dbunitxxx.jar in the class path
 * 
 * @author michael
 * 
 */
public class ExtractDataToFlatXMLTool {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String driverClassName = "oracle.jdbc.OracleDriver";
		String jdbcURL = "jdbc:oracle:thin:@localhost:1521:LOCALORA";
		String userName = "EON01";
		String password = "eon01";
		String outputFileNameAndPath = "c://temp//partial.xml";
		String schema = "EON01";

		System.out.println("Start");

		// load the driver
		Class.forName(driverClassName);
		// database connection
		Connection jdbcConnection = DriverManager.getConnection(jdbcURL,
				userName, password);

		IDatabaseConnection connection = new DatabaseConnection(jdbcConnection,
				schema);

		// partial database export
		// place here you SQL statement to extract the data
		QueryDataSet partialDataSet = new QueryDataSet(connection);
		partialDataSet.addTable("EON_SKU_BA",
				"SELECT * FROM EON_SKU_BA WHERE sku_ba_id=105686");
		partialDataSet.addTable("EON_SKU",
				"select * from eon_sku where sku_id = 1320166");
//		partialDataSet.addTable("EON_SELLING_UOM");
//		partialDataSet.addTable("EON_SHEET_TYPE");
//		partialDataSet.addTable("EON_CATEGORY");
//		partialDataSet.addTable("EON_USERS",
//				"select * from eon_users where user_id=3");
//		partialDataSet.addTable("EON_SKU_GROUP",
//				"select * from eon_sku_group where sku_group_id = 25");
//		partialDataSet.addTable("EON_ORDER_UNIT");
		System.out.println("Writing partial");
		FlatXmlDataSet.write(partialDataSet, new FileOutputStream(
				outputFileNameAndPath));

		// this section does a full database export to an xml file
		// uncomment if needed
		// IDataSet fullDataSet = connection.createDataSet();
		// FlatXmlDataSet.write(fullDataSet, new FileOutputStream("full.xml"));

		// dependent tables database export: export table X and all tables that
		// have a PK which is a FK on X, in the right order for insertion
		// uncomment if needed
		// String[] depTableNames =
		// TablesDependencyHelper.getAllDependentTables(
		// connection, "EON_SKU_BA");
		// IDataSet depDataset = connection.createDataSet(depTableNames);
		// System.out.println("Writing dependents");
		// FlatXmlDataSet
		// .write(depDataset, new FileOutputStream("c://temp//dependents.xml"));
		System.out.println("End");

	}

}
