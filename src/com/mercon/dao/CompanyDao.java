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

@ManagedBean(name = "companyService", eager = true)
@ApplicationScoped
public class CompanyDao implements DefaultConstants {
	
	private List<VdimCompanyId> companyId;
	
	@PostConstruct
	public void init() {
		try {
			getInternalCompanies();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<VdimCompanyId> getInternalCompanies() throws Exception {
		ClientRequest clientRequest = new ClientRequest((IS_PRODUCTION ? PRODUCTION_JBOSS_HOST : DEVELOPER_JBOSS_HOST) + "/CXLRWS/Company/getInternalCompanies");
		clientRequest.accept("application/json;charset=UTF-8");
		
		ClientResponse<Object> clientResponse = null;
		
		try {
			clientResponse = clientRequest.get(Object.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (clientResponse.getStatus() != 200) {
			try {
				throw new Exception("Error getting companies: " + ((ErrorResponse) clientResponse.getEntity(ErrorResponse.class)).getMessage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			VdimCompany[] dimCompany = (VdimCompany[]) clientResponse.getEntity(VdimCompany[].class);
			
			if (dimCompany != null && dimCompany.length > 0) {
				this.companyId = new ArrayList<VdimCompanyId>();
				
				for (VdimCompany company : dimCompany) {
					this.companyId.add(company.getId());
				}
			}
		}
	
		return this.companyId;
	}

	public List<VdimCompanyId> getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(List<VdimCompanyId> companyId) {
		this.companyId = companyId;
	}
	
}
