package ru.spbstu.students.dto;

public class UserInfo {
	
	private String email;
	private String password;
	private Integer category;
	private Integer type;
	
	public UserInfo(String email, String password, Integer category, Integer type) {
		this.email = email;
		this.password = password;
		this.category = category;
		this.type = type;
	}
	
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
	public Integer getCategory() {
		return category;
	}
	public void setCategory(Integer category) {
		this.category = category;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	

}
