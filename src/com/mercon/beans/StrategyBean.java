package com.mercon.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.mercon.dao.StrategyDao;
import com.mercon.entities.VdimStrategyId;

@ManagedBean(name = "strategy")
@ViewScoped
public class StrategyBean implements Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 2925780945816250238L;

	@ManagedProperty("#{strategyService}")
	private StrategyDao strategyDao;
	
	private List<VdimStrategyId> lstStrategies;
	
	public StrategyBean() { }
	
	@PostConstruct
	public void init() {
		/* Loading strategies */
		if (lstStrategies == null || lstStrategies.isEmpty() || lstStrategies.size() <= 0) {
			try {
				lstStrategies = strategyDao.getAll();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public StrategyDao getStrategyDao() {
		return strategyDao;
	}

	public void setStrategyDao(StrategyDao strategyDao) {
		this.strategyDao = strategyDao;
	}

	public List<VdimStrategyId> getLstStrategies() {
		return lstStrategies;
	}

	public void setLstStrategies(List<VdimStrategyId> lstStrategies) {
		this.lstStrategies = lstStrategies;
	}
	
}
