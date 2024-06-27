package main;
import java.util.Scanner;

import databaseManagement. * ;
import scheduleManagement.ScheduleManager;
import java.sql.SQLException;
/**
 * Main class that will create and initialize objects as well as contain the UI
 * for the application
 * @author Matthew-Bustamante
 *
 */
public class Main {
	// Main Method
	public static void main(String[] args)throws SQLException {
		ScheduleManager sm = new ScheduleManager();
		Scanner sc = new Scanner(System.in);
		
		boolean isExit = true;
		
		while(isExit) {
			System.out.println("Welcome Please Enter the Option You Would Like To Do");
			System.out.println("1) Manage Schedules");
			System.out.println("2) Exit");
			String input = sc.nextLine();
			
			//-----------------------------Schedule Manager Menu----------------------------
			if(input.equals("1")) {
				boolean isExitTwo = true;
				while(isExitTwo) {
				System.out.println("Welcome to the Schedule Manager please select an option");
				System.out.println("1) Create a new Schedule");
				System.out.println("2) View all schedules");
				System.out.println("3) Change Schedule Date");
				System.out.println("4) Delete a schedule");
				System.out.println("5) Add Employees to Schedule");
				System.out.println("6) Go back");
				String input2 = sc.nextLine();
				
				if(input2.equals("1")) {
					System.out.println("Enter a valid date in the format yyyy-mm-dd: ");
					String inputDate = sc.nextLine();
					sm.createSchedule(inputDate);
				}
				
				if(input2.equals("2")) {
					sm.readSchedule();
				}
				
				if (input2.equals("3")){
					System.out.println("Enter an existing Date in the Format yyyyy-mm-dd: ");
					String currentDate = sc.nextLine();
					System.out.println("Enter a new date in the formate yyyy-mm-dd: ");
					String newDate = sc.nextLine();
					sm.updateSchedule(currentDate, newDate);
				}
				if (input2.equals("4")){
					System.out.println("Enter an existing Date to Delete in the Format yyyyy-mm-dd: ");
					String inputDate = sc.nextLine();
					sm.deleteSchedule(inputDate);
				}
				if (input2.equals("5")){
					System.out.println("Not Implemented Yet");
					
				}
				
				if(input2.equals("6")) {
					isExitTwo = false;
					break;
				}
				}
			}
			//-------------------------------------------------------------------------
			
			if(input.equals("2")) {
				System.out.println("Goodbye!!");
				break;
			}
		}
	}
	
}
