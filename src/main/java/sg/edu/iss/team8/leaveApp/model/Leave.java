package sg.edu.iss.team8.leaveApp.model;

import java.time.LocalDate;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.NoArgsConstructor;

import sg.edu.iss.team8.leaveApp.helpers.LeaveEnum;
import sg.edu.iss.team8.leaveApp.helpers.StatusEnum;

@Entity (name = "Leaves")
@Data
@NoArgsConstructor
public class Leave {
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private Integer leaveId;
	private LocalDate startDate;
	private LocalDate endDate;
	@Column(name = "leaveType", columnDefinition = "ENUM('ANNUAL', 'MEDICAL', 'COMPENSATION')")
	@Enumerated(EnumType.STRING)
	private LeaveEnum leaveType;
	private String addtnlReason;
	private String workDissemination;
	private String contact;
	@Column(name = "status", columnDefinition = "ENUM('APPLIED', 'APPROVED', 'REJECTED', 'DELETED', 'CANCELLED', 'ARCHIVED', 'UPDATED')")
	@Enumerated(EnumType.STRING)
	private StatusEnum status;
	@NotEmpty
	private String comments;
	
	@ManyToOne
	@JoinColumn(name = "userid")
	private Employee employee;
	
	public Leave(LocalDate startDate, LocalDate endDate, LeaveEnum leaveType, String addtnlReason,
			String workDissemination, String contact, StatusEnum status, String comments) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.leaveType = leaveType;
		this.addtnlReason = addtnlReason;
		this.workDissemination = workDissemination;
		this.contact = contact;
		this.status = status;
		this.comments = comments;
	}
	
	public Leave(LocalDate startDate, LocalDate endDate, LeaveEnum leaveType, String addtnlReason,
			String workDissemination, String contact, StatusEnum status, String comments, Employee employee) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.leaveType = leaveType;
		this.addtnlReason = addtnlReason;
		this.workDissemination = workDissemination;
		this.contact = contact;
		this.status = status;
		this.comments = comments;
		this.employee = employee;
	}
}
