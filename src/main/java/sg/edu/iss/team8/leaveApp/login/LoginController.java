package sg.edu.iss.team8.leaveApp.login;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import sg.edu.iss.team8.leaveApp.model.User;



@Controller
@RequestMapping
public class LoginController {

	

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
	
    /*@RequestMapping(value = { "/overview" }, method = RequestMethod.GET)
    public ModelAndView overViewPage(HttpServletRequest request) {

        ModelAndView model = new ModelAndView();
        model.addObject("title", "Spring Security + Hibernate Example");
        model.addObject("message", "This is default page!");
        model.setViewName("hello");


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();

        User u = userService.getUser(userDetail.getUsername());
        request.getSession().setAttribute("userId", u.getUserId());

        return model;

    }*/

}
	