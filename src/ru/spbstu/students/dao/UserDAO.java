package ru.spbstu.students.dao;

import java.util.List;

import ru.spbstu.students.dto.UserInfo;

public interface UserDAO {
	
	public String insertUser(UserInfo user) throws Exception;
	public List<UserInfo> listUser();
	public String activateUser(String key);
	public String loginUser(String email, String password) throws Exception;

}
