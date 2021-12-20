package sg.edu.iss.team8.leaveApp.repo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.team8.leaveApp.model.OvertimeHours;

public interface OvertimeHoursRepo extends JpaRepository<OvertimeHours, Integer>{
	
	@Query("select o from OvertimeHours o where userId = :userId")
	public ArrayList<OvertimeHours> findOvertimeHoursByUserId(@Param("userId") Integer userId);
	
	@Query("select o from OvertimeHours o where month(date) = :month and year(date) = :year")
	public ArrayList<OvertimeHours> findOvertimeHoursByMonthYear(@Param("month") Integer month, @Param("year") Integer year);
	
	@Query("select o from OvertimeHours o where month(date) = :month and year(date) = :year and userId = :userId")
	public ArrayList<OvertimeHours> findOvertimeHoursByMonthYearUserId(@Param("month") Integer month, @Param("year") Integer year, @Param("userId") Integer userId);
	
}
