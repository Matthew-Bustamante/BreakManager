package scheduleManagement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import databaseManagement.DatabaseConnection;

/**
 * ScheduleManager class manages CRUD methods for the schedule table in the database.
 * @author Matthew-Bustamante
 *
 */
public class ScheduleManager {
	private Connection dbConnection;
	public ScheduleManager() {
		
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
	
}
