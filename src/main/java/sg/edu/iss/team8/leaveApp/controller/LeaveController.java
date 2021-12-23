package sg.edu.iss.team8.leaveApp.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.team8.leaveApp.helpers.LeaveBag;
import sg.edu.iss.team8.leaveApp.helpers.LeaveEnum;
import sg.edu.iss.team8.leaveApp.helpers.LeaveInput;
import sg.edu.iss.team8.leaveApp.model.Employee;
import sg.edu.iss.team8.leaveApp.model.Leave;
import sg.edu.iss.team8.leaveApp.repo.LeaveRepo;
import sg.edu.iss.team8.leaveApp.repo.UserRepo;
import sg.edu.iss.team8.leaveApp.service.EmployeeService;
import sg.edu.iss.team8.leaveApp.service.LeaveService;
import sg.edu.iss.team8.leaveApp.service.LeaveServiceImpl;
import sg.edu.iss.team8.leaveApp.service.UserService;
import sg.edu.iss.team8.leaveApp.validator.LeaveValidator;

@Controller
@RequestMapping("/leave")
public class LeaveController {
	
	@Autowired
	private LeaveRepo lrepo;

	@Autowired
	private LeaveService lService;
	
	@Autowired
	private UserService uService;
	
	@Autowired 
	private EmployeeService eService;
	
	@Autowired
	private UserRepo urepo;
	
	@Autowired
	public void setLeaveService(LeaveServiceImpl lserviceImpl) {
		this.lService = lserviceImpl;
	}
	
	//@Autowired
	//private EmployeeService eService;
	
	@Autowired
	private LeaveValidator lValidator;
	
