package ru.spbstu.students.dto;

public class UserInfo {
	
	private String email;
	private String password;
	private String category;
	private String type;
	
	private static int nextId = 1;
	
	public UserInfo(String email, String password, String category, String type) {
		this.email = email;
		this.password = password;
		this.category = category;
		this.type = type;
	}
	
	public UserInfo() {}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
