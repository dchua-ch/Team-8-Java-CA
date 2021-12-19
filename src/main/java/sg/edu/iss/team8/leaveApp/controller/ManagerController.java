package sg.edu.iss.team8.leaveApp.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	
	// Routes to view which displays list of subordinates leaves which are under "APPLIED" status
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
	@RequestMapping(value = "/course/display/{uid}/{lid}", method = RequestMethod.GET)
	public String displayLeaveDetails(@PathVariable("uid") Integer userId, @PathVariable("lid") Integer leaveId, Model model) {
		Employee employee = eService.findByUserId(userId);
		Leave leave = lService.findLeaveByUIDAndLID(userId, leaveId); 
		model.addAttribute("employee", employee); 
		model.addAttribute("leave", leave); 
		model.addAttribute("outcome", new Outcome()); 
		return "manager-leave-details";
	}
	
	// 
	@RequestMapping(value = "/course/edit/{uid}/{lid}", method= RequestMethod.POST)
	public String approveOrRejectLeave(@PathVariable("uid") Integer userId, @PathVariable("lid") Integer leaveId, @ModelAttribute("outcome") Outcome outcome) {
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
}
