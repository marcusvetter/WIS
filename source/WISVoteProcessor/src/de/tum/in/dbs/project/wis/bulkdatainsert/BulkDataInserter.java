package de.tum.in.dbs.project.wis.bulkdatainsert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import de.tum.in.dbs.project.wis.DBConstants;

/**
 * This class is used to insert csv-data via bulk processing (postgresql)
 * 
 * @author Marcus Vetter
 * 
 */
public class BulkDataInserter {

	/**
	 * Insert bulk data into the postgres database
	 * 
	 * @param table
	 *            name of the table
	 * @param fileName
	 *            path/name of the file
	 * @param delimiters
	 *            the delimierts used in the csv file
	 * @param columNames
	 *            names of the columns
	 */
	public static void insertBulkData(String table, String fileName,
			String delimiters, String columNames) {
		try {

			// Get the database driver and initialize the connection
			Class.forName("org.postgresql.Driver");
			Connection connection = DriverManager.getConnection(
					DBConstants.DB_CONNECTION_URL, DBConstants.DB_USERNAME,
					DBConstants.DB_PASSWORD);

			// Query for copying bulk data
			String query = "COPY " + table + " (" + columNames + ") FROM \'"
					+ fileName + "\' DELIMITERS \'" + delimiters + "\' CSV";

			// Execute the query
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);

			// Finalize
			statement.close();
			connection.close();

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

}
