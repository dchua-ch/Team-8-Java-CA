package sg.edu.iss.team8.leaveApp.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Staff {

    public Integer userId;
    private String name;
    private String username;
    private String password;

    private Integer annualLeaveN;
    private Integer medicalLeaveN;
    private Integer compLeaveN;
    private Integer reportsTo;
    private Boolean enabled;
    private String  user_type = "employee";

    public Staff(Integer userId, String name, String username, String password, Integer annualLeaveN, Integer medicalLeaveN, Integer compLeaveN, Integer reportsTo, String user_type) {
        this.userId = userId;
        this.name = name;
        this.username = username;
        this.password = password;
        this.annualLeaveN = annualLeaveN;
        this.medicalLeaveN = medicalLeaveN;
        this.compLeaveN = compLeaveN;
        this.reportsTo = reportsTo;
        this.user_type = user_type;
    }

	public Staff(Integer userId, String name, String username, String password, Integer annualLeaveN,
			Integer medicalLeaveN, Integer compLeaveN, Integer reportsTo, Boolean enabled, String user_type) {
		super();
		this.userId = userId;
		this.name = name;
		this.username = username;
		this.password = password;
		this.annualLeaveN = annualLeaveN;
		this.medicalLeaveN = medicalLeaveN;
		this.compLeaveN = compLeaveN;
		this.reportsTo = reportsTo;
		this.enabled = enabled;
		this.user_type = user_type;
	}
    
}
