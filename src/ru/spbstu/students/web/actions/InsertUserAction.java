package ru.spbstu.students.web.actions;

import ru.spbstu.students.dao.UserDAO;
import ru.spbstu.students.dto.UserInfo;

import com.opensymphony.xwork2.ModelDriven;

public class InsertUserAction extends BaseAction implements ModelDriven<UserInfo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7240629050119329136L;

	private UserDAO userDao;
	private UserInfo user = new UserInfo();

	public String execute() {
		try {
			return userDao.addUser(user);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
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
}
