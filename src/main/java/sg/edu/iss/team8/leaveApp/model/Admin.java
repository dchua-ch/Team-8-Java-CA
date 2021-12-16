package sg.edu.iss.team8.leaveApp.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Admin")
public class Admin extends User{
	
	
	public Admin()
	{
		super();
	}


	public Admin(String name, String username, String password) {
		super(name, username, password);
	
		// TODO Auto-generated constructor stub
	}

	

}
