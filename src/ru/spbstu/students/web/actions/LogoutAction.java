package ru.spbstu.students.web.actions;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

public class LogoutAction extends BaseAction implements SessionAware{
	
	private static final long serialVersionUID = -3583545790785175229L;
	
	private Map<String, Object> session;

	public String execute(){
		session.remove("email");
		return SUCCESS;
	}
	
	public void setSession(Map<String, Object> session) {
		this.session = session; 
	} 
	
}
