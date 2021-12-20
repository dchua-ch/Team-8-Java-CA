package sg.edu.iss.team8.leaveApp.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.team8.leaveApp.model.Leave;
import sg.edu.iss.team8.leaveApp.service.LeaveService;
import sg.edu.iss.team8.leaveApp.service.LeaveServiceImpl;

@Controller
@RequestMapping("/leave")
public class LeaveController {

	@Autowired
	private LeaveService lservice;
	
	@Autowired
	public void setLeaveService(LeaveServiceImpl lserviceImpl) {
		this.lservice = lserviceImpl;
	}
	
	//add leave (create a leave object)
	@RequestMapping("/add")
	public String addLeave(Model model) {
		Leave newLeave = new Leave();
		model.addAttribute("leave", newLeave);
		return "some";
	}
	
	//submit leave
	//--set status to applied
	//--before processing with db, must validate
	@PostMapping("/submit")
	public String submitLeave(@ModelAttribute("leave") @Valid Leave leave,
								BindingResult result) {
		if (result.hasErrors()) {
			return "some";
		}
		
		lservice.submitLeave(leave);
		return "some";
	}
	
	
	//Manage Leave - update
	//get request
	@GetMapping("/update/{leaveId}")
	public String updateLeavePage(@PathVariable("leaveId") Integer leaveId,
									Model model) {
		Leave currentLeave = lservice.findLeaveById(leaveId);
		model.addAttribute("leave", currentLeave);
		return "some";
	}
	
	//Manage Leave - update
	//post request
	@PostMapping("/update/{leaveId}")
	public String updateLeave(@ModelAttribute("leave") @Valid Leave leave,
								BindingResult result) {
		
		if (result.hasErrors()) {
			return "some";
		}
		
		lservice.updateLeave(leave);
		return "some";
	}
	
	//Manage leave - delete
	//--set status to deleted
	@GetMapping("/delete/{leaveId}")
	public String deleteLeave(@PathVariable("leaveId") Integer leaveId) {
		Leave leaveToDelete = lservice.findLeaveById(leaveId);
		lservice.deleteLeave(leaveToDelete);
		return "forward:/somepage";
	}
	
	//Manage leave - cancel
	//--set status to cancel
	@GetMapping("/cancel/{leaveId}")
	public String cancelLeave(@PathVariable("leaveId") Integer leaveId) {
		Leave leaveToCancel = lservice.findLeaveById(leaveId);
		lservice.cancelLeave(leaveToCancel);
		return "forward:/somepage";
	}
	
}
