package ru.spbstu.students.web.actions;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import ru.spbstu.students.dao.UserDAO;
import ru.spbstu.students.dto.UserInfo;

public class UserRoomAction extends BaseAction implements SessionAware{
	
	private static final long serialVersionUID = 7223787251407889941L;
	private Map<String, Object> session;
	private UserDAO userDao;
	private UserInfo user;

	public String getUserInfo() {
		
		if (!session.containsKey("email"))
			return ERROR;
		
		user = userDao.getUser((String)session.get("email"));
		if (user != null) {
			return SUCCESS;
		} else {
			return ERROR;
		}
	}
	
	public void setSession(Map<String, Object> session) {
		this.session = session; 
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

}
