package ru.spbstu.students.web.actions;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import ru.spbstu.students.dao.BidDAO;
import ru.spbstu.students.dao.ItemCategoriesDAO;
import ru.spbstu.students.dao.ItemDAO;
import ru.spbstu.students.dao.RegisterDAO;
import ru.spbstu.students.dao.UserDAO;
import ru.spbstu.students.dto.BidInfo;
import ru.spbstu.students.dto.ItemInfo;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

public class ItemAction extends BaseAction implements SessionAware, ModelDriven<ItemInfo> {
	
	private static final long serialVersionUID = 9214786442552920670L;
	private ItemInfo item = new ItemInfo();
	private Map<String, Object> session; 
	private ItemDAO itemDao;
	private ItemCategoriesDAO itemCategories;
	private UserDAO userDao;
	private List<String> categoryList;
	private List<ItemInfo> itemList;
	private RegisterDAO registerDao;
	
	public String addItem() {
		if (!session.containsKey("email"))
			return ERROR;
		categoryList = itemCategories.getCategoriesName();
		return SUCCESS;
	}
	
	public String insertItem() {
		
		if (!session.containsKey("email"))
			return ERROR;
		
		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
		String pathToPhoto = new String();
		try {
            String filePath = request.getSession().getServletContext().getRealPath("/");
            File fileToCreate = new File(filePath + "cache/", item.getImage().getName());
            FileUtils.copyFile(item.getImage(), fileToCreate);
            pathToPhoto = "cache/" + item.getImage().getName();
        } catch (Exception e) {
            e.printStackTrace();
            addActionError(e.getMessage());
        }		
		try {
			item.setPhoto(pathToPhoto);
			item.setSeller(userDao.getUser((String)session.get("email")).getId());
			item.setCategory(itemCategories.getCategory(item.getCategory()).toString());
			item.setState(1);
			itemDao.addItem(item);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
	public String getItemsList() {
		
		if (!session.containsKey("email"))
			return ERROR;
		try {
			itemList = itemDao.getItems();
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}	
	}
	
	public String detailsItem() {
		if (!session.containsKey("email"))
			return ERROR;
		
		if (session.containsKey("startAuc")) {
			session.remove("startAuc");
			session.remove("finishAuc");
			session.remove("startReg");
			session.remove("finishReg");
		}
		
		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
		int id = Integer.parseInt(request.getParameter("itemId"));
		item = itemDao.getItem(id);
		if (item != null) {
			int userId = userDao.getUser((String)session.get("email")).getId();
			if (registerDao.getItems(userId).contains(id))
				session.put("isRegistered", true);
			session.put("startAuc", item.getStartAuc());
			session.put("finishAuc", item.getFinishAuc());
			session.put("startReg", item.getStartReg());
			session.put("finishReg", item.getFinishReg());
			return SUCCESS;
		} else {
			return ERROR;
		}
	}
	
	public String editItemInfo() {
		
		if (!session.containsKey("email"))
			return ERROR;
		
		int userId = userDao.getUser((String)session.get("email")).getId();
		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
		int itemId = Integer.parseInt(request.getParameter("itemId"));
		item = itemDao.getItem(itemId);
		if (item.getSeller() != userId) {
			return ERROR;
		}
		
		if (item != null) {
			session.put ( "editItemId", itemId); 
			categoryList = itemCategories.getCategoriesName();
			return SUCCESS;
		} else {
			return ERROR;
		}
	}
	
	public String updateItemInfo() {
		if (!session.containsKey("email"))
			return ERROR;
		
		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
		String pathToPhoto = new String();
		try {
            String filePath = request.getSession().getServletContext().getRealPath("/");
            File fileToCreate = new File(filePath + "cache/", item.getImage().getName());
            FileUtils.copyFile(item.getImage(), fileToCreate);
            pathToPhoto = "cache/" + item.getImage().getName();
        } catch (Exception e) {
            e.printStackTrace();
            addActionError(e.getMessage());
        }		
		try {
			item.setPhoto(pathToPhoto);
			item.setSeller(userDao.getUser((String)session.get("email")).getId());
			item.setCategory(itemCategories.getCategory(item.getCategory()).toString());
			item.setState(1);
			Integer id = (Integer) session.get("editItemId");
			session.remove("editItemId");
			itemDao.editItem(id, item);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
	public String removeItem() {
		if (!session.containsKey("email"))
			return ERROR;
		
		int userId = userDao.getUser((String)session.get("email")).getId();
		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
		int itemId = Integer.parseInt(request.getParameter("itemId"));
		item = itemDao.getItem(itemId);
		if (item.getSeller() != userId) {
			return ERROR;
		}
		
		try {
            String filePath = request.getSession().getServletContext().getRealPath("/");
            File fileToDel = new File(filePath + item.getPhoto());
            FileUtils.forceDelete(fileToDel);
            itemDao.removeItem(itemId);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            addActionError(e.getMessage());
            return ERROR;
        }	
	}
	
	public String registerIn(){
		if (!session.containsKey("email"))
			return ERROR;
		
		int userId = userDao.getUser((String)session.get("email")).getId();
		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
		int itemId = Integer.parseInt(request.getParameter("itemId"));
		if(!itemDao.registerIn(itemId, userId)){
			return ERROR;
		}
		
		return SUCCESS;
	}
	
	public String unregisterIn(){
		if (!session.containsKey("email"))
			return ERROR;
		
		int userId = userDao.getUser((String)session.get("email")).getId();
		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
		int itemId = Integer.parseInt(request.getParameter("itemId"));
		
		itemDao.unregisterIn(itemId, userId);
		
		return SUCCESS;
	}
	
	public void setSession(Map<String, Object> session) {
		this.session = session; 
	}
	
	public ItemInfo getModel() {
		return item;
	}

	public ItemInfo getItem() {
		return item;
	}

	public void setItem(ItemInfo item) {
		this.item = item;
	}

	public void setItemDao(ItemDAO itemDao) {
		this.itemDao = itemDao;
	}
	
	public void setItemCategories(ItemCategoriesDAO itemCategories) {
		this.itemCategories = itemCategories;
	}
	
	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

	public List<String> getCategoryList() {
		return categoryList;
	}

	public List<ItemInfo> getItemList() {
		return itemList;
	}

	public void setItemList(List<ItemInfo> itemList) {
		this.itemList = itemList;
	}

	public void setRegisterDao(RegisterDAO registerDao) {
		this.registerDao = registerDao;
	}
}
