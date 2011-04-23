package ru.spbstu.students.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.List;

import ru.spbstu.students.dao.querysupport.QuerySupport;
import ru.spbstu.students.dto.ItemInfo;
import ru.spbstu.students.web.AuctionTypes;

public class ItemDAOSQLite extends QuerySupport implements ItemDAO {
	static final DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
	
	public void addItem(ItemInfo item) {
		Query q = new Query("INSERT INTO items(seller, name, description, photo, start_bid, type, min," +
				" start_reg, finish_reg, start_auc, finish_auc, state, delivery, category) values(")
			.append(item.getSeller() + ",")
			.append("'" + item.getName() + "',")
			.append("'" + item.getDescription() + "',")
			.append(item.getPhoto() == null ? "null," : "'" + item.getPhoto() + "'," )
			.append(item.getStartBid() + ",")
			.append(AuctionTypes.getKeyByLabel(item.getType()) + ",")
			.append(item.getMin() == null ? "null," : item.getMin() + "," )
			.append("'" + df.format(item.getStartReg()) + "',")
			.append("'" + df.format(item.getFinishReg()) + "',")
			.append("'" + df.format(item.getStartAuc()) + "',")
			.append("'" + df.format(item.getFinishAuc()) + "',")
			.append(item.getState() + ",")
			.append(item.getDelivery() == null ? "null," : "'" + item.getDelivery() + "'," )
			.append(item.getCategory())
			.append(")");
		q.execute();
	}

	public void editItem(int itemID, ItemInfo item) {
		Query q = new Query("UPDATE items SET ")
			.append("seller=" + item.getSeller() + ",")
			.append("name=" + "'" + item.getName() + "',")
			.append("description=" + "'" + item.getDescription() + "',")
			.append("photo=" + (item.getPhoto() == null ? "null," : "'" + item.getPhoto() + "',") )
			.append("start_bid=" + item.getStartBid() + ",")
			.append("type=" + AuctionTypes.getKeyByLabel(item.getType()) + ",")
			.append("min=" + (item.getMin() == null ? "null," : item.getMin() + ",") )
			.append("start_reg=" + "'" + df.format(item.getStartReg()) + "',")
			.append("finish_reg=" + "'" + df.format(item.getFinishReg()) + "',")
			.append("start_auc=" + "'" + df.format(item.getStartAuc()) + "',")
			.append("finish_auc=" + "'" + df.format(item.getFinishAuc()) + "',")
			.append("state=" + item.getState() + ",")
			.append("delivery=" + (item.getDelivery() == null ? "null," : "'" + item.getDelivery() + "',") )
			.append("category=" + item.getCategory() + " ")
			.append(where(eq("id", itemID)));
		q.execute();
	}

	public ItemInfo getItem(int itemID) {
		Query q = new Query("SELECT i.id, i.seller, i.name, i.description, i.photo, " +
				" i.start_bid, t.name as type, i.min, i.start_reg, i.finish_reg, " +
				" i.start_auc, i.finish_auc, i.state, i.delivery, c.name as category FROM items i " +
				" join i_categories c on c.id = i.category " +
				" join i_types t on t.id = i.type ").append(where(eq("i.id",itemID)));
		
		return q.list(new Fetcher<ItemInfo>(){
			@Override
			protected ItemInfo fetch() {
				try {
					return new ItemInfo(getInt("id"), getInt("seller"), getString("name"), getString("description"), getString("photo"), getDouble("start_bid"), 
							getString("type"), new Double(getDouble("min")), df.parse(getString("start_reg")), df.parse(getString("finish_reg")), df.parse(getString("start_auc")), df.parse(getString("finish_auc")),
							getInt("state"), getString("delivery"), getString("category"));
				} catch (ParseException e) {
					return new ItemInfo(getInt("id"), getInt("seller"), getString("name"), getString("description"), getString("photo"), getDouble("start_bid"), 
							getString("type"), new Double(getDouble("min")), null, null, null, null,
							getInt("state"), getString("delivery"), getString("category"));
				}
			}			
		}).get(0);
	}

	public List<ItemInfo> getItems() {
		Query q = new Query("SELECT i.id, i.seller, i.name, i.description, i.photo, " +
				" i.start_bid, t.name as type, i.min, i.start_reg, i.finish_reg, " +
				" i.start_auc, i.finish_auc, i.state, i.delivery, c.name as category FROM items i " +
				" join i_categories c on c.id = i.category " +
				" join i_types t on t.id = i.type ");
		
		return q.list(new Fetcher<ItemInfo>(){
			@Override
			protected ItemInfo fetch() {
				try {
					return new ItemInfo(getInt("id"), getInt("seller"), getString("name"), getString("description"), getString("photo"), getDouble("start_bid"), 
							getString("type"), new Double(getDouble("min")), df.parse(getString("start_reg")), df.parse(getString("finish_reg")), df.parse(getString("start_auc")), df.parse(getString("finish_auc")),
							getInt("state"), getString("delivery"), getString("category"));
				} catch (ParseException e) {
					return new ItemInfo(getInt("id"), getInt("seller"), getString("name"), getString("description"), getString("photo"), getDouble("start_bid"), 
							getString("type"), new Double(getDouble("min")), null, null, null, null,
							getInt("state"), getString("delivery"), getString("category"));
				}
			}			
		});
	}

	public List<ItemInfo> getItems(int sellerID) {
		Query q = new Query("SELECT i.id, i.seller, i.name, i.description, i.photo, " +
				" i.start_bid, t.name as type, i.min, i.start_reg, i.finish_reg, " +
				" i.start_auc, i.finish_auc, i.state, i.delivery, c.name as category FROM items i " +
				" join i_categories c on c.id = i.category " +
				" join i_types t on t.id = i.type ").append(where(eq("i.seller", sellerID)));
		
		return q.list(new Fetcher<ItemInfo>(){
			@Override
			protected ItemInfo fetch() {
				try {
					return new ItemInfo(getInt("id"), getInt("seller"), getString("name"), getString("description"), getString("photo"), getDouble("start_bid"), 
							getString("type"), new Double(getDouble("min")), df.parse(getString("start_reg")), df.parse(getString("finish_reg")), df.parse(getString("start_auc")), df.parse(getString("finish_auc")),
							getInt("state"), getString("delivery"), getString("category"));
				} catch (ParseException e) {
					return new ItemInfo(getInt("id"), getInt("seller"), getString("name"), getString("description"), getString("photo"), getDouble("start_bid"), 
							getString("type"), new Double(getDouble("min")), null, null, null, null,
							getInt("state"), getString("delivery"), getString("category"));
				}
			}			
		});
	}

	public void removeItem(int itemID) {
		Query q = new Query("DELETE FROM items ").append(where(eq("id", itemID)));
		q.execute();
	}

}
