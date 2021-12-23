package sg.edu.iss.team8.leaveApp.service;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.Calendar;

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
			if (l.getStatus() == StatusEnum.APPLIED || l.getStatus() == StatusEnum.UPDATED) {
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

	@Override
	public List<Leave> findLeaveWithinDateRange(List<Leave> lList, LocalDate start, LocalDate end) {
		List<Leave> withinRange = new ArrayList<Leave>();

		for (Leave l : lList) {
			if (l.getStatus() == StatusEnum.APPLIED || l.getStatus() == StatusEnum.APPROVED) {
				if (l.getStartDate().isBefore(start) && l.getEndDate().isBefore(end) && l.getEndDate().isAfter(start)) {
					withinRange.add(l);
				} else if (l.getStartDate().isAfter(start) && l.getStartDate().isBefore(end)
						&& l.getEndDate().isAfter(end)) {
					withinRange.add(l);
				} else if (l.getStartDate().isAfter(start) && l.getEndDate().isBefore(end)) {
					withinRange.add(l);
				} else if (l.getStartDate().isEqual(start) && l.getEndDate().isEqual(end)) {
					withinRange.add(l);
				}
			}
		}
		return withinRange;
	}

	@Override
	public List<Leave> getLeaveByDates(List<Leave> lList, LocalDate start, LocalDate end) {
		List<Leave> leaves = new ArrayList<Leave>(); 
		
		for (Leave l : lList) {
			if (l.getStatus() == StatusEnum.APPROVED) {
				if (l.getStartDate().isAfter(start) && l.getEndDate().isBefore(end)) {
					leaves.add(l);
				} else if (l.getStartDate().isEqual(start) && l.getEndDate().isEqual(end)) {
					leaves.add(l); 
				}
			}
		}
		return leaves;
	}

	
	@Override
	public Leave findLeaveById(Integer leaveId) {
		return lrepo.findLeaveByID(leaveId);
	}
}
