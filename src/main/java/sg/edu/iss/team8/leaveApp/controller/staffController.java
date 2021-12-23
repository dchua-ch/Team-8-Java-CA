package sg.edu.iss.team8.leaveApp.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
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

import sg.edu.iss.team8.leaveApp.helpers.Outcome;
import sg.edu.iss.team8.leaveApp.helpers.StatusEnum;
import sg.edu.iss.team8.leaveApp.model.Employee;
import sg.edu.iss.team8.leaveApp.model.Leave;
import sg.edu.iss.team8.leaveApp.repo.LeaveRepo;
import sg.edu.iss.team8.leaveApp.service.EmployeeService;
import sg.edu.iss.team8.leaveApp.service.LeaveService;

@Controller
@RequestMapping(value = "/staff")
public class staffController {

	@Autowired
	LeaveRepo lrepo;
	@Autowired
	EmployeeService eService;

	@Autowired
	LeaveService lService;
	
	//Check all personal leaves in current year
//	@RequestMapping(value = "/history/")
//	public String personalHistory(Model model, HttpSession session) {
//		UserSession usession = (UserSession) session.getAttribute("usession");
//		Calendar date = Calendar.getInstance();
//		ArrayList<Leave> all = new ArrayList<Leave>();
//		List<Leave> leaves = lService.findLeaveByUID(usession.getUser().userId);
//		
//		for(Leave l : leaves) {
//			if(l.getStartDate().getYear() == date.get(Calendar.YEAR)) {
//				all.add(l);
//			}
//		}
//		
//		model.addAttribute("leaves", all);
//		model.addAttribute("date", date);
//		return "staff-leave-history";
//
//	}
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
		Leave leave = lrepo.getById(id);
		mav.addObject("leave", leave);
		return mav;	
	}
	
	

}

