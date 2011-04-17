package ru.spbstu.students.web.actions;

import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import ru.spbstu.students.dao.ItemCategoriesDAO;
import ru.spbstu.students.dao.UserDAO;

public class ItemCategoriesAction extends BaseAction implements SessionAware {
	
	private static final long serialVersionUID = 8676761089103795198L;
	private UserDAO userDao;
	private Map<String, Object> session;
	private List<String> result;
	private ItemCategoriesDAO itemCategories;
	
	public String getItemCategoriesList() {

		if (!session.containsKey("email")) {
			return ERROR;
		}

		if (!userDao.isAdmin((String) session.get("email"))) {
			return ERROR;
		}
		result = itemCategories.getCategories();
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

	public ItemCategoriesDAO getItemCategories() {
		return itemCategories;
	}

	public void setItemCategories(ItemCategoriesDAO itemCategories) {
		this.itemCategories = itemCategories;
	}

}
