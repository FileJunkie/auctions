package ru.spbstu.students.dao;

import java.util.List;

import ru.spbstu.students.dto.UserInfo;

public interface UserDAO {
	
	public int insertUser(UserInfo user) throws Exception;
	public List<UserInfo> listUser();

}