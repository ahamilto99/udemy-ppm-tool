package com.alexhamilton.udemy.ppmtoolbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.alexhamilton.udemy.ppmtoolbackend.domain.Backlog;
import com.alexhamilton.udemy.ppmtoolbackend.domain.Project;
import com.alexhamilton.udemy.ppmtoolbackend.domain.User;
import com.alexhamilton.udemy.ppmtoolbackend.exceptions.DuplicateProjectIdentifierException;
import com.alexhamilton.udemy.ppmtoolbackend.exceptions.ProjectNotFoundException;
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

		if (project.getId() != null) {
			Project existingProject = projectRepo.findByProjectIdentifier(project.getProjectIdentifier()).get();

			if (existingProject != null && (!existingProject.getProjectLeader().equals(username))) {
				throw new ProjectNotFoundException("Project not found in your account");
			} else if (existingProject == null) {
				throw new ProjectNotFoundException("Project with ID: '" + project.getProjectIdentifier()
						+ "' cannot be updated because it doesn't exist");
			}
		}

		try {
			User user = userRepo.findUserByUsername(username);

			project.setUser(user);
			project.setProjectLeader(username);
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

	public Project findProjectByProjectIdentifier(String projectIdentifier, String username) {
		String upperProjectIdentifier = projectIdentifier.toUpperCase();

		Project project = projectRepo.findByProjectIdentifier(upperProjectIdentifier)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						"Project Identifier '" + upperProjectIdentifier + "' does not exist"));

		System.out.println();
		System.out.println(username);
		System.out.println();

		if (!project.getProjectLeader().equals(username)) {
			throw new ProjectNotFoundException("Project not found in your account");
		}

		return project;
	}

	public Iterable<Project> findAllProjects(String username) {
		return projectRepo.findByProjectLeader(username);
	}

	public void deleteProjectByProjectIdentifier(String projectIdentifier, String username) {
		projectRepo.delete(findProjectByProjectIdentifier(projectIdentifier, username));
	}

}
