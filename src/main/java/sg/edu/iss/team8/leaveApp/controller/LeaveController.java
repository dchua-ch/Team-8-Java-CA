package sg.edu.iss.team8.leaveApp.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

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

import sg.edu.iss.team8.leaveApp.model.Leave;
import sg.edu.iss.team8.leaveApp.repo.LeaveRepo;
import sg.edu.iss.team8.leaveApp.service.LeaveService;
import sg.edu.iss.team8.leaveApp.service.LeaveServiceImpl;
import sg.edu.iss.team8.leaveApp.validator.LeaveValidator;

@Controller
@RequestMapping("/leave")
public class LeaveController {
	
	@Autowired
	private LeaveRepo lrepo;

	@Autowired
	private LeaveService lservice;
	
	@Autowired
	public void setLeaveService(LeaveServiceImpl lserviceImpl) {
		this.lservice = lserviceImpl;
	}
	
	@Autowired
	private LeaveValidator lValidator;
	
	@InitBinder("leave")
	private void initLeaveBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(LocalDate.class, new CustomDateEditor(dateFormat, false));
		binder.addValidators(lValidator);
	}
	
	//add/initialize a Leave object
	@RequestMapping("/add")
	public String addLeave(Model model) {
		Leave newLeave = new Leave();
		model.addAttribute("leave", newLeave);
		String msg = "New leave created";
		System.out.println(msg);
		return "apply-leave";
	}
	
	//submit the Leave to persist
	@PostMapping("/submit")
	public String submitLeave(@ModelAttribute("leave") @Valid Leave leave,
								BindingResult result) {
		if (result.hasErrors()) {
			return "submit-leave-error";
		}
		
		lservice.submitLeave(leave);
		String msg = "Leave was successfully submitted.";
		System.out.println(msg);
		return "submitted-leave";
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
		Leave currentLeave = lservice.findLeaveById(leaveId);
		model.addAttribute("leave", currentLeave);
		return "update-leave";
	}
	
	//update the Leave with the new values from the page
	@PostMapping("/update/{leaveId}")
	public String updateLeave(@ModelAttribute("leave")  Leave leave,
								BindingResult result) {
		if (result.hasErrors()) {
			return "leave-update-error";
		}
		
		lservice.updateLeave(leave);
		String msg = "Leave was successfully updated.";
		System.out.println(msg);
		return "update-successful";
	}
	
	//"delete" the Leave object
	@GetMapping("/delete/{leaveId}")
	public String deleteLeave(@PathVariable("leaveId") Integer leaveId) {
		Leave leaveToDelete = lservice.findLeaveById(leaveId);
		System.out.println(leaveToDelete.getLeaveId());
		lservice.deleteLeave(leaveToDelete);
		String msg = "Leave was successfully deleted.";
		System.out.println(msg);
		return "delete-successful";
	}
	
	//"cancel" the Leave
	@GetMapping("/cancel/{leaveId}")
	public String cancelLeave(@PathVariable("leaveId") Integer leaveId) {
		Leave leaveToCancel = lservice.findLeaveById(leaveId);
		lservice.cancelLeave(leaveToCancel);
		String msg = "Leave was successfully cancelled.";
		System.out.println(msg);
		return "cancel-successful";
	}
	
}
