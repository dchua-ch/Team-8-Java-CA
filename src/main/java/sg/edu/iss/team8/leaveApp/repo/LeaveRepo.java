package sg.edu.iss.team8.leaveApp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sg.edu.iss.team8.leaveApp.model.Leave;

public interface LeaveRepo extends JpaRepository<Leave, Integer>{

	@Query("Select l from Leaves l where l.leaveId = :leaveId")
	public Leave findLeaveById(@Param("leaveId") Integer leaveId);
	
	@Query("Select l from Leaves l where l.employee.userid = :userId")
	public List<Leave> findLeaveByUserid(@Param("userId") Integer userId);

	@Query("SELECT L FROM Leaves L WHERE L.leaveId = :lid")
	public Leave findLeaveByID(@Param("lid") Integer leaveId);

}
