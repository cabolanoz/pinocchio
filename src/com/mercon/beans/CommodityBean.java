package com.mercon.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.mercon.dao.CommodityDao;
import com.mercon.entities.VdimCommodityId;

@ManagedBean(name = "commodity")
@ViewScoped
public class CommodityBean implements Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 2925780945816250238L;

	@ManagedProperty("#{commodityService}")
	private CommodityDao commodityDao;
	
	private List<VdimCommodityId> lstCommodities;
	
	public CommodityBean() { }
	
	@PostConstruct
	public void init() {
		/* Loading commodities */
		if (lstCommodities == null || lstCommodities.isEmpty() || lstCommodities.size() <= 0) {
			try {
				lstCommodities = commodityDao.getAll();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public CommodityDao getCommodityDao() {
		return commodityDao;
	}

	public void setCommodityDao(CommodityDao commodityDao) {
		this.commodityDao = commodityDao;
	}

	public List<VdimCommodityId> getLstCommodities() {
		return lstCommodities;
	}

	public void setLstCommodities(List<VdimCommodityId> lstCommodities) {
		this.lstCommodities = lstCommodities;
	}
	
}
