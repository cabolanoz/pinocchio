package com.mercon.dao;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.mercon.entities.ErrorResponse;
import com.mercon.entities.Vpermission;
import com.mercon.util.DefaultConstants;

public class UserDao implements DefaultConstants {

	public Vpermission getUserAppPermision(String userName, int appId) {
		ClientRequest clientRequest = new ClientRequest((IS_PRODUCTION ? PRODUCTION_JBOSS_HOST : DEVELOPER_JBOSS_HOST) + "/MCGENSRWS/Usuario/getPermissions/" + userName + "," + appId);
		clientRequest.accept("application/json;charset=UTF-8");
		
		ClientResponse<Object> clientResponse = null;
		
		try {
			clientResponse = clientRequest.get(Object.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (clientResponse.getStatus() != 200) {
			try {
				throw new Exception("Error getting itinerary: " + ((ErrorResponse) clientResponse.getEntity(ErrorResponse.class)).getMessage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Vpermission permission = (Vpermission) clientResponse.getEntity(Vpermission[].class)[0];
			
			return permission;
		}
		
		return null;
	}
	
}
