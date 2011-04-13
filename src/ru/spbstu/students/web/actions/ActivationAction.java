package ru.spbstu.students.web.actions;

import ru.spbstu.students.dao.UserDAO;

public class ActivationAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6419961930281401010L;
	private UserDAO userDao;
	private String key;

	public String execute() {

		return userDao.activateUser(key);

	}

	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
