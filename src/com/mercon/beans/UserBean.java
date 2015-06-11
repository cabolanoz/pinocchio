package com.mercon.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import com.mercon.auth.UserAuth;
import com.mercon.dao.UserDao;
import com.mercon.entities.Vpermission;
import com.mercon.util.DefaultConstants;

public class UserBean implements DefaultConstants, Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 3361965380158028348L;
	
	private String username;
	private String password;
	private String usersession;
	
	private Vpermission userPermission;
	
	private Date currentDt;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void login(ActionEvent actionEvent) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
		Map<String, Object> sessionParam = facesContext.getExternalContext().getSessionMap();
		
		if (this.username.length() > 0 && this.username != null && this.username.trim() != "" && this.password.trim().length() > 0 && this.password != null && this.password.trim() != "") {
//			UserDao userDao = new UserDao();
//			
//			this.userPermission = userDao.getUserAppPermision(this.username, APP_ID);
//			if (this.userPermission != null) {
				UserAuth userAuth = new UserAuth();
				
				try {
					if (userAuth.authenticate(this.username, this.password)) {
						HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
						
						session.setAttribute("sessoinid", userAuth.getUsersession());
						session.setAttribute("username", this.username);

						// Setting the unique user session
						this.setUsersession(userAuth.getUsersession());
						
						facesContext.getExternalContext().redirect("dashboard.xhtml");
					} else {
						if (sessionParam != null) {
							sessionParam.clear();
						}
						
						facesContext.addMessage(null, new FacesMessage("Username or Password incorrect"));
					}
				} catch (Exception e) {
					if (sessionParam != null) {
						sessionParam.clear();
					}
					
					facesContext.addMessage(null, new FacesMessage("Username or Password incorrect"));
					e.printStackTrace();
				}
//			} else {
//				facesContext.addMessage(null, new FacesMessage("You do not have permission to access this application. Please contact your system administrator"));
//			}
		} else {
			if (sessionParam != null) {
				sessionParam.clear();
			}
			
			facesContext.addMessage(null, new FacesMessage("Username and Password are required"));
		}
	}
	
	public void logout() {
		FacesContext facesContext = FacesContext.getCurrentInstance();

		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
		session.invalidate();
		
		facesContext.getExternalContext().invalidateSession();
		
		try {
			facesContext.getExternalContext().redirect("login.xhtml");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public String getUsersession() {
		return this.usersession;
	}

	public void setUsersession(String usersession) {
		this.usersession = usersession;
	}

	public Vpermission getUserPermission() {
		return this.userPermission;
	}

	public void setUserPermission(Vpermission userPermission) {
		this.userPermission = userPermission;
	}

	public Date getCurrentDt() {
		currentDt = new Date();
		
		return currentDt;
	}

	public void setCurrentDt(Date currentDt) {
		this.currentDt = currentDt;
	}
	
}
