package sg.edu.iss.team8.leaveApp.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import sg.edu.iss.team8.leaveApp.model.Employee;
import sg.edu.iss.team8.leaveApp.repo.UserRepo;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	@Resource 
	private UserRepo urepo; 
	
	@Override
	public List<Employee> findSubordinates(Integer userId) {
		return urepo.findSubordinates(userId);
	}
	
	@Override
	public Employee findByUserid(Integer userId) {
		return urepo.findByUserid(userId); 
	}
} 
