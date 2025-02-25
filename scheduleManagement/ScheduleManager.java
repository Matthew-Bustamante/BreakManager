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
 *
 */
public class ScheduleManager {
	private Connection dbConnection;
	private ScheduleDataTransfer pogo;
	private static String sqlQueryForDetails = 
			"SELECT schedules.date,employees.name, start_end_time.start_time, start_end_time.end_time"
			+ " FROM employees"
			+ " INNER JOIN schedules_employees"
			+" ON schedules_employees.employee_id = employees.employee_id"
			+ " INNER JOIN schedules"
			+ " ON schedules_employees.schedule_id = schedules.schedule_id"
			+ " INNER JOIN start_end_time"
			+ " ON start_end_time.employee_id = employees.employee_id"
			+ " WHERE schedules.schedule_id = ?;";
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
	
	/**
	 * setScheduleID this sets the id for the Data transfer object for schedules and returns
	 * that transfer object
	 * @param schedule date
	 * @return data transfer object for schedules
	 * @throws SQLException
	 */
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
	/**
	 * addEmployeeToSchedule method that connects the ids in the junction table between employees and schedules
	 * given an employee and a schhedule id
	 * @param scheduleID
	 * @param employeeID
	 * @throws SQLException
	 */
	public void addEmployeeToSchedule(int scheduleID, int employeeID)throws SQLException {
		
		DatabaseConnection dbConnect = new DatabaseConnection();
		dbConnect.startConnection();
		Connection c = dbConnect.getConnection();
		
		//Statement s = c.createStatement();
		PreparedStatement preparedStatement = c.prepareStatement("INSERT INTO schedules_employees (schedule_id, employee_id) VALUES (?, ?);");
		preparedStatement.setInt(1, scheduleID);
		preparedStatement.setInt(2, employeeID);
		preparedStatement.executeUpdate();
		preparedStatement.close();
		c.close();
	}
	
	/**
	 * viewScheduleDetails method that deplays information about a schedule.
	 * This includes the names of all employees in that schedule 
	 * the employee's start and end-time and will display an employee's break information
	 * @param scheduleID
	 * @throws SQLException
	 */
	public void viewScheduleDetails(int scheduleID)throws SQLException {
		scheduleID = pogo.getScheduleID();
		//String stringSchedule_id = String.valueOf(schedule_id);
		DatabaseConnection dbConnect = new DatabaseConnection();
		dbConnect.startConnection();
		Connection c = dbConnect.getConnection();
		
		PreparedStatement preparedStatement = c.prepareStatement(sqlQueryForDetails);
		preparedStatement.setInt(1, scheduleID);
		ResultSet rs = preparedStatement.executeQuery();
		System.out.println("--------Schedule Details-----------");
		while(rs.next()) {
			//String date = rs.getString("date");
			String name = rs.getString("name");
			String startTime = rs.getString("start_time");
			String endTime = rs.getString("end_time");
			
			
			System.out.println("|Employee Name: " + name + " | Start Time: " + startTime + "| End Time: " + endTime + "|");
		}
		System.out.print("---------------------------------------------" + "\n");
		
		c.close();
	}
}
