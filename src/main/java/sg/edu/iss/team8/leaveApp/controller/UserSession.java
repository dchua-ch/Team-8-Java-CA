package sg.edu.iss.team8.leaveApp.controller;

import lombok.Data;
import lombok.NoArgsConstructor;
import sg.edu.iss.team8.leaveApp.model.Admin;
import sg.edu.iss.team8.leaveApp.model.Employee;
import sg.edu.iss.team8.leaveApp.model.Manager;
import sg.edu.iss.team8.leaveApp.model.User;

@Data
@NoArgsConstructor
public class UserSession {
	private User user = null;
	private String empType = null; 
}
