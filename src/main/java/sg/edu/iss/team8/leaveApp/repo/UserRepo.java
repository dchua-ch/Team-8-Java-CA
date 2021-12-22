package sg.edu.iss.team8.leaveApp.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.team8.leaveApp.model.Employee;
import sg.edu.iss.team8.leaveApp.model.User;

public interface UserRepo extends JpaRepository<User, Integer>{
	
	@Transactional
	@Modifying
	@Query(value = "update user set user_type = :usertype where user_id = :userId",nativeQuery=true)
	public void updateUserType(@Param("usertype") String usertype, @Param("userId") Integer userId);
	
	@Query("select U from User U where TYPE(U) = 'employee'")
	public List<User> getAllEmployees();

	@Query("select U from User U where TYPE(U) = 'manager'")
	public List<User> getAllManagers();

	@Query("select U from User U where TYPE(U) = 'admin'")
	public List<User> getAlladmins();

	@Query("select U from User U")
	public List<User> getAllUsers();


	@Query("select U from User U where TYPE(U) = :type and U.userId = :id")
	public User getOneUser(String type, String id);

}
