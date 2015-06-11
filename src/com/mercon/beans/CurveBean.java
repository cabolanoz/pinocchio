package com.mercon.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.mercon.dao.CurveDao;
import com.mercon.entities.VdimMktQuoteDefinitionId;

@ManagedBean(name = "curve")
@ViewScoped
public class CurveBean implements Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 2925780945816250238L;

	@ManagedProperty("#{curveService}")
	private CurveDao curveDao;
	
	private List<VdimMktQuoteDefinitionId> lstCommodityStrategies;
	
	public CurveBean() { }
	
	@PostConstruct
	public void init() {
		/* Loading commodities */
		if (lstCommodityStrategies == null || lstCommodityStrategies.isEmpty() || lstCommodityStrategies.size() <= 0) {
			try {
				lstCommodityStrategies = curveDao.getAll();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public CurveDao getCurveDao() {
		return curveDao;
	}

	public void setCurveDao(CurveDao curveDao) {
		this.curveDao = curveDao;
	}

	public List<VdimMktQuoteDefinitionId> getLstCommodityStrategies() {
		return lstCommodityStrategies;
	}

	public void setLstCommodityStrategies(
			List<VdimMktQuoteDefinitionId> lstCommodityStrategies) {
		this.lstCommodityStrategies = lstCommodityStrategies;
	}
	
}
