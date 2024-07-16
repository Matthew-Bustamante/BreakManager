package employeeManagement;

public class EmployeeDataTransfer {
	private int employeeID;
	private String employeeName;
	private String employeeStartTime;
	private String employeeEndTime;
	
	
	public EmployeeDataTransfer() {
		employeeID = 0;
		employeeName = "";
		employeeStartTime = "";
		employeeEndTime = "";
	}


	public int getEmployeeID() {
		return employeeID;
	}


	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}


	public String getEmployeeName() {
		return employeeName;
	}


	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}


	public String getEmployeeStartTime() {
		return employeeStartTime;
	}


	public void setEmployeeStartTime(String employeeStartTime) {
		this.employeeStartTime = employeeStartTime;
	}


	public String getEmployeeEndTime() {
		return employeeEndTime;
	}


	public void setEmployeeEndTime(String employeeEndTime) {
		this.employeeEndTime = employeeEndTime;
	}
	
	
}
