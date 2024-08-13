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
	public int evaluateTime(int time) {
		if(time > 12) {
			time = time - 12;
		}
		return time;
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
			
			intStartTimeHours = Integer.parseInt(startTime.substring(0,2));
			intStartTimeMinutes = Integer.parseInt(startTime.substring(3,5));
			intEndTimeHours = Integer.parseInt(endTime.substring(0,2));
			intEndTimeMinutes = Integer.parseInt(endTime.substring(3,5));
			
			int totalHours = intEndTimeHours - intStartTimeHours;
			
			intStartTimeHours = evaluateTime(intStartTimeHours);
			intEndTimeHours = evaluateTime(intEndTimeHours);
			
			//if total hours are less than 4 then employee gets only one break
			if (totalHours < 5){
				
				int intFirstBreak = intStartTimeHours + 2;
				intFirstBreak = evaluateTime(intFirstBreak);
				int intStartTime = evaluateTime(intStartTimeHours);
				int intEndTime = evaluateTime(intEndTimeHours);
				String strFirstBreak = intFirstBreak + ":" + startTime.substring(3,5);
				String strStartTime = intStartTime + ":" + startTime.substring(3,5);
				String strEndTime = intEndTime + ":" + endTime.substring(3,5);
				System.out.println("|Employee Name: " + name + " | Start Time: " + strStartTime + " |End Time: " + strEndTime + " |First Break: " + strFirstBreak);
				System.out.println("________________________________________________________________________________________________________________________________________________");

			}
			
			// if total hours are greater than 5 hours but less than or equal to 6 hours
			//then employee gets a 1st break and lunch
			else if (totalHours >= 5 && totalHours <= 6) {
				int intFirstBreak = intStartTimeHours + 2;
				intFirstBreak = evaluateTime(intFirstBreak);
				
				int intLunch = intStartTimeHours + 4;
				intLunch = evaluateTime(intLunch);
				
				int intStartTime = evaluateTime(intStartTimeHours);
				int intEndTime = evaluateTime(intEndTimeHours);
				String strStartTime = intStartTime + ":" + startTime.substring(3,5);
				String strEndTime = intEndTime + ":" + endTime.substring(3,5);
				
				String strFirstBreak = intFirstBreak + ":" + startTime.substring(3, 5);
				String strLunch = intLunch + ":" + startTime.substring(3, 5);
				System.out.println("|Employee Name: " + name + " | Start Time: " + strStartTime + " |End Time: " + strEndTime + " |First Break: " + strFirstBreak + " |Lunch: " + strLunch);
				System.out.println("________________________________________________________________________________________________________________________________________________");
			}
			//if total hours are greater than 6 hours then 
			//employee gets a 1st break, lunch, and a 2nd break
			else if (totalHours > 6) {
				int intFirstBreak = intStartTimeHours + 2;
				intFirstBreak = evaluateTime(intFirstBreak);
				
				int intLunch = intStartTimeHours + 4;
				intLunch = evaluateTime(intLunch);
				
				int intSecondBreak = intStartTimeHours + 6;
				intSecondBreak = evaluateTime(intSecondBreak);
				
				int intStartTime = evaluateTime(intStartTimeHours);
				int intEndTime = evaluateTime(intEndTimeHours);
				
				String strStartTime = intStartTime + ":" + startTime.substring(3,5);
				String strEndTime = intEndTime + ":" + endTime.substring(3,5);
				
				String strFirstBreak = intFirstBreak + ":" + startTime.substring(3, 5);
				String strLunch = intLunch + ":" + startTime.substring(3, 5);
				String strSecondBreak = intSecondBreak + ":" + startTime.substring(3, 5);
				System.out.println("|Employee Name: " + name + " | Start Time: " + strStartTime + "| End Time: " + strEndTime + " |First Break: " + strFirstBreak + " |Lunch: " + strLunch + " |SecondBreak:" + strSecondBreak);
				System.out.println("________________________________________________________________________________________________________________________________________________");

			}
			
		}

		
		c.close();
	}
	
	
}
