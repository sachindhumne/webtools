package com.kanban.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kanban.dao.ProjectDAO;
import com.kanban.dao.TaskDAO;
import com.kanban.dao.UserStoryDAO;
import com.kanban.pojo.Task;
import com.kanban.pojo.UserStory;

@Controller
public class TaskController {
	
	@RequestMapping(value = "/tasks", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseEntity<Task> createTask(@RequestBody Task task, TaskDAO taskDAO) {
		System.out.println("In Task Controller");
		Task t = taskDAO.addTask(task);
		return new ResponseEntity<Task>(t, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/user/tasks", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ResponseEntity<ArrayList<Task>> getAllTasks(TaskDAO taskDAO) {
		System.out.println("In Controller");
		ArrayList<Task> tasks = taskDAO.getAllTasks();
		if(tasks != null)
			return new ResponseEntity<ArrayList<Task>>(tasks, HttpStatus.OK);
		else
			return new ResponseEntity<ArrayList<Task>>(HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/tasks/{id}", method = RequestMethod.PUT, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ResponseEntity<Task> updateTask(@PathVariable int id, @RequestBody Task task, TaskDAO taskDAO) {
		System.out.println("In Controller");
		Task updateTask = taskDAO.updateTask(task, id);
		if(updateTask != null)
			return new ResponseEntity<Task>(updateTask, HttpStatus.OK);
		else
			return new ResponseEntity<Task>(HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/tasks/{id}", method = RequestMethod.DELETE, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ResponseEntity<Object> deleteTask (@PathVariable int id, TaskDAO taskDAO){
		if(taskDAO.deleteTask(id))
			return new ResponseEntity<Object>(HttpStatus.OK);
		else 
			return new ResponseEntity<Object>("Invalid input or no such task exist", HttpStatus.BAD_REQUEST);
	}
}
