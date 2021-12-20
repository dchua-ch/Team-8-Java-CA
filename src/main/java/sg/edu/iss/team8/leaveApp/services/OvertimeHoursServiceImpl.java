package sg.edu.iss.team8.leaveApp.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.iss.team8.leaveApp.model.OvertimeHours;
import sg.edu.iss.team8.leaveApp.repo.OvertimeHoursRepo;

@Service
@Transactional
public class OvertimeHoursServiceImpl implements OvertimeHoursService{
	@Autowired
	OvertimeHoursRepo orepo;
	
	public boolean saveOTHours (OvertimeHours OTHours) {
		if (orepo.save(OTHours) != null) 
			return true;
		else
			return false;
	}

	public ArrayList<OvertimeHours> findOvertimeHoursByUserId(Integer userId){
		ArrayList<OvertimeHours> OTHours = orepo.findOvertimeHoursByUserId(userId);
		return OTHours;
	}
	
	public ArrayList<OvertimeHours> findOvertimeHoursByMonthYear(Integer month, Integer year){
		ArrayList<OvertimeHours> OTHours = orepo.findOvertimeHoursByMonthYear(month, year);
		return OTHours;
	}
	
	public ArrayList<OvertimeHours> findOvertimeHoursByMonthYearUserId(Integer month, Integer year, Integer userId){
		ArrayList<OvertimeHours> OTHours = orepo.findOvertimeHoursByMonthYearUserId(month, year, userId);
		return OTHours;
	}

	public Double findTotalOTHoursByMonthYearUserId(Integer month, Integer year, Integer userId) {
		ArrayList<OvertimeHours> OTHours = findOvertimeHoursByMonthYearUserId(month, year, userId);
		Double totalhours = 0.0;
		for (OvertimeHours otday : OTHours) {
			totalhours += otday.getHours();
		}
		return totalhours;
	}
}
