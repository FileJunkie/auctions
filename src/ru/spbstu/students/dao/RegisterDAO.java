package ru.spbstu.students.dao;

import java.util.List;

public interface RegisterDAO {
	public void registerUser(int userID, int itemID);
	public void unregisterUser(int userID, int itemID);
	public List<Integer> getItems(int itemID);
	public List<Integer> getUsers(int userID);
}
