package sg.edu.iss.team8.leaveApp.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import sg.edu.iss.team8.leaveApp.model.Leave;

public interface LeaveService {
	public List<Leave> findLeaveByUID(Integer userId); 
	
	public Leave findLeaveByUIDAndLID(Integer userid, Integer leaveId);
	
	public ArrayList<Leave> findPendingLeaveByUID(Integer userId);
	
	public void updateLeave(Leave leave);
	
	public List<Leave> findLeaveWithinDateRange(List<Leave> lList, Date start, Date end);
	
	public Leave findLeaveById(Integer leaveId);

}
