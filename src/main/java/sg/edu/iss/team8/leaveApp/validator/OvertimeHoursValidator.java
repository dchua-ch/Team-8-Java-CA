package sg.edu.iss.team8.leaveApp.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import sg.edu.iss.team8.leaveApp.helpers.OvertimeHoursInput;

@Component
public class OvertimeHoursValidator implements Validator{
	@Override
	public boolean supports(Class<?> c) {
		return OvertimeHoursInput.class.isAssignableFrom(c);
	}

	@Override
	public void validate(Object obj, Errors e) {
		// TODO Auto-generated method stub
		OvertimeHoursInput OTHours = (OvertimeHoursInput) obj;
		ValidationUtils.rejectIfEmpty(e, "date", "error.overtimehours.date.empty", "Date is required");
		ValidationUtils.rejectIfEmpty(e, "hours", "error.overtimehours.hours.empty", "Hours is required");
		ValidationUtils.rejectIfEmpty(e, "reason", "error.overtimehours.reason.empty", "Reason is required");
	}
}
