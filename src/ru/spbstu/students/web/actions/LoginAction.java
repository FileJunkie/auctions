package ru.spbstu.students.web.actions;

import com.opensymphony.xwork2.ModelDriven;

import ru.spbstu.students.dao.UserDAO;

public class LoginAction extends BaseAction implements ModelDriven<String[]>{

	private static final long serialVersionUID = -4144867964953564983L;
	
	private String password = new String();
	private String email = new String();
	private UserDAO userDao;
	
	public String execute() {
		try {
			return userDao.loginUser(email, password);
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

}
