package ru.spbstu.students.web.actions;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import ru.spbstu.students.dao.AutobidsDAO;
import ru.spbstu.students.dao.BidDAO;
import ru.spbstu.students.dao.ItemDAO;
import ru.spbstu.students.dao.UserDAO;
import ru.spbstu.students.dto.AutobidInfo;
import ru.spbstu.students.dto.BidInfo;
import ru.spbstu.students.dto.ItemInfo;
import ru.spbstu.students.util.CounterThread;
import ru.spbstu.students.web.UserCategories;

import com.opensymphony.xwork2.ModelDriven;

public class AutobidAction extends BaseAction implements SessionAware, ModelDriven<AutobidInfo>  {

	private static final Logger log = Logger.getLogger(AutobidAction.class);

	private static final long serialVersionUID = -5972555139531303599L;
	private AutobidInfo autobid = new AutobidInfo();
	private Map<String, Object> session; 
	private List<BidInfo> bidList;
	private BidDAO bidDao;
	private UserDAO userDao;
	private ItemDAO itemDao;
	private AutobidsDAO autobidDao;
	
	public String autoBid() {

		if (!session.containsKey("email"))
			return ERROR;
		
		int userId = userDao.getUser((String)session.get("email")).getId();
		String email = (String)session.get("email");
		ItemInfo item = itemDao.getItem(autobid.getItem());
		double step = item.getStartBid() * 0.05;

		int requestId = new Random().nextInt(Integer.MAX_VALUE);
		
		log.info("Start  autoBid transaction. Request ID: " + requestId + "User ID: " + userId + "Item ID: " + item.getId() + "User category: " + userDao.getUser(userId).getCategory());
		CounterThread.inc(UserCategories.getByLabel(userDao.getUser(userId).getCategory()));
		
		autobid.setUser(userId);
		autobid.setStep(step);
		
		try {
			AutobidInfo ab = autobidDao.getAutobid(item.getId(), userId);
			if (ab != null) {
				if (ab.getMax() < autobid.getMax()) {
					autobidDao.removeAutobid(item.getId(), userId);
					autobidDao.addAutobid(autobid);
				} else {
					log.info("Finish autoBid transaction, request ID: "	+ requestId);
					CounterThread.dec(UserCategories.getByLabel(userDao.getUser(userId).getCategory()));
					return SUCCESS;
				}
			} else {
				autobidDao.addAutobid(autobid);
			}

			bidList = bidDao.getBids(autobid.getItem());
			BidInfo bid;
			if (!bidList.isEmpty()) {
				bid = bidList.get(bidList.size()-1);
				if ((!bid.getUser().equals(email)) && (autobid.getMax() >= (bid.getAmount() + item.getStartBid()*0.05))) {
					bidDao.addBid(new BidInfo(autobid.getItem(), email, (bid.getAmount() + item.getStartBid()*0.05), new Date()));
				}
			} else if (autobid.getMax() >= item.getStartBid() * 1.05){
				bidDao.addBid(new BidInfo(autobid.getItem(), email, item.getStartBid() * 1.05, new Date()));
			}
			
			bidDao.refreshBids(autobid.getItem());
			
		} catch (Exception e) {
			log.info("Finish autoBid transaction, request ID: " + requestId);
			CounterThread.dec(UserCategories.getByLabel(userDao.getUser(userId).getCategory()));
			return ERROR;
		}
		log.info("Finish autoBid transaction, request ID: " + requestId);
		CounterThread.dec(UserCategories.getByLabel(userDao.getUser(userId).getCategory()));
		return SUCCESS;
	}
	
	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}
	
	public void setSession(Map<String, Object> session) {
		this.session = session; 
	}
	
	public AutobidInfo getModel() {
		return autobid;
	}
	
	public AutobidInfo getAutobid() {
		return autobid;
	}

	public void setAutobid(AutobidInfo autobid) {
		this.autobid = autobid;
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

	public void setItemDao(ItemDAO itemDao) {
		this.itemDao = itemDao;
	}

	public void setAutobidDao(AutobidsDAO autobidDao) {
		this.autobidDao = autobidDao;
	}
}
