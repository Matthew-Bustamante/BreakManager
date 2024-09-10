package scheduleManagement;

/**
 * ScheduleDataTransfer class:
 * this class acts as a data transfer object for all schedule data.
 * Data from schedules include:
 * schedule ID (int)
 * schedule date (string)
 *
 */
public class ScheduleDataTransfer {
	
	private int scheduleID;
	private String scheduleDate;
	
	/**
	 * Constructor
	 */
	public ScheduleDataTransfer() {
		scheduleID = 0;
		scheduleDate = "";
	}
	
	/**
	 * returns the schedule id
	 * @return schedule id (int)
	 */
	public int getScheduleID() {
		return scheduleID;
	}
	
	/**
	 * sets the schedule id to a new schedule id
	 * @param schedule ID (int)
	 */
	public void setScheduleID(int scheduleID) {
		this.scheduleID = scheduleID;
	}
	
	/**
	 * returns the schedule date
	 * @return schedule date (string)
	 */
	public String getScheduleDate() {
		return scheduleDate;
	}
	
	/**
	 * sets the schedule date to a new schedule date
	 * @param schedule date (string)
	 */
	public void setScheduleDate(String scheduleDate) {
		this.scheduleDate = scheduleDate;
	}
}
