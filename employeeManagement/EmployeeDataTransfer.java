package employeeManagement;

/**
 * EmployeeDataTransfer class:
 * This class is used as a transfer object for employee data.  This allows for data about employees
 * to be shared with other classes or with the UI
 *
 */
public class EmployeeDataTransfer {
	private int employeeID;
	private String employeeName;
	private String employeeStartTime;
	private String employeeEndTime;
	
	/**
	 * Constructor
	 */
	public EmployeeDataTransfer() {
		employeeID = 0;
		employeeName = "";
		employeeStartTime = "";
		employeeEndTime = "";
	}

	/**
	 * returns the employee ID
	 * @return employeeID (int)
	 */
	public int getEmployeeID() {
		return employeeID;
	}

	/**
	 * sets the employeeID to a new employeeID
	 * @param new employeeID (string)
	 */
	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}

	/**
	 * returns an employee's name
	 * @return employee's name (string)
	 */
	public String getEmployeeName() {
		return employeeName;
	}

	/**
	 * Sets employee name to a new name 
	 * @param new employeen name (string)
	 */
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	/**
	 * returns an employee's start-time
	 * @return employee's start-time (string)
	 */
	public String getEmployeeStartTime() {
		return employeeStartTime;
	}

	/**
	 * sets an employee's start-time to a new start-time
	 * @param employees start time (string)
	 */
	public void setEmployeeStartTime(String employeeStartTime) {
		this.employeeStartTime = employeeStartTime;
	}

	/**
	 * returns an employee's end-time
	 * @return employee's end-time (string)
	 */
	public String getEmployeeEndTime() {
		return employeeEndTime;
	}

	/**
	 * sets an employee's end-time to a new end-time
	 * @param employee end time (string)
	 */
	public void setEmployeeEndTime(String employeeEndTime) {
		this.employeeEndTime = employeeEndTime;
	}
	
	
}
