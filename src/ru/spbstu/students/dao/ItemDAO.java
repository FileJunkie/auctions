package ru.spbstu.students.dao;

import java.util.List;

import ru.spbstu.students.dto.ItemInfo;

public interface ItemDAO {
	public void addItem(ItemInfo item);
	public List<ItemInfo> getItems();
	public List<ItemInfo> getItems(int sellerID);
	public ItemInfo getItem(int itemID);
	public void removeItem(int itemID);
	public void editItem(int itemID, ItemInfo item);
	public boolean registerIn(int itemID, int userID);
	public void unregisterIn(int itemID, int userID);
	public List<ItemInfo> getBuyerItems(int buyerID);
	public int getWinner(int itemID);
}
