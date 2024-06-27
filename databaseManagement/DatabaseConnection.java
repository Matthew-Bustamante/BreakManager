package databaseManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	private Connection dbConnection;
	
	public DatabaseConnection(){
		//Connection dataConnection = dbConnection;
	}
	
	public void setConnection(String url, String username, String password) {
		try {
			dbConnection = DriverManager.getConnection(url, username, password);
			System.out.println("Connection Successful");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Connection getConnection() {
		return dbConnection;
	}
	
	public void closeConnection() {
		try {
			dbConnection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
