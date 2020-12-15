package com.kanban.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Assignee {
	@Id
	@GeneratedValue
	private int userId;
	
	@Column(name= "emailId", unique = true, nullable = false)
	private String emailId;
	
	@Column(name="userName", nullable = false) 
	private String userName;
	
	@Column(name= "image")
	private String image;
	
	//@JsonManagedReference 
	@ManyToMany(mappedBy="members", fetch = FetchType.EAGER, cascade= {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<Project> projects =  new HashSet<Project>();
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	@JsonIgnore
	public Set<Project> getProjects() {
		return projects;
	}

	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}
}
