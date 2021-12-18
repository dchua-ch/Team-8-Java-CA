package sg.edu.iss.team8.leaveApp.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.team8.leaveApp.model.Admin;
import sg.edu.iss.team8.leaveApp.model.Employee;
import sg.edu.iss.team8.leaveApp.model.Manager;
import sg.edu.iss.team8.leaveApp.model.User;

public interface UserRepo extends JpaRepository<User, Integer>{
	
	@Transactional
	@Modifying
	@Query(value = "update User U set U.user_type = :usertype where u.name = :name",nativeQuery=true)
	public void updateUserType(@Param("usertype") String usertype, @Param("name") String name);
	
	@Transactional
	@Modifying
	@Query(value = "update User U set U.user_type = :usertype where u.username = :username",nativeQuery=true)
	public void updateUserTypeByUsername(@Param("usertype") String usertype, @Param("username") String username);

	
	
	@Query("select U from User U where U.name = :name")
	public <T> T getUserByName(@Param("name") String name);
	
	@Query("select U from User U where U.username = :username")
	public <T> T getUserByUsername(@Param("username") String username);
	
	@Query("select U from User U where U.username = :username and TYPE(U) = 'employee'")
	public Employee getEmployeeByUsername(@Param("username") String username);
	
	@Query("select U from User U where U.username = :username and TYPE(U) = 'manager'")
	public Manager getManageryUsername(@Param("username") String username);
	
	@Query("select U from User U where U.username = :username and TYPE(U) = 'admin'")
	public Admin getAdminByUsername(@Param("username") String username);
	
	
	
	@Query("select U from User U where TYPE(U) = 'employee'")
	public List<Employee> getAllEmployees();
	
	@Query("select U from User U where TYPE(U) = 'manager'")
	public List<Manager> getAllManagers();
	
}
