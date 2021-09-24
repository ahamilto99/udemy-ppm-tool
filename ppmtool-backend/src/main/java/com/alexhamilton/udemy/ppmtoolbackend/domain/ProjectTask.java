package com.alexhamilton.udemy.ppmtoolbackend.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ProjectTask {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(updatable = false, unique = true)
	private String projectSequence;

	@NotBlank(message = "Please include a project summary")
	private String summary;

	private String acceptanceCriteria;

	private String status;

	private Integer priority;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate dueDate;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "backlog_id", updatable = false, nullable = false)
	private Backlog backlog;

	@Column(updatable = false)
	private String projectIdentifier;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime createdAt;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime updatedAt;

	public ProjectTask() {
	}

	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		updatedAt = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProjectSequence() {
		return projectSequence;
	}

	public void setProjectSequence(String projectSequence) {
		this.projectSequence = projectSequence;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getAcceptanceCriteria() {
		return acceptanceCriteria;
	}

	public void setAcceptanceCriteria(String acceptanceCriteria) {
		this.acceptanceCriteria = acceptanceCriteria;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getProjectIdentifier() {
		return projectIdentifier;
	}

	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}

	public Backlog getBacklog() {
		return backlog;
	}

	public void setBacklog(Backlog backlog) {
		this.backlog = backlog;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProjectTask [id=");
		builder.append(id);
		builder.append(", projectSequence=");
		builder.append(projectSequence);
		builder.append(", summary=");
		builder.append(summary);
		builder.append(", acceptanceCriteria=");
		builder.append(acceptanceCriteria);
		builder.append(", status=");
		builder.append(status);
		builder.append(", priority=");
		builder.append(priority);
		builder.append(", dueDate=");
		builder.append(dueDate);
		builder.append(", projectIdentifier=");
		builder.append(projectIdentifier);
		builder.append(", createdAt=");
		builder.append(createdAt);
		builder.append(", updatedAt=");
		builder.append(updatedAt);
		builder.append("]");
		return builder.toString();
	}
}
