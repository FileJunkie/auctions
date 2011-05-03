package ru.spbstu.students.web;

import ru.spbstu.students.dao.BidDAO;
import ru.spbstu.students.dto.BidInfo;

public class PriorityAddBid implements Comparable<PriorityAddBid>{

	private BidInfo bid;
	private int userType;
	private BidDAO bidDao;
	private String result;
	
	public BidInfo getBid() {
		return bid;
	}

	public void setBid(BidInfo bid) {
		this.bid = bid;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public BidDAO getBidDao() {
		return bidDao;
	}

	public void setBidDao(BidDAO bidDao) {
		this.bidDao = bidDao;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int compareTo(PriorityAddBid o) {
		if (this.getUserType() > o.getUserType()) 
			return 1;
		if (this.getUserType() < o.getUserType()) 
			return -1;
		if (this.getUserType() == o.getUserType()) 
			return 0;
		return 0;
	}

}
