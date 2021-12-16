package sg.edu.iss.team8.leaveApp.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Manager")
public class Manager extends Employee
{

	public Manager() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Manager(String name, String username, String password,Integer reportsTo, Integer annualLeaveN, Integer medicalLeaveN, Integer compLeaveN) {
		super(name, username, password, reportsTo, annualLeaveN, medicalLeaveN, compLeaveN);
		// TODO Auto-generated constructor stub
	}
	
	

}
