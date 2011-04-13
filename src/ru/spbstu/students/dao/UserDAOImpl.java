package ru.spbstu.students.dao;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import ru.spbstu.students.dao.querysupport.QuerySupport;
import ru.spbstu.students.dto.UserInfo;
import ru.spbstu.students.util.Util;
import ru.spbstu.students.web.Categories;
import ru.spbstu.students.web.Types;

public class UserDAOImpl extends QuerySupport implements UserDAO{

	public String insertUser(UserInfo user) throws Exception {
		if (user == null || !Util.isValidUser(user))
			return "error";
		
		int category, type, registered, active;
		
		Query isExist = new Query("select count(*) as c from users ").append(where(eq("email",user.getEmail())));
		registered = isExist.fetch(new IntegerFetcher("c"));
		
		if (registered == 0) {
			
			String pass = Util.getHashMd5(user.getPassword());
			category = Categories.getKeyByLabel(user.getCategory());
			type = Types.getKeyByLabel(user.getType());
			active = 0;
			String key = Util.getHashMd5(user.getEmail());
			
			Query q = new Query("insert into users(email,password,category,type,active,key) values (")
			.append("'" + user.getEmail() + "',")
			.append("'" + pass + "',")
			.append(category + ",")
			.append(type + ",")
			.append(active + ",")
			.append("'" + key + "')");
			
			q.execute();
			
			Util.sendActivationMail(user.getEmail(), key);
			
			return "success";
		} else {
			return "error";
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

	public String activateUser(String key) {
		
		if ((key == null) || (key.trim().length() == 0)) {
			return "error";
		}
		
		Query q = new Query("update users set active = 1 ").append(where(eq("key", key)));
		q.execute();
		return "success";
	}
	
	public String loginUser (String email, String password) throws Exception {
		if ((email == null) || (email.trim().length() == 0) || 
				(password == null) || (password.trim().length() == 0)) {
			return "error";
		}
		
		int registered;
		String pass = Util.getHashMd5(password);
		Query isExist = new Query("select count(*) as c from users ").append(where(and(eq("email",email),eq("password",pass))));
		registered = isExist.fetch(new IntegerFetcher("c"));
		if (registered == 1) {
			return "success";
		} else {
			return "error";
		}
	}
}
