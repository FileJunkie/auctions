package ru.spbstu.students.dto;

import java.util.Currency;

public class AutobidInfo {
	private int user;
	private int item;
	private Currency max;
	private Currency step;
	
	public AutobidInfo(int user, int item, Currency max, Currency step) {
		this.user = user;
		this.item = item;
		this.max = max;
		this.step = step;
	}

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

	public Currency getMax() {
		return max;
	}

	public void setMax(Currency max) {
		this.max = max;
	}

	public Currency getStep() {
		return step;
	}

	public void setStep(Currency step) {
		this.step = step;
	}	
}
