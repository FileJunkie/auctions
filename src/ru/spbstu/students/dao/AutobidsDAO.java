package ru.spbstu.students.dao;

import ru.spbstu.students.dto.AutobidInfo;

public interface AutobidsDAO {
	public void addAutobid(AutobidInfo autobidInfo);
	public void editAutobid(AutobidInfo autobidInfo);
	public void removeAutobid(AutobidInfo autobidInfo);
	public AutobidInfo getAutobid(int itemID, int userID);
}
