package com.alexhamilton.udemy.ppmtoolbackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler
	public final ResponseEntity<Object> handleDuplicateProjectIdentifierException(
			DuplicateProjectIdentifierException ex) {
		DuplicateProjectIdentifierExceptionResponse exceptionResponse = new DuplicateProjectIdentifierExceptionResponse(
				ex.getMessage());

		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public final ResponseEntity<Object> handleProjectNotFoundException(ProjectNotFoundException ex) {

		ProjectNotFoundExceptionResponse exceptionResponse = new ProjectNotFoundExceptionResponse(ex.getMessage());

		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

}
