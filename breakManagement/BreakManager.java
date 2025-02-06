package breakManagement;
import scheduleManagement.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import databaseManagement.DatabaseConnection;
import employeeManagement.*;
/**
 * BreakManager Class that schedules bre46aks according to an employee's start and end time.
 * The class also handles the case in which two employees are scheduled at the same time 
 * and will attempt to 'stagger' the breaks to prevent two employees from going to break at the same time
 *
 */
public class BreakManager {
	private int intStartTimeHours;
	private int intStartTimeMinutes;
	private int intEndTimeHours;
	private int intEndTimeMinutes;
	private int sameTimeCount;
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
	private static String querySameEmployeeTime = 
			"SELECT COUNT(employees.start_time) AS total"
					+ " FROM employees"
					+ " INNER JOIN schedules_employees"
					+" ON schedules_employees.employee_id = employees.employee_id"
					+ " INNER JOIN schedules"
					+ " ON schedules_employees.schedule_id = schedules.schedule_id"
					+ " WHERE schedules.schedule_id = ?;";
			
	
	/**
	 * Constructor
	 */
	public BreakManager() {
		breaks = new ArrayList<String>();
		
		intStartTimeHours = 0;
		intStartTimeMinutes = 0;
		intEndTimeHours = 0;
		intEndTimeMinutes = 0;
		sameTimeCount = 0;
		String employeeTime = "";
	}
	/**
	 * evaluate time method that acts a conversion from 24 hour time to 12 hour time
	 * @param time (int)
	 * @return converted Time (int)
	 */
	public int evaluateTime(int time) {
		if(time > 12) {
			time = time - 12;
		}
		return time;
	}
	
	/**
	 * This method searches the breaks array and if a break is found then  the method returns true else false
	 * @param currentBreak (string)
	 * @return true if break is found & false if its not found
	 */
	public boolean isBreakInList(String currentBreak) {
		boolean breakFound = false;
		for(int i = 0; i < breaks.size(); i ++) {
			String breakInList = breaks.get(i);
			if (breakInList.equals(currentBreak)){
				breakFound = true;
				break;
			}
		}
		return breakFound;
	}
	
	/**
	 * staggerBreak method: splits the time string into minutes and hours. If the minutes
	 * are equal to the substring '00' then we can assume the time is 60 minutes and we need to 
	 * handle that case.
	 * Else we just subtract the minutes by fifteen, thus staggering the breaks by 15 minutes
	 * @param currentBreak (String)
	 * @return new staggered break time (String)
	 */
	public String staggerBreak(String currentBreak, boolean isLunch) {
		String newBreak = "";
		int hours = 0;
		int minutes = 0;
		int minuteTracker = 0;
		String strHours = "";
		String strMinutes = "";
		int staggerNumber = 15;
		int lunchStaggerDivisor = sameTimeCount - 1;
		
		
		if(currentBreak.length() == 5) {
			hours = Integer.parseInt(currentBreak.substring(0,2));
			minutes = Integer.parseInt(currentBreak.substring(3,5));
			strHours = currentBreak.substring(0,2);
			strMinutes = currentBreak.substring(3,5);
		}
		
		else {
			hours = Integer.parseInt(currentBreak.substring(0,1));
			minutes = Integer.parseInt(currentBreak.substring(2,4));
			strHours = currentBreak.substring(0,1);
			strMinutes = currentBreak.substring(2,4);
		}
		
		// if the break is a lunch break then the stagger number needs to increase to 30 instead of 15
		// so the lunch break are staggered by 30 minutes instead of 15 minutes
		if(isLunch == true) {
			staggerNumber = 30 / lunchStaggerDivisor;
		}
		
		int newMinutes = minutes + staggerNumber;
		//If the calculated minutes is eqaul to 60
		//then we need to increase the hour and set minutes to 0
		if (newMinutes == 60) {
			int newHours = hours + 1;
			newBreak = Integer.toString(newHours) + ":" + "00";
		}
		//if the calculated minutes is greater than 60
		// then we need to increase the hour and add the remaining minutes to the minutes
		else if (newMinutes > 60) {
			int remainingMinutes = newMinutes - 60;
			int newHours = hours + 1;
			newBreak = Integer.toString(newHours) + ":" + Integer.toString(remainingMinutes);
		}
		//else calculate the new break normally
		else {
			newBreak = strHours + ":" + Integer.toString(newMinutes);
		}
			
		
		
		return newBreak;
	}
	
