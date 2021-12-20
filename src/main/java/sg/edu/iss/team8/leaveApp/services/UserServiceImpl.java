package sg.edu.iss.team8.leaveApp.services;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.iss.team8.leaveApp.model.User;
import sg.edu.iss.team8.leaveApp.repo.UserRepo;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Resource
	private UserRepo urepo;
	
	public User findUser(Integer userId) {
		return urepo.findById(userId).orElse(null);
	}
	
	public User authenticate(String uname, String pwd) {
		User u = urepo.findUserByNamePwd(uname, pwd);
		return u;
	}
}
