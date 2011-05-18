package ru.spbstu.students.web.actions;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.jdbc.core.JdbcTemplate;

import ru.spbstu.students.dao.ItemCategoriesDAO;
import ru.spbstu.students.dao.ItemDAO;
import ru.spbstu.students.dao.RegisterDAO;
import ru.spbstu.students.dao.UserDAO;
import ru.spbstu.students.dto.BidInfo;
import ru.spbstu.students.dto.ItemInfo;
import ru.spbstu.students.util.CounterThread;
import ru.spbstu.students.web.UserCategories;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

public class ItemAction extends BaseAction implements SessionAware, ModelDriven<ItemInfo> {
	
	private static final Logger log = Logger.getLogger(ItemAction.class);
	
	private static final long serialVersionUID = 9214786442552920670L;
	private ItemInfo item = new ItemInfo();
	private Map<String, Object> session; 
	private ItemDAO itemDao;
	private ItemCategoriesDAO itemCategories;
	private UserDAO userDao;
	private List<String> categoryList;
	private List<ItemInfo> itemList;
	private RegisterDAO registerDao;
	private String winner;
	private DataSource dataSource;
	
	public String addItem() {
		if (!session.containsKey("email"))
			return ERROR;
		int requestId = new Random().nextInt(Integer.MAX_VALUE);
		
		log.info("Start  addItem transaction. Request ID: " + requestId + "User ID: " + userDao.getUser((String)session.get("email")).getId() + "User category: " + userDao.getUser(userDao.getUser((String)session.get("email")).getId()).getCategory());
		CounterThread.inc(UserCategories.getByLabel(userDao.getUser(userDao.getUser((String)session.get("email")).getId()).getCategory()));
		
		categoryList = itemCategories.getCategoriesName();
		
		log.info("Finish addItem transaction, request ID: " + requestId);
		CounterThread.dec(UserCategories.getByLabel(userDao.getUser(userDao.getUser((String)session.get("email")).getId()).getCategory()));
		
		return SUCCESS;
	}
	
