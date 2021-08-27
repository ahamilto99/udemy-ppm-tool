package com.alexhamilton.udemy.ppmtoolbackend.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alexhamilton.udemy.ppmtoolbackend.domain.Project;
import com.alexhamilton.udemy.ppmtoolbackend.services.MapValidationErrorService;
import com.alexhamilton.udemy.ppmtoolbackend.services.ProjectService;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	@Autowired
	private MapValidationErrorService mapValidationErrorService;

	@PostMapping
	public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult bindingResult) {
		ResponseEntity<?> errMap = mapValidationErrorService.mapValidationErrors(bindingResult);

		if (errMap != null) {
			return errMap;
		}

		Project newProject = projectService.saveOrUpdateProject(project);
		return new ResponseEntity<Project>(newProject, HttpStatus.CREATED);
	}

	@GetMapping("/{projectIdentifier}")
	public ResponseEntity<?> getProjectByProjectIdentifier(@PathVariable String projectIdentifier) {
		Project project = projectService.findProjectByProjectIdentifier(projectIdentifier);

		return new ResponseEntity<Project>(project, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<?> getAllProjects() {
		Iterable<Project> projects = projectService.findAllProjects();

		return new ResponseEntity<Iterable<Project>>(projects, HttpStatus.OK);
	}

	@DeleteMapping("/{projectIdentifier}")
	public ResponseEntity<?> deleteProjectByProjectIdentifier(@PathVariable String projectIdentifier) {
		projectService.deleteProjectByProjectIdentifier(projectIdentifier);

		return new ResponseEntity<Object>(null, HttpStatus.NO_CONTENT);
	}

}
