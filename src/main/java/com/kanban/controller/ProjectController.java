package com.kanban.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kanban.dao.ProjectDAO;
import com.kanban.dao.UserDAO;
import com.kanban.pojo.Project;
import com.kanban.pojo.User;

@Controller
public class ProjectController {
	
	@RequestMapping(value = "/projects", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseEntity<Project> createProject(@RequestBody Project project, ProjectDAO projectDAO) {
		System.out.println("In Project Controller");
		Project p = projectDAO.addProject(project);
		if(p != null)
			return new ResponseEntity<Project>(p, HttpStatus.CREATED);
		else
			return new ResponseEntity<Project>(HttpStatus.BAD_REQUEST);
			
	}
	
	@RequestMapping(value = "/projects", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseEntity<ArrayList<Project>> createProject(ProjectDAO projectDAO) {
		ArrayList<Project> usersList =  (ArrayList<Project>) projectDAO.getAllProjects();
		if(usersList != null)
			return new ResponseEntity<ArrayList<Project>>(usersList, HttpStatus.OK);
		else 
			return new ResponseEntity<ArrayList<Project>>(HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/projects/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Project> getProject (@PathVariable int id, ProjectDAO projectDAO){
		Project p = projectDAO.getProjectDetails(id);
		if(p != null)
			return new ResponseEntity<Project>(p, HttpStatus.OK);
		else 
			return new ResponseEntity<Project>(HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/projects/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<String> deleteProject (@PathVariable int id, ProjectDAO projectDao){
		if(projectDao.deleteProject(id))
			return new ResponseEntity<String>("Project has been deleted Successfuly", HttpStatus.OK);
		else 
			return new ResponseEntity<String>("Invalid input or no such project exist", HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value="/projects/{id}", method=RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Project> updateProject(@PathVariable int id, @RequestBody Project project, ProjectDAO projectDao){
		Project p = projectDao.updateProject(project, id);
		if(p != null)
			return new ResponseEntity<Project>(p, HttpStatus.ACCEPTED);
		else 
			return new ResponseEntity<Project>(HttpStatus.BAD_REQUEST);
	}
}
