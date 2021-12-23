package sg.edu.iss.team8.leaveApp.model;

import java.time.LocalDate;
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

@Entity
@Data
@NoArgsConstructor
public class OvertimeHours {
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private Integer overtimeId;
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date date;
	private Double hours;
	private String reason;
	@Column(name = "status", columnDefinition = "ENUM('APPLIED', 'APPROVED', 'REJECTED', 'LEAVEGIVEN')")
	@Enumerated(EnumType.STRING)
	private OTEnum status;
	
	
	@ManyToOne
	@JoinColumn(name = "userid")
	private Employee employee;
	//private User user;
	
}