	/**
	 * evaluateBreak method that will check if the break time is already taken if so it will stagger that break to ensure
	 * that each break is unique
	 * @param currentBreak
	 * @return currentBreak if break is unique, returns a staggered break is break is not unique64
	 */
	public String evaluateBreak(String currentBreak, boolean isLunch) {
		//Base Case
		if(isBreakInList(currentBreak) == false) {
			return currentBreak;
		}
		//Recursive Call
		else {
			currentBreak = staggerBreak(currentBreak, isLunch);
			return evaluateBreak(currentBreak, isLunch);
		}
	}
	
	/**
	 * Schedule breaks method:
	 * This method is in charge of calculating the total hours that employee is clocked in for 
	 * and will decided how many breaks the employee gets and will calculate the time that their suppose to take
	 * their breaks.
	 * The method decides how many breaks based on this logic:
	 * If an employee's total time is less than 5 hours then the employee only gets a first 15 minute break
	 * If an employee's total time is greater than or equal to 5 hours but less than or equal to 6 hours then and employee gets one 15-minute break and a 30 minute lunch
	 * If an employee's total time is greater than 6 hours then the employee is given a two 15 minute breaks and a 30 minute lunch
	 * @param scheduleID (int)
	 * @throws SQLException
	 */
	public void scheduleBreaks(int scheduleID)throws SQLException {
		//scheduleID = pogo.getScheduleID();
		//String stringSchedule_id = String.valueOf(schedule_id);
		DatabaseConnection dbConnect = new DatabaseConnection();
		dbConnect.startConnection();
		Connection c = dbConnect.getConnection();
		
		PreparedStatement preparedStatement = c.prepareStatement(queryScheduleDetails);
		PreparedStatement preparedStatement2 = c.prepareStatement(querySameEmployeeTime);
		preparedStatement2.setInt(1, scheduleID);
		preparedStatement.setInt(1, scheduleID);
		ResultSet rs = preparedStatement.executeQuery();
		ResultSet rs2 = preparedStatement2.executeQuery();
		
		while (rs2.next()) {
			sameTimeCount = rs2.getInt("total");
		}

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
				// Break evaluation
				strFirstBreak = evaluateBreak(strFirstBreak, false); 
				breaks.add(strFirstBreak);
				//-----------------
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
				//Break evaluation//
				strFirstBreak = evaluateBreak(strFirstBreak, false); 
				breaks.add(strFirstBreak);
				//--------------------------
				//break evaluation
				String strLunch = intLunch + ":" + startTime.substring(3, 5);
				strLunch = evaluateBreak(strLunch, true); 
				breaks.add(strLunch);
				//----------------------------------
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
				//Break Evaluation
				strFirstBreak = evaluateBreak(strFirstBreak, false); 
				breaks.add(strFirstBreak);
				//--------------------------
				String strLunch = intLunch + ":" + startTime.substring(3, 5);
				//Break Evaluation
				strLunch = evaluateBreak(strLunch, true); 
				breaks.add(strLunch);
				//-----------------------------
				String strSecondBreak = intSecondBreak + ":" + startTime.substring(3, 5);
				//Break Evaluation
				strSecondBreak = evaluateBreak(strSecondBreak, false); 
				breaks.add(strSecondBreak);
				//-----------------------------------
				System.out.println("|Employee Name: " + name + " | Start Time: " + strStartTime + "| End Time: " + strEndTime + " |First Break: " + strFirstBreak + " |Lunch: " + strLunch + " |SecondBreak:" + strSecondBreak);
				System.out.println("________________________________________________________________________________________________________________________________________________");

			}
			
		}

		
		c.close();
		breaks.clear();
	}
	
	
}
