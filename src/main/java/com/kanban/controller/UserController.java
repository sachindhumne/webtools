package com.kanban.controller;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.kanban.validator.UserValidator;

@Controller
public class UserController {

	@Autowired
	UserValidator userValidator;
	
	@Autowired
	private HttpServletRequest request;
	
	private String[] basicAuth() {
		if(request.getHeader("authorization") == null) {
			return null;
		} 
		String auth = request.getHeader("authorization");		 
	    auth = auth.split(" ")[1];
	    byte[] decoded = Base64.getDecoder().decode(auth);
	    String decodedString = new String(decoded, StandardCharsets.UTF_8);
	    String[] arr = decodedString.split(":");
		
		return arr;
	}

	
	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ResponseEntity<Object> registration(@RequestBody User user, UserDAO userDAO) {
		System.out.println("In Controller");
		
		if (!userValidator.validate(user)) {
			return new ResponseEntity<Object>(new String("Validation Error... Please enter valid fields"), HttpStatus.CONFLICT);
		}
		if (userDAO.existingUser(user)) {
			return new ResponseEntity<Object>(new String("User has already registered with us...!! \n Please go to Login!"), HttpStatus.BAD_REQUEST);
		}
		User u = userDAO.addUser(user);
		return new ResponseEntity<Object>(u, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/user", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<User> updateUser(@RequestBody User user, UserDAO userDAO) {
		User u = userDAO.updateUserPassword(user);
		if (u != null)
			return new ResponseEntity<User>(u, HttpStatus.ACCEPTED);
		else
			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<String> deleteUser(@PathVariable int id, UserDAO userDAO) {
		if (userDAO.deleteUser(id))
			return new ResponseEntity<String>("User has been deleted Successfuly", HttpStatus.ACCEPTED);
		else
			return new ResponseEntity<String>("Invalid input or no such user exist", HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<User> getUser(@PathVariable int id, UserDAO userDAO) {
		if(basicAuth() == null) {
			return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
		} 
		String[] arr = basicAuth();
		
		if(!userDAO.verifyBasicAuth(arr)) {
			return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
		}
		User u = userDAO.getUser(id);

		if (u != null)
			return new ResponseEntity<User>(u, HttpStatus.ACCEPTED);
		else
			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<User> getUser(@RequestBody User user, UserDAO userDAO) {
		User u = userDAO.login(user);
		if (u != null)
			return new ResponseEntity<User>(u, HttpStatus.ACCEPTED);
		else
			return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(value = "/allUsers", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ResponseEntity<ArrayList<User>> getAllUser(UserDAO userDAO) {
		ArrayList<User> usersList = (ArrayList<User>) userDAO.getUserNames();
		if (usersList != null)
			return new ResponseEntity<ArrayList<User>>(usersList, HttpStatus.ACCEPTED);
		else
			return new ResponseEntity<ArrayList<User>>(HttpStatus.BAD_REQUEST);
	}
}
