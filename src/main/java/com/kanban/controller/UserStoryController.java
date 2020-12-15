package com.kanban.controller;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kanban.dao.UserDAO;
import com.kanban.dao.UserStoryDAO;
import com.kanban.pojo.User;
import com.kanban.pojo.UserStory;

@Controller
public class UserStoryController {
	
	@RequestMapping(value = "/user-stories", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseEntity<UserStory> registration(@RequestBody UserStory userStory, UserStoryDAO userStoryDAO) {
		System.out.println("In Controller");
		UserStory u = userStoryDAO.addStory(userStory);
		return new ResponseEntity<UserStory>(u, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/user-stories/{id}", method = RequestMethod.GET, produces  = { MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseEntity<ArrayList<UserStory>> getAllStories(@PathVariable int id, UserStoryDAO userStoryDAO) {
		System.out.println("In Controller");
		ArrayList<UserStory> stories = userStoryDAO.getAllStories(id);
		if(stories != null)
			return new ResponseEntity<ArrayList<UserStory>>(stories, HttpStatus.OK);
		else
			return new ResponseEntity<ArrayList<UserStory>>(HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/user-story/{id}", method = RequestMethod.GET, produces  = { MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseEntity<UserStory> getUserStory(@PathVariable int id, UserStoryDAO userStoryDAO) {
		System.out.println("In Controller");
		UserStory story = userStoryDAO.getUserStory(id);
		if(story != null)
			return new ResponseEntity<UserStory>(story, HttpStatus.OK);
		else
			return new ResponseEntity<UserStory>(HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/user-stories/{id}", method = RequestMethod.DELETE, produces  = { MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseEntity<String> deleteUserStory (@PathVariable int id, UserStoryDAO userStoryDAO) {
		if(userStoryDAO.deleteUserStory(id))
			return new ResponseEntity<String>("User Stories has been deleted Successfuly",HttpStatus.OK);
		else
			return new ResponseEntity<String>("Invalid input or no such project exist",HttpStatus.BAD_REQUEST);
	}
}
