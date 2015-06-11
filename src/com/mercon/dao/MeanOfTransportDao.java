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

@ManagedBean(name = "motService", eager = true)
@ApplicationScoped
public class MeanOfTransportDao implements DefaultConstants {

	private List<VgenIndicatorId> tMotId;
	private List<VgenIndicatorId> sMotId;
	
	@PostConstruct
	public void init() {
		try {
			getAll();
			getAllStatus();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<VgenIndicatorId> getAll() throws Exception {
		ClientRequest clientRequest = new ClientRequest((IS_PRODUCTION ? PRODUCTION_JBOSS_HOST : DEVELOPER_JBOSS_HOST) + "/CXLRWS/GenIndicator/getMeanOfTransport");
		clientRequest.accept("application/json;charset=UTF-8");
		
		ClientResponse<Object> clientResponse = null;
		
		try {
			clientResponse = clientRequest.get(Object.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (clientResponse.getStatus() != 200) {
			try {
				throw new Exception("Error getting means of transport: " + ((ErrorResponse) clientResponse.getEntity(ErrorResponse.class)).getMessage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			VgenIndicator[] genIndicator = (VgenIndicator[]) clientResponse.getEntity(VgenIndicator[].class);
			
			if (genIndicator != null && genIndicator.length > 0) {
				this.tMotId = new ArrayList<VgenIndicatorId>();
				
				for (VgenIndicator indicator : genIndicator) {
					this.tMotId.add(indicator.getId());
				}
			}
		}

		return this.tMotId;
	}
	
	public List<VgenIndicatorId> getAllStatus() throws Exception {
		ClientRequest clientRequest = new ClientRequest((IS_PRODUCTION ? PRODUCTION_JBOSS_HOST : DEVELOPER_JBOSS_HOST) + "/CXLRWS/GenIndicator/getMovementStatus");
		clientRequest.accept("application/json;charset=UTF-8");
		
		ClientResponse<Object> clientResponse = null;
		
		try {
			clientResponse = clientRequest.get(Object.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (clientResponse.getStatus() != 200) {
			try {
				throw new Exception("Error getting means of transport: " + ((ErrorResponse) clientResponse.getEntity(ErrorResponse.class)).getMessage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			VgenIndicator[] genIndicator = (VgenIndicator[]) clientResponse.getEntity(VgenIndicator[].class);
			
			if (genIndicator != null && genIndicator.length > 0) {
				this.sMotId = new ArrayList<VgenIndicatorId>();
				
				for (VgenIndicator indicator : genIndicator) {
					this.sMotId.add(indicator.getId());
				}
			}
		}

		return this.sMotId;
	}

	public List<VgenIndicatorId> gettMotId() {
		return tMotId;
	}

	public void settMotId(List<VgenIndicatorId> tMotId) {
		this.tMotId = tMotId;
	}

	public List<VgenIndicatorId> getsMotId() {
		return sMotId;
	}

	public void setvMotId(List<VgenIndicatorId> sMotId) {
		this.sMotId = sMotId;
	}
	
}
