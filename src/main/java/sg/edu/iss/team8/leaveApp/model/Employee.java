package sg.edu.iss.team8.leaveApp.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import sg.edu.iss.team8.leaveApp.repo.UserRepo;
import sg.edu.iss.team8.leaveApp.services.UserService;

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


	@OneToMany(mappedBy = "employee", cascade = { CascadeType.ALL })
	@LazyCollection(LazyCollectionOption.FALSE)
	private Collection<Leave> leaves = new ArrayList<Leave>();
	
	public void addLeave(Leave leave) {
		this.leaves.add(leave);
	}
	
	@OneToMany(mappedBy = "employee", cascade = { CascadeType.ALL })
	@LazyCollection(LazyCollectionOption.FALSE)
	private Collection<OvertimeHours> otHours = new ArrayList<OvertimeHours>();
	
	public void addOTHours(OvertimeHours ot) {
		this.otHours.add(ot);
	}
	
	
	public Employee(String name, String uname, String pwd) {
		super(name, uname, pwd);
	}
	
	public Employee(String name, String uname, String pwd, Integer reportsTo) {
		super(name, uname, pwd);
		this.reportsTo = reportsTo;
	}
	
}
