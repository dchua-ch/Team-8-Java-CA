package sg.edu.iss.team8.leaveApp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.edu.iss.team8.leaveApp.model.Admin;
import sg.edu.iss.team8.leaveApp.model.Employee;

import java.util.Optional;

public interface EmployeeRepo extends JpaRepository<Employee, Integer> {

}
