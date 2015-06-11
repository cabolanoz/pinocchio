package com.mercon.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.mercon.entities.ErrorResponse;
import com.mercon.entities.VdimLocation;
import com.mercon.entities.VdimLocationId;
import com.mercon.util.DefaultConstants;

@ManagedBean(name = "locationService", eager = true)
@ApplicationScoped
public class LocationDao implements DefaultConstants {

	private List<VdimLocationId> locationId;
	
	@PostConstruct
	public void init() {
		try {
			getAllLocation();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<VdimLocationId> getAllLocation() throws Exception {
		ClientRequest clientRequest = new ClientRequest((IS_PRODUCTION ? PRODUCTION_JBOSS_HOST : DEVELOPER_JBOSS_HOST) + "/CXLRWS/Location/getlocations");
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
			VdimLocation[] dimLocation = (VdimLocation[]) clientResponse.getEntity(VdimLocation[].class);
			
			if (dimLocation != null && dimLocation.length > 0) {
				this.locationId = new ArrayList<VdimLocationId>();
				
				for (VdimLocation location : dimLocation) {
					this.locationId.add(location.getId());
				}
			}
		}

		return this.locationId;
	}

	public List<VdimLocationId> getLocationId() {
		return locationId;
	}

	public void setLocationId(List<VdimLocationId> locationId) {
		this.locationId = locationId;
	}
	
}
