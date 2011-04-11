package ru.spbstu.students.dao;

import java.util.List;

import ru.spbstu.students.dao.querysupport.QuerySupport;
import ru.spbstu.students.dto.UserInfo;
import ru.spbstu.students.util.Util;
import ru.spbstu.students.web.Categories;
import ru.spbstu.students.web.Types;

public class UserDAOImpl extends QuerySupport implements UserDAO{

	public int insertUser(UserInfo user) throws Exception {
		if (user == null || !Util.isValidUser(user))
			return -1;
		
		int category, type, registered;
		
		Query isExist = new Query("select count(*) as c from users ").append(where(eq("email",user.getEmail())));
		registered = isExist.fetch(new IntegerFetcher("c"));
		
		if (registered == 0) {
			
			String pass = Util.getHashMd5(user.getPassword());
			category = Categories.getKeyByLabel(user.getCategory());
			type = Types.getKeyByLabel(user.getType());
			
			Query q = new Query("insert into users(email,password,category,type) values (")
			.append("'" + user.getEmail() + "',")
			.append("'" + pass + "',")
			.append(category + ",")
			.append(type + ")");
			
			q.execute();
			
			Util.sendActivationMail(user.getEmail());
			
			return 0;
		} else {
			return -1;
		}
	}

	public List<UserInfo> listUser() {
		Query q = new Query("select  u.email as email, u.password,  c.name as category, t.name as type" +
				" from users u " +
				" join u_categories c on c.id = u.category " +
				" join u_types t on t.id = u.type");
		
		return q.list(new Fetcher<UserInfo> () {

			@Override
			protected UserInfo fetch() {
				return new UserInfo(getString("email"), getString("password"), 
						getString("category"), getString("type"));
			}
		});
	}
}
