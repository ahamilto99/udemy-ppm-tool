package com.alexhamilton.udemy.ppmtoolbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.alexhamilton.udemy.ppmtoolbackend.domain.Backlog;
import com.alexhamilton.udemy.ppmtoolbackend.domain.Project;
import com.alexhamilton.udemy.ppmtoolbackend.domain.User;
import com.alexhamilton.udemy.ppmtoolbackend.exceptions.DuplicateProjectIdentifierException;
import com.alexhamilton.udemy.ppmtoolbackend.repositories.BacklogRepository;
import com.alexhamilton.udemy.ppmtoolbackend.repositories.ProjectRepository;
import com.alexhamilton.udemy.ppmtoolbackend.repositories.UserRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepo;

	@Autowired
	private BacklogRepository backlogRepo;
	
	@Autowired
	private UserRepository userRepo;

	public Project saveOrUpdateProject(Project project, String username) {
		String upperProjectIdentifier = project.getProjectIdentifier().toUpperCase();

		try {
			User user = userRepo.findUserByUsername(username);

			project.setUser(user);
			project.setProjectLead(username);
			project.setProjectIdentifier(upperProjectIdentifier);

			if (project.getId() == null) {
				Backlog backlog = new Backlog();
				project.setBacklog(backlog);
				backlog.setProject(project);
				backlog.setProjectIdentifier(upperProjectIdentifier);
			} else {
				// we're updating the project
				project.setBacklog(backlogRepo.findByProjectIdentifier(upperProjectIdentifier));
			}

			return projectRepo.save(project);
		} catch (Exception e) {
			throw new DuplicateProjectIdentifierException(
					"Project Identifier '" + upperProjectIdentifier + "' already exists");
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
