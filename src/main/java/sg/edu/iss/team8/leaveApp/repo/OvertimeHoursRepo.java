package sg.edu.iss.team8.leaveApp.repo;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.team8.leaveApp.helpers.OTEnum;
import sg.edu.iss.team8.leaveApp.model.OvertimeHours;

public interface OvertimeHoursRepo extends JpaRepository<OvertimeHours, Integer>{
	
	@Query("select o from OvertimeHours o where userid = :userId")
	public ArrayList<OvertimeHours> findOTHoursByUserid(@Param("userId") Integer userId);
	
	@Query("select o from OvertimeHours o where month(date) = :month and year(date) = :year")
	public ArrayList<OvertimeHours> findOTHoursByMY(@Param("month") Integer month, @Param("year") Integer year);
	
	@Query("select o from OvertimeHours o where month(date) = :month and year(date) = :year and userid = :userId")
	public ArrayList<OvertimeHours> findOTHoursByMYUserid(@Param("month") Integer month, @Param("year") Integer year, @Param("userId") Integer userid);
	
	@Query("select o from OvertimeHours o where month(date) = :month and year(date) = :year and userid = :userId and status = :status")
	public ArrayList<OvertimeHours> findOTHoursByMYUseridStatus(@Param("month") Integer month, @Param("year") Integer year, @Param("userId") Integer userid, @Param("status") OTEnum status);

}