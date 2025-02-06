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
	
	
	/**
	 * Constructor
	 */
	public EmployeeDataTransfer() {
		employeeID = 0;
		employeeName = "";
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

}
