package com.alexhamilton.udemy.ppmtoolbackend.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
public class MapValidationErrorService {

	public ResponseEntity<?> mapValidationErrors(BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			Map<String, String> errMap = new HashMap<>();

			for (FieldError err : bindingResult.getFieldErrors()) {
				errMap.put(err.getField(), err.getDefaultMessage());
			}

			return new ResponseEntity<Map<String, String>>(errMap, HttpStatus.BAD_REQUEST);
		}

		return null;
	}

}
