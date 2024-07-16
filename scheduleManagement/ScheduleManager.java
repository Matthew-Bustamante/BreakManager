package scheduleManagement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import employeeManagement. *;

import databaseManagement.DatabaseConnection;

/**
 * ScheduleManager class manages CRUD methods for the schedule table in the database.
 * @author Matthew-Bustamante
 *
 */
public class ScheduleManager {
	private Connection dbConnection;
	private ScheduleDataTransfer pogo;
	public ScheduleManager() {
		pogo = new ScheduleDataTransfer();
	}
	
	/**
	 * Creates a new schedule in the database
	 * @param String date
	 * @throws SQLException
	 */
	public void createSchedule(String date)throws SQLException {
		DatabaseConnection dbConnect = new DatabaseConnection();
		dbConnect.startConnection();
		Connection c = dbConnect.getConnection();
		
		//Statement s = c.createStatement();
		PreparedStatement preparedStatement = c.prepareStatement("INSERT INTO schedules (date) VALUES (?)");
		preparedStatement.setString(1, date);
		preparedStatement.executeUpdate();
		
		preparedStatement.close();
		c.close();
	}
	
	/**
	 * Prints out all schedules in the database
	 * @throws SQLException
	 */
	public void readSchedule() throws SQLException{
		DatabaseConnection dbConnect = new DatabaseConnection();
		dbConnect.startConnection();
		Connection c = dbConnect.getConnection();
		
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("SELECT * FROM schedules");
		System.out.println("-------------Current Schedules------------------");
		while(rs.next()) {
			System.out.println("Date: " + rs.getString("date"));
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
	public void updateSchedule(String currentDate, String newDate)throws SQLException {
		DatabaseConnection dbConnect = new DatabaseConnection();
		dbConnect.startConnection();
		Connection c = dbConnect.getConnection();
		
		//Statement s = c.createStatement();
		PreparedStatement preparedStatement = c.prepareStatement("UPDATE schedules SET date = ? WHERE date = ? ");
		preparedStatement.setString(1, newDate);
		preparedStatement.setString(2,  currentDate);
		preparedStatement.executeUpdate();
		
		preparedStatement.close();
		c.close();
	}
	
	/**
	 * Deletes a schedule from the database
	 * @param String date
	 * @throws SQLException
	 */
	public void deleteSchedule(String date)throws SQLException {
		DatabaseConnection dbConnect = new DatabaseConnection();
		dbConnect.startConnection();
		Connection c = dbConnect.getConnection();
		
		//Statement s = c.createStatement();
		PreparedStatement preparedStatement = c.prepareStatement("DELETE FROM schedules WHERE date = ?");
		preparedStatement.setString(1, date);
		preparedStatement.executeUpdate();
		System.out.println("Schedule Successfully Deleted" + "\n");
		preparedStatement.close();
		c.close();
	}
	/**
	 * scheduleExists method that runs a select statement in the database to see if a given schedule exists
	 * in the database.  If so the method returns true if not then the method returns false
	 * @param date of the schedule
	 * @return true if schedule exists in DB returns false if schedule does not exists in the DB
	 * @throws SQLException
	 */
	public boolean scheduleExists(String date)throws SQLException {
		DatabaseConnection dbConnect = new DatabaseConnection();
		dbConnect.startConnection();
		Connection c = dbConnect.getConnection();
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("SELECT * FROM schedules WHERE date = '" + date +"';");
		
		//if the result set has a next row we can assume that the schedule exists in the database and return true
		if(rs.next()) {
			s.close();
			c.close();
			return true;
		}
		// the next() method above will return false if query statement returns nothing.
		// If that's the case we can assume that the schedule is not in the database so we'll return false
		else {
			s.close();
			c.close();
			return false;
			}
	}
	
	public ScheduleDataTransfer setScheduleID(String date)throws SQLException {
		//ScheduleDataTransfer pogo = new ScheduleDataTransfer();
		DatabaseConnection dbConnect = new DatabaseConnection();
		dbConnect.startConnection();
		Connection c = dbConnect.getConnection();
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery("SELECT schedule_id FROM schedules WHERE date = '" + date +"';");
		while(rs.next()) {
			pogo.setScheduleID(rs.getInt("schedule_id"));
		}
		c.close();
		s.close();
		rs.close();
		return pogo;
		
	}
	
	public void addEmployeeToSchedule(int scheduleID, int employeeID)throws SQLException {
		
		DatabaseConnection dbConnect = new DatabaseConnection();
		dbConnect.startConnection();
		Connection c = dbConnect.getConnection();
		
		//Statement s = c.createStatement();
		PreparedStatement preparedStatement = c.prepareStatement("INSERT INTO schedules_employees (schedule_id, employee_id) VALUES (?, ?)");
		preparedStatement.setInt(1, scheduleID);
		preparedStatement.setInt(2, employeeID);
		preparedStatement.executeUpdate();
		preparedStatement.close();
		c.close();
	}
	
}
