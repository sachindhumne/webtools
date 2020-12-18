package com.kanban.pojo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.sun.istack.NotNull;


@Entity
public class User{
	
	@Id
	@GeneratedValue
	private int userId;
	
	@Email
	@NotNull
	@Column(name= "emailId", unique = true, nullable = false)
	private String emailId;
	
	@Size(min = 3, max = 12)
	@NotNull
	@Column(name="userName", nullable = false) 
	private String userName;
	
	@Column(name= "image")
	private String image;
	
	@NotNull
	@Size(min = 8)
	@Column(name= "password",  nullable = false)
	private String password;
	
	@NotNull
	private String role;

	//@JsonManagedReference 
	@ManyToMany(mappedBy="members", fetch = FetchType.EAGER, cascade= {CascadeType.PERSIST,  CascadeType.MERGE})
	private Set<Project> projects =  new HashSet<Project>();
	
	/*
	 * @OneToMany(mappedBy = "userId", cascade= {CascadeType.PERSIST,
	 * CascadeType.MERGE}) private List<Comment> comments = new
	 * ArrayList<Comment>();
	 */
	

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
		
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	/*
	 * public List<Comment> getComments() { return comments; }
	 * 
	 * public void setComments(List<Comment> comments) { this.comments = comments; }
	 */
	
	
	
}
