package ru.spbstu.students.web.actions;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionContext;

import ru.spbstu.students.dao.UserDAO;
import ru.spbstu.students.dto.UserInfo;

public class AdminAction extends BaseAction implements SessionAware {

	private static final long serialVersionUID = 2968415355537519942L;
	private UserDAO userDao;
	private Map<String, Object> session;
	private List<UserInfo> result;
	private UserInfo user;

	public String adminConsole() {

		if (!session.containsKey("email")) {
			return ERROR;
		}

		if (!userDao.isAdmin((String) session.get("email"))) {
			return ERROR;
		}
		return SUCCESS;
	}

	public String adminUserList() {

		if (!session.containsKey("email")) {
			return ERROR;
		}

		if (!userDao.isAdmin((String) session.get("email"))) {
			return ERROR;
		}

		result = userDao.getUsers();
		if (result != null) {
			return SUCCESS;
		} else {
			return ERROR;
		}
	}
	
	public String deleteUser() {

		if (!session.containsKey("email")) {
			return ERROR;
		}

		if (!userDao.isAdmin((String) session.get("email"))) {
			return ERROR;
		}

		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
		int id = Integer.parseInt(request.getParameter("id"));
		if (userDao.getUser(id).getEmail().equals((String) session.get("email"))) {
			session.remove("email");
		}
		userDao.removeUser(id);
		return SUCCESS;
	}
	
	public String editUser() {

		if (!session.containsKey("email")) {
			return ERROR;
		}

		if (!userDao.isAdmin((String) session.get("email"))) {
			return ERROR;
		}

		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
		int id = Integer.parseInt(request.getParameter("id"));
		user = userDao.getUser(id);
		if (user != null) {
			session.put ( "editUserId", id); 
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

	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}
}
