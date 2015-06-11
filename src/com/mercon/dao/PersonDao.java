package com.mercon.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.mercon.entities.ErrorResponse;
import com.mercon.entities.VdimPerson;
import com.mercon.entities.VdimPersonId;
import com.mercon.util.DefaultConstants;

@ManagedBean(name = "personService", eager = true)
@ApplicationScoped
public class PersonDao implements DefaultConstants {

	private List<VdimPersonId> personId;
	
	@PostConstruct
	public void init() {
		try {
			getAllOperators();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<VdimPersonId> getAllOperators() throws Exception {
		ClientRequest clientRequest = new ClientRequest((IS_PRODUCTION ? PRODUCTION_JBOSS_HOST : DEVELOPER_JBOSS_HOST) + "/CXLRWS/Person/getAllOperators");
		clientRequest.accept("application/json;charset=UTF-8");
		
		ClientResponse<Object> clientResponse = null;
		
		try {
			clientResponse = clientRequest.get(Object.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (clientResponse.getStatus() != 200) {
			try {
				throw new Exception("Error getting operators: " + ((ErrorResponse) clientResponse.getEntity(ErrorResponse.class)).getMessage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			VdimPerson[] dimPerson = (VdimPerson[]) clientResponse.getEntity(VdimPerson[].class);
			
			if (dimPerson != null && dimPerson.length > 0) {
				this.personId = new ArrayList<VdimPersonId>();
				
				for (VdimPerson person : dimPerson) {
					this.personId.add(person.getId());
				}
			}
		}

		return this.personId;
	}

	public List<VdimPersonId> getPersonId() {
		return personId;
	}

	public void setPersonId(List<VdimPersonId> personId) {
		this.personId = personId;
	}
	
}
