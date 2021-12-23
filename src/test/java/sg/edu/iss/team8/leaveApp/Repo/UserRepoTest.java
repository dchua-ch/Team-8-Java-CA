package sg.edu.iss.team8.leaveApp.Repo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
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
import sg.edu.iss.team8.leaveApp.service.LeaveService;
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
	
	@Autowired 
	LeaveService lService; 
	
	@Test
	@Order(1)
	public void TestFindSubordinates() {
		System.out.println("Executing TestFindSubordinates()");
		List<Employee> elist1 = urepo.findSubordinates(1);
		List<Employee> elist2 = urepo.findSubordinates(2); 
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
	@Order(2)
	public void TestFindLeaveByUID() {
		System.out.println("Executing TestFindLeaveByUID()");
		List<Leave> llist = urepo.findLeaveByUID(4); 
		for (Leave l : llist) {
			System.out.println(l.getStatus());
		}
		assertEquals(llist.size(), 5);
	}
	
	//@Test
	@Order(3)
	public void TestFindPendingLeaveByUID() {
		System.out.println("Executing TestFindPendingLeaveByUID");
		ArrayList<Leave> pendingList = lService.findPendingLeaveByUID(4); 
		for(Leave l : pendingList) {
			System.out.println(l.getLeaveId());
		}
		assertEquals(pendingList.size(), 3);
	}
	

	@Test
	@Order(4)
	public void TestFindLeaveByUIDAndLID() {
		System.out.println("Executing TestFindLeaveByUIDAndLID");
		Leave leave = lService.findLeaveByUIDAndLID(3, 8); 
		assertEquals(leave.getLeaveType(), LeaveEnum.COMPENSATION);
	}
	
	//@Test
	@Order(5)
	public void TestFindByUserId() {
		System.out.println("Executing TestFindByUserId()");
		Employee employee = urepo.findByUserId(2); 
		assertEquals(employee.getName(), "Jane"); 
	}
	
	//@Test
	@Order(6)
	public void TestFindLeaveById() {
		System.out.println("Executing TestFindLeaveById()");
		Leave leave = lrepo.findLeaveByID(8); 
		assertEquals(leave.getStatus(), StatusEnum.DELETED); 
	}
  
	@Test
	@Order(7)
	public void TestGetEmpTypeByUID() {
		System.out.println("Executing TestGetEmpTypeByUID()");
		String empType = urepo.getEmpTypeByUID(1); 
		assertEquals(empType, "manager"); 
	}

}
