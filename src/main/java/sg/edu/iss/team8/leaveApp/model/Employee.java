package sg.edu.iss.team8.leaveApp.model;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@DiscriminatorValue("employee")
public class Employee extends User{

	private Integer annualLeaveN;
	private Integer medicalLeaveN;
	private Integer compLeaveN;
	private Integer reportsTo;
	
	public Employee(String name) {
		super(name);
	}
}
