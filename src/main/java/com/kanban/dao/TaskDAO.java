package com.kanban.dao;

import java.util.ArrayList;

import org.hibernate.query.Query;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.kanban.filter.RequestWrapper;
import com.kanban.pojo.Project;
import com.kanban.pojo.Task;
import com.kanban.pojo.User;
import com.kanban.pojo.UserStory;

public class TaskDAO extends DAO{
	public Task addTask(Task task) {
		Task t = new Task();
		try {
			begin();
			t.setDescription(RequestWrapper.cleanXSS(task.getDescription()));
			t.setPriority(RequestWrapper.cleanXSS(task.getPriority()));
			t.setStatus(RequestWrapper.cleanXSS(task.getStatus()));
			t.setTitle(RequestWrapper.cleanXSS(task.getTitle()));
			t.setStoryId(task.getStoryId());
			t.setAssignee(task.getAssignee());
			getSession().save(t);
			commit();
			
		} catch (Exception e) {
			rollback();
			System.out.println("Error while inserting new user into the database");
		}
		return t;
	}
	
	public ArrayList<Task> getAllTasks() {
		ArrayList<Task> tasks = null;
		try {
			begin();
			Query query = getSession().createQuery("SELECT t FROM Task t JOIN t.assignee a");
			//query.setParameter("taskId", taskId);// SELECT t FROM Task t JOIN t.assignee a
			tasks = (ArrayList<Task>) query.getResultList();
			commit();
			
		} catch (Exception e) {
			rollback();
			tasks = null;
			System.out.println("Error while inserting new user into the database");
		}
		return tasks;
	}
	
	public Task updateTask(Task task, int taskId) {
		Task updateTask = new Task();
		try {
			begin();
			updateTask = (Task) getSession().get(Task.class, taskId);
			updateTask.setDescription(task.getDescription());
			updateTask.setPriority(task.getPriority());
			updateTask.setStatus(task.getStatus());
			updateTask.setTitle(task.getTitle());
			updateTask.setAssignee(task.getAssignee());
		    getSession().merge(updateTask);  
		    commit();
		} catch (Exception e) {
			rollback();
			System.out.println("Error while updating user into the database");
		}
		return updateTask;
	}
	
	public boolean deleteTask(int taskId) {
		try {
			begin();
			/*
			 * Task t = (Task) getSession().get(Task.class, taskId); if (null != t) {
			 */
			Query query1 = getSession().createQuery("delete from Comment where taskId=:taskId");
			query1.setParameter("taskId", taskId);
			query1.executeUpdate();
			Query query = getSession().createQuery("delete from Task where taskId=:taskId");
			query.setParameter("taskId", taskId);
			query.executeUpdate();
//				t.comments.removeAll(t.getComments());
//				getSession().save(t);
			//getSession().delete(t);
			commit();
			return true;

		} catch (Exception e) {
			rollback();
			System.out.println("Error while Deleteing project into the database");
		}
		return false;
		
	}
}
