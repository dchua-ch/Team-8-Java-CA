package sg.edu.iss.team8.leaveApp.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.springframework.beans.factory.annotation.Autowired;

import sg.edu.iss.team8.leaveApp.repo.UserRepo;

@Entity
@DiscriminatorValue("admin")
public class Admin extends User{
	
	//@Autowired
	//UserRepo urepo;
	
	public Admin()
	{
		super();
	}


	public Admin(String name, String username, String password) {
		super(name, username, password);
	
		// TODO Auto-generated constructor stub
	}
	
	public void updateUserType(Integer targetId, String targetUserType)
	{
		//urepo.updateUserType(targetId, targetUserType);
		
	}


	@Override
	public String toString() {
		return "Admin [hashCode()=" + hashCode() + ", getName()=" + getName() + ", getPassword()=" + getPassword()
				+ ", getClass()=" + getClass() + ", toString()=" + super.toString() + "]";
	}

	

}
