package sg.edu.iss.team8.leaveApp.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import sg.edu.iss.team8.leaveApp.helpers.LeaveEnum;
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
	public int calculatePeriodDays(Leave leave) {
		LocalDate startDate = leave.getStartDate();
		LocalDate endDate = leave.getEndDate();
		//LocalDate startDate = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		//LocalDate endDate = end.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		Period period = Period.between(startDate, endDate);
		int periodDays = Math.abs(period.getDays());
		return periodDays;
	}
	
	@Transactional
	public int calculateDaysToExclude(Leave leave) {
		LeaveEnum leaveType = leave.getLeaveType();
		LocalDate startDate = leave.getStartDate();
		LocalDate endDate = leave.getEndDate();
		//LocalDate startDate = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		//LocalDate endDate = end.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		Period period = Period.between(startDate, endDate);
		int periodDays = Math.abs(period.getDays());
		int daysToExclude = 0;
		if (leaveType == LeaveEnum.ANNUAL && periodDays <= 14) {
			List<LocalDate> totalDates = new ArrayList<>();
			while (!startDate.isAfter(endDate)) {
				totalDates.add(startDate);
				startDate = startDate.plusDays(1);
			}
			for (LocalDate date : totalDates) {
				DayOfWeek day = date.getDayOfWeek();
				if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
					daysToExclude++;
				}
			}
		}
		
		return daysToExclude;
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
