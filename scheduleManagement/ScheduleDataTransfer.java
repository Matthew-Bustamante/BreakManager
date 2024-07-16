package scheduleManagement;

public class ScheduleDataTransfer {
	
	private int scheduleID;
	private String scheduleDate;
	
	public ScheduleDataTransfer() {
		scheduleID = 0;
		scheduleDate = "";
	}

	public int getScheduleID() {
		return scheduleID;
	}

	public void setScheduleID(int scheduleID) {
		this.scheduleID = scheduleID;
	}

	public String getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(String scheduleDate) {
		this.scheduleDate = scheduleDate;
	}
}
