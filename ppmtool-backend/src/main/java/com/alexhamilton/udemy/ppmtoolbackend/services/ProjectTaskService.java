package com.alexhamilton.udemy.ppmtoolbackend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alexhamilton.udemy.ppmtoolbackend.domain.Backlog;
import com.alexhamilton.udemy.ppmtoolbackend.domain.ProjectTask;
import com.alexhamilton.udemy.ppmtoolbackend.exceptions.ProjectNotFoundException;
import com.alexhamilton.udemy.ppmtoolbackend.repositories.BacklogRepository;
import com.alexhamilton.udemy.ppmtoolbackend.repositories.ProjectRepository;
import com.alexhamilton.udemy.ppmtoolbackend.repositories.ProjectTaskRepository;

@Service
public class ProjectTaskService {

	@Autowired
	private BacklogRepository backlogRepo;

	@Autowired
	private ProjectTaskRepository projectTaskRepo;

	@Autowired
	private ProjectRepository projectRepo;

	@Autowired
	private ProjectService projectService;

	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username) {
		// project != null => backlog exists
		Backlog backlog = projectService.findProjectByProjectIdentifier(projectIdentifier, username).getBacklog();
		projectTask.setBacklog(backlog);

		Integer backlogSequence = backlog.getProjectTaskSequence();
		++backlogSequence;

		backlog.setProjectTaskSequence(backlogSequence);

		projectTask.setProjectSequence(projectIdentifier + '-' + backlogSequence);
		projectTask.setProjectIdentifier(projectIdentifier);

		if (projectTask.getPriority() == null || projectTask.getPriority() == 0) {
			projectTask.setPriority(3); // 3 is low priority; 1 is high priority
		}

		if (projectTask.getStatus() == null || projectTask.getStatus().equals("")) {
			projectTask.setStatus("TO DO");
		}

		return projectTaskRepo.save(projectTask);
	}

	public List<ProjectTask> findBacklogById(String backlogId, String username) {
		projectRepo.findByProjectIdentifier(backlogId).orElseThrow(
				() -> new ProjectNotFoundException("Project with Identifier '" + backlogId + "' not found"));

		projectService.findProjectByProjectIdentifier(backlogId, username);
		
		return projectTaskRepo.findByProjectIdentifierOrderByPriority(backlogId);
	}

	public ProjectTask findProjectTaskByProjectSequence(String backlogId, String projectTaskIdentifier, String username) {
		Backlog backlog = backlogRepo.findByProjectIdentifier(backlogId);

		if (backlog == null) {
			throw new ProjectNotFoundException("Project with Identifier '" + backlogId + "' not found");
		}

		ProjectTask projectTask = projectTaskRepo.findByProjectSequence(projectTaskIdentifier);

		if (projectTask == null) {
			throw new ProjectNotFoundException("Project Task '" + projectTaskIdentifier + "' not found");
		}

		if (!projectTask.getBacklog().getProjectIdentifier().equals(backlogId)) {
			throw new ProjectNotFoundException(
					"Project Task '" + projectTaskIdentifier + "' does not exist in Project '" + backlogId + '\'');
		}

		return projectTask;
	}

	public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlogId,
			String projectTaskIdentifier, String username) {
		ProjectTask projectTask = findProjectTaskByProjectSequence(backlogId, projectTaskIdentifier, username);

		projectTask = updatedTask;

		return projectTaskRepo.save(projectTask);
	}

	public void deleteProjectTaskByProjectSequence(String backlogId, String projectTaskIdentifier, String username) {
		ProjectTask projectTask = findProjectTaskByProjectSequence(backlogId, projectTaskIdentifier, username);

		projectTaskRepo.delete(projectTask);
	}

}
