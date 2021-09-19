package com.alexhamilton.udemy.ppmtoolbackend.web;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	@Autowired
	private MapValidationErrorService mapValidationErrorService;

	@PostMapping
	public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult bindingResult, Principal principal) {
		ResponseEntity<?> errMap = mapValidationErrorService.mapValidationErrors(bindingResult);

		if (errMap != null) {
			return errMap;
		}

		Project newProject = projectService.saveOrUpdateProject(project, principal.getName());
		return new ResponseEntity<Project>(newProject, HttpStatus.OK);
	}

	@GetMapping("/{projectIdentifier}")
	public ResponseEntity<?> getProjectByProjectIdentifier(@PathVariable String projectIdentifier, Principal principal) {
		Project project = projectService.findProjectByProjectIdentifier(projectIdentifier, principal.getName());

		return new ResponseEntity<Project>(project, HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllProjects(Principal principal) {
		Iterable<Project> projects = projectService.findAllProjects(principal.getName());

		return new ResponseEntity<Iterable<Project>>(projects, HttpStatus.OK);
	}

	@DeleteMapping("/{projectIdentifier}")
	public ResponseEntity<?> deleteProjectByProjectIdentifier(@PathVariable String projectIdentifier, Principal principal) {
		projectService.deleteProjectByProjectIdentifier(projectIdentifier, principal.getName());

		return new ResponseEntity<Object>(null, HttpStatus.NO_CONTENT);
	}

}
