package com.mercon.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.mercon.entities.ErrorResponse;
import com.mercon.entities.VdimTransfer;
import com.mercon.util.DefaultConstants;

@ManagedBean(name = "transferService", eager = true)
@ApplicationScoped
public class TransferDao implements DefaultConstants {

	public List<VdimTransfer> getAll() {
		List<VdimTransfer> transfer = new ArrayList<VdimTransfer>();
		
		ClientRequest clientRequest = new ClientRequest((IS_PRODUCTION ? PRODUCTION_JBOSS_HOST : DEVELOPER_JBOSS_HOST) + "/CXLRWS/Transfer/getAll");
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
			VdimTransfer[] dimTransfer = (VdimTransfer[]) clientResponse.getEntity(VdimTransfer[].class);
			
			transfer = Arrays.asList(dimTransfer);
		}
		
		return transfer;
	}
	
}
