package ru.spbstu.students.web.actions;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import ru.spbstu.students.dao.BlacklistDAO;
import ru.spbstu.students.dao.UserDAO;
import ru.spbstu.students.dto.BannedEmail;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

public class BlacklistAction extends BaseAction implements SessionAware, ModelDriven<BannedEmail> {
	
	private static final long serialVersionUID = -223161690140383249L;
	private List<BannedEmail> result;
	private BlacklistDAO blacklist;
	private BannedEmail banned = new BannedEmail();
	private Map<String, Object> session;
	private UserDAO userDao;
	
	public boolean isAdminSession() {
		if ((!session.containsKey("email")) || (!userDao.isAdmin((String) session.get("email")))) {
			return false;
		} else return true;
	}

	public String getBlackList() {

		if (!isAdminSession()) {
			return ERROR;
		}

		result = blacklist.getBlackList();
		if (result != null) {
			return SUCCESS;
		} else {
			return ERROR;
		}
	}
	
	public String addBan() {

		if (!isAdminSession()) {
			return ERROR;
		}
		
		blacklist.ban(banned.getEmail());
		return SUCCESS;
	}
	
	public String unBan() {

		if (!isAdminSession()) {
			return ERROR;
		}
		
		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
		int id = Integer.parseInt(request.getParameter("id"));
		blacklist.unban(id);
		return SUCCESS;
	}

	public List<BannedEmail> getResult() {
		return result;
	}

	public void setResult(List<BannedEmail> result) {
		this.result = result;
	}

	public BlacklistDAO getBlacklist() {
		return blacklist;
	}

	public void setBlacklist(BlacklistDAO blacklist) {
		this.blacklist = blacklist;
	}

	public BannedEmail getModel() {
		return banned;
	}
	
	public void setSession(Map<String, Object> session) {
		this.session = session; 
	}

	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

}
