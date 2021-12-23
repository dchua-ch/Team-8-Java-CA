package sg.edu.iss.team8.leaveApp.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import sg.edu.iss.team8.leaveApp.helpers.LeaveInput;
import sg.edu.iss.team8.leaveApp.helpers.Outcome;
import sg.edu.iss.team8.leaveApp.helpers.StatusEnum;
import sg.edu.iss.team8.leaveApp.model.Employee;
import sg.edu.iss.team8.leaveApp.model.Leave;
import sg.edu.iss.team8.leaveApp.repo.LeaveRepo;
import sg.edu.iss.team8.leaveApp.service.EmployeeService;
import sg.edu.iss.team8.leaveApp.service.LeaveService;
import sg.edu.iss.team8.leaveApp.service.LeaveServiceImpl;
import sg.edu.iss.team8.leaveApp.validator.LeaveValidator;

@Controller
@RequestMapping(value = "/staff")
public class staffController {

	@Autowired
	LeaveRepo lrepo;
	@Autowired
	EmployeeService eService;

	@Autowired
	LeaveService lService;
	
	@Autowired
	public void setLeaveService(LeaveServiceImpl lserviceImpl) {
		this.lService = lserviceImpl;
	}
	
	@Autowired
	private LeaveValidator lValidator;
	
	@InitBinder("leave")
	private void initLeaveBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
		binder.addValidators(lValidator);
	}
	
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

		Leave leave = new Leave(leaveInput);	
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
	

	
	//get the Leave object to display on the update page
	@GetMapping("/update/{leaveId}")
	public String updateLeavePage(@PathVariable("leaveId") Integer leaveId,
									Model model) {
		Leave currentLeave = lService.findLeaveById(leaveId);
		LeaveInput leaveInput = new LeaveInput(currentLeave);
		model.addAttribute("leave", leaveInput);
		
		return "update-leave";
	}
	
	//update the Leave with the new values from the page
	@PostMapping("/update/{leaveId}")
	public String updateLeave(@ModelAttribute("leave")  LeaveInput leaveInput,
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
		
	//Check all personal leaves in current year
	@RequestMapping(value = "/history/", method = RequestMethod.GET)
	public ModelAndView leaveHistory(HttpSession session) {
		UserSession usession = (UserSession) session.getAttribute("usession");
		Calendar date = Calendar.getInstance();
		ArrayList<Leave> all = new ArrayList<Leave>();
		List<Leave> leaves = lService.findLeaveByUID(usession.getUser().userId);
		
		for(Leave l : leaves) {
			if(l.getStartDate().getYear() == date.get(Calendar.YEAR)) {
				all.add(l);
			}
		}
		ModelAndView mav = new ModelAndView("staff-leave-history", "leaves", all);
		return mav;	
	}
	//Check all personal leaves
	@RequestMapping(value = "/history/all")
	public String personalAllHistory(Model model, HttpSession session) {
		UserSession usession = (UserSession) session.getAttribute("usession");
		ArrayList<Leave> all1 = new ArrayList<Leave>();
		all1.addAll(lService.findLeaveByUID(usession.getUser().userId));
		model.addAttribute("leaves", all1);
		return "staff-leave-history-all";
		}


	//leave details for the specific leave
	@RequestMapping(value = "/history/details/{id}" ,method = RequestMethod.GET)
	public ModelAndView leaveDetails(@PathVariable Integer id) {
		ModelAndView mav = new ModelAndView("ldetails");
		
		LeaveInput leaveInput = lService.findLeaveById1(id);
//		Leave leave = lrepo.getById(id);
//		mav.addObject("leave", leave);
		mav.addObject("leave", leaveInput);
		return mav;	
	}
	

}

