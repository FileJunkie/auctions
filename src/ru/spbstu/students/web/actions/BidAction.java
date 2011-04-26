package ru.spbstu.students.web.actions;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import ru.spbstu.students.dao.BidDAO;
import ru.spbstu.students.dao.UserDAO;
import ru.spbstu.students.dto.BidInfo;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

public class BidAction extends BaseAction implements SessionAware, ModelDriven<BidInfo>  {


	private static final long serialVersionUID = -5972555139531303599L;
	private BidInfo bid = new BidInfo();
	private Map<String, Object> session; 
	private BidDAO bidDao;
	private List<BidInfo> bidList;
	private UserDAO userDao;
	
	public String bid(){
		if (!session.containsKey("email"))
			return ERROR;
		
		int userId = userDao.getUser((String)session.get("email")).getId();
		bid.setUserID(userId);
		bid.setTime(new Date());
		if(!bidDao.addBid(bid)){
			return ERROR;
		}
		
		return SUCCESS;
	}
	
	public String getBids(){		
		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
		int itemId = Integer.parseInt(request.getParameter("itemId"));
		
		try{
			bidList = bidDao.getBids(itemId);
		}
		catch(Exception e){
			return ERROR;
		}
		
		return SUCCESS;
	}
	
	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}
	
	public void setSession(Map<String, Object> session) {
		this.session = session; 
	}
	
	public BidInfo getModel() {
		return bid;
	}
	
	public void setBidDao(BidDAO bidDao) {
		this.bidDao = bidDao;
	}

	public List<BidInfo> getBidList() {
		return bidList;
	}

	public void setBidList(List<BidInfo> bidList) {
		this.bidList = bidList;
	}

	public BidInfo getBid() {
		return bid;
	}

	public void setBid(BidInfo bid) {
		this.bid = bid;
	}		

}
