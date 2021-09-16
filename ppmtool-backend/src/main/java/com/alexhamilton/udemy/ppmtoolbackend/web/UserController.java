package com.alexhamilton.udemy.ppmtoolbackend.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alexhamilton.udemy.ppmtoolbackend.domain.User;
import com.alexhamilton.udemy.ppmtoolbackend.services.MapValidationErrorService;
import com.alexhamilton.udemy.ppmtoolbackend.services.UserService;
import com.alexhamilton.udemy.ppmtoolbackend.validator.UserValidator;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private MapValidationErrorService mapValidationErrorService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserValidator userValidator;

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {
		userValidator.validate(user, result);
		
		ResponseEntity<?> errMap = mapValidationErrorService.mapValidationErrors(result);
		if (errMap != null) {
			return errMap;
		}

		User newUser = userService.saveUser(user);

		return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
	}

}
