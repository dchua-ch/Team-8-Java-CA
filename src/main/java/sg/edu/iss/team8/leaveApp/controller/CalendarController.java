package sg.edu.iss.team8.leaveApp.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.iss.team8.leaveApp.model.Leave;
import sg.edu.iss.team8.leaveApp.repo.LeaveRepo;
import sg.edu.iss.team8.leaveApp.service.LeaveService;
import sg.edu.iss.team8.leaveApp.service.LeaveServiceImpl;
import sg.edu.iss.team8.leaveApp.validator.LeaveValidator;

@CrossOrigin(origins = "http://localhost:3000") //testing with PostMan
@RestController
@RequestMapping("/calendarAPI")
public class CalendarController {

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
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(LocalDate.class, new CustomDateEditor(dateFormat, false));
		binder.addValidators(lValidator);
	}
	
	@GetMapping("/getAllLeaves")
	public List<Leave> getAllLeaves() {
		return lrepo.findAll();
	}
	
	@GetMapping("/getApplicableLeavesForUser/{userId}")
	public List<Leave> getApplicableLeavesForUser(@PathVariable("userId") Integer userId) {
		return lservice.findApplicableLeaveByUserId(userId);
	}
	
	//When a user selects the dates on the calendar, calendar UI appends each
	//day to a collection, and when the user clicks on some button like "add leave"
	//on the page, the calendar UI should send this collection of dates to this
	//REST Controller.
	//This method receives that collection from the RequestBody, and checks if the
	//dates are continuous. The method creates subsets of continuous dates; and
	//for each subset of continuous dates, selects the first item to be the each leave's startDate,
	//leave's startDate, and selects the last item to be each leave's endDate.
	//Redirect to the Leave Form page with dates pre-filled.
	
	
	
	
	
}
