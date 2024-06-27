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
	
	public void updateSchedule() {
		
	}
	
	public void deleteSchedule() {
		
	}
	
}
