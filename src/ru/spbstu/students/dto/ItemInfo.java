package ru.spbstu.students.dto;

import java.util.Date;

public class ItemInfo {
	private int seller;
	private String name;
	private String description;
	private String photo;
	private double startBid;
	private int type;
	private Double min;
	private Date startReg;
	private Date finishReg;
	private Date startAuc;
	private Date finishAuc;
	private int state;
	private String delivery;
	private int category;
	private int id;
	
	public ItemInfo(int id, int seller, String name, String description, String photo,
			double startBid, int type, Double min, Date startReg,
			Date finishReg, Date startAuc, Date finishAuc, int state,
			String delivery, int category) {
		this.id = id;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Double getMin() {
		return min;
	}

	public void setMin(Double min) {
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
