package ru.spbstu.students.web.actions;

import java.util.List;

import ru.spbstu.students.web.Categories;
import ru.spbstu.students.web.Types;

import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4044915638702787675L;
	
	private static List<String> categoriesLabels = Categories.labelList();
	private static List<String> typesLabels = Types.labelList();
	
	public List<String> getCategoriesLabels() {
		return categoriesLabels;
	}

	public List<String> getTypesLabels() {
		return typesLabels;
	}
}
