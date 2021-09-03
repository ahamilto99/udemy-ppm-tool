package com.alexhamilton.udemy.ppmtoolbackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProjectNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -2308334654659573492L;

	public ProjectNotFoundException(String message) {
		super(message);
	}

}
