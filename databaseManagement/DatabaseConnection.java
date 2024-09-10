package databaseManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * DatabaseConnection class:
 * Handles the connection to the database
 * Also returns the connection of the database for other classes to use the database
 *
 */
public class DatabaseConnection {
	
	private Connection dbConnection;
	
	/**
	 * Constructor
	 */
	public DatabaseConnection(){
		//Connection dataConnection = dbConnection;
	}
	
	/**
	 * Creates a connection to the database requireing a url of the database, the username of the user,
	 * and password of the user
	 * @param url of the database
	 * @param username of the user
	 * @param password of the user
	 */
	public void startConnection() {
		try {
			ReadPropertiesFile properties = new ReadPropertiesFile();
			properties.readPropertiesFile();
			
			String url = properties.getCredentialsOne();
			String username = properties.getCredentialsTwo();
			String password = properties.getCredentialsThree();
			dbConnection = DriverManager.getConnection(url, username, password);
			//System.out.println("Connection Successful");
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Returns the database connection object
	 * @return connection object
	 */
	public Connection getConnection() {
		return dbConnection;
	}
	
	/**
	 * Closes database connection 
	 */
	public void closeConnection() {
		try {
			dbConnection.close();
			System.out.println("Connection Closed");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
