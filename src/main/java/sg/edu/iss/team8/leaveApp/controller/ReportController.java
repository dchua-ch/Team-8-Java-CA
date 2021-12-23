package sg.edu.iss.team8.leaveApp.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import sg.edu.iss.team8.leaveApp.helpers.LeaveEnum;
import sg.edu.iss.team8.leaveApp.helpers.LeaveSummary;
import sg.edu.iss.team8.leaveApp.helpers.Outcome;
import sg.edu.iss.team8.leaveApp.helpers.StatusEnum;
import sg.edu.iss.team8.leaveApp.model.Employee;
import sg.edu.iss.team8.leaveApp.model.Leave;
import sg.edu.iss.team8.leaveApp.service.EmployeeService;
import sg.edu.iss.team8.leaveApp.service.LeaveService;

@RestController
@RequestMapping(value = "/reportAPI")
public class ReportController {
	
	@Autowired
	EmployeeService eService;
	
	@Autowired
	LeaveService lService; 
	
	
	@RequestMapping(value="/search", method=RequestMethod.GET)
	public ModelAndView loadReport(HttpSession session) {
		UserSession usession = (UserSession) session.getAttribute("usession"); 
		List<Employee> subordinates = eService.findSubordinates(usession.getUser().userid);
		
		ModelAndView mav = new ModelAndView("manager-report", "sub", subordinates);
		mav.addObject("outcome", new Outcome()); 
		return mav; 
	}
	
	@RequestMapping(value = "/date", method=RequestMethod.GET)
	public List<LeaveSummary> getLeaveByDate1(@RequestParam("id") Integer id, @RequestParam("start") String start, @RequestParam("end") String end, @RequestParam("comp") Boolean compOnly)  {
		Employee emp = eService.findByUserid(id); 
		List<Leave> allLeave = lService.findLeaveByUID(id); 
		List<Leave> approved = new ArrayList<Leave>(); 
		for (Leave l : allLeave) {
			if (compOnly) {
				if (l.getLeaveType() == LeaveEnum.COMPENSATION && l.getStatus() == StatusEnum.APPROVED)
				{
					approved.add(l);
				}
			} else {
				if (l.getStatus() == StatusEnum.APPROVED) {
					approved.add(l);
				}
			}
			
		}
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
		List<Leave> datedLeave = lService.getLeaveByDates(approved, LocalDate.parse(start, formatter), LocalDate.parse(end, formatter));
		List<LeaveSummary> leaveSummaryList = new ArrayList<LeaveSummary>(); 
		for (Leave l : datedLeave) {
			LeaveSummary ls = new LeaveSummary(l.getLeaveId(), l.getEmployee().getName(), l.getStartDate(), l.getEndDate(), l.getLeaveType());
			leaveSummaryList.add(ls); 
		}
		
		return leaveSummaryList;
	}
}
