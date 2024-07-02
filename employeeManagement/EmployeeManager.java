package employeeManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import databaseManagement.DatabaseConnection;

public class EmployeeManager {
	
	public EmployeeManager() {
		
	}
	
	public void createEmployee(String name)throws SQLException {
		DatabaseConnection dbConnect = new DatabaseConnection();
		dbConnect.startConnection();
		Connection c = dbConnect.getConnection();
		
		//Statement s = c.createStatement();
		PreparedStatement preparedStatement = c.prepareStatement("INSERT INTO employees (name, start_time, end_time) VALUES (?, NULL, NULL)");
		preparedStatement.setString(1, name);
		//preparedStatement.setString(2, startTime);
		//preparedStatement.setString(3, endTime);
		preparedStatement.executeUpdate();
		
		preparedStatement.close();
		c.close();
	}
	
	/**
	 * Prints out all schedules in the database
	 * @throws SQLException
	 */
	public void readEmployee() throws SQLException{
		DatabaseConnection dbConnect = new DatabaseConnection();
		dbConnect.startConnection();
		Connection c = dbConnect.getConnection();
		
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("SELECT * FROM employees");
		System.out.println("-------------Current Schedules------------------");
		while(rs.next()) {
			System.out.println("Employee Name: " + rs.getString("name"));
		}
		System.out.print("---------------------------------------------" + "\n");
		s.close();
		c.close();
	}
	
	/**
	 * Updates a schedule date in the database
	 * @param String currentDate
	 * @param String newDate
	 * @throws SQLException
	 */
	public void updateEmployee(String currentName, String newName)throws SQLException {
		DatabaseConnection dbConnect = new DatabaseConnection();
		dbConnect.startConnection();
		Connection c = dbConnect.getConnection();
		
		//Statement s = c.createStatement();
		PreparedStatement preparedStatement = c.prepareStatement("UPDATE employees SET name = ? WHERE name = ? ");
		preparedStatement.setString(1, newName);
		preparedStatement.setString(2,  currentName);
		preparedStatement.executeUpdate();
		
		preparedStatement.close();
		c.close();
	}
	
	/**
	 * Deletes a schedule from the database
	 * @param String date
	 * @throws SQLException
	 */
	public void deleteEmployee(String name)throws SQLException {
		DatabaseConnection dbConnect = new DatabaseConnection();
		dbConnect.startConnection();
		Connection c = dbConnect.getConnection();
		
		//Statement s = c.createStatement();
		PreparedStatement preparedStatement = c.prepareStatement("DELETE FROM employees WHERE name = ?");
		preparedStatement.setString(1, name);
		preparedStatement.executeUpdate();
		System.out.println("Employee Successfully Deleted" + "\n");
		preparedStatement.close();
		c.close();
	}
}
