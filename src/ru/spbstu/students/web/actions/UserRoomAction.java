package ru.spbstu.students.web.actions;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionContext;

import ru.spbstu.students.dao.ItemDAO;
import ru.spbstu.students.dao.UserDAO;
import ru.spbstu.students.dto.ItemInfo;
import ru.spbstu.students.dto.UserInfo;

public class UserRoomAction extends BaseAction implements SessionAware{
	
	private static final long serialVersionUID = 7223787251407889941L;
	private Map<String, Object> session;
	private UserDAO userDao;
	private ItemDAO itemDao;
	private UserInfo user;
	private List<ItemInfo> itemInfo;

	public String getUserInfo() {
		
		if (!session.containsKey("email"))
			return ERROR;
		
		user = userDao.getUser((String)session.get("email"));
		itemInfo = itemDao.getItems(user.getId());
		if (user != null) {
			return SUCCESS;
		} else {
			return ERROR;
		}
	}
	
	public String editUserInfo() {
		
		if (!session.containsKey("email"))
			return ERROR;
		
		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
		int id = Integer.parseInt(request.getParameter("id"));
		user = userDao.getUser(id);
		if (user != null) {
			session.put ( "editUserId", id); 
			session.put ( "editUserInfo", true); 
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

	public List<ItemInfo> getItemInfo() {
		return itemInfo;
	}

	public void setItemInfo(List<ItemInfo> itemInfo) {
		this.itemInfo = itemInfo;
	}

	public void setItemDao(ItemDAO itemDao) {
		this.itemDao = itemDao;
	}

}
