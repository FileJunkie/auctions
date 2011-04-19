package ru.spbstu.students.dto;

import java.util.Date;

public class BidInfo {
	private int itemID;
	private int userID;
	private double amount;
	private Date date;
	
	public BidInfo(int itemID, int userID, double amount, Date date) {
		this.itemID = itemID;
		this.userID = userID;
		this.amount = amount;
		this.date = date;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
