package ru.spbstu.students.dto;

import java.sql.Date;

public class ItemInfo {
	private int seller;
	private String name;
	private String description;
	private String photo;
	private double startBid;
	private String type;
	private double min;
	private Date startReg;
	private Date finishReg;
	private Date startAuc;
	private Date finishAuc;
	private int state;
	private String delivery;
	private int category;
	
	public ItemInfo(int seller, String name, String description, String photo,
			double startBid, String type, double min, Date startReg,
			Date finishReg, Date startAuc, Date finishAuc, int state,
			String delivery, int category) {
		this.seller = seller;
		this.name = name;
		this.description = description;
		this.photo = photo;
		this.startBid = startBid;
		this.type = type;
		this.min = min;
		this.startReg = startReg;
		this.finishReg = finishReg;
		this.startAuc = startAuc;
		this.finishAuc = finishAuc;
		this.state = state;
		this.delivery = delivery;
		this.category = category;
	}

	public int getSeller() {
		return seller;
	}

	public void setSeller(int seller) {
		this.seller = seller;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public double getStartBid() {
		return startBid;
	}

	public void setStartBid(double startBid) {
		this.startBid = startBid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public Date getStartReg() {
		return startReg;
	}

	public void setStartReg(Date startReg) {
		this.startReg = startReg;
	}

	public Date getFinishReg() {
		return finishReg;
	}

	public void setFinishReg(Date finishReg) {
		this.finishReg = finishReg;
	}

	public Date getStartAuc() {
		return startAuc;
	}

	public void setStartAuc(Date startAuc) {
		this.startAuc = startAuc;
	}

	public Date getFinishAuc() {
		return finishAuc;
	}

	public void setFinishAuc(Date finishAuc) {
		this.finishAuc = finishAuc;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getDelivery() {
		return delivery;
	}

	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}
	
	
}
