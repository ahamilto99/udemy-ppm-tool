package com.alexhamilton.udemy.ppmtoolbackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicateProjectIdentifierException extends RuntimeException {

	private static final long serialVersionUID = 9087724524763973403L;

	public DuplicateProjectIdentifierException(String msg) {
		super(msg);
	}

}
