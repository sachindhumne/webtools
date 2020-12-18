package com.kanban.dao;

import java.util.ArrayList;

import org.hibernate.query.Query;

import com.kanban.filter.RequestWrapper;
import com.kanban.pojo.Comment;
import com.kanban.pojo.User;

public class CommentDAO extends DAO{
	public Comment addComment(Comment comment) {
		Comment c = new Comment();
		try {
			begin();
			c.setComment(RequestWrapper.cleanXSS(comment.getComment()));
			c.setTaskId(comment.getTaskId());
			//User u = (User) getSession().get(User.class, comment.getCommentId());
			//List<User> author = (Set<User>) project.getMembers(); 
			c.setUserId(comment.getUserId());;
			getSession().save(c);
			commit();
			
		} catch (Exception e) {
			rollback();
			System.out.println("Error while inserting new user into the database");
		}
		return c;
	}
	
	public ArrayList<Comment> getAllComments(int taskId) {
		ArrayList<Comment> comments = null;
		try {
			begin();
			Query query = getSession().createQuery("SELECT c.comment, u.userName, u.image FROM Comment c JOIN User u ON c.userId=u.userId  where c.taskId=:taskId");
			query.setParameter("taskId", taskId);
			comments = (ArrayList<Comment>) query.getResultList();
			commit();
			
		} catch (Exception e) {
			rollback();
			comments = null;
			System.out.println("Error while inserting new user into the database");
		}
		return comments;
	}
	
	public Comment getComment(int taskId) {
		ArrayList<Comment> comments = null;
		Comment comment = null;
		try {
			begin();
			Query query = getSession().createQuery("FROM Comment where taskId = :taskId");
			query.setParameter("taskId", taskId);
			comment =  (Comment) query.getResultList();
			commit();
			
		} catch (Exception e) {
			rollback();
			comment = null;
			System.out.println("Error while inserting new user into the database");
		}
		return comment;
	}
}
