package sg.edu.iss.team8.leaveApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.iss.team8.leaveApp.helpers.StatusEnum;
import sg.edu.iss.team8.leaveApp.model.Leave;
import sg.edu.iss.team8.leaveApp.repo.LeaveRepo;

@Service
public class LeaveServiceImpl implements LeaveService {
	
	@Autowired
	LeaveRepo lrepo;
	
	@Transactional
	public Leave findLeaveById(Integer leaveId) {
		return lrepo.findLeaveById(leaveId);
	}
	
	@Transactional
	public Leave submitLeave(Leave leave) {
		
		leave.setStatus(StatusEnum.APPLIED);
		return lrepo.saveAndFlush(leave);
	}

	@Transactional
	public void updateLeave(Leave leave) {
		
		leave.setStatus(StatusEnum.UPDATED);
		lrepo.saveAndFlush(leave);
	}
	
	@Transactional
	public void deleteLeave(Leave leave) {
		leave.setStatus(StatusEnum.DELETED);
		lrepo.saveAndFlush(leave);
		return;
	}
	
	@Transactional
	public void cancelLeave(Leave leave) {
		leave.setStatus(StatusEnum.CANCELLED);
		lrepo.saveAndFlush(leave);
		return;
	}

}
