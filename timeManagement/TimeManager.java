package timeManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import databaseManagement.DatabaseConnection;

public class TimeManager {
	
	public TimeManager() {
		
	}
	
	public void createTime(String startTime, String endTime, String isStartTimeAmOrPm, String isEndTimeAmOrPm, int scheduleID, int employeeID)throws SQLException {
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
		PreparedStatement preparedStatement = c.prepareStatement("INSERT INTO start_end_time (start_time, end_time, schedule_id, employee_id) VALUES (?, ?, ?, ?);");
		preparedStatement.setString(1, startTime);
		preparedStatement.setString(2,  endTime);
		preparedStatement.setInt(3, scheduleID);
		preparedStatement.setInt(4, employeeID);
		preparedStatement.executeUpdate();
		
		preparedStatement.close();
		c.close();
	}
	
	public void updateTime() {
		
	}
	
	public void deleteTime() {
		
	}
}
