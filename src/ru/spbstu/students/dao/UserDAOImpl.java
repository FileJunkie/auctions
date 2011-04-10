package ru.spbstu.students.dao;

import java.util.List;

import ru.spbstu.students.dao.querysupport.QuerySupport;

import ru.spbstu.students.dto.UserInfo;

public class UserDAOImpl extends QuerySupport implements UserDAO{

	public void insertUser(UserInfo user) {
				
	}

	public List<UserInfo> listUser() {
		Query q = new Query("select * from users");
		
		return q.list(new Fetcher<UserInfo> () {

			@Override
			protected UserInfo fetch() {
				return new UserInfo(getString("email"), getString("password"), 
						getInt("category"), getInt("type"));
			}
		});
	}
}
