package ru.spbstu.students.dao;

import java.util.List;

import ru.spbstu.students.dto.BannedEmail;

public interface BlacklistDAO {
	public List<BannedEmail> getBlackList();
	public boolean isBanned(String email);
	public void ban(String email);
	public void unban(int id);
}
