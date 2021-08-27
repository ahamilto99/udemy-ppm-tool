package com.alexhamilton.udemy.ppmtoolbackend.exceptions;

public class DuplicateProjectIdentifierExceptionResponse {

	private String projectIdentifier;

	public DuplicateProjectIdentifierExceptionResponse(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}

	public String getProjectIdentifier() {
		return projectIdentifier;
	}

	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}

}
