package com.mercon.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.mercon.dao.TransferAtDao;
import com.mercon.entities.VgenIndicatorId;

@ManagedBean(name = "transferAt")
@ViewScoped
public class TransferAtBean implements Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 1777396795253549124L;

	@ManagedProperty("#{transferAtService}")
	private TransferAtDao transferAtDao;
	
	private List<VgenIndicatorId> transferAts;

	public TransferAtBean() { }
	
	@PostConstruct
	public void init() {
		if (this.transferAts == null || this.transferAts.isEmpty() || this.transferAts.size() <= 0) {
			try {
				this.transferAts = transferAtDao.getAll();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public TransferAtDao getTransferAtDao() {
		return transferAtDao;
	}

	public void setTransferAtDao(TransferAtDao transferAtDao) {
		this.transferAtDao = transferAtDao;
	}

	public List<VgenIndicatorId> getTransferAts() {
		return transferAts;
	}

	public void setTransferAts(List<VgenIndicatorId> transferAts) {
		this.transferAts = transferAts;
	}
	
}
