package ru.spbstu.students.web.actions;

import java.util.List;

import ru.spbstu.students.dao.UserDAO;
import ru.spbstu.students.dto.UserInfo;

public class ListUserAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4601647944830111579L;
	
	private List<UserInfo> result;
	private UserDAO userDao;
	
	public String execute() {
		
		result = userDao.listUser();
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
}
