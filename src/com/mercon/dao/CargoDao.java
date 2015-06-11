package com.mercon.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.mercon.entities.VdimCargo;
import com.mercon.util.DefaultConstants;

@ManagedBean(name = "cargoService", eager = true)
@ApplicationScoped
public class CargoDao implements DefaultConstants {

	private List<VdimCargo> levelLst;
	
	public VdimCargo getCargoById(int cargoNum) {
		ClientRequest clientRequest = new ClientRequest((IS_PRODUCTION ? PRODUCTION_JBOSS_HOST : DEVELOPER_JBOSS_HOST) + "/CXLRWS/Cargo/getCargoById/" + cargoNum);
		clientRequest.accept("application/json;charset=UTF-8");
		
		ClientResponse<Object> clientResponse = null;
		
		try {
			clientResponse = clientRequest.get(Object.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (clientResponse.getStatus() != 200) {
//			try {
//				throw new Exception("Error getting cargo: " + ((ErrorResponse) clientResponse.getEntity(ErrorResponse.class)).getMessage());
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
		} else {
			VdimCargo dimCargo = (VdimCargo) clientResponse.getEntity(VdimCargo.class);
			
			return dimCargo;
		}
		
		return null;
	}
	
	public List<VdimCargo> getCargoByEquipment(int equipmentNum) {
		ClientRequest clientRequest = new ClientRequest((IS_PRODUCTION ? PRODUCTION_JBOSS_HOST : DEVELOPER_JBOSS_HOST) + "/CXLRWS/Cargo/getLevelsByStorage/" + equipmentNum);
		clientRequest.accept("application/json;charset=UTF-8");
		
		ClientResponse<Object> clientResponse = null;
		
		try {
			clientResponse = clientRequest.get(Object.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (clientResponse.getStatus() != 200) {
//			try {
//				throw new Exception("Error getting cargo: " + ((ErrorResponse) clientResponse.getEntity(ErrorResponse.class)).getMessage());
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
		} else {
			VdimCargo[] dimCargo = (VdimCargo[]) clientResponse.getEntity(VdimCargo[].class);
			
			return Arrays.asList(dimCargo);
		}
		
		return new ArrayList<VdimCargo>();
	}

	public List<VdimCargo> getLevelLst() {
		return levelLst;
	}

	public void setLevelLst(List<VdimCargo> levelLst) {
		this.levelLst = levelLst;
	}
	
}
