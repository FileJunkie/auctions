package ru.spbstu.students.web.actions;

import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import ru.spbstu.students.dao.BlacklistDAO;
import ru.spbstu.students.dao.UserDAO;

public class BlacklistAction extends BaseAction implements SessionAware {
	

	private static final long serialVersionUID = -223161690140383249L;
	private UserDAO userDao;
	private Map<String, Object> session;
	private List<String> result;
	private BlacklistDAO blacklist;

	public String getBlackList() {

		if (!session.containsKey("email")) {
			return ERROR;
		}

		if (!userDao.isAdmin((String) session.get("email"))) {
			return ERROR;
		}
		result = blacklist.getBlackList();
		if (result != null) {
			return SUCCESS;
		} else {
			return ERROR;
		}
	}

	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}
	
	public void setSession(Map<String, Object> session) {
		this.session = session; 
	}

	public List<String> getResult() {
		return result;
	}

	public void setResult(List<String> result) {
		this.result = result;
	}

	public BlacklistDAO getBlacklist() {
		return blacklist;
	}

	public void setBlacklist(BlacklistDAO blacklist) {
		this.blacklist = blacklist;
	}

}
