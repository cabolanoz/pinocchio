package com.mercon.dao;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.mercon.entities.ErrorResponse;
import com.mercon.entities.VopsItiDefaultActionDetail;
import com.mercon.util.DefaultConstants;

@ManagedBean(name = "itiDefaultActionDetailService", eager = true)
@ApplicationScoped
public class ItiDefaultActionDetailDao implements DefaultConstants {

	public VopsItiDefaultActionDetail getByItineraryAndMovement(int itineraryNum, int movementNum) {
		ClientRequest clientRequest = new ClientRequest((IS_PRODUCTION ? PRODUCTION_JBOSS_HOST : DEVELOPER_JBOSS_HOST) + "/CXLRWS/ItiDefaultActionDetail/getByItineraryAndMovement?itineraryNum=" + itineraryNum + "&movementNum=" + movementNum);
		clientRequest.accept("application/json;charset=UTF-8");
		
		ClientResponse<Object> clientResponse = null;
		
		try {
			clientResponse = clientRequest.get(Object.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (clientResponse.getStatus() != 200) {
			try {
				throw new Exception("Error getting itinerary default action detail: " + ((ErrorResponse) clientResponse.getEntity(ErrorResponse.class)).getMessage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			//if (clientResponse.getEntity() != null) {
				VopsItiDefaultActionDetail itiDefaultActionDetail = (VopsItiDefaultActionDetail) clientResponse.getEntity(VopsItiDefaultActionDetail.class);
				
				return itiDefaultActionDetail;
			//}
		}
		
		return null;
	}
	
}
