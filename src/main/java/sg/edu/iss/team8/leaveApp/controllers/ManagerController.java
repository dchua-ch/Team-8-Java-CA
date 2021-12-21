package sg.edu.iss.team8.leaveApp.controllers;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.catalina.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sg.edu.iss.team8.leaveApp.helpers.MonthYear;
import sg.edu.iss.team8.leaveApp.model.Employee;
import sg.edu.iss.team8.leaveApp.model.OvertimeHours;
import sg.edu.iss.team8.leaveApp.model.User;
import sg.edu.iss.team8.leaveApp.repo.OvertimeHoursRepo;
import sg.edu.iss.team8.leaveApp.repo.UserRepo;
import sg.edu.iss.team8.leaveApp.services.OvertimeHoursService;
import sg.edu.iss.team8.leaveApp.services.OvertimeHoursServiceImpl;
import sg.edu.iss.team8.leaveApp.services.UserService;

@Controller
@RequestMapping("/manager")
public class ManagerController {
	@Autowired
	private OvertimeHoursService oservice;
	
	@Autowired
	private UserService uservice;
	
	@Autowired
	private UserRepo urepo;
	
	@Autowired
	private OvertimeHoursRepo orepo;
	
	@Autowired
	public void setOvertimeHoursSerivce(OvertimeHoursServiceImpl oserviceImpl) {
		this.oservice = oserviceImpl;
	}
	
	@RequestMapping(value = "/subordinatelistot")
	public String employeeListOT(HttpSession session, Model model) {
		UserSession usession = (UserSession) session.getAttribute("usession");
		if (usession != null) { //if not logged in
			User u = usession.getUser();
			if (u.getClass().getSimpleName().equalsIgnoreCase("manager")) { 
				HashMap<Employee, ArrayList<OvertimeHours>> submap = new HashMap<Employee, ArrayList<OvertimeHours>>();
				for (Employee subordinate : urepo.findSubordinates(u.getUserId())) {
					submap.put(subordinate, oservice.findOvertimeHoursByUserId(subordinate.getUserId()));
				}
				model.addAttribute("OTHistory", submap);
				return "subordinate-ot-list";
			}
		}
		return "forward:/home/login";
	}
	
	@RequestMapping(value = "/subordinatelistot1")
	public String findMonthOT(HttpSession session, Model model) {
		UserSession usession = (UserSession) session.getAttribute("usession");
		if (usession != null) {
			model.addAttribute("OTMonth", new MonthYear());
			return "find-month-ot";
		}
		return "forward:/home/login";
	}
	
	@RequestMapping(value = "/filtersubordinatelistot1summary/", method = RequestMethod.GET)
	public String employeeListOT1summary(@ModelAttribute ("total") @Valid MonthYear OTMonth, @ModelAttribute ("OTMonth") @Valid MonthYear OTMonth2, HttpSession session, Model model) {
		UserSession usession = (UserSession) session.getAttribute("usession");
		User u = usession.getUser();
		if (u.getClass().getSimpleName().equalsIgnoreCase("manager")) { 
			HashMap<Employee, Double> hrsmap = new HashMap<Employee, Double>();
			for (Employee subordinate : urepo.findSubordinates(u.getUserId())) {
				System.out.println(oservice.findTotalOTHoursByMonthYearUserId(OTMonth.getMonth(), OTMonth.getYear(), subordinate.getUserId()));
				hrsmap.put(subordinate, oservice.findTotalOTHoursByMonthYearUserId(OTMonth.getMonth(), OTMonth.getYear(), subordinate.getUserId()));
			}
			model.addAttribute("total", hrsmap);
			OTMonth2.setMonth(OTMonth.getMonth());
			OTMonth2.setYear(OTMonth.getYear());
			model.addAttribute("OTMonth", OTMonth2);
			return "subordinate-ot-summary";
		}
		return "forward:/home/login";
	}
	
	@RequestMapping(value = "/approveot/{id}/{month}/{year}", method = RequestMethod.GET)
	public String subordinateotdetails(HttpSession session, @PathVariable Integer id, @PathVariable Integer month, @PathVariable Integer year) {
		UserSession usession = (UserSession) session.getAttribute("usession");
		User u = usession.getUser();
		System.out.println("test1");
		if (u.getClass().getSimpleName().equalsIgnoreCase("manager")) { 
			Employee employee = (Employee) uservice.findUser(id);
			System.out.println("test2");
			System.out.println(month);
			System.out.println(year);
			Double add = oservice.findTotalOTHoursByMonthYearUserId(month, year, id) / 4;
			System.out.println(employee.getCompLeaveN());
			System.out.println(add);
			Integer newCompLeave = employee.getCompLeaveN() + add.intValue();
			System.out.println(newCompLeave);
			employee.setCompLeaveN(newCompLeave);
			urepo.saveAndFlush(employee);
			
			return "subordinate-ot-summary";
		}
		return "forward:/home/login";
	}
	
	@RequestMapping(value = "/filtersubordinatelistot1/")
	public String employeeListOT1(@ModelAttribute ("OTMonth") @Valid MonthYear OTMonth, HttpSession session, Model model) {
		UserSession usession = (UserSession) session.getAttribute("usession");
		User u = usession.getUser();
		if (u.getClass().getSimpleName().equalsIgnoreCase("manager")) { 
			HashMap<Employee, ArrayList<OvertimeHours>> submap = new HashMap<Employee, ArrayList<OvertimeHours>>();
			HashMap<Employee, Double> hrsmap = new HashMap<Employee, Double>();
			for (Employee subordinate : urepo.findSubordinates(u.getUserId())) {
				submap.put(subordinate, oservice.findOvertimeHoursByMonthYearUserId(OTMonth.getMonth(), OTMonth.getYear(), subordinate.getUserId()));
				System.out.println(oservice.findTotalOTHoursByMonthYearUserId(OTMonth.getMonth(), OTMonth.getYear(), subordinate.getUserId()));
				hrsmap.put(subordinate, oservice.findTotalOTHoursByMonthYearUserId(OTMonth.getMonth(), OTMonth.getYear(), subordinate.getUserId()));
			}
			model.addAttribute("OTHistory", submap);
			model.addAttribute("total", hrsmap);
			return "subordinate-ot-list";
		}
		return "forward:/home/login";
	}
}
