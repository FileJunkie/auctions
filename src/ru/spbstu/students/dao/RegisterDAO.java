package ru.spbstu.students.dao;

import java.util.List;

import ru.spbstu.students.dto.UserInfo;
import ru.spbstu.students.dto.ItemInfo;

public interface RegisterDAO {
	public void registerUser(int userID, int itemID);
	public void unregisterUser(int userID, int itemID);
	public List<UserInfo> getItems(int itemID);
	public List<ItemInfo> getUsers(int userID);
}
