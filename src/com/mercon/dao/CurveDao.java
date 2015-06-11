package com.mercon.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.mercon.entities.ErrorResponse;
import com.mercon.entities.VdimMktQuoteDefinition;
import com.mercon.entities.VdimMktQuoteDefinitionId;
import com.mercon.util.DefaultConstants;

@ManagedBean(name = "curveService", eager = true)
@ApplicationScoped
public class CurveDao implements DefaultConstants {

	private List<VdimMktQuoteDefinitionId> commodityStrategyId;
	
	@PostConstruct
	public void init() {
		try {
			getAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<VdimMktQuoteDefinitionId> getAll() throws Exception {
		ClientRequest clientRequest = new ClientRequest((IS_PRODUCTION ? PRODUCTION_JBOSS_HOST : DEVELOPER_JBOSS_HOST) + "/CXLRWS/MktQuoteDefinition/getCurvesall");
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
			VdimMktQuoteDefinition[] dimCommodityStrategy = (VdimMktQuoteDefinition[]) clientResponse.getEntity(VdimMktQuoteDefinition[].class);
			
			if (dimCommodityStrategy != null && dimCommodityStrategy.length > 0) {
				this.commodityStrategyId = new ArrayList<VdimMktQuoteDefinitionId>();
				
				for (VdimMktQuoteDefinition commodityStrategy : dimCommodityStrategy) {
					this.commodityStrategyId.add(commodityStrategy.getId());
				}
			}
		}

		return this.commodityStrategyId;
	}

	public List<VdimMktQuoteDefinitionId> getCommodityStrategyId() {
		return commodityStrategyId;
	}

	public void setCommodityStrategyId(
			List<VdimMktQuoteDefinitionId> commodityStrategyId) {
		this.commodityStrategyId = commodityStrategyId;
	}
	
}
