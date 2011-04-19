package ru.spbstu.students.web.actions;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import ru.spbstu.students.dao.ItemCategoriesDAO;
import ru.spbstu.students.dao.UserDAO;
import ru.spbstu.students.dto.ItemCategories;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

public class ItemCategoriesAction extends BaseAction implements SessionAware, ModelDriven<ItemCategories>{
	
	private static final long serialVersionUID = 8676761089103795198L;
	private List<ItemCategories> result;
	private ItemCategoriesDAO itemCategories;
	private ItemCategories item = new ItemCategories();
	private Map<String, Object> session;
	private UserDAO userDao;
	
	public boolean isAdminSession() {
		if ((!session.containsKey("email")) || (!userDao.isAdmin((String) session.get("email")))) {
			return false;
		} else return true;
	}
	
	public String getItemCategoriesList() {

		if (!isAdminSession()) {
			return ERROR;
		}
		
		result = itemCategories.getCategories();
		if (result != null) {
			return SUCCESS;
		} else {
			return ERROR;
		}
	}
	
	public String addCategory() {
		
		if (!isAdminSession()) {
			return ERROR;
		}
		
		itemCategories.addCaterogy(item.getName());
		return SUCCESS;
	}
	
	public String removeCategory() {
		
		if (!isAdminSession()) {
			return ERROR;
		}
		
		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
		int id = Integer.parseInt(request.getParameter("id"));
		itemCategories.removeCaterogy(id);
		return SUCCESS;
	}
	
	public String editCategory() {
		
		if (!isAdminSession()) {
			return ERROR;
		}
		
		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
		int id = Integer.parseInt(request.getParameter("id"));
		
		String item = itemCategories.getCategory(id);
		if ((item != null) && (item.trim().length() > 0)) {
			session.put ( "editItemId", id); 
			return SUCCESS;
		} else {
			return ERROR;
		}	
	}
	
	public String updateCategory() {
		
		if (!isAdminSession()) {
			return ERROR;
		}
		
		Integer id = (Integer) session.get("editItemId");
		session.remove("editItemId");
		itemCategories.renameCategory(id, item.getName());
		return SUCCESS;
	}

	public List<ItemCategories> getResult() {
		return result;
	}

	public void setResult(List<ItemCategories> result) {
		this.result = result;
	}

	public ItemCategoriesDAO getItemCategories() {
		return itemCategories;
	}

	public void setItemCategories(ItemCategoriesDAO itemCategories) {
		this.itemCategories = itemCategories;
	}

	public ItemCategories getItem() {
		return item;
	}

	public void setItem(ItemCategories item) {
		this.item = item;
	}

	public ItemCategories getModel() {
		return item;
	}
	
	public void setSession(Map<String, Object> session) {
		this.session = session; 
	}

	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

}
