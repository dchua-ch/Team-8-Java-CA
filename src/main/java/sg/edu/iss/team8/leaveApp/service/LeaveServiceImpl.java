package sg.edu.iss.team8.leaveApp.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import sg.edu.iss.team8.leaveApp.helpers.StatusEnum;
import sg.edu.iss.team8.leaveApp.model.Leave;
import sg.edu.iss.team8.leaveApp.repo.LeaveRepo;
import sg.edu.iss.team8.leaveApp.repo.UserRepo;

@Service
public class LeaveServiceImpl implements LeaveService {
	
	@Resource
	private UserRepo urepo; 
	
	@Resource 
	private LeaveRepo lrepo; 
	
	@Override
	public List<Leave> findLeaveByUID(Integer userId) {
		return urepo.findLeaveByUID(userId); 
	}
	
	@Override
	public Leave findLeaveByUIDAndLID(Integer userId, Integer leaveId) {
		List<Leave> lList = findLeaveByUID(userId); 
		for (Leave l : lList) {
			if (l.getLeaveId() == leaveId) {
				return l;  
			}
		}
		return null;
	}
	
	@Override
	public ArrayList<Leave> findPendingLeaveByUID(Integer userId) {
		List<Leave> lList = urepo.findLeaveByUID(userId); 
		ArrayList<Leave> pendingList = new ArrayList<Leave>(); 
		for (Leave l : lList) {
			if (l.getStatus() == StatusEnum.APPLIED) {
				pendingList.add(l); 
			}
		}
		return pendingList; 
	}
	
	@Transactional
	@Modifying
	@Override
	public void updateLeave(Leave leave) {
		lrepo.saveAndFlush(leave);
	}
}
