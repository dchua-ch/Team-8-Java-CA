package sg.edu.iss.team8.leaveApp.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.team8.leaveApp.model.Employee;
import sg.edu.iss.team8.leaveApp.model.Manager;
import sg.edu.iss.team8.leaveApp.model.User;

public interface UserRepo extends JpaRepository<User, Integer>{
	
	@Transactional
	@Modifying
	@Query(value = "update User U set U.user_type = :usertype where u.name = :name",nativeQuery=true)
	public void updateUserType(@Param("usertype") String usertype, @Param("name") String name);
	
	@Query("select U from User U where TYPE(U) = 'employee'")
	public List<Employee> getAllEmployees();
	
	@Query("select U from User U where TYPE(U) = 'manager'")
	public List<Manager> getAllManagers();
	
}
