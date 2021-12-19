package sg.edu.iss.team8.leaveApp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sg.edu.iss.team8.leaveApp.model.Leave;

public interface LeaveRepo extends JpaRepository<Leave, Integer>{
//	@Query("SELECT L FROM Leave L WHERE L.leaveId = :lid")
//	public Leave findLeaveByLID(@Param("lid") Integer leaveId);
}
