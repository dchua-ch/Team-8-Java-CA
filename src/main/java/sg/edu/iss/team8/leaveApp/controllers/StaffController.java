package sg.edu.iss.team8.leaveApp.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.team8.leaveApp.helpers.OTEnum;
import sg.edu.iss.team8.leaveApp.model.Employee;
import sg.edu.iss.team8.leaveApp.model.Manager;
import sg.edu.iss.team8.leaveApp.model.OvertimeHours;
import sg.edu.iss.team8.leaveApp.model.User;
import sg.edu.iss.team8.leaveApp.repo.OvertimeHoursRepo;
import sg.edu.iss.team8.leaveApp.repo.UserRepo;
import sg.edu.iss.team8.leaveApp.services.OvertimeHoursService;
import sg.edu.iss.team8.leaveApp.services.OvertimeHoursServiceImpl;

@Controller
@RequestMapping("/staff")
public class StaffController {
	@Autowired
	private OvertimeHoursService oservice;
	
	@Autowired
	private UserRepo urepo;
	
	@Autowired
	private OvertimeHoursRepo orepo;
	
	@Autowired
	public void setOvertimeHoursSerivce(OvertimeHoursServiceImpl oserviceImpl) {
		this.oservice = oserviceImpl;
	}
	
	@RequestMapping(value = "/addot")
	public String addOTForm(HttpSession session, Model model) {
		UserSession usession = (UserSession) session.getAttribute("usession");
		if (usession != null) {
			model.addAttribute("OTHours", new OvertimeHours());
			return "ot-form";
		}
		return "forward:/home/login";
	}
	
	@RequestMapping(value = "/saveot")
	public String saveOTHours(@ModelAttribute("OTHours") @Valid OvertimeHours OTHours, BindingResult bindingResult, Model model,
			HttpSession session){
		UserSession usession = (UserSession) session.getAttribute("usession");
//		binding has errors as of now. 		
//		if (bindingResult.hasErrors()) {
//			return "ot-form";
//		}
		if (usession != null) {
			User u = usession.getUser();
			
			if (u.getClass().getSimpleName().equalsIgnoreCase("admin")) {
				return "adminerror";
			}
			else {
				OTHours.setEmployee((Employee) u);
				OTHours.setStatus(OTEnum.APPLIED);
				oservice.saveOTHours(OTHours);
				return "redirect:/staff/listot";
			}
		}
		return "forward:/home/login";
		
	}
	
	@RequestMapping(value = "/listot")
	public String listOT(HttpSession session, Model model) {
		UserSession usession = (UserSession) session.getAttribute("usession");
		if (usession != null) { //if not logged in
			User u = usession.getUser();
			if (u.getClass().getSimpleName().equalsIgnoreCase("admin")) { //if user is admin
				return "adminerror";
			}
			model.addAttribute("OTHistory", oservice.findOTHoursByUserId(u.getUserId()));
			return "ot-list";
		}
		return "forward:/home/login";
	}
}
