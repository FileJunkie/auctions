package ru.spbstu.students.web.actions;

import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import ru.spbstu.students.dao.UserDAO;
import ru.spbstu.students.dto.UserInfo;

public class AdminUserListAction extends BaseAction implements SessionAware{

	private static final long serialVersionUID = 4601647944830111579L;
	
	private List<UserInfo> result;
	private UserDAO userDao;
	private Map<String, Object> session;
	
	public String execute() {
		
		if(!session.containsKey("email")){
			return ERROR;
		}
		
		if(!userDao.isAdmin((String) session.get("email"))){
			return ERROR;
		}
		
		result = userDao.getUsers();
		if (result != null) {
			return SUCCESS;
		} else {
			return ERROR;
		}
	}

	public List<UserInfo> getResult() {
		return result;
	}

	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}
	
	public void setSession(Map<String, Object> session) {
		this.session = session; 
	}
}
