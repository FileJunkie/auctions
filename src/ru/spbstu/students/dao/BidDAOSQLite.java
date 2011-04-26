package ru.spbstu.students.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.List;

import ru.spbstu.students.dao.querysupport.QuerySupport;
import ru.spbstu.students.dto.BidInfo;

public class BidDAOSQLite extends QuerySupport implements BidDAO {

	static final DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
	
	public boolean addBid(BidInfo bid) {
		Query q = new Query("SELECT amount FROM bids ")
			.append(where(and(eq("item", bid.getItemID()),eq("user", bid.getUserID()))))
			.append(" ORDER BY amount DESC LIMIT 1");
		List<Double> max = q.list(new Fetcher<Double>(){
			@Override
			protected Double fetch(){
				return getDouble("amount");
			}
		});		
		
		if((!max.isEmpty())&&(max.get(0) > bid.getAmount())){
			return false;
		}
		
		q = new Query("INSERT INTO bids(item, user, time, amount) VALUES(")
			.append(bid.getItemID() + ",")
			.append(bid.getUserID() + ",")
			.append("'" + df.format(bid.getTime()) + "',")
			.append(bid.getAmount() + ")");
		q.execute();
		
		return true;
	}

	public List<BidInfo> getBids(int itemID) {
		Query q = new Query("SELECT * FROM bids ")
			.append(where(eq("item", itemID)));
		
		return q.list(new Fetcher<BidInfo>(){
			@Override
			protected BidInfo fetch() { 				
				try {
					return new BidInfo(getInt("item"), getInt("user"), getDouble("amount"), df.parse(getString("time")));
				} catch (ParseException e) {
					return new BidInfo(getInt("item"), getInt("user"), getDouble("amount"), null);
				}
			}
		});
	}

	public void removeBid(int bidID) {
		Query q = new Query("DELETE FROM bids ").append(where(eq("id", bidID)));
		q.execute();
	}

}
