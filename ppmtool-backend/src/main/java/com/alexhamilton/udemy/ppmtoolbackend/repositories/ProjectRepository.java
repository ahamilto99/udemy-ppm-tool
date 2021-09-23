package com.alexhamilton.udemy.ppmtoolbackend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alexhamilton.udemy.ppmtoolbackend.domain.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

	Optional<Project> findByProjectIdentifier(String projectIdentifier);
	
	Iterable<Project> findByProjectLeader(String username);
	
}
