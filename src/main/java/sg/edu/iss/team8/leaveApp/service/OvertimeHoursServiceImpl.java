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

	public ArrayList<OvertimeHours> findOTHoursByUserId(Integer userId){
		ArrayList<OvertimeHours> OTHours = orepo.findOTHoursByUserId(userId);
		return OTHours;
	}
	
	public ArrayList<OvertimeHours> findOTHoursByMY(Integer month, Integer year){
		ArrayList<OvertimeHours> OTHours = orepo.findOTHoursByMY(month, year);
		return OTHours;
	}
	
	public ArrayList<OvertimeHours> findOTHoursByMYUserId(Integer month, Integer year, Integer userId){
		ArrayList<OvertimeHours> OTHours = orepo.findOTHoursByMYUserId(month, year, userId);
		return OTHours;
	}

	public Double findTotalOTHoursByMYUserId(Integer month, Integer year, Integer userId) {
		ArrayList<OvertimeHours> OTHours = findOTHoursByMYUserId(month, year, userId);
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
	public ArrayList<OvertimeHours> findOTHoursByMYUserIdStatus(Integer month, Integer year, Integer userId, OTEnum status) {
		return orepo.findOTHoursByMYUserIdStatus(month, year, userId, status);
	}

	@Override
	public Double findTotalOTHoursByMYUserIdStatus(Integer month, Integer year, Integer userId, OTEnum status) {
		ArrayList<OvertimeHours> OTHours = findOTHoursByMYUserIdStatus(month, year, userId, status);
		Double totalhours = 0.0;
		for (OvertimeHours otday : OTHours) {
			totalhours += otday.getHours();
		}
		return totalhours;
	}

}
