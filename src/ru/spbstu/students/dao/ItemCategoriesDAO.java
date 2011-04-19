package ru.spbstu.students.dao;

import java.util.List;

import ru.spbstu.students.dto.ItemCategories;

public interface ItemCategoriesDAO {
	 public List<ItemCategories> getCategories();
	 public String getCategory(int catID);
	 public void addCaterogy(String name);
	 public void removeCaterogy(int id);
	 public void renameCategory(int id, String newname);
}
