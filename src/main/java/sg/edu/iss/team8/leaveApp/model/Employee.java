package sg.edu.iss.team8.leaveApp.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("1")
public class Employee extends User {

	
	
	private Integer annualLeaveN;
	private Integer medicalLeaveN;
	private Integer compLeaveN;
	
	public Employee() {
		super();
	}
	
	public Employee(String name, String password, Integer annualLeaveN, Integer medicalLeaveN, Integer compLeaveN) {
		super(name,password);
		this.annualLeaveN = annualLeaveN;
		this.medicalLeaveN = medicalLeaveN;
		this.compLeaveN = compLeaveN;
	}
	
	

	public Integer getAnnualLeaveN() {
		return annualLeaveN;
	}

	public void setAnnualLeaveN(Integer annualLeaveN) {
		this.annualLeaveN = annualLeaveN;
	}

	public Integer getMedicalLeaveN() {
		return medicalLeaveN;
	}

	public void setMedicalLeaveN(Integer medicalLeaveN) {
		this.medicalLeaveN = medicalLeaveN;
	}

	public Integer getCompLeaveN() {
		return compLeaveN;
	}

	public void setCompLeaveN(Integer compLeaveN) {
		this.compLeaveN = compLeaveN;
	}
	
	

}
