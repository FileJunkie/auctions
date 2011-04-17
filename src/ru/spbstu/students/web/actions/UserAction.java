package ru.spbstu.students.web.actions;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import ru.spbstu.students.dao.UserDAO;
import ru.spbstu.students.dto.UserInfo;

import com.opensymphony.xwork2.ModelDriven;

public class UserAction extends BaseAction implements SessionAware, ModelDriven<UserInfo> {

	private static final long serialVersionUID = 2101986413066433069L;
	private UserDAO userDao;
	private UserInfo user = new UserInfo();
	private String key;
	private String password = new String();
	private String email = new String();
	private Map<String, Object> session; 
	
	public String registration() throws Exception {
        return SUCCESS;
    }
	
	public String insertUser() {
		try {
			return userDao.addUser(user);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
	public String activation() {
		return userDao.activateUser(key);
	}
	
	public String login() {
		try {
			String result = userDao.loginUser(user);
			if (result.equals("success")) {
				session.put ( "email", user.getEmail()); 
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
	public String updateUser() {
		try {
			Integer id = (Integer) session.get("editUserId");
			session.remove("editUserId");
			UserInfo us = userDao.getUser(id);
			String result = userDao.editUser(id, user);
			if (us.getEmail().equals((String) session.get("email"))) {
				session.remove("email");
				session.put("email", userDao.getUser(id).getEmail());
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
	public String logout(){
		session.remove("email");
		return SUCCESS;
	}
	
	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}

	public UserInfo getModel() {
		return user;
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session; 
	}

}
