package sg.edu.iss.team8.leaveApp.Repo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		Leave leave1 = new Leave(LocalDate.parse("08/01/2020", format), LocalDate.parse("10/01/2020", format),LeaveEnum.ANNUAL , "...",
				"...", "91111", StatusEnum.APPLIED, "..."); 
		Leave leave2 = new Leave(LocalDate.parse("08/01/2020", format), LocalDate.parse("10/01/2020", format),LeaveEnum.COMPENSATION , "...",
				"...", "91111", StatusEnum.DELETED, "..."); 
		Leave leave3 = new Leave(LocalDate.parse("08/01/2020", format), LocalDate.parse("10/01/2020", format),LeaveEnum.MEDICAL , "...",
				"...", "91111", StatusEnum.APPROVED, "...");
		lrepo.deleteAll();
		lrepo.saveAndFlush(leave1);
		lrepo.saveAndFlush(leave2);
		lrepo.saveAndFlush(leave3);
		
		List<Leave> leaves = lrepo.findAll();
		assertEquals(leaves.size(), 3);
	}
	
	@Test
	@Order(2)
	public void testFindLeaveByLeaveId() {
		List<Leave> leaves = lrepo.findAll();
		Integer id1 = leaves.get(0).getLeaveId();
		Integer id2 = leaves.get(1).getLeaveId();
		Leave sampleLeave1 = lrepo.findLeaveById(id1);
		Leave sampleLeave2 = lrepo.findLeaveById(id2);
		List<Leave> leavesList = new ArrayList<>();
		leavesList.add(sampleLeave1);
		leavesList.add(sampleLeave2);
		assertEquals(leavesList.size(), 2);
	}
}
