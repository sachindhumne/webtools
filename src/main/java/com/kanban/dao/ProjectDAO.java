package com.kanban.dao;

import java.util.ArrayList;
import java.util.Set;

import org.hibernate.query.Query;

import com.kanban.filter.RequestWrapper;
import com.kanban.pojo.Project;
import com.kanban.pojo.User;

public class ProjectDAO extends DAO{
	
	public Project addProject(Project project) {
		Project p = new Project();
		try {
			begin();
			p.setOwner(RequestWrapper.cleanXSS(project.getOwner()));
			p.setDescription(RequestWrapper.cleanXSS(project.getDescription()));
			p.setStatus("New");
			p.setTitle(RequestWrapper.cleanXSS(project.getTitle()));
			//int projectID = (Integer) getSession().save(p);
			
			/*
			 * Set<User> members = (Set<User>) project.getMembers(); for(User u: members) {
			 * Query query = getSession().
			 * createQuery("update User set projectId=:projectId where userId=:userId ");
			 * query.setParameter("projectId", projectID); query.setParameter("userId",
			 * u.getUserId()); query.executeUpdate(); }
			 */
			
			Set<User> members = (Set<User>) project.getMembers(); 
			for(User u: members) {
				p.getMembers().add(u);
			}
			getSession().save(p);
			commit();		
		} catch (Exception e) {
			rollback();
			System.out.println("Error while adding project to the database");
		}
		return p;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Project> getAllProjects() {
		ArrayList<Project> projects = new ArrayList<Project>();	
		try {
			begin();
			Query query =  getSession().createQuery("FROM Project");
			projects = (ArrayList<Project>) query.list();
			/*
			 * for(Project p: projects) { for(User u: p.getMembers()) { u.setPassword(""); }
			 * }
			 */
			//query.setParameter("subtype", User.class);
			//projects = (ArrayList<Project>) getSession().createCriteria(Project.class)
			//.createCriteria("members").list();
			//select b.* from bar b inner join foo f on f.bar_id = b.id// SELECT p FROM Project p JOIN p.members a
//			SELECT p, new map(u.userId as userId, u.image as image, u.userName as userName, u.emailId as emailId) FROM Project p JOIN p.members u
					
			commit();
		} catch (Exception e) {
			rollback();
			projects = null;
			System.out.println("Error while fectching all Projects the database");
		}
		return projects;
	}
	
	public Project getProjectDetails(int projectId) {
		Project project = new Project();	
		try {
			begin();
			//This will return user object
			//Query query =  getSession().createQuery("SELECT u FROM User u JOIN u.projects p WHERE p.projectId =:projectId");
			
			Query query =  getSession().createQuery("SELECT p FROM Project p  WHERE p.projectId =:projectId");
			//SELECT i FROM Item i JOIN FETCH i.order", Item.class
			query.setParameter("projectId", projectId);
			project = (Project) query.uniqueResult();
			/*
			 * for(User u: project.getMembers()) { u.setPassword(""); }
			 */
			commit();
		} catch (Exception e) {
			rollback();
			project = null;
			System.out.println("Error while inserting new project into the database");
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
			System.out.println("Error while updating project into the database");
		}
		return updateProject;
	}
}
