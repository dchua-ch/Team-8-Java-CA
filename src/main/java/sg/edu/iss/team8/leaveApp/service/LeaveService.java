package sg.edu.iss.team8.leaveApp.service;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

import sg.edu.iss.team8.leaveApp.model.Leave;

public interface LeaveService {
	
	public Leave findLeaveById(Integer leaveId);
	
	public List<Leave> findLeaveByUserId(Integer userId);
	
	public List<Leave> findPendingLeaveByUserId(Integer userId);
	
	public List<Leave> findApplicableLeaveByUserId(Integer userId);
	
	public int calculatePeriodDays(Leave leave);
	
	public int calculateDaysToExclude(Leave leave);
	
	public Leave submitLeave(Leave leave);
	
	public void updateLeave(Leave leave);
	
	public void deleteLeave(Leave leave);
	
	public void cancelLeave(Leave leave);

	public List<Leave> findLeaveByUID(Integer userId); 
	
	public Leave findLeaveByUIDAndLID(Integer userid, Integer leaveId);
	
	public ArrayList<Leave> findPendingLeaveByUID(Integer userId);
	
	public List<Leave> findLeaveWithinDateRange(List<Leave> lList, LocalDate start, LocalDate end);

	public List<Leave> getLeaveByDates(List<Leave> lList, LocalDate start, LocalDate end);

}
