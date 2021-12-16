package sg.edu.iss.team8.leaveApp.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("2")
public class Admin extends User{
	@Column(name = "adminDescription")
	private String description;
	
	public Admin()
	{
		super();
	}


	public Admin(String name, String password, String description) {
		super(name, password);
		this.description = description;
		// TODO Auto-generated constructor stub
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	

}
