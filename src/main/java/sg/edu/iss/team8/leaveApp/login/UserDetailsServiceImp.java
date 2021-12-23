package sg.edu.iss.team8.leaveApp.login;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class UserDetailsServiceImp implements UserDetailsService{

	@Autowired
	public BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private DataSource dataSource;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<UserDetails> userDetailsList = populateUserDetails();
		
		for (UserDetails u: userDetailsList)
		{
			if (u.getUsername().equals(username))
			{
				return u;
			}
		}
		return null;
	}
	
	public List<UserDetails> populateUserDetails(){
		List<UserDetails> userDetailsList = new ArrayList<>();
		//auth.dataSource();
		
		return userDetailsList;
	}


}
