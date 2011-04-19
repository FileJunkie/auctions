package ru.spbstu.students.dto;

public class ItemCategories {
	
	private String name;
	private int id;
	
	public ItemCategories(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public ItemCategories() {}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

}
