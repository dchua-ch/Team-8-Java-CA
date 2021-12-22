package sg.edu.iss.team8.leaveApp.controller;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@GetMapping("/home")
	public String goHome(Model model)
	{
	
		return "index";
	}
}
