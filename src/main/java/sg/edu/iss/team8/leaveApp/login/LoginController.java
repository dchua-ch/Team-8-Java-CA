package sg.edu.iss.team8.leaveApp.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;



@Controller
@RequestMapping
public class LoginController {

	
	@RequestMapping("/")
	public ModelAndView defaultHome() {
		return new ModelAndView("dashboard");
	}

	@RequestMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("login");
	}

	@RequestMapping("/dashboard")
	public ModelAndView userDashboard() {
		return new ModelAndView("dashboard");
	}


	@RequestMapping("/adminDashboard")
	public String admindashboard() {
		return "adminDashboard";
	}
	
	@RequestMapping("/hello")
	public String seeHello() {
		return "hello";
	}
	
	@RequestMapping("/home")
	public String goHome() {
		return "home";
	}
}
	