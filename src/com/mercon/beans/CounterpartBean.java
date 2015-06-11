package com.mercon.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.mercon.dao.CounterpartDao;
import com.mercon.entities.VdimCompanyId;

@ManagedBean(name = "counterpart")
@ViewScoped
public class CounterpartBean implements Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -5217146707309967918L;

	@ManagedProperty("#{counterpartService}")
	private CounterpartDao counterpartDao;
	
	private List<VdimCompanyId> lstCounterparts;
	
	public CounterpartBean() { }
	
	@PostConstruct
	public void init() {
		/* Loading units of measure */
		if (lstCounterparts == null || lstCounterparts.isEmpty() || lstCounterparts.size() <= 0) {
			try {
				lstCounterparts = counterpartDao.getAll();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public CounterpartDao getCounterpartDao() {
		return counterpartDao;
	}

	public void setCounterpartDao(CounterpartDao counterpartDao) {
		this.counterpartDao = counterpartDao;
	}

	public List<VdimCompanyId> getLstCounterparts() {
		return lstCounterparts;
	}

	public void setLstCounterparts(List<VdimCompanyId> lstCounterparts) {
		this.lstCounterparts = lstCounterparts;
	}
	
}
