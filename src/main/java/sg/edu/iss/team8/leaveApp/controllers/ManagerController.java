package sg.edu.iss.team8.leaveApp.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.catalina.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.annotation.ModelFactory;

import sg.edu.iss.team8.leaveApp.helpers.MonthYear;
import sg.edu.iss.team8.leaveApp.helpers.OTEnum;
import sg.edu.iss.team8.leaveApp.helpers.Outcome;
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
					submap.put(subordinate, oservice.findOTHoursByUserId(subordinate.getUserId()));
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
				hrsmap.put(subordinate, oservice.findTotalOTHoursByMYUserId(OTMonth.getMonth(), OTMonth.getYear(), subordinate.getUserId()));
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
		if (u.getClass().getSimpleName().equalsIgnoreCase("manager")) { 
			Employee employee = (Employee) uservice.findUser(id);
			Double add = oservice.findTotalOTHoursByMYUserIdStatus(month, year, id, OTEnum.APPLIED) + oservice.findTotalOTHoursByMYUserIdStatus(month, year, id, OTEnum.APPROVED);
			add = add / 4; 
			ArrayList<OvertimeHours> oth = oservice.findOTHoursByMYUserId(month, year, id);
			for (OvertimeHours o : oth) {
				if (o.getStatus() == OTEnum.APPLIED || o.getStatus() == OTEnum.APPROVED) {
					o.setStatus(OTEnum.LEAVEGIVEN);
					oservice.updateOTHours(o);
				}
			}
			//Double add = oservice.findTotalOTHoursByMYUserId(month, year, id) / 4;
			//Double add = oservice.findTotalOTHoursByMYUserIdStatus(month, year, id, OTEnum.APPLIED) / 4;
			Integer newCompLeave = employee.getCompLeaveN() + add.intValue();
			employee.setCompLeaveN(newCompLeave);
			urepo.saveAndFlush(employee);
			
			return "subordinate-ot-summary";
		}
		return "forward:/home/login";
	}
	
	@RequestMapping(value = "/approveot1/{id}/{month}/{year}/{otid}", method = RequestMethod.GET)
	public String approveot1(HttpSession session, @PathVariable Integer id, @PathVariable Integer month, @PathVariable Integer year, @PathVariable Integer otid) {
		UserSession usession = (UserSession) session.getAttribute("usession");
		User u = usession.getUser();
		if (u.getClass().getSimpleName().equalsIgnoreCase("manager")) { 
			OvertimeHours ot = orepo.findById(otid).orElse(null);
			ot.setStatus(OTEnum.APPROVED);
			oservice.updateOTHours(ot);
			return "redirect:/manager/otdetails/" + id + "/" + month + "/" + year;
		}
		return "forward:/home/login";
	}
	
	@RequestMapping(value = "/rejectot1/{id}/{month}/{year}/{otid}", method = RequestMethod.GET)
	public String rejectot1(HttpSession session, @PathVariable Integer id, @PathVariable Integer month, @PathVariable Integer year, @PathVariable Integer otid) {
		UserSession usession = (UserSession) session.getAttribute("usession");
		User u = usession.getUser();
		if (u.getClass().getSimpleName().equalsIgnoreCase("manager")) { 
			//Employee employee = (Employee) uservice.findUser(id);
			OvertimeHours ot = orepo.findById(otid).orElse(null);
			ot.setStatus(OTEnum.REJECTED);
			oservice.updateOTHours(ot);
			return "redirect:/manager/otdetails/" + id + "/" + month + "/" + year;
		}
		return "forward:/home/login";
	}
	
	@RequestMapping(value = "/calc/{id}/{month}/{year}", method = RequestMethod.GET)
	public String calc(HttpSession session, @PathVariable Integer id, @PathVariable Integer month, @PathVariable Integer year) {
		UserSession usession = (UserSession) session.getAttribute("usession");
		User u = usession.getUser();
		System.out.println("test");
		if (u.getClass().getSimpleName().equalsIgnoreCase("manager")) { 
			Employee employee = (Employee) urepo.findById(id).orElse(null);
			Double add = oservice.findTotalOTHoursByMYUserIdStatus(month, year, id, OTEnum.APPROVED) / 4;
			ArrayList<OvertimeHours> oth = oservice.findOTHoursByMYUserIdStatus(month, year, id, OTEnum.APPROVED);
			for (OvertimeHours o : oth) {
				o.setStatus(OTEnum.LEAVEGIVEN);
				oservice.updateOTHours(o);
			}
			System.out.println(add);
			Integer newCompLeave = employee.getCompLeaveN() + add.intValue();
			employee.setCompLeaveN(newCompLeave);
			urepo.saveAndFlush(employee);
			
			return "redirect:/manager/otdetails/" + id + "/" + month + "/" + year;
		}
		return "forward:/home/login";
	}
	
	@RequestMapping(value = "/otdetails/{id}/{month}/{year}", method = RequestMethod.GET)
	public String otdetails(HttpSession session, Model model, @PathVariable Integer id, @PathVariable Integer month, @PathVariable Integer year) {
		UserSession usession = (UserSession) session.getAttribute("usession");
		User u = usession.getUser();
		if (u.getClass().getSimpleName().equalsIgnoreCase("manager")) { 
			model.addAttribute("OTHistory", oservice.findOTHoursByMYUserId(month, year, id));
			Integer[] obj = {id, month, year};
			model.addAttribute("identity", obj);
			model.addAttribute("total", oservice.findTotalOTHoursByMYUserId(month, year, id));
			model.addAttribute("totalapprove", oservice.findTotalOTHoursByMYUserIdStatus(month, year, id, OTEnum.APPROVED));
			model.addAttribute("totalapplied", oservice.findTotalOTHoursByMYUserIdStatus(month, year, id, OTEnum.APPLIED));
			return "test";
		}
		return "forward:/home/login";
	}
}
