package sg.edu.iss.team8.leaveApp.service;

import java.util.ArrayList;

import sg.edu.iss.team8.leaveApp.helpers.OTEnum;
import sg.edu.iss.team8.leaveApp.model.OvertimeHours;

public interface OvertimeHoursService {
	boolean saveOTHours(OvertimeHours OTHours);

	ArrayList<OvertimeHours> findOTHoursByUserid(Integer userId);

	ArrayList<OvertimeHours> findOTHoursByMY(Integer month, Integer year);

	ArrayList<OvertimeHours> findOTHoursByMYUserid(Integer month, Integer year, Integer userId);
	
	ArrayList<OvertimeHours> findOTHoursByMYUseridStatus(Integer month, Integer year, Integer userId, OTEnum status);
	
	Double findTotalOTHoursByMYUserid(Integer month, Integer year, Integer userId);
	
	Double findTotalOTHoursByMYUseridStatus(Integer month, Integer year, Integer userId, OTEnum status);
	
	boolean updateOTHours(OvertimeHours OTHours);
}
