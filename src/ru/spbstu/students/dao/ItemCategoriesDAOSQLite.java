package ru.spbstu.students.dao;

import java.util.List;

import ru.spbstu.students.dao.querysupport.QuerySupport;
import ru.spbstu.students.dto.ItemCategories;

public class ItemCategoriesDAOSQLite extends QuerySupport implements
		ItemCategoriesDAO {

	public void addCaterogy(String name) {
		Query q = new Query("INSERT INTO i_categories(name) VALUES('").append(name).append("')");
		q.execute();
	}

	public List<ItemCategories> getCategories() {
		Query q = new Query("SELECT id, name FROM i_categories");
		
		return q.list(new Fetcher<ItemCategories>(){
			@Override
			protected ItemCategories fetch() {
				return new ItemCategories(getInt("id"), getString("name"));
			}						
		});
	}
	
	public List<String> getCategoriesName() {
		Query q = new Query("SELECT name FROM i_categories");
		
		return q.list(new Fetcher<String>(){
			@Override
			protected String fetch() {
				return getString("name");
			}						
		});
	}

	public void removeCaterogy(int id) {
		Query q = new Query("DELETE FROM i_categories ").append(where(eq("id",id)));
		q.execute();
	}

	public void renameCategory(int id, String newname) {
		Query q = new Query("UPDATE i_categories SET name='").append(newname).append("' ").append(where(eq("id", id)));
		q.execute();
	}

	public String getCategory(int catID) {
		Query q = new Query("SELECT name FROM i_categories ").append(where(eq("id", catID)));
		
		return q.list(new Fetcher<String>(){
			@Override
			protected String fetch() {
				return getString("name");
			}						
		}).get(0);
	}
	
	public Integer getCategory(String name) {
		Query q = new Query("SELECT id FROM i_categories ").append(where(eq("name", name)));
		
		return q.list(new Fetcher<Integer>(){
			@Override
			protected Integer fetch() {
				return getInt("id");
			}						
		}).get(0);
	}

}
