package sg.edu.iss.team8.leaveApp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.edu.iss.team8.leaveApp.model.Admin;
import sg.edu.iss.team8.leaveApp.model.Leave;

public interface AdminRepo extends JpaRepository<Admin, Integer> {

}
