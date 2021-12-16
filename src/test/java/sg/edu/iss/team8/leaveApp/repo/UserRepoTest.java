package sg.edu.iss.team8.leaveApp.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import sg.edu.iss.team8.leaveApp.Team8LeaveApplication;
import sg.edu.iss.team8.leaveApp.model.Admin;
import sg.edu.iss.team8.leaveApp.model.Employee;
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
		User myUser = new User("Billy","obvious password");
		Employee myEmployee = new Employee("Jane", "cool password",4,2,1);
		Admin myAdmin = new Admin("Adekunle", "uncrackable password","overworked sys admin");
		
		urepo.saveAndFlush(myUser);
		urepo.saveAndFlush(myEmployee);
		urepo.saveAndFlush(myAdmin);
		
		List<User> users = urepo.findAll();
		assertEquals(users.size(),3);
	
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
