package com.kanban.pojo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Task {
	@Id
	@GeneratedValue
	private int taskId;
	private int storyId;
	private String title;
	private String description;
	private String status;
	private String priority;
	
	@OneToOne(fetch = FetchType.EAGER,cascade= {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval=true)
	@JoinColumn(name="userId")
	private User assignee;
	
	@OneToMany(targetEntity = Comment.class, orphanRemoval=true)
	@JoinColumn(name="taskId",  referencedColumnName = "taskId")
	public List<Comment> comments;
	
	public int getTaskId() {
		return taskId;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public int getStoryId() {
		return storyId;
	}
	public void setStoryId(int storyId) {
		this.storyId = storyId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public User getAssignee() {
		return assignee;
	}
	public void setAssignee(User assignee) {
		this.assignee = assignee;
	}
	
	/*
	 * @OneToMany private ArrayList<Comment> comments;
	 */
	
}
