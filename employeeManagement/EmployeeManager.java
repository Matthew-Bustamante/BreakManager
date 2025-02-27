package employeeManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import employeeManagement.EmployeeDataTransfer;

import databaseManagement.DatabaseConnection;

/**
 * EmployeeManager class that handles the creation of new employees, views employees,
 * updates employee information, and removes employees.
 *
 */
public class EmployeeManager {
	
	/**
	 * Constructor
	 */
	
	
	public EmployeeManager() {
	}
	/**
	 * Creates a new employee in the database, setting the name of the employee
	 * @param name of employees
	 * @throws SQLException
	 */
	public void createEmployee(String name)throws SQLException {
		DatabaseConnection dbConnect = new DatabaseConnection();
		dbConnect.startConnection();
		Connection c = dbConnect.getConnection();
		
		//Statement s = c.createStatement();
		PreparedStatement preparedStatement = c.prepareStatement("INSERT INTO employees (name) VALUES (?)");
		preparedStatement.setString(1, name);
		//preparedStatement.setString(2, startTime);
		//preparedStatement.setString(3, endTime);
		preparedStatement.executeUpdate();
		
		preparedStatement.close();
		c.close();
	}
	
	/**
	 * Prints out all employees in the database
	 * @throws SQLException
	 */
	public void readEmployee() throws SQLException{
		DatabaseConnection dbConnect = new DatabaseConnection();
		dbConnect.startConnection();
		Connection c = dbConnect.getConnection();
		
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("SELECT * FROM employees");
		System.out.println("-------------Current Employees------------------");
		while(rs.next()) {
			System.out.println("Employee Name: " + rs.getString("name"));
		}
		System.out.print("---------------------------------------------" + "\n");
		s.close();
		c.close();
	}
	
	/**
	 * Updates an employee's name in the database
	 * @param String currentName
	 * @param String newName
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
	 * Deletes an employee from the database
	 * @param String employee name
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
	
	/**
	 * checks if the employee given the name is in the database.
	 * If the employee is in the database the method will return true.
	 * If the employee is not in the database the method will retun false.
	 * @param name of employee
	 * @return returns true if employee is in database or returns false if employee is not in database
	 * @throws SQLException
	 */
	public boolean employeeExists(String name)throws SQLException {
		DatabaseConnection dbConnect = new DatabaseConnection();
		dbConnect.startConnection();
		Connection c = dbConnect.getConnection();
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("SELECT * FROM employees WHERE name = '" + name +"';");
		
		//if the result set has a next row we can assume that the employee exists in the database and return true
		if(rs.next()) {
			s.close();
			c.close();
			return true;
		}
		// the next() method above will return false if query statement returns nothing.
		// If that's the case we can assume that the employee is not in the database so we'll return false
		else {
			s.close();
			c.close();
			return false;
			}
		}
	/**
	 * setEmpID sets the employee ID for the employee transfer object given an employee name.  THe method then returns
	 * this transfer object containing the data	
	 * @param name
	 * @return EmployeeDataTransfer object
	 * @throws SQLException
	 */
	public EmployeeDataTransfer setEmpID(String name)throws SQLException {
		EmployeeDataTransfer pogo = new EmployeeDataTransfer();
				DatabaseConnection dbConnect = new DatabaseConnection();
				dbConnect.startConnection();
				Connection c = dbConnect.getConnection();
				Statement s = c.createStatement();
				ResultSet rs = s.executeQuery("SELECT employee_id FROM employees WHERE name = '" + name +"';");
				while(rs.next()) {
					int employeeID = rs.getInt("employee_id");
					pogo.setEmployeeID(employeeID);
				}
				c.close();
				s.close();
				rs.close();
				return pogo;
				
			}
	
	/**
	 * updateEmployeeTime method that updates an employee's start-time and end-time in the database
	 * @param startTime (string)
	 * @param endTime (string)
	 * @param name (string)
	 * @throws SQLException
	 */
	public void updateEmployeeTime(String startTime, String endTime, String name, String isStartTimeAmOrPm, String isEndTimeAmOrPm)throws SQLException {
		DatabaseConnection dbConnect = new DatabaseConnection();
		dbConnect.startConnection();
		Connection c = dbConnect.getConnection();
		
		//Statement s = c.createStatement();
		if(isStartTimeAmOrPm.equals("PM")) {
			int intStartTime = Integer.parseInt(startTime.substring(0,2));
			int intNewStartTime = intStartTime + 12;
			String startTimeMinutes = startTime.substring(3, 5);
			startTime = Integer.toString(intNewStartTime) + ":" + startTimeMinutes + ":00";	
		}
		if(isEndTimeAmOrPm.equals("PM")) {
			int intEndTime = Integer.parseInt(endTime.substring(0,2));
			int intNewEndTime = intEndTime + 12;
			String endTimeMinutes = endTime.substring(3, 5);
			endTime = Integer.toString(intNewEndTime) + ":" + endTimeMinutes + ":00";
		}
		PreparedStatement preparedStatement = c.prepareStatement("UPDATE employees SET start_time = ?, end_time = ? WHERE name = ? ;");
		preparedStatement.setString(1, startTime);
		preparedStatement.setString(2,  endTime);
		preparedStatement.setString(3, name);
		preparedStatement.executeUpdate();
		
		preparedStatement.close();
		c.close();
	}
			
		}
	

