package ru.spbstu.students.dao;

import java.util.List;

import ru.spbstu.students.dto.ItemInfo;

public interface ItemDAO {
	public void addItem(ItemInfo item);
	public List<ItemInfo> getItems();
	public List<ItemInfo> getItems(int sellerID);
	public ItemInfo getItem(int itemID);
	public void removeItem(int itemID);
	public void editItem(int itemID);
}
