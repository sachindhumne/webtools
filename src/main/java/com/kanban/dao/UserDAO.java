	package com.kanban.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.query.Query;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.kanban.pojo.User;

public class UserDAO extends DAO{
	public User addUser(User user) {
		User u = new User();
		String pw_hash = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10));
		try {
			begin();
			u.setEmailId(user.getEmailId());
			u.setUserName(user.getUserName());
			u.setPassword(pw_hash);
			u.setImage(user.getImage());
			getSession().save(u);
			commit();
			
		} catch (Exception e) {
			rollback();
			System.out.println("Error while inserting new user into the database");
		}
		return u;
	}
	
	public User updateUserPassword(User user) {
		User u = null;
		try {
			begin();
			int userId = user.getUserId();
			u = (User) getSession().get(User.class, userId);
			String pw_hash = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10));
		    u.setPassword(pw_hash);
		    getSession().persist(u);  
			commit();
			return u;
			
		} catch (Exception e) {
			rollback();
			System.out.println("Error while updating user into the database");
		}
		return u;
	}
	
	public boolean deleteUser(int userid) {
		try {
			begin();
			User u = (User) getSession().get(User.class, userid);
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
			System.out.println("Error while Deleteing user into the database");
		}
		return false;
		
	}
	
	public User getUser(int id) {
		User u = null;
		try {
			begin();
			u = getSession().get(User.class, id);
			commit();
			u.setPassword("");
		} catch (Exception e) {
			rollback();
			System.out.println("Error while inserting new user into the database");
		}
		return u;
	}
	
	public User login(User user) {
		User u = null;
		try {
			begin();
			Query query = getSession().createQuery("from User where userName=:uname");
			query.setParameter("uname", user.getUserName());
			u = (User) query.uniqueResult();
			if(BCrypt.checkpw(user.getPassword(), u.getPassword())) {
				return u;
			};
			
		} catch (Exception e) {
			rollback();
			System.out.println("Error while Deleteing user into the database");
		}
		return u;
	}
	
	public ArrayList<User> getUserNames() {
		ArrayList<User> users = new ArrayList<User>();
		try {
			begin();
			Query query = getSession().createQuery("select new map(u.userId as userId, u.image as image, u.userName as userName, u.emailId as emailId) from User u");
			users = (ArrayList<User>) query.getResultList();
			commit();
		} catch (Exception e) {
			rollback();
			System.out.println("Error while inserting new user into the database");
		}
		return users;
	}
}
