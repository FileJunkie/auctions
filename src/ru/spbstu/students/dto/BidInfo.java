package ru.spbstu.students.dto;

import java.util.Date;

public class BidInfo {
	private int itemID;
	private String user;
	private double amount;
	private Date time;
	
	public BidInfo(int itemID, String user, double amount, Date time) {
		this.itemID = itemID;
		this.user = user;
		this.amount = amount;
		this.time = time;
	}

	public BidInfo() {}

	public int getItemID() {
		return itemID;
	}

	public void setItemID(int itemID) {
		this.itemID = itemID;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
}
