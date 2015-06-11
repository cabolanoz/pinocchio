package com.mercon.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.mercon.entities.ErrorResponse;
import com.mercon.entities.VdimCompany;
import com.mercon.entities.VdimCompanyId;
import com.mercon.util.DefaultConstants;

@ManagedBean(name = "counterpartService", eager = true)
@ApplicationScoped
public class CounterpartDao implements DefaultConstants {

	private List<VdimCompanyId> counterpartId;
	
	@PostConstruct
	public void init() {
		try {
			getAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<VdimCompanyId> getAll() throws Exception {
		ClientRequest clientRequest = new ClientRequest((IS_PRODUCTION ? PRODUCTION_JBOSS_HOST : DEVELOPER_JBOSS_HOST) + "/CXLRWS/Counterpart/get");
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
			VdimCompany[] dimCounterpart = (VdimCompany[]) clientResponse.getEntity(VdimCompany[].class);
			
			if (dimCounterpart != null && dimCounterpart.length > 0) {
				this.counterpartId = new ArrayList<VdimCompanyId>();
				
				for (VdimCompany counterpart : dimCounterpart) {
					this.counterpartId.add(counterpart.getId());
				}
			}
		}

		return this.counterpartId;
	}

	public List<VdimCompanyId> getCounterpartId() {
		return counterpartId;
	}

	public void setCounterpartId(List<VdimCompanyId> counterpartId) {
		this.counterpartId = counterpartId;
	}
	
}
