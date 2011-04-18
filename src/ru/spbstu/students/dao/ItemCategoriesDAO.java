package ru.spbstu.students.dao;

import java.util.List;

public interface ItemCategoriesDAO {
	 public List<String> getCategories();
	 public String getCategory(int catID);
	 public void addCaterogy(String name);
	 public void removeCaterogy(String name);
	 public void renameCategory(String oldname, String newname);
}
