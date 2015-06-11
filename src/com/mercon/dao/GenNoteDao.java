package com.mercon.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.mercon.entities.ErrorResponse;
import com.mercon.entities.VgenNote;
import com.mercon.util.DefaultConstants;

@ManagedBean(name = "genNoteService", eager = true)
@ApplicationScoped
public class GenNoteDao implements DefaultConstants {

	public List<VgenNote> getByItinerary(int itineraryNum) {
		ClientRequest clientRequest = new ClientRequest((IS_PRODUCTION ? PRODUCTION_JBOSS_HOST : DEVELOPER_JBOSS_HOST) + "/CXLRWS/GenNote/getByItinerary/" + itineraryNum);
		clientRequest.accept("application/json;charset=UTF-8");
		
		ClientResponse<Object> clientResponse = null;
		
		try {
			clientResponse = clientRequest.get(Object.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (clientResponse.getStatus() != 200) {
			try {
				throw new Exception("Error getting itinerary notes: " + ((ErrorResponse) clientResponse.getEntity(ErrorResponse.class)).getMessage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			VgenNote[] genNote = (VgenNote[]) clientResponse.getEntity(VgenNote[].class);
			
			return Arrays.asList(genNote);
		}
		
		return new ArrayList<VgenNote>();
	}
	
	public VgenNote getByItineraryAndMovement(int itineraryNum, int movementNum) {
		ClientRequest clientRequest = new ClientRequest((IS_PRODUCTION ? PRODUCTION_JBOSS_HOST : DEVELOPER_JBOSS_HOST) + "/CXLRWS/GenNote/getByItineraryAndMovement");
		clientRequest.accept("application/json;charset=UTF-8");
		
		clientRequest.queryParameter("itineraryNum", itineraryNum);
		clientRequest.queryParameter("movementNum", movementNum);
		
		ClientResponse<Object> clientResponse = null;
		
		try {
			clientResponse = clientRequest.get(Object.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (clientResponse.getStatus() != 200) {
			try {
				throw new Exception("Error getting itinerary note: " + ((ErrorResponse) clientResponse.getEntity(ErrorResponse.class)).getMessage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			VgenNote genNote = (VgenNote) clientResponse.getEntity(VgenNote.class);
			
			return genNote;
		}
		
		return null;
	}
	
}
