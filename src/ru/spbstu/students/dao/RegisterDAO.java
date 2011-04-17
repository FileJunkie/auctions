package ru.spbstu.students.dao;

import java.util.List;

public interface RegisterDAO {
	public String registerUser(int userID, int itemID);
	public void unregisterUser(int userID, int itemID);
	public List<Integer> getItems(int userI);
	public List<Integer> getUsers(int itemID);
}
