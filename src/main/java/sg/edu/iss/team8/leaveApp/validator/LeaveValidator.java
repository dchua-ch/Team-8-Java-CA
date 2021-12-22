package sg.edu.iss.team8.leaveApp.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import sg.edu.iss.team8.leaveApp.model.Leave;

@Component
public class LeaveValidator implements Validator {

	@Override
	public boolean supports(Class<?> c) {
		return Leave.class.isAssignableFrom(c);
	}
	
	@Override
	public void validate(Object obj, Errors e) {
		Leave leave = (Leave) obj;
		//ValidationUtils.rejectIfEmpty(e, "leaveId", "error.leave.leaveId.empty");
		//ValidationUtils.rejectIfEmpty(e, "startDate", "error.leave.startDate.empty", "Start Date required");
		//ValidationUtils.rejectIfEmpty(e, "endDate", "error.leave.endDate.empty", "End Date required");
		ValidationUtils.rejectIfEmpty(e, "leaveType", "error.leave.leaveType.empty", "Leave Type required");
		ValidationUtils.rejectIfEmpty(e, "addtnlReason", "error.leave.addtnlReason.empty", "Reason required");
		
		System.out.println(leave.getStartDate());
		System.out.println(leave.getEndDate());
		System.out.println(leave.getLeaveType());
		System.out.println(leave.getAddtnlReason());
		System.out.println(leave.getWorkDissemination());
		if(leave.getStartDate() != null && leave.getEndDate() != null)
		{
			if ((leave.getStartDate().after(leave.getEndDate()))) 
			{
				e.reject("startDate", "Start Date should be before the or equal with End Date");
				e.rejectValue("startDate", "error.leave.startDate-endDate.chronological", "startDate must be <= endDate");
			}
		}
		System.out.println(e);
	}
	
	
}
