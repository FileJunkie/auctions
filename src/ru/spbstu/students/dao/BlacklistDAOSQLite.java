package ru.spbstu.students.dao;

import java.util.List;

import ru.spbstu.students.dao.querysupport.QuerySupport;
import ru.spbstu.students.dto.BannedEmail;

public class BlacklistDAOSQLite extends QuerySupport implements BlacklistDAO {

	public void ban(String email) {
		Query ban = new Query("INSERT INTO blacklist(email) VALUES('").append(email).append("')");
		ban.execute();
	}

	public List<BannedEmail> getBlackList() {
		Query q = new Query("SELECT id, email FROM blacklist");
		
		return q.list(new Fetcher<BannedEmail> () {

			@Override
			protected BannedEmail fetch() {
				return new BannedEmail(getInt("id"),getString("email"));
			}
		});
	}

	public boolean isBanned(String email) {
		Query isBanned = new Query("SELECT count(*) as c FROM blacklist ").append(where(eq("email", email)));
		return isBanned.fetch(new IntegerFetcher("c")) > 0;
	}

	public void unban(int id) {
		Query unban = new Query("DELETE FROM blacklist ").append(where(eq("id", id)));
		unban.execute();
	}

}
