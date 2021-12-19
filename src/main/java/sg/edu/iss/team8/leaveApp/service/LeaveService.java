package sg.edu.iss.team8.leaveApp.service;

import sg.edu.iss.team8.leaveApp.helpers.StatusEnum;
import sg.edu.iss.team8.leaveApp.model.Leave;

public interface LeaveService {
	
	public Leave findLeaveById(Integer leaveId);
	
	public Leave submitLeave(Leave leave);
	
	public void updateLeave(Leave leave);
	
	public void deleteLeave(Leave leave);
	
	public void cancelLeave(Leave leave);

}
