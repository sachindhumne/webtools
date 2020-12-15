package com.kanban.controller;

import java.util.ArrayList;

import org.hibernate.mapping.List;
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
import com.kanban.pojo.User;

@Controller
public class UserController {
	
	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseEntity<User> registration(@RequestBody User user, UserDAO userDAO) {
		System.out.println("In Controller");
		User u = userDAO.addUser(user);
		return new ResponseEntity<User>(u, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/user", method=RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<User> updateUser(@RequestBody User user, UserDAO userDAO){
		User u = userDAO.updateUserPassword(user);
		if(u != null)
			return new ResponseEntity<User>(u, HttpStatus.ACCEPTED);
		else 
			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<String> deleteUser (@PathVariable int id, UserDAO userDAO){
		if(userDAO.deleteUser(id))
			return new ResponseEntity<String>("User has been deleted Successfuly", HttpStatus.ACCEPTED);
		else 
			return new ResponseEntity<String>("Invalid input or no such user exist", HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<User> getUser (@PathVariable int id, UserDAO userDAO){
		User u = userDAO.getUser(id);
		if(u != null)
			return new ResponseEntity<User>(u, HttpStatus.ACCEPTED);
		else 
			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<User> getUser (@RequestBody User user, UserDAO userDAO){
		User u = userDAO.login(user);
		if(u != null)
			return new ResponseEntity<User>(u, HttpStatus.ACCEPTED);
		else 
			return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "/allUsers", method = RequestMethod.GET,  produces = { MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseEntity<ArrayList<User>> getAllUser (UserDAO userDAO){
		ArrayList<User> usersList =  (ArrayList<User>) userDAO.getUserNames();
		if(usersList != null)
			return new ResponseEntity<ArrayList<User>>(usersList, HttpStatus.ACCEPTED);
		else 
			return new ResponseEntity<ArrayList<User>>(HttpStatus.BAD_REQUEST);
	}
}
