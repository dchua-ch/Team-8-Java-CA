package sg.edu.iss.team8.leaveApp.repo;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.team8.leaveApp.model.User;


public interface UserRepo extends JpaRepository<User, Integer>{
	

}
