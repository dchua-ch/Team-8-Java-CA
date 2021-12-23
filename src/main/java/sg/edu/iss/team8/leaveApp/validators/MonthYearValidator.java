package sg.edu.iss.team8.leaveApp.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import sg.edu.iss.team8.leaveApp.helpers.MonthYear;

@Component
public class MonthYearValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		return MonthYear.class.isAssignableFrom(arg0);

	}

	@Override
	public void validate(Object arg0, Errors arg1) {
		// TODO Auto-generated method stub
		MonthYear my = (MonthYear) arg0;
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "date", "error.MonthYear", "Month is required.");
		}
	
	
	}