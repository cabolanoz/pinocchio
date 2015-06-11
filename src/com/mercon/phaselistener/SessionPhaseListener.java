package com.mercon.phaselistener;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

public class SessionPhaseListener implements PhaseListener {
	
    /**
	 * Serial ID
	 */
	private static final long serialVersionUID = 1L;

	@Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }

    @Override
    public void beforePhase(PhaseEvent phaseEvent) { }

    @Override
    public void afterPhase(PhaseEvent phaseEvent) {
    	FacesContext facesContext = FacesContext.getCurrentInstance();
    	NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
    	
    	String currentView = facesContext.getViewRoot().getViewId();
    	
    	boolean isLoginView = (currentView.lastIndexOf("login.xhtml") > -1);
    	
    	HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    	
    	if (session == null) {
    		navigationHandler.handleNavigation(facesContext, null, "login");
    	} else {
    		//Map<String, Object> sessionMap = facesContext.getExternalContext().getSessionMap();
    		
    		Object username = session.getAttribute("username");
    		
    		if (!isLoginView && username == null) {
    			navigationHandler.handleNavigation(facesContext, null, "login");
    		} 
    	}
    }
    
}
