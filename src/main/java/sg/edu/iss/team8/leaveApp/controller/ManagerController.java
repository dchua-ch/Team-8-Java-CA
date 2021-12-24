package sg.edu.iss.team8.leaveApp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpSession;
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
import org.springframework.web.servlet.ModelAndView;

import sg.edu.iss.team8.leaveApp.helpers.LeaveEnum;
import sg.edu.iss.team8.leaveApp.helpers.MonthYear;
import sg.edu.iss.team8.leaveApp.helpers.OTEnum;
import sg.edu.iss.team8.leaveApp.helpers.Outcome;
import sg.edu.iss.team8.leaveApp.helpers.StatusEnum;
import sg.edu.iss.team8.leaveApp.model.Employee;
import sg.edu.iss.team8.leaveApp.model.Leave;
import sg.edu.iss.team8.leaveApp.model.OvertimeHours;
import sg.edu.iss.team8.leaveApp.model.User;
import sg.edu.iss.team8.leaveApp.repo.OvertimeHoursRepo;
import sg.edu.iss.team8.leaveApp.repo.UserRepo;
import sg.edu.iss.team8.leaveApp.service.EmployeeService;
import sg.edu.iss.team8.leaveApp.service.LeaveService;
import sg.edu.iss.team8.leaveApp.service.OvertimeHoursService;
import sg.edu.iss.team8.leaveApp.service.OvertimeHoursServiceImpl;
import sg.edu.iss.team8.leaveApp.service.UserService;
import sg.edu.iss.team8.leaveApp.validator.LeaveValidator;
import sg.edu.iss.team8.leaveApp.validator.MonthYearValidator;

@Controller
@RequestMapping(value = "/manager")
public class ManagerController {

	@Autowired
	EmployeeService eService;

	@Autowired
	LeaveService lService;

	// Routes to view which displays list of subordinates leaves which are under
	// "APPLIED" status
	@RequestMapping(value = "/pending")
	public String pendingApprovals(Model model, HttpSession session ) {
		UserSession usession = (UserSession) session.getAttribute("usession");
		HashMap<Employee, List<Leave>> hm = new HashMap<Employee, List<Leave>>();
		for (Employee employee : eService.findSubordinates(usession.getUser().userId)) {
			List<Leave> llist = lService.findPendingLeaveByUID(employee.getUserId());
			hm.put(employee, llist);
		}

		model.addAttribute("pendinghistory", hm);
		return "manager-pending";

	}

	// Routes to view which displays leave details for selected employee
	@RequestMapping(value = "/leave/display/{uid}/{lid}", method = RequestMethod.GET)
	public ModelAndView displayLeaveDetails(@PathVariable("uid") Integer userId, @PathVariable("lid") Integer leaveId) {
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

		ModelAndView mav = new ModelAndView("manager-leave-details", "leave", leave);
		mav.addObject("outcome", new Outcome()); 
		mav.addObject("employee", employee); 
		mav.addObject("others", otherSubordinatesLeaves);
		return mav;
	}
	
