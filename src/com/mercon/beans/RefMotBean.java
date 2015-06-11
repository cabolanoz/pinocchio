package com.mercon.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.mercon.dao.RefMotDao;
import com.mercon.entities.VrefMotId;

@ManagedBean(name = "refMot")
@ViewScoped
public class RefMotBean implements Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 2925780945816250238L;

	@ManagedProperty("#{refMotService}")
	private RefMotDao refMotDao;
	
	private List<VrefMotId> lstRefMots;
	
	public RefMotBean() { }
	
	@PostConstruct
	public void init() {
		/* Loading ref mots */
		if (lstRefMots == null || lstRefMots.isEmpty() || lstRefMots.size() <= 0) {
			try {
				lstRefMots = refMotDao.getAll();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public RefMotDao getRefMotDao() {
		return refMotDao;
	}

	public void setRefMotDao(RefMotDao refMotDao) {
		this.refMotDao = refMotDao;
	}

	public List<VrefMotId> getLstRefMots() {
		return lstRefMots;
	}

	public void setLstRefMots(List<VrefMotId> lstRefMots) {
		this.lstRefMots = lstRefMots;
	}
	
}
