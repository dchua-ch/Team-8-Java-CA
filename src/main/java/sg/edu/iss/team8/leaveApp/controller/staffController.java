package sg.edu.iss.team8.leaveApp.controller;

import java.util.ArrayList;
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
	
	@RequestMapping(value = "/history/{uid}")
	public String pendingApprovals(@PathVariable("uid") Integer userId, Model model /* HttpSession session */) {
		// UserSession usession = (UserSession) session.getAttribute("usession");
		
		ArrayList<Leave> all = new ArrayList<Leave>();
		//UserSession usession = (UserSession) session.getAttribute("usession");
		all.addAll(lService.findLeaveByUID(userId));
		model.addAttribute("leaves", all);
		return "staff-leave-history";

	}
	
//	@RequestMapping(value = "/history")
//	public String staffLeaveHistory(Model model) {
//			ArrayList<Leave> all = new ArrayList<Leave>();
//			//UserSession usession = (UserSession) session.getAttribute("usession");
//			all.addAll(lrepo.findAll());
//			model.addAttribute("leaves", all);
//			return "staff-leave-history";
//	}
	@RequestMapping(value = "/history/details/{id}" ,method = RequestMethod.GET)
	public ModelAndView leaveDetails(@PathVariable Integer id) {
		ModelAndView mav = new ModelAndView("ldetails");
		Leave leave = lrepo.getById(id);
		mav.addObject("leave", leave);
		return mav;	
	}
}
