package com.alexhamilton.udemy.ppmtoolbackend.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Backlog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Integer projectTaskSequence = 0;

	private String projectIdentifier;

	// prevents infinite JSON recursion
	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "project_id", nullable = false)
	private Project project;

	public Backlog() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getProjectTaskSequence() {
		return projectTaskSequence;
	}

	public void setProjectTaskSequence(Integer projectTaskSequence) {
		this.projectTaskSequence = projectTaskSequence;
	}

	public String getProjectIdentifier() {
		return projectIdentifier;
	}

	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

}
