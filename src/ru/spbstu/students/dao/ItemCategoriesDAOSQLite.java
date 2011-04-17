package ru.spbstu.students.dao;

import java.util.List;

import ru.spbstu.students.dao.querysupport.QuerySupport;

public class ItemCategoriesDAOSQLite extends QuerySupport implements
		ItemCategoriesDAO {

	public void addCaterogy(String name) {
		Query q = new Query("INSERT INTO i_categories(name) VALUES('").append(name).append("')");
		q.execute();
	}

	public List<String> getCategories() {
		Query q = new Query("SELECT name FROM i_categories");
		
		return q.list(new Fetcher<String>(){
			@Override
			protected String fetch() {
				return getString("name");
			}						
		});
	}

	public void removeCaterogy(String name) {
		Query q = new Query("DELETE FROM i_categories ").append(where(eq("name",name)));
		q.execute();
	}

	public void renameCategory(String oldname, String newname) {
		Query q = new Query("UPDATE i_categories SET name='").append(newname).append("' ").append(where(eq("name", oldname)));
		q.execute();
	}

}
