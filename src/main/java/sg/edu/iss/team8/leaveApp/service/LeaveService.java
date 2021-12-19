package sg.edu.iss.team8.leaveApp.service;

import java.util.ArrayList;
import java.util.List;

import sg.edu.iss.team8.leaveApp.model.Leave;

public interface LeaveService {
	List<Leave> findLeaveByUID(Integer userId); 
	
	Leave findLeaveByUIDAndLID(Integer userid, Integer leaveId);
	
	ArrayList<Leave> findPendingLeaveByUID(Integer userId);
	
	void updateLeave(Leave leave);
}
