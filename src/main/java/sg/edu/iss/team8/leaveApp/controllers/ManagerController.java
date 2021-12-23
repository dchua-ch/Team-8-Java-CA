package sg.edu.iss.team8.leaveApp.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sg.edu.iss.team8.leaveApp.helpers.MonthYear;
import sg.edu.iss.team8.leaveApp.helpers.OTEnum;
import sg.edu.iss.team8.leaveApp.model.Employee;
import sg.edu.iss.team8.leaveApp.model.OvertimeHours;
import sg.edu.iss.team8.leaveApp.model.User;
import sg.edu.iss.team8.leaveApp.repo.OvertimeHoursRepo;
import sg.edu.iss.team8.leaveApp.repo.UserRepo;
import sg.edu.iss.team8.leaveApp.services.OvertimeHoursService;
import sg.edu.iss.team8.leaveApp.services.OvertimeHoursServiceImpl;
import sg.edu.iss.team8.leaveApp.services.UserService;
import sg.edu.iss.team8.leaveApp.validators.MonthYearValidator;
import sg.edu.iss.team8.leaveApp.validators.OvertimeHoursValidator;

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
	
	@Autowired
	private MonthYearValidator myValidator;
	
	@InitBinder("OTMonth")
	private void initLeaveBinder(WebDataBinder binder) {
		//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		//dateFormat.setLenient(false);
		//binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
		binder.addValidators(myValidator);
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
	
	@RequestMapping(value = "/subordinatelistot" , method = RequestMethod.GET)
	public String employeeListOT(@ModelAttribute ("OTMonth") @Valid MonthYear OTMonth, BindingResult bindingResult, HttpSession session, Model model) {
		UserSession usession = (UserSession) session.getAttribute("usession");
		if (usession != null) { //if not logged in
			User u = usession.getUser();
			if (u.getClass().getSimpleName().equalsIgnoreCase("manager")) { 
//				if (OTMonth.getDate() == "") {
//					return "redirect:/manager/subordinatelistot1";
//				}
				if (bindingResult.hasErrors()) {
					return "find-month-ot";
				}
				
				Integer month = Integer.parseInt(OTMonth.getDate().substring(5));
				Integer year = Integer.parseInt(OTMonth.getDate().substring(0,4));
				System.out.println(month);
				System.out.println(year);
				LinkedHashMap<Employee, ArrayList<OvertimeHours>> submap = new LinkedHashMap<Employee, ArrayList<OvertimeHours>>();
				LinkedHashMap<Employee, Double> totalmap = new LinkedHashMap<Employee, Double>();
				LinkedHashMap<Employee, Double> totalapprmap = new LinkedHashMap<Employee, Double>();
				LinkedHashMap<Employee, Double> totalapplmap = new LinkedHashMap<Employee, Double>();
				LinkedHashMap<Employee, Double> totalgivenmap = new LinkedHashMap<Employee, Double>();
				LinkedHashMap<Employee, Double> totalrejmap = new LinkedHashMap<Employee, Double>();
				LinkedHashMap<Employee, Integer> totalleavegivenmap = new LinkedHashMap<Employee, Integer>();
				for (Employee subordinate : urepo.findSubordinates(u.getUserId())) {
					submap.put(subordinate, oservice.findOTHoursByMYUserId(month, year, subordinate.getUserId()));
					totalmap.put(subordinate, oservice.findTotalOTHoursByMYUserId(month, year, subordinate.getUserId()));
					totalapprmap.put(subordinate, oservice.findTotalOTHoursByMYUserIdStatus(month, year, subordinate.getUserId(), OTEnum.APPROVED));
					totalapplmap.put(subordinate, oservice.findTotalOTHoursByMYUserIdStatus(month, year, subordinate.getUserId(), OTEnum.APPLIED));
					totalrejmap.put(subordinate, oservice.findTotalOTHoursByMYUserIdStatus(month, year, subordinate.getUserId(), OTEnum.REJECTED));
					totalgivenmap.put(subordinate, oservice.findTotalOTHoursByMYUserIdStatus(month, year, subordinate.getUserId(), OTEnum.LEAVEGIVEN));
					Double add = oservice.findTotalOTHoursByMYUserIdStatus(month, year, subordinate.getUserId(), OTEnum.LEAVEGIVEN) / 4;
					totalleavegivenmap.put(subordinate, add.intValue());
				}
				model.addAttribute("OTHistory", submap);
				Integer[] my = {month, year};
				model.addAttribute("monthyear", my);
				model.addAttribute("total", totalmap);
				model.addAttribute("totalapprove", totalapprmap);
				model.addAttribute("totalapplied", totalapplmap);
				model.addAttribute("totalrejected", totalrejmap);
				model.addAttribute("totalgiven", totalgivenmap);
				model.addAttribute("totalleavegiven", totalleavegivenmap);
				return "subordinate-ot-list";
			}
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
			Double add = oservice.findTotalOTHoursByMYUserIdStatus(month, year, id, OTEnum.APPLIED) + oservice.findTotalOTHoursByMYUserIdStatus(month, year, id, OTEnum.APPROVED);
			add = add / 4; 
			ArrayList<OvertimeHours> oth = oservice.findOTHoursByMYUserId(month, year, id);
			for (OvertimeHours o : oth) {
				if (o.getStatus() == OTEnum.APPLIED || o.getStatus() == OTEnum.APPROVED) {
					o.setStatus(OTEnum.LEAVEGIVEN);
					oservice.updateOTHours(o);
				}
			}
			Integer newCompLeave = employee.getCompLeaveN() + add.intValue();
			employee.setCompLeaveN(newCompLeave);
			urepo.saveAndFlush(employee);
			
			return "redirect:/manager/subordinatelistot?date=" + year + "-" + month;
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
			return "redirect:/manager/subordinatelistot?date=" + year + "-" + month;
		}
		return "forward:/home/login";
	}
	
	@RequestMapping(value = "/rejectot1/{id}/{month}/{year}/{otid}", method = RequestMethod.GET)
	public String rejectot1(HttpSession session, @PathVariable Integer id, @PathVariable Integer month, @PathVariable Integer year, @PathVariable Integer otid) {
		UserSession usession = (UserSession) session.getAttribute("usession");
		User u = usession.getUser();
		if (u.getClass().getSimpleName().equalsIgnoreCase("manager")) { 
			OvertimeHours ot = orepo.findById(otid).orElse(null);
			ot.setStatus(OTEnum.REJECTED);
			oservice.updateOTHours(ot);
			return "redirect:/manager/subordinatelistot?date=" + year + "-" + month;
		}
		return "forward:/home/login";
	}
	
	@RequestMapping(value = "/calc/{id}/{month}/{year}", method = RequestMethod.GET)
	public String calc(HttpSession session, @PathVariable Integer id, @PathVariable Integer month, @PathVariable Integer year) {
		UserSession usession = (UserSession) session.getAttribute("usession");
		User u = usession.getUser();
		if (u.getClass().getSimpleName().equalsIgnoreCase("manager")) { 
			Employee employee = (Employee) urepo.findById(id).orElse(null);
			Double add = oservice.findTotalOTHoursByMYUserIdStatus(month, year, id, OTEnum.APPROVED) / 4;
			ArrayList<OvertimeHours> oth = oservice.findOTHoursByMYUserIdStatus(month, year, id, OTEnum.APPROVED);
			for (OvertimeHours o : oth) {
				o.setStatus(OTEnum.LEAVEGIVEN);
				oservice.updateOTHours(o);
			}
			Integer newCompLeave = employee.getCompLeaveN() + add.intValue();
			employee.setCompLeaveN(newCompLeave);
			urepo.saveAndFlush(employee);
			return "redirect:/manager/subordinatelistot?date=" + year + "-" + month;
		}
		return "forward:/home/login";
	}

}
