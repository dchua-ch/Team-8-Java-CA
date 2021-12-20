package sg.edu.iss.team8.leaveApp.controllers;

import javax.servlet.http.HttpSession;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.team8.leaveApp.model.Admin;
import sg.edu.iss.team8.leaveApp.model.Employee;
import sg.edu.iss.team8.leaveApp.model.Manager;
import sg.edu.iss.team8.leaveApp.model.User;
import sg.edu.iss.team8.leaveApp.repo.UserRepo;
import sg.edu.iss.team8.leaveApp.services.UserService;

@Controller
@RequestMapping("/home")
public class CommonController {
	@Autowired
	private UserService uService;
	@Autowired
	private UserRepo urepo;
	
	@RequestMapping(value = "/authenticate")
	public String authenticate(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model,
			HttpSession session) {
		UserSession usession = new UserSession();
		if (bindingResult.hasErrors()) {
			return "login";
		}
		else {
			User u = uService.authenticate(user.getUsername(), user.getPassword());
			System.out.println(user.getUsername());
			System.out.println(user.getPassword());
			if (u == null) {
				return "login";
			}
			usession.setUser(u);
			session.setAttribute("usession", usession);
			return "redirect:/staff/addot";
		}
	}
	
	@RequestMapping(value = "/login")
	public String login(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}
}
