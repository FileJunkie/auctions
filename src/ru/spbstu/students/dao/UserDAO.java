package ru.spbstu.students.dao;

import java.util.List;

import ru.spbstu.students.dto.UserInfo;

public interface UserDAO {
	
	public void insertUser(UserInfo user);
	public List<UserInfo> listUser();

}
