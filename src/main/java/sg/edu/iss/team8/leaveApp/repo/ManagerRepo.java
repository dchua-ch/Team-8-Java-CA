package sg.edu.iss.team8.leaveApp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.edu.iss.team8.leaveApp.model.Employee;
import sg.edu.iss.team8.leaveApp.model.Manager;

public interface ManagerRepo extends JpaRepository<Manager, Integer> {

}
