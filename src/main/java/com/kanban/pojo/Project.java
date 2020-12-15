package com.kanban.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Project {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int projectId;
	private String title;
	private String description;
	private String status;
	private String owner;
	
	//@JsonBackReference
	@ManyToMany(cascade= {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
	private Set<Assignee> members = new HashSet<Assignee>();
	
	@OneToMany(targetEntity = UserStory.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="projectId", referencedColumnName = "projectId")
	private Set<UserStory> userStories;

	public int getProjectId() {
		return projectId;
	}

	//@JsonIgnore
	public Set<Assignee> getMembers() {
		return members;
	}

	public void setMembers(Set<Assignee> members) {
		this.members = members;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
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

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Set<UserStory> getUserStories() {
		return userStories;
	}

	public void setUserStories(Set<UserStory> userStories) {
		this.userStories = userStories;
	}
	
	
}
