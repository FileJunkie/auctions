package ru.spbstu.students.dto;

public class BannedEmail {
	
	private String email;
	private int id;
	
	public BannedEmail(int id, String email) {
		this.id = id;
		this.email = email;
	}
	
	public BannedEmail() {	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
