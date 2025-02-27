package main;
import java.util.Scanner;

import breakManagement.BreakManager;
import databaseManagement. * ;
import employeeManagement.EmployeeDataTransfer;
import employeeManagement.EmployeeManager;
import scheduleManagement.ScheduleDataTransfer;
import scheduleManagement.ScheduleManager;
import timeManagement.TimeManager;
import java.sql.SQLException;
/**
 * Main class that will create and initialize objects as well as contain the UI
 * for the application
 *
 */
public class Main {
	// Main Method
	public static void main(String[] args)throws SQLException {
		//----------------Objects------------------//
		ScheduleManager sm = new ScheduleManager();
		EmployeeManager em = new EmployeeManager();
		BreakManager bm = new BreakManager();
		TimeManager tm = new TimeManager();
		//-----------------------------------------//
		Scanner sc = new Scanner(System.in);
		
		boolean isExit = true;
		
		while(isExit) {
			System.out.println("Welcome Please Enter the Option You Would Like To Do");
			System.out.println("1) Manage Schedules");
			System.out.println("2) Manage Employees");
			System.out.println("3) Exit");
			String input = sc.nextLine();
			
			//-----------------------------Schedule Manager Menu----------------------------//
			if(input.equals("1")) {
				boolean isExitTwo = true;
				while(isExitTwo) {
					System.out.println("Welcome to the Schedule Manager please select an option");
					System.out.println("1) Create a new Schedule");
					System.out.println("2) View all schedules");
					System.out.println("3) View a schedule details");
					System.out.println("4) Change Schedule Date");
					System.out.println("5) Delete a schedule");
					System.out.println("6) Add Employees to Schedule");
					System.out.println("7) Create Break Schedule");
					System.out.println("8) Go back");
					String input2 = sc.nextLine();
					
					if(input2.equals("1")) {
						System.out.println("Enter a valid date in the format yyyy-mm-dd: ");
						String inputDate = sc.nextLine();
						sm.createSchedule(inputDate);
					}
					
					else if(input2.equals("2")) {
						sm.readSchedule();
					}
					
					else if (input2.equals("3")) {
						System.out.println("Enter an Existing Schedule Date in the Format yyyy-mm-dd");
						String inputDate = sc.nextLine();
						
						if(sm.scheduleExists(inputDate)) {
							ScheduleDataTransfer scheduleResults = sm.setScheduleID(inputDate);
							int scheduleID = scheduleResults.getScheduleID();
							sm.viewScheduleDetails(scheduleID);
							
						}
						else {
							System.out.println("Date: " + inputDate + "Does not Exist returning back to schedule manager");
						}
					}
					
					else if (input2.equals("4")){
						System.out.println("Enter an existing Date in the Format yyyy-mm-dd: ");
						String currentDate = sc.nextLine();
						if(sm.scheduleExists(currentDate)) {
							System.out.println("Enter a New Schedule Date in the Formate yyyy-mm-dd: ");
							String newDate = sc.nextLine();
							sm.updateSchedule(currentDate, newDate);
							System.out.println("Schedule Successfully Updated");
						}
						else {
							System.out.println("Schedule Date: '" + currentDate +"' does not exist please try again");
						}
						//System.out.println("Enter a new date in the formate yyyy-mm-dd: ");
						//String newDate = sc.nextLine();
						//sm.updateSchedule(currentDate, newDate);
					}
					else if (input2.equals("5")){
						System.out.println("Enter an existing Date to Delete in the Format yyyyy-mm-dd: ");
						String inputDate = sc.nextLine();
						if(sm.scheduleExists(inputDate)) {
							System.out.println("Are you sure you want to delete schedule? (Y/N)");
							String inputConfirm = sc.nextLine();
							if(inputConfirm.equals("Y")) {
								sm.deleteSchedule(inputDate);
								System.out.println("Schedule Sucessfully Deleted");
							}
							else {
								System.out.println("Schedule was not deleted returing back to schedule manager");
							}
						}
						else {
							System.out.println("Schedule Date: '" + inputDate +"' does not exist please try again");
						}
						
					}
					else if (input2.equals("6")){
						boolean isExitThree = true;
						while(isExitThree) {
							System.out.println("Select a Schedule or press 1 to exit");
							sm.readSchedule();
							String userInput = sc.nextLine();
							boolean scheduleInDB = sm.scheduleExists(userInput);
							
							if(scheduleInDB == true) {
								//set the schedule and retireve schedule ID
								ScheduleDataTransfer scheduleResults = sm.setScheduleID(userInput);
								
								boolean isExitFour = true;
								while(isExitFour) {
									System.out.println("Select employees and type done when done");
									em.readEmployee();
									String userInputEmployeeName = sc.nextLine();
									boolean employeeInDB = em.employeeExists(userInputEmployeeName);
									
									if(employeeInDB == true) {
										EmployeeDataTransfer employeeResults = em.setEmpID(userInputEmployeeName);
										int employeeID = employeeResults.getEmployeeID();
										int scheduleID = scheduleResults.getScheduleID();
										System.out.println("Please Enter Employee's Start Time In the Format HH:MM:SS");
										String userInputStartTime = sc.nextLine();
										System.out.println("Is This Time AM or PM? ");
										String startTimeAMorPM = sc.nextLine();
										System.out.println("Please Enter Employee's End Time In the Format HH:MM:SS");
										String userInputEndTime = sc.nextLine();
										System.out.println("Is This Time AM or PM?");
										String endTimeAMorPM = sc.nextLine();
										//em.updateEmployeeTime(userInputStartTime, userInputEndTime, userInputEmployeeName, startTimeAMorPM, endTimeAMorPM);
										tm.createTime(userInputStartTime, userInputEndTime, startTimeAMorPM, endTimeAMorPM, scheduleID, employeeID);
										sm.addEmployeeToSchedule(scheduleID, employeeID);
										System.out.println("Employee Added to Schedule");
									}
									
									if(userInputEmployeeName.equals("done")) {
										isExitFour = false;
										break;
									}
								}
							}
							//else if(scheduleInDB == false) {
								//System.out.println("Schedule Does Not Exist");
							//}
								
							else if(userInput.equals("1")) {
								isExitThree = false;
								break;
								}
							}
						
						
					}
					else if (input2.equals("7")) {
						boolean notExit = true;

						while(notExit) {
							System.out.println("Select a Schedule or press 1 to exit");
							sm.readSchedule();
							String userInput = sc.nextLine();
							boolean scheduleInDB = sm.scheduleExists(userInput);
							if(userInput.equals("1")) {
								break;
							}
							
							else if (scheduleInDB == true) {
								//BreakManager bm = new BreakManager();
								ScheduleDataTransfer scheduleResults = sm.setScheduleID(userInput);
								bm.scheduleBreaks(scheduleResults.getScheduleID());
							}
							else if (scheduleInDB == false) {
								System.out.println("Schedule: " + userInput + "Does not exist please try again");
							}
						}
					}
					
					else if(input2.equals("8")) {
						isExitTwo = false;
						break;
					}
					else {
						System.out.println("Invalid Option Please Try Again");
					}
				}
			}
			//-------------------------------------------------------------------------//
			
			
			//---------------------Employee Manager Menu----------------------------------//
			if(input.equals("2")) {
				boolean isExitThree = true;
			
				while(isExitThree) {
					System.out.println("Welcome to the Employee Manager Please Select an Option: ");
					System.out.println("1) Add new employee");
					System.out.println("2) View All Current Employees");
					System.out.println("3) Update Employee Info");
					System.out.println("4) Remove Employee");
					System.out.println("5) Go Back");
					String input3 = sc.nextLine();
					
					// -----------CREATE A NEW EMPLOYEE-------------//
					if(input3.equals("1")) {
						System.out.println("Enter a name of Employee: ");
						String inputName = sc.nextLine();
						System.out.println("Employees Was Successfully Created");
						em.createEmployee(inputName);
					}
					//--------------------------------------------------//
					
					//-------------READ ALL EMPLOYEES------------------//
					else if(input3.equals("2")) {
						em.readEmployee();
					}
					//--------------------------------------------------//
					//--------------UPDATE EMPLOYEES---------------//
					else if(input3.equals("3")) {
						System.out.println("Enter an existing Employee Name: ");
						String currentName = sc.nextLine();
						if(em.employeeExists(currentName)) {
							System.out.println("Enter a New Employee Name: ");
							String newName = sc.nextLine();
							em.updateEmployee(currentName, newName);
						}
						else {
							System.out.println("Employee: '" + currentName +"' does not exist please try again");
						}
						
					}
					//--------------------------------------------------------------------------------------------//
					
					//---------------DELETE EMPLOYEES------------------------//
					else if(input3.equals("4")) {
						System.out.println("Enter an existing Employee Name: ");
						String inputName = sc.nextLine();
						
						if(em.employeeExists(inputName)) {
							System.out.println("Are you sure you want to Delete Employee:? (Y/N)");
							String inputConfirmation = sc.nextLine();
							if(inputConfirmation.equals("Y")) {
								em.deleteEmployee(inputName);
								System.out.println("Employee was Successfully Deleted");
							}
							else {
								System.out.println("Employee was not Deleted Returning Back to Employee Manager");
							}
						}
						else {
							System.out.println("Employee: '" + inputName +"' does not exist please try again");
						}
						//-----------------------------------------------------------------------------------------//
						
					}
					else if(input3.equals("5")) {
						isExitThree = false;
						break;
					}
					else {
						System.out.println("Invalid Option Please Try Again");
					}
				}
			}
			//-----------------------------------------------------------------------------------------//
			
			
			
			
			if(input.equals("3")) {
				System.out.println("Goodbye!!");
				break;
			}
			else {
				System.out.println("Invalid Option Please Try Again");
			}
		}
	}
	
}
