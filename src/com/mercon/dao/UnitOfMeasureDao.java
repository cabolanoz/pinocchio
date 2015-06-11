package com.mercon.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.mercon.entities.ErrorResponse;
import com.mercon.entities.VdimUnitOfMeasure;
import com.mercon.entities.VdimUnitOfMeasureId;
import com.mercon.util.DefaultConstants;

@ManagedBean(name = "uomService", eager = true)
@ApplicationScoped
public class UnitOfMeasureDao implements DefaultConstants {

	private List<VdimUnitOfMeasureId> unitOfMeasureId;
	
	@PostConstruct
	public void init() {
		try {
			getAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<VdimUnitOfMeasureId> getAll() throws Exception {
		ClientRequest clientRequest = new ClientRequest((IS_PRODUCTION ? PRODUCTION_JBOSS_HOST : DEVELOPER_JBOSS_HOST) + "/CXLRWS/UOM/get");
		clientRequest.accept("application/json;charset=UTF-8");
		
		ClientResponse<Object> clientResponse = null;
		
		try {
			clientResponse = clientRequest.get(Object.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (clientResponse.getStatus() != 200) {
			try {
				throw new Exception("Error getting units of measure: " + ((ErrorResponse) clientResponse.getEntity(ErrorResponse.class)).getMessage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			VdimUnitOfMeasure[] dimUnitOfMeasure = (VdimUnitOfMeasure[]) clientResponse.getEntity(VdimUnitOfMeasure[].class);
			
			if (dimUnitOfMeasure != null && dimUnitOfMeasure.length > 0) {
				this.unitOfMeasureId = new ArrayList<VdimUnitOfMeasureId>();
				
				for (VdimUnitOfMeasure unitOfMeasure : dimUnitOfMeasure) {
					this.unitOfMeasureId.add(unitOfMeasure.getId());
				}
			}
		}

		return this.unitOfMeasureId;
	}
	
	public Double getConversionFactor(String fromUom, String toUom) {
		VdimUnitOfMeasure fromUnitOfMeasure = this.getUOMByCode(fromUom);
		VdimUnitOfMeasure toUnitOfMeasure = this.getUOMByCode(toUom);
		
		if (fromUnitOfMeasure != null && toUnitOfMeasure != null) {
			return ((1 / fromUnitOfMeasure.getId().getFactUom()) * toUnitOfMeasure.getId().getFactUom());
		}
		
		return 0.0d;
	}

	public VdimUnitOfMeasure getUOMByCode(String uomCod) {
		ClientRequest clientRequest = new ClientRequest((IS_PRODUCTION ? PRODUCTION_JBOSS_HOST : DEVELOPER_JBOSS_HOST) + "/CXLRWS/UOM/get/" + uomCod);
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
			VdimUnitOfMeasure dimUnitOfMeasure = (VdimUnitOfMeasure) clientResponse.getEntity(VdimUnitOfMeasure.class);
			
			return dimUnitOfMeasure;
		}
		
		return null;
	}
	
	public List<VdimUnitOfMeasureId> getUnitOfMeasureId() {
		return unitOfMeasureId;
	}

	public void setUnitOfMeasureId(List<VdimUnitOfMeasureId> unitOfMeasureId) {
		this.unitOfMeasureId = unitOfMeasureId;
	}
	
}
