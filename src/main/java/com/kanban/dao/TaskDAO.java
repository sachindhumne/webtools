package com.kanban.dao;

import java.util.ArrayList;

import org.hibernate.query.Query;

import com.kanban.pojo.Task;
import com.kanban.pojo.UserStory;

public class TaskDAO extends DAO{
	public Task addTask(Task task) {
		Task t = new Task();
		try {
			begin();
			t.setDescription(task.getDescription());
			t.setPriority(task.getPriority());
			t.setStatus(task.getStatus());
			t.setTitle(task.getTitle());
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
			Query query = getSession().createQuery("SELECT t.taskId, t.storyId, t.title, t.description, t.status, t.priority FROM Task t JOIN t.assignee a");
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
}
