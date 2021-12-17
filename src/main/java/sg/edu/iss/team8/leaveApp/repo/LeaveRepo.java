package sg.edu.iss.team8.leaveApp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sg.edu.iss.team8.leaveApp.model.Leave;

public interface LeaveRepo extends JpaRepository<Leave, Integer>{

}
