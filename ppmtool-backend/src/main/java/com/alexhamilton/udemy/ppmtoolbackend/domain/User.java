package com.alexhamilton.udemy.ppmtoolbackend.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Email(message = "Username must be an email address")
	@NotBlank(message = "Username is required")
	@Column(unique = true)
	private String username;

	@NotBlank(message = "Please enter your full name")
	private String fullName;

	@NotBlank(message = "Password field is required")
	private String password;

	@Transient
	private String confirmPassword;

	private LocalDate created_At;

	private LocalDate updated_At;

	public User() {
	}

	@PrePersist
	protected void onCreate() {
		this.created_At = LocalDate.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updated_At = LocalDate.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public LocalDate getCreated_At() {
		return created_At;
	}

	public void setCreated_At(LocalDate created_At) {
		this.created_At = created_At;
	}

	public LocalDate getUpdated_At() {
		return updated_At;
	}

	public void setUpdated_At(LocalDate updated_At) {
		this.updated_At = updated_At;
	}

}
