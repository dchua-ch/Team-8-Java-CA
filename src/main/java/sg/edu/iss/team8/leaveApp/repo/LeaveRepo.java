package sg.edu.iss.team8.leaveApp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.team8.leaveApp.model.Leave;

public interface LeaveRepo extends JpaRepository<Leave, Integer>{

	@Query("Select l from Leave l where l.leaveId = :leaveId")
	public Leave findLeaveById(@Param("leaveId") Integer leaveId);
}
