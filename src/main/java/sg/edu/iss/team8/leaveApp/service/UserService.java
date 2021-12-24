package sg.edu.iss.team8.leaveApp.service;

import sg.edu.iss.team8.leaveApp.model.Employee;
import sg.edu.iss.team8.leaveApp.model.User;

public interface UserService {

	User findUser(Integer userId);
	
	User authenticate(String uname, String pwd);
	
	String getEmpTypeByUID(Integer userId);

	void removeUser(User user);

	User findUserByUsername(String username);
}

