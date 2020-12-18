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

import com.kanban.dao.CommentDAO;
import com.kanban.pojo.Comment;

@Controller
public class CommentController {
	@RequestMapping(value = "/comments", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseEntity<Comment> addComment(@RequestBody Comment comment, CommentDAO commentDAO) {
		System.out.println("In Controller");
		Comment c = commentDAO.addComment(comment);
		return new ResponseEntity<Comment>(c, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/comments/{id}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ResponseEntity<ArrayList<Comment>> getComments(@PathVariable int id, CommentDAO commentDAO) {
		System.out.println("In Controller");
		ArrayList<Comment> c = commentDAO.getAllComments(id);
		if(c != null)
			return new ResponseEntity<ArrayList<Comment>>(c, HttpStatus.OK);
		else 
			return new ResponseEntity<ArrayList<Comment>>( HttpStatus.BAD_REQUEST);
	}
}
