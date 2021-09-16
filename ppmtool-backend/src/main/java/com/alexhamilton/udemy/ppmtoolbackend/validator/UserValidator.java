package com.alexhamilton.udemy.ppmtoolbackend.validator;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.alexhamilton.udemy.ppmtoolbackend.domain.User;

@Component
public class UserValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);	// the class this validator applies to
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		
		if (user.getPassword().length() < 6) {
			errors.rejectValue("password", "Length", "Length must be at least 6 characters");
		}
		
		if (!user.getPassword().equals(user.getConfirmPassword())) {
			errors.rejectValue("confirmPassword", "Match", "Passwords must match");
		}
	}

}
