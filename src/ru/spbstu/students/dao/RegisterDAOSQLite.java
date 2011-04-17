package ru.spbstu.students.dao;

import java.util.List;

import ru.spbstu.students.dao.querysupport.QuerySupport;

public class RegisterDAOSQLite extends QuerySupport implements RegisterDAO {

	public List<Integer> getItems(int userID) {
		Query q = new Query("SELECT item FROM register ").append(where(eq("user", userID)));
		
		return q.list(new Fetcher<Integer>(){
			
			@Override
			protected Integer fetch(){	
				return getInt("item");
			}
		});
	}

	public List<Integer> getUsers(int itemID) {
		Query q = new Query("SELECT user FROM register ").append(where(eq("item", itemID)));
		
		return q.list(new Fetcher<Integer>(){
			
			@Override
			protected Integer fetch(){	
				return getInt("user");
			}
		});
	}

	public String registerUser(int userID, int itemID) {
		Query q = new Query("INSERT INTO register(item, user) VALUES(").append(userID + ",").append(itemID + ")");		
		
		try{
			q.execute();
		}
		catch (Exception e) {
			return "sqlError";
		}
		
		return "success";
	}

	public void unregisterUser(int userID, int itemID) {
		Query q = new Query("DELETE FROM register ").append(where(and(eq("item",itemID),eq("user",userID))));
		q.execute();
	}

}
