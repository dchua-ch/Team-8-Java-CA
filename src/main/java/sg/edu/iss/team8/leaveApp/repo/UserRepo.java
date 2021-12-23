package sg.edu.iss.team8.leaveApp.repo;

import java.util.ArrayList;
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
	
//	@Transactional
//	@Modifying
//	@Query(value = "update User U set U.user_type = :usertype where u.name = :name",nativeQuery=true)
//	public void updateUserType(@Param("usertype") String usertype, @Param("name") String name);
	
	@Transactional
	@Modifying
	@Query(value = "update User U set U.user_type = :usertype where U.user_id = :id",nativeQuery=true)
	public void updateUserType(@Param("usertype") String usertype, @Param("id") Integer id);
	
//	@Transactional
//	@Modifying
//	@Query(value = "update User U set U.compLeaven = :compleaven where u.userId = :userId",nativeQuery=true)
//	public void updateCompLeave(@Param("compleaven") Integer compleaven, @Param("userId") Integer userId);
	
	@Query("select U from User U")
	public List<User> getAllUsers();
	
	@Query("select U from User U where TYPE(U) = 'employee'")
	public List<Employee> getAllEmployees();
	
	@Query("select U from User U where TYPE(U) = 'manager'")
	public List<Manager> getAllManagers();
	
	@Query("SELECT u FROM User u WHERE u.username=:un AND u.password=:pwd")
	public User findUserByNamePwd(@Param("un") String uname, @Param("pwd") String pwd);
	
	@Query("SELECT DISTINCT u2 FROM User u1, User u2 WHERE u1.userId = u2.reportsTo AND u1.userId = :eid order by u2.userId")
	public ArrayList<Employee> findSubordinates(@Param("eid") Integer eid);
	
//	@Query(value = "select u.user_type from user u where u.user_Id = :userId", nativeQuery = true)
//	public String findUserType(@Param("userId") Integer userId);
	
}
