package sg.edu.iss.team8.leaveApp.Repo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import sg.edu.iss.team8.leaveApp.Team8LeaveApplication;
import sg.edu.iss.team8.leaveApp.helpers.LeaveEnum;
import sg.edu.iss.team8.leaveApp.helpers.StatusEnum;
import sg.edu.iss.team8.leaveApp.model.Admin;
import sg.edu.iss.team8.leaveApp.model.Employee;
import sg.edu.iss.team8.leaveApp.model.Leave;
import sg.edu.iss.team8.leaveApp.model.Manager;
import sg.edu.iss.team8.leaveApp.model.OvertimeHours;
import sg.edu.iss.team8.leaveApp.model.User;
import sg.edu.iss.team8.leaveApp.repo.LeaveRepo;
import sg.edu.iss.team8.leaveApp.repo.UserRepo;
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Team8LeaveApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureTestDatabase(replace =
AutoConfigureTestDatabase.Replace.NONE)

public class UserRepoTest {
	@Autowired 
	UserRepo urepo;
	@Autowired
	LeaveRepo lrepo;
	
	@Test
	@Order(1)
	public void TestCreateUser()
	{
		System.out.println("Executing TestCreateUser()");
		//User myUser = new User("Billy","Hillbilly123","obvious password");
		Employee myEmployee = new Employee("Jane");
		Employee myEmployee2 = new Employee("MarryManager");
		Admin myAdmin = new Admin("Adekunle");
		Manager myManager = new Manager("Hilda");
		
		urepo.saveAndFlush(myEmployee);
		urepo.saveAndFlush(myEmployee2);
		urepo.saveAndFlush(myAdmin);
		urepo.saveAndFlush(myManager);
		
		List<User> users = urepo.findAll();
		assertEquals(users.size(),4);
	}
	
	@Test
	@Order(2)
	public void TestGetAllEmployees()
	{
		System.out.println("Executing TestGetAllEmployees()...");
		// get employee
		List<Employee> employees = urepo.getAllEmployees();
		for(Employee employee : employees)
		{
			System.out.println(employee.getName());
		}
		assertEquals(employees.size(), 2);
	}
	
	@Test
	@Order(3)
	public void TestUpdateUserType()
	{
		System.out.println("Executing TestUpdateUserType()...");
		// change MaryManager to manager 
		urepo.updateUserType("manager", "MarryManager");
		List<Manager> managers = urepo.getAllManagers();
		for(Manager manager : managers)
		{
			System.out.println(manager.getName());
		}
		assertEquals(managers.size(),2);
		
	}
	
	
	@Test
	@Order(4)
	public void TestDeleteAllUser() 
	{
		System.out.println("Executing TestDeleteAllUser()");
		urepo.deleteAll();
		List<User> users = urepo.findAll();
		assertEquals(users.size(),0);
	}
	
	@Test
	@Order(5)
	public void TestAddLeave() {
		Employee employee = urepo.getAllEmployees().get(0);
		Leave leave1 = new Leave();
		leave1.setLeaveType(LeaveEnum.ANNUAL);
		leave1.setStatus(StatusEnum.APPLIED);
		leave1.setEmployee(employee);
		employee.addLeave(leave1);

		urepo.saveAndFlush(employee);
	}
	
	@Test
	@Order(6)
	public void TestAddOTHours() {
		Employee employee = urepo.getAllEmployees().get(0);
		OvertimeHours ot1 = new OvertimeHours();
		ot1.setEmployee(employee);
		employee.addOTHours(ot1);

		urepo.saveAndFlush(employee);
	}
}
