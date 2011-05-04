package ru.spbstu.students.dao;

import java.util.List;

import ru.spbstu.students.dto.AutobidInfo;

public interface AutobidsDAO {
	public void addAutobid(AutobidInfo autobidInfo);
	public void editAutobid(AutobidInfo autobidInfo);
	public void removeAutobid(int itemID, int userID);
	public AutobidInfo getAutobid(int itemID, int userID);
	public List<AutobidInfo> getAutobidList(int itemID);
}
