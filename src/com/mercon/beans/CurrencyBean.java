package com.mercon.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.mercon.dao.CurrencyDao;
import com.mercon.entities.VdimCurrencyId;

@ManagedBean(name = "currency")
@ViewScoped
public class CurrencyBean implements Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 2694165468456202440L;

	@ManagedProperty("#{currencyService}")
	private CurrencyDao currencyDao;
	
	private List<VdimCurrencyId> lstCurrencies;
	
	public CurrencyBean() { }
	
	@PostConstruct
	public void init() {
		/* Loading currencies */
		if (lstCurrencies == null || lstCurrencies.isEmpty() || lstCurrencies.size() <= 0) {
			try {
				lstCurrencies = currencyDao.getAll();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public CurrencyDao getCurrencyDao() {
		return currencyDao;
	}

	public void setCurrencyDao(CurrencyDao currencyDao) {
		this.currencyDao = currencyDao;
	}

	public List<VdimCurrencyId> getLstCurrencies() {
		return lstCurrencies;
	}

	public void setLstCurrencies(List<VdimCurrencyId> lstCurrencies) {
		this.lstCurrencies = lstCurrencies;
	}
	
}
