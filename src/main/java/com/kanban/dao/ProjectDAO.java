package com.kanban.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

import com.kanban.pojo.Assignee;
import com.kanban.pojo.Project;
import com.kanban.pojo.User;

public class ProjectDAO extends DAO{
	
	public Project addProject(Project project) {
		Project p = new Project();
		try {
			begin();
			p.setOwner(project.getOwner());
			p.setDescription(project.getDescription());
			p.setStatus("New");
			p.setTitle(project.getTitle());
			//int projectID = (Integer) getSession().save(p);
			
			/*
			 * Set<User> members = (Set<User>) project.getMembers(); for(User u: members) {
			 * Query query = getSession().
			 * createQuery("update User set projectId=:projectId where userId=:userId ");
			 * query.setParameter("projectId", projectID); query.setParameter("userId",
			 * u.getUserId()); query.executeUpdate(); }
			 */
			
			Set<Assignee> members = (Set<Assignee>) project.getMembers(); 
			for(Assignee u: members) {
				p.getMembers().add(u);
			}
			getSession().save(p);
			commit();		
		} catch (Exception e) {
			rollback();
			System.out.println("Error while inserting new user into the database");
		}
		return p;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Project> getAllProjects() {
		ArrayList<Project> projects = new ArrayList<Project>();
		ArrayList<Project> assigneeProjects = new ArrayList<Project>();	
		try {
			begin();
			Query query =  getSession().createQuery("SELECT p, new map(u.userId as userId, u.image as image, u.userName as userName, u.emailId as emailId) FROM Project p JOIN p.members u");
			
			//query.setParameter("subtype", User.class);
			//projects = (ArrayList<Project>) getSession().createCriteria(Project.class)
			//.createCriteria("members").list();
			//select b.* from bar b inner join foo f on f.bar_id = b.id// SELECT p FROM Project p JOIN p.members a
			
			projects = (ArrayList<Project>) query.getResultList();
//			for(Project p: projects) {
//				Project dummy = new Project();
//				dummy.setOwner(p.getOwner());
//				dummy.setDescription(p.getDescription());
//				dummy.setStatus("New");
//				dummy.setTitle(p.getTitle());
//				Set<Assignee> ass = new HashSet<Assignee>();
//				ass = (Set<Assignee>) p.getMembers();
//				dummy.setMembers(ass);
//				dummy.setUserStories(p.getUserStories());
//				assigneeProjects.add(dummy);
//			}
					
			commit();
		} catch (Exception e) {
			rollback();
			projects = null;
			System.out.println("Error while inserting new user into the database");
		}
		return assigneeProjects;
	}
	
	public Project getProjectDetails(int projectId) {
		Project project = new Project();	
		try {
			begin();
			//This will return user object
			//Query query =  getSession().createQuery("SELECT u FROM User u JOIN u.projects p WHERE p.projectId =:projectId");
			
			Query query =  getSession().createQuery("SELECT p FROM Project p JOIN FETCH p.members  WHERE p.projectId =:projectId");
			//SELECT i FROM Item i JOIN FETCH i.order", Item.class
			query.setParameter("projectId", projectId);
			project = (Project) query.uniqueResult();
			commit();
		} catch (Exception e) {
			rollback();
			project = null;
			System.out.println("Error while inserting new user into the database");
		}
		return project;
	}
	
	public boolean deleteProject(int projectId) {
		try {
			begin();
			Project p = (Project) getSession().get(Project.class, projectId);
			if (null != p) {
				getSession().delete(p);
				commit();
				return true;
			} else {
				rollback();
				return false;
			}
		} catch (Exception e) {
			rollback();
			System.out.println("Error while Deleteing project into the database");
		}
		return false;
		
	}
	
	public Project updateProject(Project project, int projectId) {
		Project updateProject = null;
		try {
			begin();
			getSession().saveOrUpdate(project);
			commit();
			updateProject = (Project) getSession().load(Project.class, projectId);

		} catch (Exception e) {
			rollback();
			System.out.println("Error while updating user into the database");
		}
		return updateProject;
	}
}
