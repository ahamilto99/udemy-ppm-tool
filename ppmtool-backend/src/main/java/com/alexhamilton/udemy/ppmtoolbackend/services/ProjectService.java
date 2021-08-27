package com.alexhamilton.udemy.ppmtoolbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.alexhamilton.udemy.ppmtoolbackend.domain.Project;
import com.alexhamilton.udemy.ppmtoolbackend.exceptions.DuplicateProjectIdentifierException;
import com.alexhamilton.udemy.ppmtoolbackend.repositories.ProjectRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepo;

	public Project saveOrUpdateProject(Project project) {
		try {
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

			return projectRepo.save(project);
		} catch (Exception e) {
			throw new DuplicateProjectIdentifierException(
					"Project Identifier '" + project.getProjectIdentifier().toUpperCase() + "' already exists");
		}

	}

	public Project findProjectByProjectIdentifier(String projectIdentifier) {
		String upperProjectIdentifier = projectIdentifier.toUpperCase();

		return projectRepo.findByProjectIdentifier(upperProjectIdentifier)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						"Project Identifier '" + upperProjectIdentifier + "' does not exist"));
	}

	public Iterable<Project> findAllProjects() {
		return projectRepo.findAll();
	}

	public void deleteProjectByProjectIdentifier(String projectIdentifier) {
		String upperProjectIdentifier = projectIdentifier.toUpperCase();
		
		Project project = projectRepo.findByProjectIdentifier(upperProjectIdentifier)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						"Cannot delete Project with Project Identifier '" + upperProjectIdentifier
								+ "' because Project does not exist"));
		projectRepo.delete(project);
	}

}
