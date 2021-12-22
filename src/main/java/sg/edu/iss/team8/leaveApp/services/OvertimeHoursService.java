package sg.edu.iss.team8.leaveApp.services;

import java.util.ArrayList;

import java.util.List;

import sg.edu.iss.team8.leaveApp.helpers.OTEnum;
import sg.edu.iss.team8.leaveApp.model.Employee;
import sg.edu.iss.team8.leaveApp.model.OvertimeHours;

public interface OvertimeHoursService {

	boolean saveOTHours(OvertimeHours OTHours);

	ArrayList<OvertimeHours> findOTHoursByUserId(Integer userId);

	ArrayList<OvertimeHours> findOTHoursByMY(Integer month, Integer year);

	ArrayList<OvertimeHours> findOTHoursByMYUserId(Integer month, Integer year, Integer userId);
	
	ArrayList<OvertimeHours> findOTHoursByMYUserIdStatus(Integer month, Integer year, Integer userId, OTEnum status);
	
	Double findTotalOTHoursByMYUserId(Integer month, Integer year, Integer userId);
	
	Double findTotalOTHoursByMYUserIdStatus(Integer month, Integer year, Integer userId, OTEnum status);
	
	boolean updateOTHours(OvertimeHours OTHours);
}