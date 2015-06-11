package com.mercon.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.mercon.entities.ErrorResponse;
import com.mercon.entities.VdimCommodity;
import com.mercon.entities.VdimCommodityId;
import com.mercon.util.DefaultConstants;

@ManagedBean(name = "commodityService", eager = true)
@ApplicationScoped
public class CommodityDao implements DefaultConstants {

	private List<VdimCommodityId> commodityId;
	
	@PostConstruct
	public void init() {
		try {
			getAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<VdimCommodityId> getAll() throws Exception {
		ClientRequest clientRequest = new ClientRequest((IS_PRODUCTION ? PRODUCTION_JBOSS_HOST : DEVELOPER_JBOSS_HOST) + "/CXLRWS/Commodity/get");
		clientRequest.accept("application/json;charset=UTF-8");
		
		ClientResponse<Object> clientResponse = null;
		
		try {
			clientResponse = clientRequest.get(Object.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (clientResponse.getStatus() != 200) {
			try {
				throw new Exception("Error getting commodity: " + ((ErrorResponse) clientResponse.getEntity(ErrorResponse.class)).getMessage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			VdimCommodity[] dimCommodity = (VdimCommodity[]) clientResponse.getEntity(VdimCommodity[].class);
			
			if (dimCommodity != null && dimCommodity.length > 0) {
				this.commodityId = new ArrayList<VdimCommodityId>();
				
				for (VdimCommodity commodity : dimCommodity) {
					this.commodityId.add(commodity.getId());
				}
			}
		}

		return this.commodityId;
	}

	public List<VdimCommodityId> getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(List<VdimCommodityId> commodityId) {
		this.commodityId = commodityId;
	}
	
}
