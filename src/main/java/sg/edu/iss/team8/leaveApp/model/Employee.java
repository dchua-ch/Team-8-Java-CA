package sg.edu.iss.team8.leaveApp.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("employee")
public class Employee extends User {

	
	@Column(name = "[Annual Leave balance]")
	private Integer annualLeaveN;
	
	@Column(name = "[Medical leave balance]")
	private Integer medicalLeaveN;
	

	@Column(name = "[Compensation leave balance]")
	private Integer compLeaveN;
	
	@Column(name = "[Reports to]")
	private Integer reportsTo;
	
	public Employee() {
		super();
	}
	
	public Employee(String name, String username, String password, Integer reportsTo, Integer annualLeaveN, Integer medicalLeaveN, Integer compLeaveN) {
		super(name, username, password);
		this.reportsTo = reportsTo;
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
	
	public Integer getReportsTo() {
		return reportsTo;
	}

	public void setReportsTo(Integer reportsTo) {
		this.reportsTo = reportsTo;
	}

	@Override
	public String toString() {
		return "Employee [annualLeaveN=" + annualLeaveN + ", medicalLeaveN=" + medicalLeaveN + ", compLeaveN="
				+ compLeaveN + ", reportsTo=" + reportsTo + "]";
	}
	
	

}
