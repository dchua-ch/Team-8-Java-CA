package sg.edu.iss.team8.leaveApp.service;

import java.util.ArrayList;
import java.util.List;

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
	public List<Leave> findLeaveByUserId(Integer userId) {
		return lrepo.findLeaveByUserId(userId);
	}
	
	@Transactional
	public List<Leave> findPendingLeaveByUserId(Integer userId) {
		List<Leave> userLeaves = lrepo.findLeaveByUserId(userId);
		List<Leave> pendingLeaves = new ArrayList<>();
		for (Leave leave : userLeaves) {
			if (leave.getStatus() == StatusEnum.APPLIED || 
					leave.getStatus() == StatusEnum.UPDATED) {
				pendingLeaves.add(leave);
			}
		}
		return pendingLeaves;
	}
	
	@Transactional
	public List<Leave> findApplicableLeaveByUserId(Integer userId) {
		List<Leave> userLeaves = lrepo.findLeaveByUserId(userId);
		List<Leave> applicableLeaves = new ArrayList<>();
		for (Leave leave : userLeaves) {
			if (leave.getStatus() == StatusEnum.APPLIED ||
					leave.getStatus() == StatusEnum.APPROVED ||
					leave.getStatus() == StatusEnum.UPDATED) {
				applicableLeaves.add(leave);
			}
		}
		return applicableLeaves;
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
