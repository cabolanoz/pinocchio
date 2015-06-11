package com.mercon.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.mercon.entities.ErrorResponse;
import com.mercon.entities.VdimStrategy;
import com.mercon.entities.VdimStrategyId;
import com.mercon.util.DefaultConstants;

@ManagedBean(name = "strategyService", eager = true)
@ApplicationScoped
public class StrategyDao implements DefaultConstants {

	private List<VdimStrategyId> strategyId;

	@PostConstruct
	public void init() {
		try {
			getAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<VdimStrategyId> getAll() throws Exception {
		ClientRequest clientRequest = new ClientRequest((IS_PRODUCTION ? PRODUCTION_JBOSS_HOST : DEVELOPER_JBOSS_HOST) + "/CXLRWS/Strategy/get");
		clientRequest.accept("application/json;charset=UTF-8");
		
		ClientResponse<Object> clientResponse = null;
		
		try {
			clientResponse = clientRequest.get(Object.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (clientResponse.getStatus() != 200) {
			try {
				throw new Exception("Error getting strategies: " + ((ErrorResponse) clientResponse.getEntity(ErrorResponse.class)).getMessage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			VdimStrategy[] dimStrategy = (VdimStrategy[]) clientResponse.getEntity(VdimStrategy[].class);
			
			if (dimStrategy != null && dimStrategy.length > 0) {
				this.strategyId = new ArrayList<VdimStrategyId>();
				
				for (VdimStrategy strategy : dimStrategy) {
					this.strategyId.add(strategy.getId());
				}
			}
		}

		return this.strategyId;
	}
	
	public List<VdimStrategyId> getStrategyId() {
		return strategyId;
	}

	public void setStrategyId(List<VdimStrategyId> strategyId) {
		this.strategyId = strategyId;
	}
	
}
