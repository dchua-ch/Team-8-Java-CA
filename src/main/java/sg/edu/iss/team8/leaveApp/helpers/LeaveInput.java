package sg.edu.iss.team8.leaveApp.helpers;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import sg.edu.iss.team8.leaveApp.model.Employee;
import sg.edu.iss.team8.leaveApp.model.Leave;

public class LeaveInput {
	

	private Integer leaveId;

	private Date startDate;
	
	private Date endDate;

	private LeaveEnum leaveType;
	private String addtnlReason;
	private String workDissemination;
	private String contact;

	private StatusEnum status;
	private String comments;
	
	private Employee employee;
	
	
	public LeaveInput(Integer leaveId, Date startDate, Date endDate, LeaveEnum leaveType, String addtnlReason,
			String workDissemination, String contact, StatusEnum status, String comments, Employee employee) 
	{
		super();
		this.leaveId = leaveId;
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
	public LeaveInput()
	{
		
	}
	
	public LeaveInput(Leave leave)
	{
		super();
		this.leaveId = leave.getLeaveId();
		this.startDate = convertToDate(leave.getStartDate());
		this.endDate = convertToDate(leave.getEndDate());
		this.leaveType = leave.getLeaveType();
		this.addtnlReason = leave.getAddtnlReason();
		this.workDissemination = leave.getWorkDissemination();
		this.contact = leave.getContact();
		this.status = leave.getStatus();
		this.comments = leave.getComments();
		this.employee = leave.getEmployee();
	}
	private Date convertToDate(LocalDate dateToConvert) {
	    return java.util.Date.from(dateToConvert.atStartOfDay()
	      .atZone(ZoneId.systemDefault())
	      .toInstant());
	}
	
	public Integer getLeaveId() {
		return leaveId;
	}
	public void setLeaveId(Integer leaveId) {
		this.leaveId = leaveId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public LeaveEnum getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(LeaveEnum leaveType) {
		this.leaveType = leaveType;
	}
	public String getAddtnlReason() {
		return addtnlReason;
	}
	public void setAddtnlReason(String addtnlReason) {
		this.addtnlReason = addtnlReason;
	}
	public String getWorkDissemination() {
		return workDissemination;
	}
	public void setWorkDissemination(String workDissemination) {
		this.workDissemination = workDissemination;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public StatusEnum getStatus() {
		return status;
	}
	public void setStatus(StatusEnum status) {
		this.status = status;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	
	

}
