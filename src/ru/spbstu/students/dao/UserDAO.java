package ru.spbstu.students.dao;

import java.util.List;

import ru.spbstu.students.dto.UserInfo;

public interface UserDAO {
	
	public String addUser(UserInfo user) throws Exception;
	public List<UserInfo> getUsers();
	public String activateUser(String key);
	public String loginUser(UserInfo user) throws Exception;
	public boolean isAdmin(String email);
	public UserInfo getUser(int id);
	public UserInfo getUser(String email);
	public String editUser(UserInfo user) throws Exception;
	public void removeUser(int id);
	
}
