package sg.edu.iss.team8.leaveApp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sg.edu.iss.team8.leaveApp.helpers.Outcome;
import sg.edu.iss.team8.leaveApp.helpers.StatusEnum;
import sg.edu.iss.team8.leaveApp.model.Employee;
import sg.edu.iss.team8.leaveApp.model.Leave;
import sg.edu.iss.team8.leaveApp.service.EmployeeService;
import sg.edu.iss.team8.leaveApp.service.LeaveService;

@Controller
@RequestMapping(value = "/manager")
public class ManagerController {

	@Autowired
	EmployeeService eService;

	@Autowired
	LeaveService lService;

	// Routes to view which displays list of subordinates leaves which are under
	// "APPLIED" status
	@RequestMapping(value = "/pending/{uid}")
	public String pendingApprovals(@PathVariable("uid") Integer userId, Model model /* HttpSession session */) {
		// UserSession usession = (UserSession) session.getAttribute("usession");
		HashMap<Employee, List<Leave>> hm = new HashMap<Employee, List<Leave>>();
		for (Employee employee : eService.findSubordinates(userId)) {
			List<Leave> llist = lService.findPendingLeaveByUID(employee.getUserId());
			hm.put(employee, llist);
		}

		model.addAttribute("pendinghistory", hm);
		return "manager-pending";

	}

	// Routes to view which displays leave details for selected employee
	@RequestMapping(value = "/leave/display/{uid}/{lid}", method = RequestMethod.GET)
	public String displayLeaveDetails(@PathVariable("uid") Integer userId, @PathVariable("lid") Integer leaveId,
			Model model) {
		Employee employee = eService.findByUserId(userId);
		Leave leave = lService.findLeaveByUIDAndLID(userId, leaveId);

		HashMap<Employee, Leave> otherSubordinatesLeaves = new HashMap<Employee, Leave>();
		for (Employee e : eService.findSubordinates(employee.getReportsTo())) {
			List<Leave> llist = lService.findLeaveByUID(e.getUserId());
			List<Leave> withinRange = lService.findLeaveWithinDateRange(llist, leave.getStartDate(),
					leave.getEndDate());

			for (Leave l : withinRange) {
				if (l.getEmployee() != employee) {
					otherSubordinatesLeaves.put(e, l);
				}
			}
		}

		model.addAttribute("employee", employee);
		model.addAttribute("leave", leave);
		model.addAttribute("others", otherSubordinatesLeaves);
		model.addAttribute("outcome", new Outcome());
		return "manager-leave-details";
	}

	// Updates the leave status based on approval or rejection, routes back to
	// pending approvals page
	@RequestMapping(value = "/leave/edit/{uid}/{lid}", method = RequestMethod.POST)
	public String approveOrRejectLeave(@PathVariable("uid") Integer userId, @PathVariable("lid") Integer leaveId,
			@ModelAttribute("outcome") Outcome outcome) {
		Leave leave = lService.findLeaveByUIDAndLID(userId, leaveId);
		if (outcome.getDecision().trim().equalsIgnoreCase(StatusEnum.APPROVED.toString())) {
			leave.setStatus(StatusEnum.APPROVED);
		} else {
			leave.setStatus(StatusEnum.REJECTED);
		}
		leave.setComments(outcome.getComments());

		lService.updateLeave(leave);

		Employee employee = eService.findByUserId(userId);
		Integer managerId = employee.getReportsTo();

		return "forward:/manager/pending/" + managerId;
	}
	// Routes to view which displays the leave history for subordinates (i.e.
		// APPROVED or REJECTED or ARCHIVED leaves)
		@RequestMapping(value = "/leave-history/{uid}", method = RequestMethod.GET)
		public String subLeaveHistory(@PathVariable("uid") Integer userId, Model model) {
			List<Employee> subordinates = eService.findSubordinates(userId);
			HashMap<Employee, List<Leave>> hm = new HashMap<Employee, List<Leave>>();
			for (Employee employee : subordinates) {
				List<Leave> lList = lService.findLeaveByUID(employee.getUserId());
				List<Leave> lRecord = new ArrayList<Leave>();
				for (Leave leave : lList) {
					lRecord.add(leave);
//					if (leave.getStatus() == StatusEnum.APPROVED || leave.getStatus() == StatusEnum.REJECTED) {
//						lRecord.add(leave);
//					}
				}
				hm.put(employee, lRecord);
			}

			model.addAttribute("leavehistory", hm);
			return "manager-history";
		}
		
		
		@RequestMapping(value="/leave-history/display/{uid}/{lid}", method= RequestMethod.GET)
		public String displayPastLeaveDetails(@PathVariable("uid") Integer userId, @PathVariable("lid") Integer leaveId, Model model) {
			Leave leave = lService.findLeaveById(leaveId);
			Employee employee = eService.findByUserId(userId); 
			
			model.addAttribute("employee", employee);
			model.addAttribute("leave", leave);
			
			return "manager-leave-history-details";
		}
}
