package com.alexhamilton.udemy.ppmtoolbackend.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alexhamilton.udemy.ppmtoolbackend.domain.ProjectTask;
import com.alexhamilton.udemy.ppmtoolbackend.services.MapValidationErrorService;
import com.alexhamilton.udemy.ppmtoolbackend.services.ProjectTaskService;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

	@Autowired
	private ProjectTaskService projectTaskService;

	@Autowired
	private MapValidationErrorService mapValidationErrorService;

	@PostMapping("/{backlog-id}")
	public ResponseEntity<?> addProjectTaskToBacklog(@PathVariable("backlog-id") String backlogId,
			@Valid @RequestBody ProjectTask projectTask, BindingResult bindingResult) {
		ResponseEntity<?> errMap = mapValidationErrorService.mapValidationErrors(bindingResult);
		if (errMap != null) {
			return errMap;
		}

		ProjectTask newProjectTask = projectTaskService.addProjectTask(backlogId, projectTask);

		return new ResponseEntity<ProjectTask>(newProjectTask, HttpStatus.OK);
	}

	@GetMapping("/{backlog-id}")
	public List<ProjectTask> getProjectBacklog(@PathVariable("backlog-id") String backlogId) {
		return projectTaskService.findBacklogById(backlogId);
	}

}
