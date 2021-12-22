package sg.edu.iss.team8.leaveApp.helpers;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Outcome {
	
	@NotEmpty
	private String decision;
	@NotEmpty
	private String comments;
	private Integer id;
	private String start; 
	private String end; 
}
