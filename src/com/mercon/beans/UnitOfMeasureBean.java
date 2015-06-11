package com.mercon.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.mercon.dao.UnitOfMeasureDao;
import com.mercon.entities.VdimUnitOfMeasureId;

@ManagedBean(name = "uom")
@ViewScoped
public class UnitOfMeasureBean implements Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -5217146707309967918L;

	@ManagedProperty("#{uomService}")
	private UnitOfMeasureDao unitOfMeasureDao;
	
	private List<VdimUnitOfMeasureId> lstUnitsOfMeasure;
	
	public UnitOfMeasureBean() { }
	
	@PostConstruct
	public void init() {
		/* Loading units of measure */
		if (lstUnitsOfMeasure == null || lstUnitsOfMeasure.isEmpty() || lstUnitsOfMeasure.size() <= 0) {
			try {
				lstUnitsOfMeasure = unitOfMeasureDao.getAll();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public UnitOfMeasureDao getUnitOfMeasureDao() {
		return unitOfMeasureDao;
	}

	public void setUnitOfMeasureDao(UnitOfMeasureDao unitOfMeasureDao) {
		this.unitOfMeasureDao = unitOfMeasureDao;
	}

	public List<VdimUnitOfMeasureId> getLstUnitsOfMeasure() {
		return lstUnitsOfMeasure;
	}

	public void setLstUnitsOfMeasure(List<VdimUnitOfMeasureId> lstUnitsOfMeasure) {
		this.lstUnitsOfMeasure = lstUnitsOfMeasure;
	}
	
}
