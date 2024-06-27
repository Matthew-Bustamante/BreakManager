package scheduleManagement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import databaseManagement.DatabaseConnection;
public class ScheduleManager {
	private Connection dbConnection;
	public ScheduleManager() {
		
	}
	
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
