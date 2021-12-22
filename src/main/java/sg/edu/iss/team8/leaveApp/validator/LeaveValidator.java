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
		ValidationUtils.rejectIfEmpty(e, "leaveId", "error.leave.leaveId.empty");
		ValidationUtils.rejectIfEmpty(e, "startDate", "error.leave.startDate.empty");
		ValidationUtils.rejectIfEmpty(e, "endDate", "error.leave.endDate.empty");
		ValidationUtils.rejectIfEmpty(e, "leaveType", "error.leave.leaveType.empty");
		ValidationUtils.rejectIfEmpty(e, "addtnlReason", "error.leave.addtnlReason.empty");
		ValidationUtils.rejectIfEmpty(e, "comments", "error.leave.comments.empty");
		
		if (leave.getStartDate().after(leave.getEndDate())) {
			e.rejectValue("startDate", "error.leave.startDate-endDate.chronological");
		}
	}
	
	
}
