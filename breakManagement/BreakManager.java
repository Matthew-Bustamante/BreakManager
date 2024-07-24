package breakManagement;
import scheduleManagement.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import databaseManagement.DatabaseConnection;
import employeeManagement.*;

public class BreakManager {
	private int intStartTimeHours;
	private int intStartTimeMinutes;
	private int intEndTimeHours;
	private int intEndTimeMinutes;
	private String employeeTime;
	private ArrayList<String> breaks;
	private static String queryScheduleDetails = 
			"SELECT schedules.date,employees.name, employees.start_time, employees.end_time"
			+ " FROM employees"
			+ " INNER JOIN schedules_employees"
			+" ON schedules_employees.employee_id = employees.employee_id"
			+ " INNER JOIN schedules"
			+ " ON schedules_employees.schedule_id = schedules.schedule_id"
			+ " WHERE schedules.schedule_id = ?;";
	public BreakManager() {
		breaks = new ArrayList<String>();
		intStartTimeHours = 0;
		intStartTimeMinutes = 0;
		intEndTimeHours = 0;
		intEndTimeMinutes = 0;
		String employeeTime = "";
	}
	
	
	public void scheduleBreaks(int scheduleID)throws SQLException {
		//scheduleID = pogo.getScheduleID();
		//String stringSchedule_id = String.valueOf(schedule_id);
		DatabaseConnection dbConnect = new DatabaseConnection();
		dbConnect.startConnection();
		Connection c = dbConnect.getConnection();
		
		PreparedStatement preparedStatement = c.prepareStatement(queryScheduleDetails);
		preparedStatement.setInt(1, scheduleID);
		ResultSet rs = preparedStatement.executeQuery();

		while(rs.next()) {
			//String date = rs.getString("date");
			String name = rs.getString("name");
			String startTime = rs.getString("start_time");
			String endTime = rs.getString("end_time");
			
			intStartTimeHours = Integer.parseInt(startTime.substring(0,1));
			intStartTimeMinutes = Integer.parseInt(endTime.substring(3,4));
			
		}

		
		c.close();
	}
}
