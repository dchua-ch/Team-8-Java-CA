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
}
