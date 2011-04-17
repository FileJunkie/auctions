package ru.spbstu.students.dto;

public class UserInfo {
	
	private String email;
	private String password;
	private String category;
	private String type;
	private boolean admin;
	private boolean active;
	private int id;
	
	public UserInfo(int id, String email, String password, String category, String type, boolean admin, boolean active) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.category = category;
		this.type = type;
		this.admin = admin;
		this.active = active;
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
	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
