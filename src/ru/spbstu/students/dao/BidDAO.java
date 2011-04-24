package ru.spbstu.students.dao;

import java.util.List;

import ru.spbstu.students.dto.BidInfo;

public interface BidDAO {
	public boolean addBid(BidInfo bid);
	public void removeBid(int bidID);
	public List<BidInfo> getBids(int itemID);
}
