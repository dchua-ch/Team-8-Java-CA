package sg.edu.iss.team8.leaveApp.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@DiscriminatorValue("manager")
public class Manager extends Employee {
	
	public Manager(String name) {
		super(name);
	}
	
	public Manager(String name, String username, String password, 
			Integer annualLeaveN, Integer medicalLeaveN, Integer compLeaveN, 
			Integer reportsTo) {
		super(name, username, password, annualLeaveN, medicalLeaveN, compLeaveN, reportsTo);
	}
}