	// Updates the leave status based on approval or rejection, routes back to
	// pending approvals page
	@RequestMapping(value = "/leave/edit/{uid}/{lid}", method = RequestMethod.POST)
	public String approveOrRejectLeave(@PathVariable("uid") Integer userId, @PathVariable("lid") Integer leaveId,
			@ModelAttribute("outcome") @Valid Outcome outcome, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "manager-leave-details";
		} else {
			Leave leave = lService.findLeaveByUIDAndLID(userId, leaveId);
			if (outcome.getDecision().trim().equalsIgnoreCase(StatusEnum.APPROVED.toString())) {
				leave.setStatus(StatusEnum.APPROVED);
			} else {
				//<--- Additional code included to compensate leave
				int periodDays = lService.calculatePeriodDays(leave);
				int daysToExclude = lService.calculateDaysToExclude(leave);
				int compensation = periodDays - daysToExclude;
				LeaveEnum leaveType = leave.getLeaveType();
				Employee employee = leave.getEmployee();
				if (leaveType == LeaveEnum.ANNUAL)
				{
					employee.setAnnualLeaveN(employee.getAnnualLeaveN() + compensation);
				}
				else if (leaveType == LeaveEnum.MEDICAL)
				{
					employee.setMedicalLeaveN(employee.getMedicalLeaveN() + compensation);
				}
				else if (leaveType == LeaveEnum.COMPENSATION)
				{
					employee.setCompLeaveN(employee.getCompLeaveN() + compensation);
				}
				urepo.saveAndFlush(employee);
				//<--- Additional code included to compensate leave
				leave.setStatus(StatusEnum.REJECTED);
			}
			leave.setComments(outcome.getComments());

			lService.updateLeave(leave);

			Employee employee = eService.findByUserId(userId);
			Integer managerId = employee.getReportsTo();

			return "forward:/manager/pending/";
		}
	}

	// Routes to view which displays the leave history for subordinates (i.e.
	// APPROVED or REJECTED or ARCHIVED leaves)
	@RequestMapping(value = "/leave-history", method = RequestMethod.GET)
	public String subLeaveHistory(HttpSession session, Model model) {
		UserSession usession = (UserSession) session.getAttribute("usession");
		List<Employee> subordinates = eService.findSubordinates(usession.getUser().userId);
		HashMap<Employee, List<Leave>> hm = new HashMap<Employee, List<Leave>>();
		for (Employee employee : subordinates) {
			List<Leave> lList = lService.findLeaveByUID(employee.getUserId());
			List<Leave> lRecord = new ArrayList<Leave>();
			for (Leave leave : lList) {
				if (leave.getStatus() == StatusEnum.APPROVED || leave.getStatus() == StatusEnum.REJECTED) {
					lRecord.add(leave);
				}
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
	
	//Overtime
	@Autowired
	private OvertimeHoursService oservice;
	
	@Autowired
	private UserService uservice;
	
	@Autowired
	private UserRepo urepo;
	
	@Autowired
	private OvertimeHoursRepo orepo;
	
	@Autowired
	public void setOvertimeHoursSerivce(OvertimeHoursServiceImpl oserviceImpl) {
		this.oservice = oserviceImpl;
	}
	
	@Autowired
	private MonthYearValidator myValidator;
	
	@InitBinder("OTMonth")
	private void initLeaveBinder(WebDataBinder binder) {
		binder.addValidators(myValidator);
	}
	
	@RequestMapping(value = "/toapproveot")
	public String findMonthOT(HttpSession session, Model model) {
		UserSession usession = (UserSession) session.getAttribute("usession");
		if (usession != null) {
			model.addAttribute("OTMonth", new MonthYear());
			return "find-month-ot";
		}
		return "forward:/home/login";
	}
	
	@RequestMapping(value = "/subordinatelistot" , method = RequestMethod.GET)
	public String employeeListOT(@ModelAttribute ("OTMonth") @Valid MonthYear OTMonth, BindingResult bindingResult, HttpSession session, Model model) {
		UserSession usession = (UserSession) session.getAttribute("usession");
		if (usession != null) { //if not logged in
			User u = usession.getUser();
			if (u.getClass().getSimpleName().equalsIgnoreCase("manager")) { 
				if (bindingResult.hasErrors()) {
					return "find-month-ot";
				}
				
				Integer month = Integer.parseInt(OTMonth.getDate().substring(5));
				Integer year = Integer.parseInt(OTMonth.getDate().substring(0,4));
				LinkedHashMap<Employee, ArrayList<OvertimeHours>> submap = new LinkedHashMap<Employee, ArrayList<OvertimeHours>>();
				LinkedHashMap<Employee, Double> totalmap = new LinkedHashMap<Employee, Double>();
				LinkedHashMap<Employee, Double> totalapprmap = new LinkedHashMap<Employee, Double>();
				LinkedHashMap<Employee, Double> totalapplmap = new LinkedHashMap<Employee, Double>();
				LinkedHashMap<Employee, Double> totalgivenmap = new LinkedHashMap<Employee, Double>();
				LinkedHashMap<Employee, Double> totalrejmap = new LinkedHashMap<Employee, Double>();
				LinkedHashMap<Employee, Integer> totalleavegivenmap = new LinkedHashMap<Employee, Integer>();
				for (Employee subordinate : urepo.findSubordinates(u.getUserId())) {
					submap.put(subordinate, oservice.findOTHoursByMYUserId(month, year, subordinate.getUserId()));
					totalmap.put(subordinate, oservice.findTotalOTHoursByMYUserId(month, year, subordinate.getUserId()));
					totalapprmap.put(subordinate, oservice.findTotalOTHoursByMYUserIdStatus(month, year, subordinate.getUserId(), OTEnum.APPROVED));
					totalapplmap.put(subordinate, oservice.findTotalOTHoursByMYUserIdStatus(month, year, subordinate.getUserId(), OTEnum.APPLIED));
					totalrejmap.put(subordinate, oservice.findTotalOTHoursByMYUserIdStatus(month, year, subordinate.getUserId(), OTEnum.REJECTED));
					totalgivenmap.put(subordinate, oservice.findTotalOTHoursByMYUserIdStatus(month, year, subordinate.getUserId(), OTEnum.LEAVEGIVEN));
					Double add = oservice.findTotalOTHoursByMYUserIdStatus(month, year, subordinate.getUserId(), OTEnum.LEAVEGIVEN) / 4;
					totalleavegivenmap.put(subordinate, add.intValue());
				}
				model.addAttribute("OTHistory", submap);
				Integer[] my = {month, year};
				model.addAttribute("monthyear", my);
				model.addAttribute("total", totalmap);
				model.addAttribute("totalapprove", totalapprmap);
				model.addAttribute("totalapplied", totalapplmap);
				model.addAttribute("totalrejected", totalrejmap);
				model.addAttribute("totalgiven", totalgivenmap);
				model.addAttribute("totalleavegiven", totalleavegivenmap);
				return "subordinate-ot-list";
			}
		}
		return "forward:/home/login";
	}
	
	@RequestMapping(value = "/approveall/{id}/{month}/{year}", method = RequestMethod.GET)
	public String subordinateotdetails(HttpSession session, @PathVariable Integer id, @PathVariable Integer month, @PathVariable Integer year) {
		UserSession usession = (UserSession) session.getAttribute("usession");
		User u = usession.getUser();
		if (u.getClass().getSimpleName().equalsIgnoreCase("manager")) { 
			Employee employee = (Employee) uservice.findUser(id);
			ArrayList<OvertimeHours> oth = oservice.findOTHoursByMYUserId(month, year, id);
			for (OvertimeHours o : oth) {
				if (o.getStatus() == OTEnum.APPLIED || o.getStatus() == OTEnum.REJECTED) {
					o.setStatus(OTEnum.APPROVED);
					oservice.updateOTHours(o);
				}
			}
			
			return "redirect:/manager/subordinatelistot?date=" + year + "-" + month;
		}
		return "forward:/home/login";
	}
	
	@RequestMapping(value = "/approveot1/{id}/{month}/{year}/{otid}", method = RequestMethod.GET)
	public String approveot1(HttpSession session, @PathVariable Integer id, @PathVariable Integer month, @PathVariable Integer year, @PathVariable Integer otid) {
		UserSession usession = (UserSession) session.getAttribute("usession");
		User u = usession.getUser();
		if (u.getClass().getSimpleName().equalsIgnoreCase("manager")) { 
			OvertimeHours ot = orepo.findById(otid).orElse(null);
			ot.setStatus(OTEnum.APPROVED);
			oservice.updateOTHours(ot);
			return "redirect:/manager/subordinatelistot?date=" + year + "-" + month;
		}
		return "forward:/home/login";
	}
	
	@RequestMapping(value = "/rejectot1/{id}/{month}/{year}/{otid}", method = RequestMethod.GET)
	public String rejectot1(HttpSession session, @PathVariable Integer id, @PathVariable Integer month, @PathVariable Integer year, @PathVariable Integer otid) {
		UserSession usession = (UserSession) session.getAttribute("usession");
		User u = usession.getUser();
		if (u.getClass().getSimpleName().equalsIgnoreCase("manager")) { 
			OvertimeHours ot = orepo.findById(otid).orElse(null);
			ot.setStatus(OTEnum.REJECTED);
			oservice.updateOTHours(ot);
			return "redirect:/manager/subordinatelistot?date=" + year + "-" + month;
		}
		return "forward:/home/login";
	}
	
	@RequestMapping(value = "/calc/{id}/{month}/{year}", method = RequestMethod.GET)
	public String calc(HttpSession session, @PathVariable Integer id, @PathVariable Integer month, @PathVariable Integer year) {
		UserSession usession = (UserSession) session.getAttribute("usession");
		User u = usession.getUser();
		if (u.getClass().getSimpleName().equalsIgnoreCase("manager")) { 
			Employee employee = (Employee) urepo.findById(id).orElse(null);
			Double add = oservice.findTotalOTHoursByMYUserIdStatus(month, year, id, OTEnum.APPROVED) / 4;
			ArrayList<OvertimeHours> oth = oservice.findOTHoursByMYUserIdStatus(month, year, id, OTEnum.APPROVED);
			for (OvertimeHours o : oth) {
				o.setStatus(OTEnum.LEAVEGIVEN);
				oservice.updateOTHours(o);
			}
			Integer newCompLeave = employee.getCompLeaveN() + add.intValue();
			employee.setCompLeaveN(newCompLeave);
			urepo.saveAndFlush(employee);
			return "redirect:/manager/subordinatelistot?date=" + year + "-" + month;
		}
		return "forward:/home/login";
	}
}