	public String insertItem() {
		
		if (!session.containsKey("email"))
			return ERROR;
		
		int requestId = new Random().nextInt(Integer.MAX_VALUE);
		
		log.info("Start  insertItem transaction. Request ID: " + requestId + "User ID: " + userDao.getUser((String)session.get("email")).getId() + "User category: " + userDao.getUser(userDao.getUser((String)session.get("email")).getId()).getCategory());
		CounterThread.inc(UserCategories.getByLabel(userDao.getUser(userDao.getUser((String)session.get("email")).getId()).getCategory()));
		
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
			log.info("Finish insertItem transaction, request ID: " + requestId);
			CounterThread.dec(UserCategories.getByLabel(userDao.getUser(userDao.getUser((String)session.get("email")).getId()).getCategory()));
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Finish insertItem transaction, request ID: " + requestId);
			CounterThread.dec(UserCategories.getByLabel(userDao.getUser(userDao.getUser((String)session.get("email")).getId()).getCategory()));
			return ERROR;
		}
	}
	
	public String getItemsList() {
		
		if (!session.containsKey("email"))
			return ERROR;
		int requestId = new Random().nextInt(Integer.MAX_VALUE);
		
		log.info("Start  getItemsList transaction. Request ID: " + requestId + "User ID: " + userDao.getUser((String)session.get("email")).getId() + "User category: " + userDao.getUser(userDao.getUser((String)session.get("email")).getId()).getCategory());
		CounterThread.inc(UserCategories.getByLabel(userDao.getUser(userDao.getUser((String)session.get("email")).getId()).getCategory()));
		
		try {
			itemList = itemDao.getItems();
			log.info("Finish getItemsList transaction, request ID: " + requestId);
			CounterThread.dec(UserCategories.getByLabel(userDao.getUser(userDao.getUser((String)session.get("email")).getId()).getCategory()));
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Finish getItemsList transaction, request ID: " + requestId);
			CounterThread.dec(UserCategories.getByLabel(userDao.getUser(userDao.getUser((String)session.get("email")).getId()).getCategory()));
			return ERROR;
		}	
	}
	
	public String getItemsListNotReg() {
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
		
		int requestId = new Random().nextInt(Integer.MAX_VALUE);
		
		log.info("Start  detailsItem transaction. Request ID: " + requestId + "User ID: " + userDao.getUser((String)session.get("email")).getId() + "User category: " + userDao.getUser(userDao.getUser((String)session.get("email")).getId()).getCategory());
		CounterThread.inc(UserCategories.getByLabel(userDao.getUser(userDao.getUser((String)session.get("email")).getId()).getCategory()));
		
		int id = 0;
		try {
			HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
			id = Integer.parseInt(request.getParameter("itemId"));
			session.put("itemId", id);
		} catch (Exception e) {
			id = (Integer) session.get("itemId");
		}
		
		item = itemDao.getItem(id);
		log.info("Finish detailsItem transaction, request ID: " + requestId);
		CounterThread.dec(UserCategories.getByLabel(userDao.getUser(userDao.getUser((String)session.get("email")).getId()).getCategory()));
		if (item != null) {
			return SUCCESS;
		} else {
			return ERROR;
		}
	}
	
	public String updateDetails() {
		if (!session.containsKey("email"))
			return ERROR;
		
		Calendar cal = Calendar.getInstance();
		int requestId = new Random().nextInt(Integer.MAX_VALUE);
		
		int userId = userDao.getUser((String)session.get("email")).getId();
		log.info("Start  updateDetails transaction. Request ID: " + requestId + "User ID: " + userId + "User category: " + userDao.getUser(userId).getCategory());
		CounterThread.inc(UserCategories.getByLabel(userDao.getUser(userId).getCategory()));
		
		if (session.containsKey("startAuc")) {
			session.remove("startAuc");
			session.remove("finishAuc");
			session.remove("startReg");
			session.remove("finishReg");
			session.remove("aucState");
		}
		
		int id = 0;
		try {
			HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
			id = Integer.parseInt(request.getParameter("itemId"));
			session.put("itemId", id);
		} catch (Exception e) {
			id = (Integer) session.get("itemId");
		}
		
		item = itemDao.getItem(id);
		if (item != null) {
			if (registerDao.getItems(userId).contains(id))
				session.put("isRegistered", true);
			else 
				session.put("isRegistered", false);
			session.put("startAuc", item.getStartAuc());
			session.put("finishAuc", item.getFinishAuc());
			session.put("startReg", item.getStartReg());
			session.put("finishReg", item.getFinishReg());
			session.put("aucState", item.getState());
			
			if ((item.getState() != 2)&&(item.getFinishAuc().compareTo(cal.getTime()) < 0)) {
				item.setState(2);
				JdbcTemplate edit = new JdbcTemplate(dataSource);
				edit.execute("UPDATE items SET state = 2 where id = " + id);
				session.remove("aucState");
				session.put("aucState", 2);
			}
			
			if (item.getState() == 2) {
				String win = itemDao.getWinner(id);
				if (win != null) {
					winner = userDao.getUser(win).getEmail();
				}
			}
			log.info("Finish updateDetails transaction, request ID: " + requestId);
			CounterThread.dec(UserCategories.getByLabel(userDao.getUser(userId).getCategory()));
			return SUCCESS;
		} else {
			log.info("Finish updateDetails transaction, request ID: " + requestId);
			CounterThread.dec(UserCategories.getByLabel(userDao.getUser(userId).getCategory()));
			return ERROR;
		}
	}
	
	public String editItemInfo() {
		
		if (!session.containsKey("email"))
			return ERROR;
		
		int userId = userDao.getUser((String)session.get("email")).getId();
		
		int requestId = new Random().nextInt(Integer.MAX_VALUE);
		
		log.info("Start  editItemInfo transaction. Request ID: " + requestId + "User ID: " + userId + "User category: " + userDao.getUser(userDao.getUser((String)session.get("email")).getId()).getCategory());
		CounterThread.inc(UserCategories.getByLabel(userDao.getUser(userId).getCategory()));
		
		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
		int itemId = Integer.parseInt(request.getParameter("itemId"));
		item = itemDao.getItem(itemId);
		if (item.getSeller() != userId) {
			log.info("Finish editItemInfo transaction, request ID: " + requestId);
			CounterThread.dec(UserCategories.getByLabel(userDao.getUser(userId).getCategory()));
			return ERROR;
		}
		
		if (item != null) {
			session.put ( "editItemId", itemId); 
			categoryList = itemCategories.getCategoriesName();
			log.info("Finish editItemInfo transaction, request ID: " + requestId);
			CounterThread.dec(UserCategories.getByLabel(userDao.getUser(userId).getCategory()));
			return SUCCESS;
		} else {
			log.info("Finish editItemInfo transaction, request ID: " + requestId);
			CounterThread.dec(UserCategories.getByLabel(userDao.getUser(userId).getCategory()));
			return ERROR;
		}
	}
	
	public String updateItemInfo() {
		if (!session.containsKey("email"))
			return ERROR;
		
		int requestId = new Random().nextInt(Integer.MAX_VALUE);
		
		int userId = userDao.getUser((String)session.get("email")).getId();
		log.info("Start  updateItemInfo transaction. Request ID: " + requestId + "User ID: " + userId + "User category: " + userDao.getUser(userId).getCategory());
		CounterThread.inc(UserCategories.getByLabel(userDao.getUser(userId).getCategory()));
		
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
			item.setSeller(userId);
			item.setCategory(itemCategories.getCategory(item.getCategory()).toString());
			item.setState(1);
			Integer id = (Integer) session.get("editItemId");
			session.remove("editItemId");
			itemDao.editItem(id, item);
			log.info("Finish updateItemInfo transaction, request ID: " + requestId);
			CounterThread.dec(UserCategories.getByLabel(userDao.getUser(userId).getCategory()));
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Finish updateItemInfo transaction, request ID: " + requestId);
			CounterThread.dec(UserCategories.getByLabel(userDao.getUser(userId).getCategory()));
			return ERROR;
		}
	}
	
	public String removeItem() {
		if (!session.containsKey("email"))
			return ERROR;
		
		int requestId = new Random().nextInt(Integer.MAX_VALUE);
		
		int userId = userDao.getUser((String)session.get("email")).getId();
		log.info("Start  removeItem transaction. Request ID: " + requestId + "User ID: " + userId + "User category: " + userDao.getUser(userId).getCategory());
		CounterThread.inc(UserCategories.getByLabel(userDao.getUser(userId).getCategory()));
		
		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
		int itemId = Integer.parseInt(request.getParameter("itemId"));
		item = itemDao.getItem(itemId);
		if (item.getSeller() != userId) {
			log.info("Finish removeItem transaction, request ID: " + requestId);
			CounterThread.dec(UserCategories.getByLabel(userDao.getUser(userId).getCategory()));
			return ERROR;
		}
		
		try {
            String filePath = request.getSession().getServletContext().getRealPath("/");
            File fileToDel = new File(filePath + item.getPhoto());
            FileUtils.forceDelete(fileToDel);
            itemDao.removeItem(itemId);
            log.info("Finish removeItem transaction, request ID: " + requestId);
            CounterThread.dec(UserCategories.getByLabel(userDao.getUser(userId).getCategory()));
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            addActionError(e.getMessage());
            log.info("Finish removeItem transaction, request ID: " + requestId);
            CounterThread.dec(UserCategories.getByLabel(userDao.getUser(userId).getCategory()));
            return ERROR;
        }	
	}
	
	public String registerIn(){
		if (!session.containsKey("email"))
			return ERROR;
		
		int requestId = new Random().nextInt(Integer.MAX_VALUE);
		
		int userId = userDao.getUser((String)session.get("email")).getId();
		log.info("Start  registerIn transaction. Request ID: " + requestId + "User ID: " + userId + "User category: " + userDao.getUser(userId).getCategory());
		CounterThread.inc(UserCategories.getByLabel(userDao.getUser(userId).getCategory()));
		
		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
		int itemId = Integer.parseInt(request.getParameter("itemId"));
		if(!itemDao.registerIn(itemId, userId)){
			log.info("Finish registerIn transaction, request ID: " + requestId);
			CounterThread.dec(UserCategories.getByLabel(userDao.getUser(userId).getCategory()));
			return ERROR;
		}
		log.info("Finish registerIn transaction, request ID: " + requestId);
		CounterThread.dec(UserCategories.getByLabel(userDao.getUser(userId).getCategory()));
		return SUCCESS;
	}
	
	public String unregisterIn(){
		if (!session.containsKey("email"))
			return ERROR;
		
		int requestId = new Random().nextInt(Integer.MAX_VALUE);
		
		int userId = userDao.getUser((String)session.get("email")).getId();
		log.info("Start  unregisterIn transaction. Request ID: " + requestId + "User ID: " + userId + "User category: " + userDao.getUser(userId).getCategory());
		CounterThread.inc(UserCategories.getByLabel(userDao.getUser(userId).getCategory()));
		
		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
		int itemId = Integer.parseInt(request.getParameter("itemId"));
		
		itemDao.unregisterIn(itemId, userId);
		log.info("Finish unregisterIn transaction, request ID: " + requestId);
		CounterThread.dec(UserCategories.getByLabel(userDao.getUser(userId).getCategory()));
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

	public String getWinner() {
		return winner;
	}
	
	public void setDataSource(DataSource ds) {
		dataSource = ds;
	}
}
