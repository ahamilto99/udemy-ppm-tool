package com.alexhamilton.udemy.ppmtoolbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alexhamilton.udemy.ppmtoolbackend.domain.Backlog;
import com.alexhamilton.udemy.ppmtoolbackend.domain.ProjectTask;
import com.alexhamilton.udemy.ppmtoolbackend.repositories.BacklogRepository;
import com.alexhamilton.udemy.ppmtoolbackend.repositories.ProjectTaskRepository;

@Service
public class ProjectTaskService {

	@Autowired
	private BacklogRepository backlogRepo;

	@Autowired
	private ProjectTaskRepository projectTaskRepo;

	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
		// project != null => backlog exists
		Backlog backlog = backlogRepo.findByProjectIdentifier(projectIdentifier);
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

}
