	package com.kanban.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.query.Query;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.kanban.filter.RequestWrapper;
import com.kanban.pojo.User;

public class UserDAO extends DAO{
	public User addUser(User user) {
		User u = new User();
		String pw_hash = BCrypt.hashpw(RequestWrapper.cleanXSS(user.getPassword()), BCrypt.gensalt(10));
		try {
			begin();
			u.setEmailId(RequestWrapper.cleanXSS(user.getEmailId()));
			u.setUserName(RequestWrapper.cleanXSS(user.getUserName()));
			u.setPassword(pw_hash);
			u.setImage(RequestWrapper.cleanXSS(user.getImage()));
			u.setRole(RequestWrapper.cleanXSS(user.getRole()));
			getSession().save(u);
			commit();
			
		} catch (Exception e) {
			rollback();
			System.out.println("Error while inserting new user into the database");
		}
		return u;
	}
	
	public boolean existingUser(User user) {
		User u = null;
		String pw_hash = BCrypt.hashpw(RequestWrapper.cleanXSS(user.getPassword()), BCrypt.gensalt(10));
		try {
			begin();
			Query query = getSession().createQuery("from User where emailId= :emailId or userName= :userName");
			query.setParameter("emailId", user.getEmailId());
			query.setParameter("userName", user.getUserName());
			u = (User) query.uniqueResult();
			commit();
			
		} catch (Exception e) {
			rollback();
			System.out.println("Error while searching user into the database");
		}
		return u != null ? true: false;
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
				u.setPassword("");
				return u;
			};
			
		} catch (Exception e) {
			rollback();
			System.out.println("Error while Deleteing user into the database");
		}
		return u;
	}
	
	public boolean verifyBasicAuth(String[] userPass) {
		try {
			begin();
			Query query = getSession().createQuery("from User where userName=:uname");
			query.setParameter("uname", userPass[0]);
			User u = (User) query.uniqueResult();
			if(BCrypt.checkpw(userPass[1], u.getPassword())) {
				commit();
				return true;
			};
			
		} catch (Exception e) {
			rollback();
			System.out.println("Error while Deleteing user into the database");
		}
		return false;
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
