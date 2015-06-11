package com.mercon.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.mercon.entities.ErrorResponse;
import com.mercon.entities.VgenIndicator;
import com.mercon.entities.VgenIndicatorId;
import com.mercon.util.DefaultConstants;

@ManagedBean(name = "fromTypeService", eager = true)
@ApplicationScoped
public class FromTypeDao implements DefaultConstants {

	private List<VgenIndicatorId> fromTypeId;

	@PostConstruct
	public void init() {
		try {
			getAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<VgenIndicatorId> getAll() throws Exception {
		ClientRequest clientRequest = new ClientRequest((IS_PRODUCTION ? PRODUCTION_JBOSS_HOST : DEVELOPER_JBOSS_HOST) + "/CXLRWS/GenIndicator/getFromType");
		clientRequest.accept("application/json;charset=UTF-8");
		
		ClientResponse<Object> clientResponse = null;
		
		try {
			clientResponse = clientRequest.get(Object.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (clientResponse.getStatus() != 200) {
			try {
				throw new Exception("Error getting from type: " + ((ErrorResponse) clientResponse.getEntity(ErrorResponse.class)).getMessage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			VgenIndicator[] genIndicator = (VgenIndicator[]) clientResponse.getEntity(VgenIndicator[].class);
			
			if (genIndicator != null && genIndicator.length > 0) {
				this.fromTypeId = new ArrayList<VgenIndicatorId>();
				
				for (VgenIndicator indicator : genIndicator) {
					this.fromTypeId.add(indicator.getId());
				}
			}
		}

		return this.fromTypeId;
	}
	
	public List<VgenIndicatorId> getFromTypeId() {
		return fromTypeId;
	}

	public void setFromTypeId(List<VgenIndicatorId> fromTypeId) {
		this.fromTypeId = fromTypeId;
	}
	
}
