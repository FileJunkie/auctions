package ru.spbstu.students.dao;

import java.util.List;

import ru.spbstu.students.dao.querysupport.QuerySupport;

public class BlacklistDAOImpl extends QuerySupport implements BlacklistDAO {

	public void ban(String email) {
		Query ban = new Query("INSERT INTO blacklist(email) VALUES('").append(email).append("')");
		ban.execute();
	}

	public List<String> getBlackList() {
		Query q = new Query("SELECT email FROM blacklist");
		
		return q.list(new Fetcher<String> () {

			@Override
			protected String fetch() {
				return getString("email");
			}
		});
	}

	public boolean isBanned(String email) {
		Query isBanned = new Query("SELECT count(*) as c FROM blacklist ").append(where(eq("email", email)));
		return isBanned.fetch(new IntegerFetcher("c")) > 0;
	}

	public void unban(String email) {
		Query unban = new Query("DELETE FROM blacklist ").append(where(eq("email", email)));
		unban.execute();
	}

}
