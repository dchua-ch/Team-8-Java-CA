package sg.edu.iss.team8.leaveApp.controllers;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.team8.leaveApp.helpers.OTEnum;
import sg.edu.iss.team8.leaveApp.helpers.OvertimeHoursInput;
import sg.edu.iss.team8.leaveApp.model.Employee;
import sg.edu.iss.team8.leaveApp.model.OvertimeHours;
import sg.edu.iss.team8.leaveApp.model.User;
import sg.edu.iss.team8.leaveApp.repo.OvertimeHoursRepo;
import sg.edu.iss.team8.leaveApp.repo.UserRepo;
import sg.edu.iss.team8.leaveApp.services.OvertimeHoursService;
import sg.edu.iss.team8.leaveApp.services.OvertimeHoursServiceImpl;
import sg.edu.iss.team8.leaveApp.validators.OvertimeHoursValidator;

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
	
	@Autowired
	private OvertimeHoursValidator otValidator;
	
	@InitBinder("OTHours")
	private void initLeaveBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
		binder.addValidators(otValidator);
	}
	
	@RequestMapping(value = "/addot")
	public String addOTForm(HttpSession session, Model model) {
		UserSession usession = (UserSession) session.getAttribute("usession");
		if (usession != null) {
			model.addAttribute("OTHours", new OvertimeHoursInput());
			return "ot-form";
		}
		return "forward:/home/login";
	}
	
	@RequestMapping(value = "/saveot")
	public String saveOTHours(@ModelAttribute("OTHours") @Valid OvertimeHoursInput OTHours, BindingResult bindingResult, Model model,
			HttpSession session){
		UserSession usession = (UserSession) session.getAttribute("usession");
		if (bindingResult.hasErrors()) {
			return "ot-form";
		}
		if (usession != null) {
			User u = usession.getUser();
			
			if (u.getClass().getSimpleName().equalsIgnoreCase("admin")) {
				return "adminerror";
			}
			else {
				OvertimeHours ot = new OvertimeHours(OTHours);
				ot.setEmployee((Employee) u);
				ot.setStatus(OTEnum.APPLIED);
				oservice.saveOTHours(ot);
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
