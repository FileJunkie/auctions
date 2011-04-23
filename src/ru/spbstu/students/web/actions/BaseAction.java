package ru.spbstu.students.web.actions;

import java.util.List;

import ru.spbstu.students.web.AuctionTypes;
import ru.spbstu.students.web.LoggedUser;
import ru.spbstu.students.web.UserCategories;
import ru.spbstu.students.web.UserTypes;

import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4044915638702787675L;
	
	private static List<String> categoriesLabels = UserCategories.labelList();
	private static List<String> typesLabels = UserTypes.labelList();
	private static List<String> auctionTypes = AuctionTypes.labelList();
	private LoggedUser loggedUser;
	
	public List<String> getCategoriesLabels() {
		return categoriesLabels;
	}

	public List<String> getTypesLabels() {
		return typesLabels;
	}

	public List<String> getAuctionTypes() {
		return auctionTypes;
	}
	
	public String getDefaultTypeValue(){
        return "English";
    }

	public LoggedUser getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(LoggedUser loggedUser) {
		this.loggedUser = loggedUser;
	}
}
