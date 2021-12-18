package sg.edu.iss.team8.leaveApp.Repo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
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
import sg.edu.iss.team8.leaveApp.repo.UserRepo;
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Team8LeaveApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureTestDatabase(replace =
AutoConfigureTestDatabase.Replace.NONE)

public class UserRepoQueryTest {
	@Autowired
	UserRepo urepo;

	//@Test
	//@Order(1)
	public void testSavePolymorphicList()
	{
		
		System.out.println("Executing TestSavePolymorphicList()...");
		
		Employee myEmployee = new Employee("Jane");
		myEmployee.setUsername("userJane");
		
		Employee myEmployee2 = new Employee("MarryManager");
		myEmployee2.setUsername("userMarry");
		
		Admin myAdmin = new Admin("Adekunle");
		myAdmin.setUsername("userAdekunle");
		
		Manager myManager = new Manager("Hilda");
		myManager.setUsername("userHilda");
		
		List<User> users = new ArrayList<User>();
		users.add(myEmployee);
		users.add(myEmployee2);
		users.add(myAdmin);
		users.add(myManager);
		
		urepo.saveAllAndFlush(users);
		List<User> usersInDB = urepo.findAll();
		
		assertEquals(usersInDB.size(),4);
	}
	
	//@Test
	//@Order(2)
	public void testGetUserByUsername()
	{
		Admin targetAdmin = null;
		Manager targetManager = null;
		Employee targetEmployee = null;
		String targetName = "userMarry";
		var targetUser = urepo.getUserByUsername(targetName);	
		System.out.println(targetUser.getClass());
		//assertEquals(targetUser.getClass(),Admin.class);
		var userType = targetUser.getClass();
		if (userType == Admin.class)
		{
			targetAdmin = (Admin) targetUser;
			System.out.println("Admin: " + targetAdmin.getName());
			assertEquals(targetAdmin.getUsername(),targetName);
		}
		else if (userType == Manager.class)
		{
			targetManager = (Manager) targetUser;
			System.out.println("Manager: " + targetManager.getName());
			assertEquals(targetManager.getUsername(),targetName);
		}
		else if (userType == Employee.class)
		{
			targetEmployee = (Employee) targetUser;
			System.out.println("Employee: " +targetEmployee.getName());
			assertEquals(targetEmployee.getUsername(),targetName);
		}
		
		
	}
	//@Test
	//@Order(3)
	public void testGetEmployeeByUsername()
	{
		System.out.println("Executing testGetEmployeeByUsername()");
		String targetUsername = "userJane";
		Employee targetUser = urepo.getEmployeeByUsername(targetUsername);	
		System.out.println(targetUser);
		assertEquals(targetUser.getClass(),Employee.class);
		assertEquals(targetUser.getUsername(),targetUsername);
		
	}
	
	//@Test
	//@Order(4)
	public void testGetEmployeeByUsernameNegative()
	{
		System.out.println("Executing testGetEmployeeByUsernameNegative()");
		String targetUsername = "Adekunle";
		Employee targetUser = urepo.getEmployeeByUsername(targetUsername);	
		System.out.println(targetUser);
		assertEquals(targetUser,null);
	}
	//@Test
	//@Order(5)
	public void testGetManagerByUsername()
	{
		System.out.println("Executing testGetManagerByUsername()");
		String targetUsername = "userHilda";
		Manager targetManager = urepo.getManageryUsername(targetUsername);
		System.out.println(targetManager);
		assertEquals(targetManager.getUsername(),targetUsername);
		
	}
	//@Test
	//@Order(6)
	public void testGetManagerByUsernameNegative()
	{
		System.out.println("Executing testGetManagerByUsernameNegative()");
		String targetUsername = "userJane";
		Manager targetManager = urepo.getManageryUsername(targetUsername);
		System.out.println(targetManager);
		assertEquals(targetManager,null);
		
	}
	
	//@Test
	//@Order(7)
	public void testGetAdminByUsername()
	{
		System.out.println("Executing testGetAdminByUsername()");
		String targetUsername = "userAdekunle";
		Admin targetAdmin = urepo.getAdminByUsername(targetUsername);
		System.out.println(targetAdmin);
		assertEquals(targetAdmin.getUsername(),targetUsername);
		
	}
	//@Test
	//@Order(8)
	public void testGetAdminByUsernameNegative()
	{
		System.out.println("Executing testGetManagerByUsernameNegative()");
		String targetUsername = "userJane";
		Admin targetAdmin = urepo.getAdminByUsername(targetUsername);
		System.out.println(targetAdmin);
		assertEquals(targetAdmin, null);
		
	}
	
	//@Test
	//Order(9)
	public void TestUpdateUserTypeByUsername()
	{
		System.out.println("Executing TestUpdateUserTypeByUsername()...");
		// change MaryManager to Employee 
		urepo.updateUserTypeByUsername("manager", "userMarry");
		List<Manager> managers = urepo.getAllManagers();
		for(Manager manager : managers)
		{
			System.out.println(manager.getName());
		}
		assertEquals(managers.size(),2);
	}
	@Test
	//@Order(10)
	public void TestDeleteAllUser() 
	{
		System.out.println("Executing TestDeleteAllUser()");
		urepo.deleteAll();
		List<User> users = urepo.findAll();
		assertEquals(users.size(),0);
	}

}
