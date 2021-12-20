package sg.edu.iss.team8.leaveApp.services;

import sg.edu.iss.team8.leaveApp.model.Employee;
import sg.edu.iss.team8.leaveApp.model.User;

public interface UserService {

	User findUser(Integer userId);
	
	User authenticate(String uname, String pwd);
}