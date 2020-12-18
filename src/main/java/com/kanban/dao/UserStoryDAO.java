package com.kanban.dao;

import java.util.ArrayList;

import org.hibernate.query.Query;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.kanban.filter.RequestWrapper;
import com.kanban.pojo.Project;
import com.kanban.pojo.User;
import com.kanban.pojo.UserStory;
import com.mysql.cj.Session;

public class UserStoryDAO extends DAO{
	
	public UserStory addStory(UserStory userStory) {
		UserStory u = new UserStory();
		try {
			begin();
			u.setDescription(RequestWrapper.cleanXSS(userStory.getDescription()));
			u.setPriority(RequestWrapper.cleanXSS(userStory.getPriority()));
			u.setStatus(RequestWrapper.cleanXSS(userStory.getStatus()));
			u.setStoryPoints(userStory.getStoryPoints());
			u.setTitle(RequestWrapper.cleanXSS(userStory.getTitle()));
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
	
	public UserStory updateStory(UserStory userStory, int storyId) {
		UserStory updateStory = null;
		try {
			begin();
			updateStory = (UserStory) getSession().load(UserStory.class, storyId);
			updateStory.setDescription(userStory.getDescription());
			updateStory.setPriority(userStory.getPriority());
			updateStory.setStatus(userStory.getStatus());
			updateStory.setStoryPoints(userStory.getStoryPoints());
			updateStory.setTitle(userStory.getTitle());
			commit();
			updateStory = (UserStory) getSession().load(UserStory.class, storyId);
		} catch (Exception e) {
			rollback();
			System.out.println("Error while updating user into the database");
		}
		return updateStory;
	}
}
