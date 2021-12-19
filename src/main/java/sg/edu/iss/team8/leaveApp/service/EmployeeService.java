package sg.edu.iss.team8.leaveApp.service;

import java.util.List;

import sg.edu.iss.team8.leaveApp.model.Employee;

public interface EmployeeService {

	List<Employee> findSubordinates(Integer userId); 
	
	Employee findByUserId(Integer userId); 
}
