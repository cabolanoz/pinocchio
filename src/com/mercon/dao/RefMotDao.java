package com.mercon.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.mercon.entities.ErrorResponse;
import com.mercon.entities.VrefMot;
import com.mercon.entities.VrefMotId;
import com.mercon.util.DefaultConstants;

@ManagedBean(name = "refMotService", eager = true)
@ApplicationScoped
public class RefMotDao implements DefaultConstants {

	private List<VrefMotId> refMotId;

	@PostConstruct
	public void init() {
		try {
			getAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<VrefMotId> getAll() throws Exception {
		ClientRequest clientRequest = new ClientRequest((IS_PRODUCTION ? PRODUCTION_JBOSS_HOST : DEVELOPER_JBOSS_HOST) + "/CXLRWS/RefMot/getAll");
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
			VrefMot[] refMot = (VrefMot[]) clientResponse.getEntity(VrefMot[].class);
			
			if (refMot != null && refMot.length > 0) {
				this.refMotId = new ArrayList<VrefMotId>();
				
				for (VrefMot ref : refMot) {
					this.refMotId.add(ref.getId());
				}
			}
		}

		return this.refMotId;
	}

	public List<VrefMotId> getRefMotId() {
		return refMotId;
	}

	public void setRefMotId(List<VrefMotId> refMotId) {
		this.refMotId = refMotId;
	}
	
}
