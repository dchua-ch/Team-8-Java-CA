package sg.edu.iss.team8.leaveApp.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import sg.edu.iss.team8.leaveApp.model.User;
import sg.edu.iss.team8.leaveApp.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService{
	@Resource
	private UserRepo urepo;
	
	public User findUser(Integer userId) {
		return urepo.findById(userId).orElse(null);
	}
	
	public User authenticate(String uname, String pwd) {
		User u = urepo.findUserByNamePwd(uname, pwd);
		return u;
	}
	
	public String getEmpTypeByUID(Integer userId) {
		return urepo.getEmpTypeByUID(userId);
	}

}
