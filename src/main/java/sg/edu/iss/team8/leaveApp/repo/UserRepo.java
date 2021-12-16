package sg.edu.iss.team8.leaveApp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.iss.team8.leaveApp.model.User;

public interface UserRepo extends JpaRepository<User, Integer>{

}
