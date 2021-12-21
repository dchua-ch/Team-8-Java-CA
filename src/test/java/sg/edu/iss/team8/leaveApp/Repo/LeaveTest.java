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
import sg.edu.iss.team8.leaveApp.model.Leave;
import sg.edu.iss.team8.leaveApp.repo.LeaveRepo;
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Team8LeaveApplication.class)
@TestMethodOrder(OrderAnnotation.class)
@AutoConfigureTestDatabase(replace =
AutoConfigureTestDatabase.Replace.NONE)

public class LeaveTest {

	@Autowired
	private LeaveRepo lrepo;
	
	@Test
	@Order(1)
	public void testCreateLeave() {
		Leave leave1 = new Leave(LocalDate.parse("2020-01-08"), LocalDate.parse("2020-01-10"),LeaveEnum.ANNUAL , "...",
				"...", "91111", StatusEnum.APPLIED, "..."); 
		Leave leave2 = new Leave(LocalDate.parse("2020-01-08"), LocalDate.parse("2020-01-10"),LeaveEnum.COMPENSATION , "...",
				"...", "91111", StatusEnum.DELETED, "..."); 
		Leave leave3 = new Leave(LocalDate.parse("2020-01-08"), LocalDate.parse("2020-01-10"),LeaveEnum.MEDICAL , "...",
				"...", "91111", StatusEnum.APPROVED, "...");
		lrepo.saveAndFlush(leave1);
		lrepo.saveAndFlush(leave2);
		lrepo.saveAndFlush(leave3);
		
		List<Leave> leaves = lrepo.findAll();
		assertEquals(leaves.size(), 3);
		//Date format in this test was able to store in DB because controller was bypassed
	}
}
