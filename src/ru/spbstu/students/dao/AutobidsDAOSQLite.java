package ru.spbstu.students.dao;

import ru.spbstu.students.dao.querysupport.QuerySupport;
import ru.spbstu.students.dto.AutobidInfo;

public class AutobidsDAOSQLite extends QuerySupport implements AutobidsDAO {

	public void addAutobid(AutobidInfo autobidInfo) {
		Query q = new Query("INSERT INTO  autobids(item, user, max, step) VALUES(")
			.append(autobidInfo.getItem() + ",")
			.append(autobidInfo.getUser() + ",")
			.append(autobidInfo.getMax() + ",")
			.append(autobidInfo.getStep() + ")");
		q.execute();
	}

	public void editAutobid(AutobidInfo autobidInfo) {
		Query q = new Query("UPDATE autobids SET ")
			.append("max=" + autobidInfo.getMax() + ",")
			.append("step=" + autobidInfo.getStep() + " ")
			.append(where(and(eq("item",autobidInfo.getItem()),eq("user",autobidInfo.getUser()))));
		q.execute();
	}

	public AutobidInfo getAutobid(int itemID, int userID) {
		Query q = new Query("SELECT item, user, max, step FROM autobids ").append(where(and(eq("item",itemID),eq("user",userID))));
		
		return q.list(new Fetcher<AutobidInfo>(){			
			@Override
			protected AutobidInfo fetch() {
				return new AutobidInfo(getInt("user"), getInt("item"), getDouble("max"), getDouble("step"));
			}
		}).get(0);		
	}

	public void removeAutobid(int itemID, int userID) {
		Query q = new Query("DELETE FROM autobids ").append(where(and(eq("item",itemID),eq("user",userID))));
		q.execute();
	}

}
