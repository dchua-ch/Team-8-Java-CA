package sg.edu.iss.team8.leaveApp.services;

import java.util.ArrayList;

import java.util.List;

import sg.edu.iss.team8.leaveApp.model.Employee;
import sg.edu.iss.team8.leaveApp.model.OvertimeHours;

public interface OvertimeHoursService {

	boolean saveOTHours(OvertimeHours OTHours);

	ArrayList<OvertimeHours> findOvertimeHoursByUserId(Integer userId);

	ArrayList<OvertimeHours> findOvertimeHoursByMonthYear(Integer month, Integer year);

	ArrayList<OvertimeHours> findOvertimeHoursByMonthYearUserId(Integer month, Integer year, Integer userId);
	
	Double findTotalOTHoursByMonthYearUserId(Integer month, Integer year, Integer userId);

}