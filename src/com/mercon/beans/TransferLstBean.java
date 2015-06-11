package com.mercon.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.mercon.dao.TransferDao;
import com.mercon.entities.VdimTransfer;

@ManagedBean(name = "transferLst")
@ViewScoped
public class TransferLstBean implements Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -8935087173117909663L;

	@ManagedProperty("#{user}")
	private UserBean user;

	@ManagedProperty("#{transferService}")
	private TransferDao transferDao;
	
	private List<VdimTransfer> transferDataModel = new ArrayList<VdimTransfer>();
	
	@PostConstruct
	public void init() {
//		this.transferDataModel = this.transferDao.getAll();
//		
//		if (!this.transferDataModel.isEmpty() && this.transferDataModel.size() > 0) {
//			RequestContext.getCurrentInstance().execute("onPopulateTable(" + (new Gson()).toJson(this.transferDataModel) + ")");
//		}
	}
	
	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}

	public TransferDao getTransferDao() {
		return transferDao;
	}

	public void setTransferDao(TransferDao transferDao) {
		this.transferDao = transferDao;
	}

	public List<VdimTransfer> getTransferDataModel() {
		return transferDataModel;
	}

	public void setTransferDataModel(List<VdimTransfer> transferDataModel) {
		this.transferDataModel = transferDataModel;
	}
	
}
