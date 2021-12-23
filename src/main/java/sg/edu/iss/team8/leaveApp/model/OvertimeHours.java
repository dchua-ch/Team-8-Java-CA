package sg.edu.iss.team8.leaveApp.model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;
import sg.edu.iss.team8.leaveApp.helpers.OTEnum;
import sg.edu.iss.team8.leaveApp.helpers.OvertimeHoursInput;

@Entity
@Data
@NoArgsConstructor
public class OvertimeHours {
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private Integer overtimeId;
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private LocalDate date;
	private Double hours;
	private String reason;
	@Column(name = "status", columnDefinition = "ENUM('APPLIED', 'APPROVED', 'REJECTED', 'LEAVEGIVEN')")
	@Enumerated(EnumType.STRING)
	private OTEnum status;
	
	
	@ManyToOne
	@JoinColumn(name = "userid")
	private Employee employee;
	//private User user;


	public OvertimeHours(OvertimeHoursInput otinput) {
		super();
		this.overtimeId = otinput.getOvertimeId();
		this.date = convertToLocalDate(otinput.getDate());
		this.hours = otinput.getHours();
		this.reason = otinput.getReason();
		this.status = otinput.getStatus();
	}
	
	public LocalDate convertToLocalDate(Date dateToConvert) {
	    return dateToConvert.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDate();
	}
}
