package sg.edu.iss.team8.leaveApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import sg.edu.iss.team8.leaveApp.repo.UserRepo;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepo.class)
public class Team8LeaveApplication {

	public static void main(String[] args) {
		SpringApplication.run(Team8LeaveApplication.class, args);
	}

}
