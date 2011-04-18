package ru.spbstu.students.dao;

import java.util.List;

import ru.spbstu.students.dao.querysupport.QuerySupport;
import ru.spbstu.students.dto.ItemInfo;

public class ItemDAOSQLite extends QuerySupport implements ItemDAO {

	public void addItem(ItemInfo item) {
		Query q = new Query("INSERT INTO items(seller, name, description, photo, start_bid, type, min," +
				" start_reg, finish_reg, start_auc, finish_auc, state, delivery, category) values(")
			.append(item.getSeller() + ",")
			.append("'" + item.getName() + "',")
			.append("'" + item.getDescription() + "',")
			.append(item.getPhoto() == null ? "null," : "'" + item.getPhoto() + "'," )
			.append(item.getStartBid() + ",")
			.append(item.getType() + ",")
			.append(item.getMin() == null ? "null," : item.getMin() + "," )
			.append("'" + item.getStartReg() + "',")
			.append("'" + item.getFinishReg() + "',")
			.append("'" + item.getStartAuc() + "',")
			.append("'" + item.getFinishAuc() + "',")
			.append(item.getState() + ",")
			.append(item.getDelivery() == null ? "null," : "'" + item.getDelivery() + "'," )
			.append((new Integer(item.getCategory())).toString())
			.append(")");
		q.execute();
	}

	public void editItem(int itemID, ItemInfo item) {
		Query q = new Query("UPDATE items SET ")
			.append("seller=" + item.getSeller() + ",")
			.append("name=" + "'" + item.getName() + "',")
			.append("description=" + "'" + item.getDescription() + "',")
			.append("photo=" + item.getPhoto() == null ? "null," : "'" + item.getPhoto() + "'," )
			.append("start_bid=" + item.getStartBid() + ",")
			.append("type=" + item.getType() + ",")
			.append("min=" + item.getMin() == null ? "null," : item.getMin() + "," )
			.append("start_reg=" + "'" + item.getStartReg() + "',")
			.append("finish_reg=" + "'" + item.getFinishReg() + "',")
			.append("start_auc=" + "'" + item.getStartAuc() + "',")
			.append("finish_auc=" + "'" + item.getFinishAuc() + "',")
			.append("state=" + item.getState() + ",")
			.append("delivery=" + item.getDelivery() == null ? "null," : "'" + item.getDelivery() + "'," )
			.append("category=" + item.getCategory() + " ")
			.append(where(eq("id", itemID)));
		q.execute();
	}

	public ItemInfo getItem(int itemID) {
		Query q = new Query("SELECT * FROM items ").append(where(eq("id",itemID)));
		
		return q.list(new Fetcher<ItemInfo>(){
			@Override
			protected ItemInfo fetch() {
				return new ItemInfo(getInt("seller"), getString("name"), getString("description"), getString("photo"), getDouble("start_bid"), 
						getInt("type"), new Double(getDouble("min")), getDate("start_reg"), getDate("finish_reg"), getDate("start_auc"), getDate("finish_auc"),
						getInt("state"), getString("delivery"), getInt("category"));
			}			
		}).get(0);
	}

	public List<ItemInfo> getItems() {
		Query q = new Query("SELECT * FROM items");
		
		return q.list(new Fetcher<ItemInfo>(){
			@Override
			protected ItemInfo fetch() {
				return new ItemInfo(getInt("seller"), getString("name"), getString("description"), getString("photo"), getDouble("start_bid"), 
						getInt("type"), new Double(getDouble("min")), getDate("start_reg"), getDate("finish_reg"), getDate("start_auc"), getDate("finish_auc"),
						getInt("state"), getString("delivery"), getInt("category"));
			}			
		});
	}

	public List<ItemInfo> getItems(int sellerID) {
		Query q = new Query("SELECT * FROM items").append(where(eq("seller", sellerID)));
		
		return q.list(new Fetcher<ItemInfo>(){
			@Override
			protected ItemInfo fetch() {
				return new ItemInfo(getInt("seller"), getString("name"), getString("description"), getString("photo"), getDouble("start_bid"), 
						getInt("type"), new Double(getDouble("min")), getDate("start_reg"), getDate("finish_reg"), getDate("start_auc"), getDate("finish_auc"),
						getInt("state"), getString("delivery"), getInt("category"));
			}			
		});
	}

	public void removeItem(int itemID) {
		Query q = new Query("DELETE FROM items ").append(where(eq("id", itemID)));
		q.execute();
	}

}
