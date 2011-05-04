package ru.spbstu.students.dto;

public class AutobidInfo {
	private int user;
	private int item;
	private double max;
	private double step;
	
	public AutobidInfo(int user, int item, double max, double step) {
		this.user = user;
		this.item = item;
		this.max = max;
		this.step = step;
	}

	public AutobidInfo() {}

	public int getUser() {
		return user;
	}

	public void setUser(int user) {
		this.user = user;
	}

	public int getItem() {
		return item;
	}

	public void setItem(int item) {
		this.item = item;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public double getStep() {
		return step;
	}

	public void setStep(double step) {
		this.step = step;
	}	
}
