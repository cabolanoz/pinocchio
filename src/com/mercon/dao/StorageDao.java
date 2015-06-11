package com.mercon.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.mercon.entities.ErrorResponse;
import com.mercon.entities.VdimStorage;
import com.mercon.entities.VdimStorageId;
import com.mercon.util.DefaultConstants;

@ManagedBean(name = "storageService", eager = true)
@ApplicationScoped
public class StorageDao implements DefaultConstants {

	private List<VdimStorageId> storageId;

	@PostConstruct
	public void init() {
		try {
			getAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public VdimStorage getStorageByEquipment(int equipmentNum) {
		ClientRequest clientRequest = new ClientRequest((IS_PRODUCTION ? PRODUCTION_JBOSS_HOST : DEVELOPER_JBOSS_HOST) + "/CXLRWS/Storage/getStorageByStorageNumber?storage=" + equipmentNum);
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
			VdimStorage dimStorage = (VdimStorage) clientResponse.getEntity(VdimStorage.class);
			
			return dimStorage;
		}
		
		return null;
	}
	
	public List<VdimStorageId> getAll() throws Exception {
		ClientRequest clientRequest = new ClientRequest((IS_PRODUCTION ? PRODUCTION_JBOSS_HOST : DEVELOPER_JBOSS_HOST) + "/CXLRWS/Storage/get");
		clientRequest.accept("application/json;charset=UTF-8");
		
		ClientResponse<Object> clientResponse = null;
		
		try {
			clientResponse = clientRequest.get(Object.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (clientResponse.getStatus() != 200) {
			try {
				throw new Exception("Error getting storage: " + ((ErrorResponse) clientResponse.getEntity(ErrorResponse.class)).getMessage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			VdimStorage[] dimStorage = (VdimStorage[]) clientResponse.getEntity(VdimStorage[].class);
			
			if (dimStorage != null && dimStorage.length > 0) {
				this.storageId = new ArrayList<VdimStorageId>();
				
				for (VdimStorage storage : dimStorage) {
					this.storageId.add(storage.getId());
				}
			}
		}

		return this.storageId;
	}
	
	public List<VdimStorageId> getStorageId() {
		return storageId;
	}

	public void setStorageId(List<VdimStorageId> storageId) {
		this.storageId = storageId;
	}
	
}
