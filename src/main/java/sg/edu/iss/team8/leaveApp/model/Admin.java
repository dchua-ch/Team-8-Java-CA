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
@DiscriminatorValue("admin")
public class Admin extends User {
	
	public Admin(String name) {
		super(name);
	}
	
	public Admin(String name, String uname, String pwd) {
		super(name, uname, pwd);
	}
}
