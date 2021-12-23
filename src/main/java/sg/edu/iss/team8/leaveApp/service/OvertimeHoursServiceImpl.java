package sg.edu.iss.team8.leaveApp.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.iss.team8.leaveApp.helpers.OTEnum;
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

	public ArrayList<OvertimeHours> findOTHoursByUserid(Integer userId){
		ArrayList<OvertimeHours> OTHours = orepo.findOTHoursByUserid(userId);
		return OTHours;
	}
	
	public ArrayList<OvertimeHours> findOTHoursByMY(Integer month, Integer year){
		ArrayList<OvertimeHours> OTHours = orepo.findOTHoursByMY(month, year);
		return OTHours;
	}
	
	public ArrayList<OvertimeHours> findOTHoursByMYUserid(Integer month, Integer year, Integer userId){
		ArrayList<OvertimeHours> OTHours = orepo.findOTHoursByMYUserid(month, year, userId);
		return OTHours;
	}

	public Double findTotalOTHoursByMYUserid(Integer month, Integer year, Integer userId) {
		ArrayList<OvertimeHours> OTHours = findOTHoursByMYUserid(month, year, userId);
		Double totalhours = 0.0;
		for (OvertimeHours otday : OTHours) {
			totalhours += otday.getHours();
		}
		return totalhours;
	}
	
	@Transactional
	@Modifying
	public boolean updateOTHours (OvertimeHours OTHours) {
		if (orepo.saveAndFlush(OTHours) != null) 
			return true;
		else
			return false;
	}

	@Override
	public ArrayList<OvertimeHours> findOTHoursByMYUseridStatus(Integer month, Integer year, Integer userId, OTEnum status) {
		return orepo.findOTHoursByMYUseridStatus(month, year, userId, status);
	}

	@Override
	public Double findTotalOTHoursByMYUseridStatus(Integer month, Integer year, Integer userId, OTEnum status) {
		ArrayList<OvertimeHours> OTHours = findOTHoursByMYUseridStatus(month, year, userId, status);
		Double totalhours = 0.0;
		for (OvertimeHours otday : OTHours) {
			totalhours += otday.getHours();
		}
		return totalhours;
	}

}
