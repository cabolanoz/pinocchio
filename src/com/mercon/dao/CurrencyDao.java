package com.mercon.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.mercon.entities.ErrorResponse;
import com.mercon.entities.VdimCurrency;
import com.mercon.entities.VdimCurrencyId;
import com.mercon.util.DefaultConstants;

@ManagedBean(name = "currencyService", eager = true)
@ApplicationScoped
public class CurrencyDao implements DefaultConstants {

	private List<VdimCurrencyId> currencyId;
	
	@PostConstruct
	public void init() {
		try {
			getAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<VdimCurrencyId> getAll() throws Exception {
		ClientRequest clientRequest = new ClientRequest((IS_PRODUCTION ? PRODUCTION_JBOSS_HOST : DEVELOPER_JBOSS_HOST) + "/CXLRWS/Currency/get");
		clientRequest.accept("application/json;charset=UTF-8");
		
		ClientResponse<Object> clientResponse = null;
		
		try {
			clientResponse = clientRequest.get(Object.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (clientResponse.getStatus() != 200) {
			try {
				throw new Exception("Error getting currencies: " + ((ErrorResponse) clientResponse.getEntity(ErrorResponse.class)).getMessage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			VdimCurrency[] dimCurrency = (VdimCurrency[]) clientResponse.getEntity(VdimCurrency[].class);
			
			if (dimCurrency != null && dimCurrency.length > 0) {
				this.currencyId = new ArrayList<VdimCurrencyId>();
				
				for (VdimCurrency currency : dimCurrency) {
					this.currencyId.add(currency.getId());
				}
			}
		}

		return this.currencyId;
	}

	public List<VdimCurrencyId> getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(List<VdimCurrencyId> currencyId) {
		this.currencyId = currencyId;
	}
	
}
