package com.mercon.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.mercon.dao.MeanOfTransportDao;
import com.mercon.entities.VgenIndicatorId;

@ManagedBean(name = "mot")
@ViewScoped
public class MeanOfTransportBean implements Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -6391875943764849346L;

	@ManagedProperty("#{motService}")
	private MeanOfTransportDao motDao;
	
	private List<VgenIndicatorId> lstMots;
	private List<VgenIndicatorId> lstStatuses;

	public MeanOfTransportBean() { }
	
	@PostConstruct
	public void init() {
		/* Loading means of transport */
		if (lstMots == null || lstMots.isEmpty() || lstMots.size() <= 0) {
			try {
				lstMots = motDao.getAll();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/* Loading vehicles */
		if (lstStatuses == null || lstStatuses.isEmpty() || lstStatuses.size() <= 0) {
			try {
				lstStatuses = motDao.getAllStatus();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public MeanOfTransportDao getMotDao() {
		return motDao;
	}

	public void setMotDao(MeanOfTransportDao motDao) {
		this.motDao = motDao;
	}

	public List<VgenIndicatorId> getLstMots() {
		return lstMots;
	}

	public void setLstMots(List<VgenIndicatorId> lstMots) {
		this.lstMots = lstMots;
	}

	public List<VgenIndicatorId> getLstStatuses() {
		return lstStatuses;
	}

	public void setLstStatuses(List<VgenIndicatorId> lstStatuses) {
		this.lstStatuses = lstStatuses;
	}
	
}
