package ru.spbstu.students.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import ru.spbstu.students.dao.querysupport.QuerySupport;
import ru.spbstu.students.dto.AutobidInfo;
import ru.spbstu.students.dto.BidInfo;
import ru.spbstu.students.dto.ItemInfo;
import ru.spbstu.students.dto.UserInfo;

public class BidDAOSQLite extends QuerySupport implements BidDAO, ApplicationContextAware {

	private static final DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
	private ApplicationContext context;

	public String addBid(BidInfo bid) {	
		
		ItemDAO itemDao = (ItemDAO) context.getBean("itemDao");
		ItemInfo item = itemDao.getItem(bid.getItemID());
		double step = item.getStartBid() * 0.05; // 5% shall be the minimal step
		
		if(item.getType().equals("English")){
			Query q = new Query("SELECT amount FROM bids ")
				.append(where(eq("item", bid.getItemID())))
				.append(" ORDER BY amount DESC LIMIT 1");
			List<Double> lMax = q.list(new Fetcher<Double>(){
				@Override
				protected Double fetch(){
					return getDouble("amount");
				}
			});
			
			if(!lMax.isEmpty()){						
				double max = lMax.get(0);
			
				if(bid.getAmount() < max + step){
					return "lessThanStep";
				}
			}
		}
		else if(item.getType().equals("Dutch")){
			Query q = new Query("SELECT amount FROM bids ")
				.append(where(eq("item", bid.getItemID())))
				.append(" ORDER BY amount ASC LIMIT 1");
			List<Double> lMin = q.list(new Fetcher<Double>(){
				@Override
				protected Double fetch(){
					return getDouble("amount");
				}
			});
		
			if(!lMin.isEmpty()){						
				double min = lMin.get(0);
			
				if(bid.getAmount() > min - step){
					return "lessThanStep";
				}
			}			
		}
		else{
			return "unkType";
		}
		
		Query q = new Query("INSERT INTO bids(item, user, time, amount) VALUES(")
			.append(bid.getItemID() + ",")
			.append("'" + bid.getUser() + "',")
			.append("'" + df.format(bid.getTime()) + "',")
			.append(bid.getAmount() + ")");
		q.execute();
		
		return "success";
	}

	public List<BidInfo> getBids(int itemID) {
		Query q = new Query("SELECT b.item as item, u.email as user, b.amount as amount, b.time as time " +
				" FROM bids b join users u on b.user = u.email ")
			.append(where(eq("item", itemID)));
		
		return q.list(new Fetcher<BidInfo>(){
			@Override
			protected BidInfo fetch() { 				
				try {
					return new BidInfo(getInt("item"), getString("user"), getDouble("amount"), df.parse(getString("time")));
				} catch (ParseException e) {
					return new BidInfo(getInt("item"), getString("user"), getDouble("amount"), null);
				}
			}
		});
	}

	public void removeBid(int bidID) {
		Query q = new Query("DELETE FROM bids ").append(where(eq("id", bidID)));
		q.execute();
	}

	public void dutchBuy(int itemID, int userID){
		endAuction(itemID);
		
		Query q = new Query("INSERT INTO winners(item,user) VALUES(")
			.append(itemID + ",")
			.append(userID + ")");
		q.execute();
	}
	
	public void endAuction(int itemID){
		Query q = new Query("UPDATE items SET ")
			.append("state=2 ")
			.append(where(eq("id",itemID)));
		q.execute();		
	}
	
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		context = arg0;
	}

	public void refreshBids(int itemID) {
		AutobidsDAO autobidDao = (AutobidsDAO) context.getBean("autobidDao");
		UserDAO userDao = (UserDAO) context.getBean("userDao");
		ItemDAO itemDao = (ItemDAO) context.getBean("itemDao");
		ItemInfo item = itemDao.getItem(itemID);
		List<AutobidInfo> autobidList = autobidDao.getAutobidList(itemID);
		BidInfo bid = new BidInfo();
		List<BidInfo> bidList;
		UserInfo user;
		boolean isAdd = false;
		for (AutobidInfo ab : autobidList) {
			bidList = this.getBids(itemID);
			bid = bidList.get(bidList.size()-1);
			user = userDao.getUser(ab.getUser());
			if ((!bid.getUser().equals(user.getEmail())) && (ab.getMax() >= (bid.getAmount() + item.getStartBid()*0.05))) {
				this.addBid(new BidInfo(itemID, user.getEmail(), (bid.getAmount() + item.getStartBid()*0.05), new Date()));
				isAdd = true;
			}
		}
		if (isAdd) {
			refreshBids(itemID);
			isAdd = false;
		}
	}
}
