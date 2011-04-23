package ru.spbstu.students.dao;

import java.util.List;

import ru.spbstu.students.dto.ItemCategories;

public interface ItemCategoriesDAO {
	 List<ItemCategories> getCategories();
	 List<String> getCategoriesName();
	 String getCategory(int catID);
	 void addCaterogy(String name);
	 void removeCaterogy(int id);
	 void renameCategory(int id, String newname);
	 Integer getCategory(String name);
}
