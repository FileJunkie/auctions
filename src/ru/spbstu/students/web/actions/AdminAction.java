package ru.spbstu.students.web.actions;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionContext;

import ru.spbstu.students.dao.UserDAO;
import ru.spbstu.students.dto.UserInfo;
import ru.spbstu.students.util.CounterThread;
import ru.spbstu.students.web.UserCategories;

public class AdminAction extends BaseAction implements SessionAware {
	
	private static final long serialVersionUID = 2968415355537519942L;
	private UserDAO userDao;
	private Map<String, Object> session;
	private List<UserInfo> result;
	private UserInfo user;
	private int counter;
	private int permCounter;
	private int counterFull;
	private int permCounterFull;
	private int counterLite;
	private int permCounterLite;
	
	public boolean isAdminSession() {
		if ((!session.containsKey("email")) || (!userDao.isAdmin((String) session.get("email")))) {
			return false;
		} else return true;
	}

	public String adminConsole() {

		if (!isAdminSession()) {
			return ERROR;
		}
		counter = CounterThread.getCounter(UserCategories.VIP);
		permCounter = CounterThread.getCounterPermanent(UserCategories.VIP);
		counterLite = CounterThread.getCounter(UserCategories.Lite);
		permCounterLite = CounterThread.getCounterPermanent(UserCategories.Lite);
		counterFull = CounterThread.getCounter(UserCategories.Full);
		permCounterFull = CounterThread.getCounterPermanent(UserCategories.Full);
		return SUCCESS;
	}

	public String adminUserList() {

		if (!isAdminSession()) {
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

		if (!isAdminSession()) {
			return ERROR;
		}

		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
		int id = Integer.parseInt(request.getParameter("id"));
		if (userDao.getUser(id).getEmail().equals((String) session.get("email"))) {
			session.remove("email");
			session.remove("admin");
			session.remove("seller");
		}
		userDao.removeUser(id);
		return SUCCESS;
	}
	
	public String editUser() {

		if (!isAdminSession()) {
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

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public int getPermCounter() {
		return permCounter;
	}

	public void setPermCounter(int permCounter) {
		this.permCounter = permCounter;
	}

	public int getCounterFull() {
		return counterFull;
	}

	public void setCounterFull(int counterFull) {
		this.counterFull = counterFull;
	}

	public int getPermCounterFull() {
		return permCounterFull;
	}

	public void setPermCounterFull(int permCounterFull) {
		this.permCounterFull = permCounterFull;
	}

	public int getCounterLite() {
		return counterLite;
	}

	public void setCounterLite(int counterLite) {
		this.counterLite = counterLite;
	}

	public int getPermCounterLite() {
		return permCounterLite;
	}

	public void setPermCounterLite(int permCounterLite) {
		this.permCounterLite = permCounterLite;
	}
}
