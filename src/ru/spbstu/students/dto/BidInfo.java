package ru.spbstu.students.dto;

import java.util.Date;

public class BidInfo {
	private int itemID;
	private int userID;
	private double amount;
	private Date time;
	
	public BidInfo(int itemID, int userID, double amount, Date time) {
		this.itemID = itemID;
		this.userID = userID;
		this.amount = amount;
		this.time = time;
	}

	public int getItemID() {
		return itemID;
	}

	public void setItemID(int itemID) {
		this.itemID = itemID;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
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
