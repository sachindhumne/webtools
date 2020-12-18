package com.kanban.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Comment{
	@Id
	@GeneratedValue
	private int commentId;
	private int taskId;
	private String comment;
	private int userId;
	
	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	

	/*
	 * public Set<User> getAuthors() { return authors; }
	 * 
	 * public void setAuthors(Set<User> authors) { this.authors = authors; }
	 */
	
	
}
