package sg.edu.iss.team8.leaveApp.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
	@Id
	@GeneratedValue
	private Integer userId;
	private String name;
	private String password;
	
	// no-args constructor
	public User() {
	}
	
	
	
	public User(String name, String password) {
		super();
		this.name = name;
		this.password = password;
	}



	@Override
	public int hashCode() {
		return Objects.hash(userId);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(userId, other.userId);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
