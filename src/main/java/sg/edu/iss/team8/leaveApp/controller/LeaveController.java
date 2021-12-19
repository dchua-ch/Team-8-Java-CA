package sg.edu.iss.team8.leaveApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
	
	//Wire up UserService or repo?
	
	//add leave (create a leave object)
	@RequestMapping("/add")
	public String addLeave(Model model) {
		Leave newLeave = new Leave();
		model.addAttribute("leave", newLeave);
		return "some";
	}
	
	//submit leave
	//--user details
	//--leave period
	//--what type of leave
	//--additional reasons
	//--work dissem
	//--contact details
	//--set status to applied
	//--before processing with db, must validate
	@PostMapping("/submit")
	public String submitLeave(@ModelAttribute("leave") Leave leave) {
		
		//some
		
		lservice.submitLeave(leave);
		return "some";
	}
	
	
	//Manage Leave - update
	//allow attributes of Leave to be changed 
	//--set status to updated
	@PutMapping("/update/{leaveId}")
	public String updateLeave(@PathVariable("leaveId") Integer leaveId,
								@ModelAttribute("leave") Leave leave) {
		//some
		
		lservice.updateLeave(leave);
		return "some";
	}
	
	//Manage leave - delete
	//--set status to deleted
	@RequestMapping("/delete/{leaveId}")
	public String deleteLeave(@PathVariable("leaveId") Integer leaveId) {
		Leave leaveToDelete = lservice.findLeaveById(leaveId);
		lservice.deleteLeave(leaveToDelete);
		return "forward:/somepage";
	}
	
	//Manage leave - cancel
	//--set status to cancel
	@RequestMapping("/cancel/{leaveId}")
	public String cancelLeave(@PathVariable("leaveId") Integer leaveId) {
		Leave leaveToCancel = lservice.findLeaveById(leaveId);
		lservice.cancelLeave(leaveToCancel);
		return "forward:/somepage";
	}
}
