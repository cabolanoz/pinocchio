package com.mercon.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.mercon.dao.StorageDao;
import com.mercon.entities.VdimStorageId;

@ManagedBean(name = "storage")
@ViewScoped
public class StorageBean {

	@ManagedProperty("#{storageService}")
	private StorageDao storageDao;
	
	private List<VdimStorageId> lstStorages;

	public StorageBean() { }
	
	@PostConstruct
	public void init() {
		/* Loading storages */
		if (this.lstStorages == null || this.lstStorages.isEmpty() || this.lstStorages.size() <= 0) {
			try {
				this.lstStorages = this.storageDao.getAll();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public StorageDao getStorageDao() {
		return storageDao;
	}

	public void setStorageDao(StorageDao storageDao) {
		this.storageDao = storageDao;
	}

	public List<VdimStorageId> getLstStorages() {
		return lstStorages;
	}

	public void setLstStorages(List<VdimStorageId> lstStorages) {
		this.lstStorages = lstStorages;
	}
	
}
