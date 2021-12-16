package sg.edu.iss.team8.leaveApp.repo;

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
import sg.edu.iss.team8.leaveApp.model.Admin;
import sg.edu.iss.team8.leaveApp.model.Employee;
import sg.edu.iss.team8.leaveApp.model.Manager;
import sg.edu.iss.team8.leaveApp.model.User;
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Team8LeaveApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureTestDatabase(replace =
AutoConfigureTestDatabase.Replace.NONE)

public class UserRepoTest {
	@Autowired 
	UserRepo urepo;
	
	@Test
	@Order(1)
	public void TestCreateUser()
	{
		System.out.println("Executing TestCreateUser()");
		User myUser = new User("Billy","Hillbilly123","obvious password");
		Employee myEmployee = new Employee("Jane","TarzanNumber1Fan", "cool password",1,4,2,1);
		Admin myAdmin = new Admin("Adekunle", "chef33","uncrackable password");
		Manager myManager = new Manager("Hilda","bestboss22", "manager password",2,3,3,3);
		
		urepo.saveAndFlush(myUser);
		urepo.saveAndFlush(myEmployee);
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
		System.out.println(employees.get(0));
		assertEquals(employees.get(0).getName(), "Jane");
	}
	
	@Test
	@Order(3)
	public void TestUpdateUserType()
	{
		System.out.println("Executing TestUpdateUserType()...");
		// get employee
		List<Employee> employees = urepo.getAllEmployees();
		int targetId = employees.get(0).userId;
		String targetUserType = "manager";
		urepo.updateUserType();
		List<Manager> managers = urepo.getAllManagers();
		for(Manager manager : managers)
		{
			System.out.println(manager);
		}
		assertEquals(managers.size(),2);
		
	}
	
	
	//@Test
	//@Order(2)
	public void TestDeleteAllUser() 
	{
		System.out.println("Executing TestDeleteAllUser()");
		urepo.deleteAll();
		List<User> users = urepo.findAll();
		assertEquals(users.size(),0);
	}
	
	

}
