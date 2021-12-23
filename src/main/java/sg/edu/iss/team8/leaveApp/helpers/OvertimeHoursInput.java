package sg.edu.iss.team8.leaveApp.helpers;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;
import sg.edu.iss.team8.leaveApp.model.OvertimeHours;

@Data
@NoArgsConstructor
public class OvertimeHoursInput {

	private Integer overtimeId;
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date date;
	private Double hours;
	private String reason;
	private OTEnum status;
	
	public OvertimeHoursInput(OvertimeHours overtimehours) {
		super();
		this.overtimeId = overtimehours.getOvertimeId();
		this.date = convertToDate(overtimehours.getDate());
		this.hours = overtimehours.getHours();
		this.reason = overtimehours.getReason();
		this.status = overtimehours.getStatus();
	}
	
	private Date convertToDate(LocalDate dateToConvert) {
	    return java.util.Date.from(dateToConvert.atStartOfDay()
	      .atZone(ZoneId.systemDefault())
	      .toInstant());
	}
}