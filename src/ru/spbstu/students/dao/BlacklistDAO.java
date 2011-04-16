package ru.spbstu.students.dao;

import java.util.List;

public interface BlacklistDAO {
	public List<String> getBlackList();
	public boolean isBanned(String email);
	public void ban(String email);
	public void unban(String email);
}
