package com.kanban.dao;

import java.util.ArrayList;

import org.hibernate.query.Query;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.kanban.pojo.Project;
import com.kanban.pojo.User;
import com.kanban.pojo.UserStory;
import com.mysql.cj.Session;

public class UserStoryDAO extends DAO{
	
	public UserStory addStory(UserStory userStory) {
		UserStory u = new UserStory();
		try {
			begin();
			u.setDescription(userStory.getDescription());
			u.setPriority(userStory.getPriority());
			u.setStatus(userStory.getStatus());
			u.setStoryPoints(userStory.getStoryPoints());
			u.setTitle(userStory.getTitle());
			u.setProjectId(userStory.getProjectId());
			getSession().save(u);
			commit();
			
		} catch (Exception e) {
			rollback();
			System.out.println("Error while inserting new user into the database");
		}
		return u;
	}
	
	public ArrayList<UserStory> getAllStories(int projectID) {
		ArrayList<UserStory> stories = null;
		try {
			begin();
			Query query = getSession().createQuery("from UserStory where projectId=:projectId");
			query.setParameter("projectId", projectID);
			stories = (ArrayList<UserStory>) query.getResultList();
			commit();
			
		} catch (Exception e) {
			rollback();
			stories = null;
			System.out.println("Error while inserting new user into the database");
		}
		return stories;
	}
	
	public UserStory getUserStory(int storyId) {
		UserStory story = new UserStory();
		try {
			begin();
			//Query query = getSession().createQuery("SELECT u FROM UserStory u JOIN u.tasks where u.storyId=:storyID");
			Query query = getSession().createQuery("FROM UserStory u where u.storyId=:storyID");
			query.setParameter("storyID", storyId);
			story = (UserStory) query.uniqueResult();
			commit();		
		} catch (Exception e) {
			rollback();
			story = null;
			System.out.println("Error while fetching "+e.getMessage());
		}
		return story;
	}
	
	public boolean deleteUserStory(int StoryId) {
		try {
			begin();
			UserStory u = (UserStory) getSession().get(UserStory.class, StoryId);
			if (null != u) {
				getSession().delete(u);
				commit();
				return true;
			} else {
				rollback();
				return false;
			}
		} catch (Exception e) {
			rollback();
			System.out.println("Error while Deleteing userstory into the database");
		}
		return false;
		
	}
}
