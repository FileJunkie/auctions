package ru.spbstu.students.web.actions;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import ru.spbstu.students.dao.UserDAO;

import com.opensymphony.xwork2.ModelDriven;

public class LoginAction extends BaseAction implements ModelDriven<String[]>,SessionAware{

	private static final long serialVersionUID = -4144867964953564983L;
	
	private String password = new String();
	private String email = new String();
	private UserDAO userDao;
	private Map<String, Object> session; 

	
	public String execute() {
		try {
			String result = userDao.loginUser(email, password);
			if (result.equals("success")) {
				session.put ( "email", email); 
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
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

	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

	public String[] getModel() {
		String[] args = {email, password};
		return args;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session; 
	}

}