	@InitBinder("leave")
	private void initLeaveBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
		binder.addValidators(lValidator);
	}
	
	//add/initialize a Leave object
	@RequestMapping("/add")
	public String addLeave(Model model) {
		LeaveInput leaveInput = new LeaveInput();
		//Leave newLeave = new Leave();
		model.addAttribute("leave", leaveInput);
		String msg = "New leave created";
		System.out.println(msg);
		return "apply-leave";
	}
	

	
	//submit the Leave to persist
	@PostMapping("/submit")
	public String submitLeave(@ModelAttribute("leave") @Valid LeaveInput leaveInput,
								BindingResult result, HttpSession session) {
		if (result.hasErrors()) {
			return "submit-leave-error";
		}
		
		UserSession usession = (UserSession) session.getAttribute("usession");
		Integer userId = usession.getUser().getUserId();
		Employee employee = eService.findByUserId(userId);
		leaveInput.setEmployee(employee);
		System.out.println(leaveInput.getEmployee().getUserId());
		
		LeaveEnum leaveType = leaveInput.getLeaveType();
	

		Leave leave = new Leave(leaveInput);	
		//needs logic for checking if annual leave period <= 14 days.
		//if period <= 14, weekends should not be included in the leave count
		//LocalDate start = leave.getStartDate();
		//LocalDate end = leave.getEndDate();
		//LocalDate startDate = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		//LocalDate endDate = end.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int periodDays = lService.calculatePeriodDays(leave);
		int daysToExclude = lService.calculateDaysToExclude(leave);
		int totalLeavesToDeduct = periodDays - daysToExclude;
		System.out.println("Leaves to deduct: "+ totalLeavesToDeduct);
		if (leaveType == LeaveEnum.ANNUAL) 
		{
			System.out.println("Annual leave balance: "+ (employee.getAnnualLeaveN() - totalLeavesToDeduct));
			if(employee.getAnnualLeaveN() - totalLeavesToDeduct <= 0)
			{
				return "annual-leave-exceeded";
			}
			else
			{
				employee.setAnnualLeaveN(employee.getAnnualLeaveN() - totalLeavesToDeduct);
			}
		} 
		
		else if (leaveType == LeaveEnum.MEDICAL) 
		{
			System.out.println("Medical leave balance: "+ (employee.getMedicalLeaveN() - totalLeavesToDeduct));
			if(employee.getMedicalLeaveN() - totalLeavesToDeduct <= 0)
			{
				return "medical-leave-exceeded";
			}
			else
			{
				employee.setMedicalLeaveN(employee.getMedicalLeaveN() - totalLeavesToDeduct);
			}
		} 
		
		else if (leaveType == LeaveEnum.COMPENSATION) 
		{
			System.out.println("Annual leave balance: "+ (employee.getCompLeaveN() - totalLeavesToDeduct));
			if(employee.getCompLeaveN() - totalLeavesToDeduct <= 0)
			{
				return "compensation-leave-exceeded";
			}
			else
			{
				employee.setCompLeaveN(employee.getCompLeaveN() - totalLeavesToDeduct);
			}
		}
		urepo.saveAndFlush(employee);
		
		lService.submitLeave(leave);
		String msg = "Leave was successfully submitted.";
		System.out.println(msg);
		return "submitted-leave";
	}
	
	private LocalDate convertToLocalDate(Date dateToConvert) {
	    return dateToConvert.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDate();
	}
	
	@GetMapping("/select")
	public String selectLeavePage(Model model)
	{
		List<Leave> leaves= lrepo.findAll();
		for(Leave leave : leaves)
		{
			System.out.println(leave.getLeaveId());
		}
		model.addAttribute("leaves",leaves);
	
		return "select-leave";
	}
	

	
	//get the Leave object to display on the update page
	@GetMapping("/update/{leaveId}")
	public String updateLeavePage(@PathVariable("leaveId") Integer leaveId,
									Model model) {
		Leave currentLeave = lService.findLeaveById(leaveId);
		
		LocalDate startDate = currentLeave.getStartDate();
		LocalDate endDate = currentLeave.getEndDate();
		Period period = Period.between(startDate, endDate);
		int periodDays = Math.abs(period.getDays());
		int daysToExclude = lService.calculateDaysToExclude(currentLeave);
		int totalLeavesToDeduct = periodDays - daysToExclude;
		//System.out.println("Leaves deducted in: " + totalLeavesToDeduct);
		
		LeaveBag leaveBag = new LeaveBag(totalLeavesToDeduct);
		System.out.println("Leaves deducted in: " + leaveBag.getDuration());
		LeaveInput leaveInput = new LeaveInput(currentLeave);
		model.addAttribute("leave", leaveInput);
		model.addAttribute("leaveBag", leaveBag);
		
		return "update-leave";
	}
	
	//update the Leave with the new values from the page
	@PostMapping("/update/{leaveId}")
	public String updateLeave(@ModelAttribute("leave")  LeaveInput leaveInput, 
								@ModelAttribute("leaveBag") LeaveBag leaveBag,
								BindingResult result) {
		if (result.hasErrors()) {
			return "leave-update-error";
		}
		Leave leave = lService.findLeaveById(leaveInput.getLeaveId());
		LocalDate startDate = convertToLocalDate(leaveInput.getStartDate());
		LocalDate endDate = convertToLocalDate(leaveInput.getEndDate());
		leave.setStartDate(startDate);
		leave.setEndDate(endDate);
		leave.setAddtnlReason(leaveInput.getAddtnlReason());
		leave.setLeaveType(leaveInput.getLeaveType());
		leave.setAddtnlReason(leaveInput.getAddtnlReason());
		leave.setWorkDissemination(leaveInput.getWorkDissemination());
		leave.setContact(leaveInput.getContact());
		System.out.println("Leaves deducted out: " + leaveBag.getDuration());
		
		/*
		 * Add logic for leave deduction here
		 * 
		Employee employee = leave.getEmployee();
		LeaveEnum leaveType = leaveInput.getLeaveType();
	
		Period period = Period.between(startDate, endDate);
		int periodDays = Math.abs(period.getDays());
		int daysToExclude = lService.calculateDaysToExclude(leave);
		int totalLeavesToDeduct = periodDays - daysToExclude;
		System.out.println("Leaves to deduct: "+ totalLeavesToDeduct);
		if (leaveType == LeaveEnum.ANNUAL) 
		{
			System.out.println("Annual leave balance: "+ (employee.getAnnualLeaveN() - totalLeavesToDeduct));
			if(employee.getAnnualLeaveN() - totalLeavesToDeduct <= 0)
			{
				return "annual-leave-exceeded";
			}
			else
			{
				employee.setAnnualLeaveN(employee.getAnnualLeaveN() - totalLeavesToDeduct);
			}
		} 
		
		else if (leaveType == LeaveEnum.MEDICAL) 
		{
			System.out.println("Medical leave balance: "+ (employee.getMedicalLeaveN() - totalLeavesToDeduct));
			if(employee.getMedicalLeaveN() - totalLeavesToDeduct <= 0)
			{
				return "medical-leave-exceeded";
			}
			else
			{
				employee.setMedicalLeaveN(employee.getMedicalLeaveN() - totalLeavesToDeduct);
			}
		} 
		
		else if (leaveType == LeaveEnum.COMPENSATION) 
		{
			System.out.println("Annual leave balance: "+ (employee.getCompLeaveN() - totalLeavesToDeduct));
			if(employee.getCompLeaveN() - totalLeavesToDeduct <= 0)
			{
				return "compensation-leave-exceeded";
			}
			else
			{
				employee.setCompLeaveN(employee.getCompLeaveN() - totalLeavesToDeduct);
			}
		}
		urepo.saveAndFlush(employee);
		*/
		lService.updateLeave(leave);
		String msg = "Leave was successfully updated.";
		System.out.println(msg);
		return "update-successful";
	}
	
	//"delete" the Leave object
	@GetMapping("/delete/{leaveId}")
	public String deleteLeave(@PathVariable("leaveId") Integer leaveId) {
		Leave leaveToDelete = lService.findLeaveById(leaveId);
		lService.deleteLeave(leaveToDelete);
		String msg = "Leave was successfully deleted.";
		System.out.println(msg);
		return "delete-successful";
	}
	
	//"cancel" the Leave
	@GetMapping("/cancel/{leaveId}")
	public String cancelLeave(@PathVariable("leaveId") Integer leaveId) {
		Leave leaveToCancel = lService.findLeaveById(leaveId);
		lService.cancelLeave(leaveToCancel);
		String msg = "Leave was successfully cancelled.";
		System.out.println(msg);
		return "cancel-successful";
	}
	
	//get user leave history
	//might have overlap with RZ's code, but included for good measure.
	@GetMapping("/leavehistory")
	public String getLeaveHistory(Model model, HttpSession session) {
		UserSession usession = (UserSession) session.getAttribute("usession");
		Integer userId = usession.getUser().getUserId();
		List<Leave> leaveHistory = lService.findLeaveByUserId(userId);
		model.addAttribute("leaveHistory", leaveHistory);
		return "some";
	}
	
	//get user pending leaves
	@GetMapping("pendingleaves")
	public String getPendingLeaves(Model model, HttpSession session) {
		UserSession usession = (UserSession) session.getAttribute("usession");
		Integer userId = usession.getUser().getUserId();
		List<Leave> pendingLeaves = lService.findPendingLeaveByUserId(userId);
		model.addAttribute("pendingLeaves", pendingLeaves);
		return "some";
	}
	
}
