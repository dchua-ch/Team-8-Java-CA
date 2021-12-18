package sg.edu.iss.team8.leaveApp.Repo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
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
	
//	@Test
//	@Order(1)
//	public void TestCreateUser()
//	{
//		System.out.println("Executing TestCreateUser()");
//		//User myUser = new User("Billy","Hillbilly123","obvious password");
//		Employee myEmployee = new Employee("Jane");
//		Employee myEmployee2 = new Employee("MarryManager");
//		Admin myAdmin = new Admin("Adekunle");
//		Manager myManager = new Manager("Hilda");
//		
//		urepo.saveAndFlush(myEmployee);
//		urepo.saveAndFlush(myEmployee2);
//		urepo.saveAndFlush(myAdmin);
//		urepo.saveAndFlush(myManager);
//		
//		List<User> users = urepo.findAll();
//		assertEquals(users.size(),4);
//	}
//	
//	@Test
//	@Order(2)
//	public void TestGetAllEmployees()
//	{
//		System.out.println("Executing TestGetAllEmployees()...");
//		// get employee
//		List<Employee> employees = urepo.getAllEmployees();
//		for(Employee employee : employees)
//		{
//			System.out.println(employee.getName());
//		}
//		assertEquals(employees.size(), 2);
//	}
//	
//	@Test
//	@Order(3)
//	public void TestUpdateUserType()
//	{
//		System.out.println("Executing TestUpdateUserType()...");
//		// change MaryManager to manager 
//		urepo.updateUserType("manager", "MarryManager");
//		List<Manager> managers = urepo.getAllManagers();
//		for(Manager manager : managers)
//		{
//			System.out.println(manager.getName());
//		}
//		assertEquals(managers.size(),2);
//		
//	}
//	
//	
//	@Test
//	@Order(4)
//	public void TestDeleteAllUser() 
//	{
//		System.out.println("Executing TestDeleteAllUser()");
//		urepo.deleteAll();
//		List<User> users = urepo.findAll();
//		assertEquals(users.size(),0);
//	}
//	
//	@Test
//	@Order(5)
//	public void TestAddLeave() {
//		Employee employee = urepo.getAllEmployees().get(0);
//		Leave leave1 = new Leave();
//		leave1.setLeaveType(LeaveEnum.ANNUAL);
//		leave1.setStatus(StatusEnum.APPLIED);
//		leave1.setEmployee(employee);
//		employee.addLeave(leave1);
//
//		urepo.saveAndFlush(employee);
//	}
//	
//	@Test
//	@Order(6)
//	public void TestAddOTHours() {
//		Employee employee = urepo.getAllEmployees().get(0);
//		OvertimeHours ot1 = new OvertimeHours();
//		ot1.setEmployee(employee);
//		employee.addOTHours(ot1);
//
//		urepo.saveAndFlush(employee);
//	}
	
	Manager manager1 = new Manager("Peter", "peter", "pwd", 14, 60, 0, null); 
	Manager manager2 = new Manager("Jane", "jane", "pwd", 14, 60, 0, 1); 
	
	Employee employee1 = new Employee("Jack", "jack", "pwd", 14, 60, 0, 1); 
	Employee employee2 = new Employee("Jill", "jill", "pwd", 14, 60, 0, 1); 
	Employee employee3 = new Employee("Jake", "jake", "pwd", 14, 60, 0, 5); 
	
	Admin admin1 = new Admin("Adam", "adam", "pwd"); 
	
	@Test
	@Order(1)
	public void TestCreateUserAndLeaves()
	{
		System.out.println("Executing TestCreateUserAndLeaves()");
		// For Manager1
		Leave leave1 = new Leave(LocalDate.parse("2020-01-08"), LocalDate.parse("2020-01-10"),LeaveEnum.ANNUAL , "...",
				"...", "91111", StatusEnum.APPLIED, "...", manager1); 
		Leave leave2 = new Leave(LocalDate.parse("2020-01-08"), LocalDate.parse("2020-01-10"),LeaveEnum.COMPENSATION , "...",
				"...", "91111", StatusEnum.DELETED, "...", manager1); 
		Leave leave3 = new Leave(LocalDate.parse("2020-01-08"), LocalDate.parse("2020-01-10"),LeaveEnum.MEDICAL , "...",
				"...", "91111", StatusEnum.APPROVED, "...", manager1);
		manager1.addLeave(leave1);
		manager1.addLeave(leave2);
		manager1.addLeave(leave3);
		
		Leave leave4 = new Leave(LocalDate.parse("2020-01-08"), LocalDate.parse("2020-01-10"),LeaveEnum.ANNUAL , "...",
				"...", "92222", StatusEnum.APPLIED, "...", manager2); 
		Leave leave5 = new Leave(LocalDate.parse("2020-01-08"), LocalDate.parse("2020-01-10"),LeaveEnum.COMPENSATION , "...",
				"...", "92222", StatusEnum.DELETED, "...", manager2); 
		Leave leave6 = new Leave(LocalDate.parse("2020-01-08"), LocalDate.parse("2020-01-10"),LeaveEnum.MEDICAL , "...",
				"...", "92222", StatusEnum.APPROVED, "...", manager2);
		manager2.addLeave(leave4);
		manager2.addLeave(leave5);
		manager2.addLeave(leave6);
		
		Leave leave7 = new Leave(LocalDate.parse("2020-01-08"), LocalDate.parse("2020-01-10"),LeaveEnum.ANNUAL , "...",
				"...", "93333", StatusEnum.APPLIED, "...", employee1); 
		Leave leave8 = new Leave(LocalDate.parse("2020-01-08"), LocalDate.parse("2020-01-10"),LeaveEnum.COMPENSATION , "...",
				"...", "93333", StatusEnum.DELETED, "...", employee1); 
		Leave leave9 = new Leave(LocalDate.parse("2020-01-08"), LocalDate.parse("2020-01-10"),LeaveEnum.MEDICAL , "...",
				"...", "93333", StatusEnum.APPROVED, "...", employee1);
		employee1.addLeave(leave7);
		employee1.addLeave(leave8);
		employee1.addLeave(leave9);
		
		Leave leave10 = new Leave(LocalDate.parse("2020-01-08"), LocalDate.parse("2020-01-10"),LeaveEnum.ANNUAL , "...",
				"...", "94444", StatusEnum.APPLIED, "...", employee2); 
		Leave leave11 = new Leave(LocalDate.parse("2020-01-08"), LocalDate.parse("2020-01-10"),LeaveEnum.COMPENSATION , "...",
				"...", "94444", StatusEnum.DELETED, "...", employee2); 
		Leave leave12 = new Leave(LocalDate.parse("2020-01-08"), LocalDate.parse("2020-01-10"),LeaveEnum.MEDICAL , "...",
				"...", "94444", StatusEnum.APPROVED, "...", employee2);
		employee2.addLeave(leave10);
		employee2.addLeave(leave11);
		employee2.addLeave(leave12);
		
		Leave leave13 = new Leave(LocalDate.parse("2020-01-08"), LocalDate.parse("2020-01-10"),LeaveEnum.ANNUAL , "...",
				"...", "95555", StatusEnum.APPLIED, "...", employee3); 
		Leave leave14 = new Leave(LocalDate.parse("2020-01-08"), LocalDate.parse("2020-01-10"),LeaveEnum.COMPENSATION , "...",
				"...", "95555", StatusEnum.DELETED, "...", employee3); 
		Leave leave15 = new Leave(LocalDate.parse("2020-01-08"), LocalDate.parse("2020-01-10"),LeaveEnum.MEDICAL , "...",
				"...", "95555", StatusEnum.APPROVED, "...", employee3);
		employee3.addLeave(leave13);
		employee3.addLeave(leave14);
		employee3.addLeave(leave15);
		
		
		urepo.saveAndFlush(manager1);
		urepo.saveAndFlush(manager2);
		urepo.saveAndFlush(employee1);
		urepo.saveAndFlush(employee2);
		urepo.saveAndFlush(employee3);
		urepo.saveAndFlush(admin1); 
		
		List<User> users = urepo.findAll();
		assertEquals(users.size(),6);
		
		List<Leave> leaves = lrepo.findAll(); 
		assertEquals(leaves.size(), 15);
	}
	
	@Test
	@Order(2)
	public void TestFindSubordinates() {
		System.out.println("Executing TestFindSubordinates()");
		List<Employee> elist1 = urepo.findSubordinates(1);
		List<Employee> elist2 = urepo.findSubordinates(5); 
		System.out.println("Subordinates for Manager1: \n");
		for (Employee e : elist1) {
			System.out.println(e.getName());
		}
		System.out.println("\n");
		
		System.out.println("Subordinates for Manager2: \n");
		for (Employee e : elist2) {
			System.out.println(e.getName());
		}
		System.out.println("\n");
		assertEquals(elist1.size(), 3);
		assertEquals(elist2.size(), 1);
	}
	
	@Test 
	@Order(3)
	public void TestFindPendingLeaveByUID() {
		System.out.println("Executing TestFindPendingLeaveByUID()");
		List<Leave> llist = lrepo.findPendingLeaveByUID(9); 
		for (Leave l : llist) {
			System.out.println(l.getStatus());
		}
		assertEquals(llist.size(), 1);
	}
}
