package com.mercon.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.mercon.dao.LoadDischargeDao;
import com.mercon.entities.VgenIndicatorId;

@ManagedBean(name = "loadDischarge")
@ViewScoped
public class LoadDischargeBean implements Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 1777396795253549124L;

	@ManagedProperty("#{loadDischargeService}")
	private LoadDischargeDao loadDischargeDao;
	
	private List<VgenIndicatorId> lstLoadDischarges;

	public LoadDischargeBean() { }
	
	@PostConstruct
	public void init() {
		if (this.lstLoadDischarges == null || this.lstLoadDischarges.isEmpty() || this.lstLoadDischarges.size() <= 0) {
			try {
				this.lstLoadDischarges = loadDischargeDao.getAll();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public LoadDischargeDao getLoadDischargeDao() {
		return loadDischargeDao;
	}

	public void setLoadDischargeDao(LoadDischargeDao loadDischargeDao) {
		this.loadDischargeDao = loadDischargeDao;
	}

	public List<VgenIndicatorId> getLstLoadDischarges() {
		return lstLoadDischarges;
	}

	public void setLstLoadDischarges(List<VgenIndicatorId> lstLoadDischarges) {
		this.lstLoadDischarges = lstLoadDischarges;
	}
	
}
