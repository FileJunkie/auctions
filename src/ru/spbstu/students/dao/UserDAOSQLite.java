package ru.spbstu.students.dao;

import java.util.List;

import org.apache.log4j.Logger;

import ru.spbstu.students.dao.querysupport.QuerySupport;
import ru.spbstu.students.dto.UserInfo;
import ru.spbstu.students.util.Util;
import ru.spbstu.students.web.UserCategories;
import ru.spbstu.students.web.UserTypes;

public class UserDAOSQLite extends QuerySupport implements UserDAO{
	
	private static final Logger log = Logger.getLogger(UserDAOSQLite.class);
			
	public String addUser(UserInfo user) throws Exception {
		if (user == null || !Util.isValidUser(user)) {
			log.error("Bad parameters to insert");
			return "error";
		}
		
		int category, type, registered, active;
		
		Query isExist = new Query("select count(*) as c from users ").append(where(eq("email",user.getEmail())));
		registered = isExist.fetch(new IntegerFetcher("c"));
		
		if (registered == 0) {
			
			String pass = Util.getHashMd5(user.getPassword());
			category = UserCategories.getKeyByLabel(user.getCategory());
			type = UserTypes.getKeyByLabel(user.getType());
			active = 0;
			String key = Util.getHashMd5(user.getEmail());
			
			Query q = new Query("insert into users(email,password,category,type,active,key) values (")
			.append("'" + user.getEmail() + "',")
			.append("'" + pass + "',")
			.append(category + ",")
			.append(type + ",")
			.append(active + ",")
			.append("'" + key + "')");
			try {
				q.execute();
			} catch (Exception e) {
				log.error("Execute insert query error");
				e.printStackTrace();
			}
			try {
				Util.sendActivationMail(user.getEmail(), key);
			} catch (Exception e) {
				log.error("Sending activation mail error");
				Query delete = new Query("delete from users ").append(where(eq("email",user.getEmail())));
				delete.execute();
				e.printStackTrace();
			}
			return "success";
		} else {
			log.error("User with same email is already registered");
			return "alreadyReg";
		}
	}

	public List<UserInfo> getUsers() {
		Query q = new Query("select u.id as id, u.email as email, u.password,  c.name as category, t.name as type, u.admin as admin, u.active as active " +
				" from users u " +
				" join u_categories c on c.id = u.category " +
				" join u_types t on t.id = u.type");
		
		return q.list(new Fetcher<UserInfo> () {

			@Override
			protected UserInfo fetch() {
				return new UserInfo(getInt("id"), getString("email"), getString("password"), 
						getString("category"), getString("type"), getInt("admin") != 0, getInt("active") != 0);
			}
		});
	}

	public String activateUser(String key) {
		
		if ((key == null) || (key.trim().length() == 0)) {
			log.error("Bad key for activate");
			return "error";
		}
		
		Query q = new Query("update users set active = 1 ").append(where(eq("key", key)));
		try {
			q.execute();
		} catch (Exception e) {
			log.error("Execute activation query error");
			e.printStackTrace();
		}
		return "success";
	}
	
	public String loginUser (UserInfo user) throws Exception {
		if (user == null || user.getEmail() == null || user.getPassword() == null 
				|| user.getEmail().trim().length() == 0 || user.getPassword().trim().length() == 0) {
			log.error("Wrong parameters for login");
			return "not login";
		}
		
		int registered;
		String pass = Util.getHashMd5(user.getPassword());
		Query isExist = new Query("select count(*) as c from users ").append(where(and(and(eq("email",user.getEmail()),eq("password",pass)),eq("active",1))));
		registered = isExist.fetch(new IntegerFetcher("c"));
		if (registered == 1) {
			return "success";
		} else {
			return "not login";
		}
	}
	
	public boolean isAdmin(String email){
		
		if(email == null || email.isEmpty()){
			return false;
		}
	
		Query isAdmin = new Query("select admin from users ").append(where(eq("email",email)));
	
		return isAdmin.fetch(new IntegerFetcher("admin")) != 0;
		
	}
	
	public UserInfo getUser(int id){
		Query q = new Query("select u.id as id, u.email as email, u.password,  c.name as category, t.name as type, u.admin as admin, u.active as active " +
				" from users u " +
				" join u_categories c on c.id = u.category " +
				" join u_types t on t.id = u.type ").append(where(eq("u.id", id)));
		
		return q.list(new Fetcher<UserInfo> () {

			@Override
			protected UserInfo fetch() {
				return new UserInfo(getInt("id"), getString("email"), getString("password"), 
						getString("category"), getString("type"), getInt("admin") != 0, getInt("active") != 0);
			}
		}).get(0);
		
	}
	
	public UserInfo getUser(String email){
		Query q = new Query("select u.id as id, u.email as email, u.password,  c.name as category, t.name as type, u.admin as admin, u.active as active " +
				" from users u " +
				" join u_categories c on c.id = u.category " +
				" join u_types t on t.id = u.type ").append(where(eq("u.email", email)));
		
		return q.list(new Fetcher<UserInfo> () {

			@Override
			protected UserInfo fetch() {
				return new UserInfo(getInt("id"), getString("email"), getString("password"), 
						getString("category"), getString("type"), getInt("admin") != 0, getInt("active") != 0);
			}
		}).get(0);
		
	}
	
	public String editUser(UserInfo user) throws Exception{
		if (user == null || !Util.isValidUser(user)) {
			log.error("Bad parameters to insert");
			return "error";
		}
		
		int category, type, registered, active, admin;
		
		Query isExist = new Query("select count(*) as c from users ").append(where(eq("email",user.getEmail())));
		registered = isExist.fetch(new IntegerFetcher("c"));
		
		if (registered == 0) {
			
			String pass = Util.getHashMd5(user.getPassword());
			category = UserCategories.getKeyByLabel(user.getCategory());
			type = UserTypes.getKeyByLabel(user.getType());
			active = user.isActive() == true ? 1 : 0;
			admin = user.isAdmin() == true ? 1 : 0;
			String key = Util.getHashMd5(user.getEmail());
			
			Query q = new Query("UPDATE users SET ")
			.append("email='" + user.getEmail() + "',")
			.append("password='" + pass + "',")
			.append("category=" + category + ",")
			.append("type=" + type + ",")
			.append("active=" + active + ",")
			.append("admin=" + admin + ",")
			.append("key='" + key + "' ")
			.append("WHERE email='" + user.getEmail() + "';");
			try {
				q.execute();
			} catch (Exception e) {
				log.error("Execute update query error");
				e.printStackTrace();
			}
			return "success";
		} else {
			log.error("Attempted to edit unregistered user");
			return "alreadyReg";
		}
	}
	
	public void removeUser(int id){
		Query removeUser = new Query("DELETE FROM users ").append(where(eq("id", id)));
		removeUser.execute();
	}
}
