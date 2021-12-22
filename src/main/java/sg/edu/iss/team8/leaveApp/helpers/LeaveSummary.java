package sg.edu.iss.team8.leaveApp.helpers;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LeaveSummary {

	private Integer leaveId;
	private String name;
	private LocalDate startDate;
	private LocalDate endDate;
	private LeaveEnum leaveType;
	
	public LeaveSummary(Integer leaveId, String name, LocalDate startDate, LocalDate endDate, LeaveEnum leaveType) {
		super();
		this.leaveId = leaveId;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.leaveType = leaveType;
	}
}
