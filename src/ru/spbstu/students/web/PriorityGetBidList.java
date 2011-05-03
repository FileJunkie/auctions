package ru.spbstu.students.web;

import java.util.List;

import ru.spbstu.students.dao.BidDAO;
import ru.spbstu.students.dto.BidInfo;

public class PriorityGetBidList implements Comparable<PriorityGetBidList>{
	
	private String email;
	private int itemId;
	private int userType;
	private List<BidInfo> bidList;
	private BidDAO bidDao;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
	}
	public List<BidInfo> getBidList() {
		return bidList;
	}
	public void setBidList(List<BidInfo> bidList) {
		this.bidList = bidList;
	}
	public BidDAO getBidDao() {
		return bidDao;
	}
	public void setBidDao(BidDAO bidDao) {
		this.bidDao = bidDao;
	}
	public int compareTo(PriorityGetBidList o) {
		if (this.getUserType() > o.getUserType()) 
			return 1;
		if (this.getUserType() < o.getUserType()) 
			return -1;
		if (this.getUserType() == o.getUserType()) 
			return 0;
		return 0;
	}
	
	

}
