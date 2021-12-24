package sg.edu.iss.team8.leaveApp.login;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import sg.edu.iss.team8.leaveApp.model.Employee;
import sg.edu.iss.team8.leaveApp.service.EmployeeService;
import sg.edu.iss.team8.leaveApp.service.UserService;



@Controller
@RequestMapping
public class LoginController {

	@Autowired
	EmployeeService eService;
	
	@Autowired
	UserService uService;

	@RequestMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("login");
	}

	@RequestMapping("/dashboard")
	public String userDashboard(Model model, Principal principal) {
		Integer userId = uService.findUserByUsername(principal.getName()).userId;
		Employee employee = eService.findByUserId(userId);
		model.addAttribute("employee",employee);
		return "dashboard";
	}


	@RequestMapping("/adminDashboard")
	public String admindashboard() {
		return "adminDashboard";
	}
	

}
	