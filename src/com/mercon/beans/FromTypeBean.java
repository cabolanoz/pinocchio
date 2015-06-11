package com.mercon.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.mercon.dao.FromTypeDao;
import com.mercon.entities.VgenIndicatorId;

@ManagedBean(name = "fromType")
@ViewScoped
public class FromTypeBean implements Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 6755744537972958125L;
	
	@ManagedProperty("#{fromTypeService}")
	private FromTypeDao fromTypeDao;
	
	private List<VgenIndicatorId> lstFromTypes;

	public FromTypeBean() { }
	
	@PostConstruct
	public void init() {
		if (this.lstFromTypes == null || this.lstFromTypes.isEmpty() || this.lstFromTypes.size() <= 0) {
			try {
				this.lstFromTypes = fromTypeDao.getAll();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public FromTypeDao getFromTypeDao() {
		return fromTypeDao;
	}

	public void setFromTypeDao(FromTypeDao fromTypeDao) {
		this.fromTypeDao = fromTypeDao;
	}

	public List<VgenIndicatorId> getLstFromTypes() {
		return lstFromTypes;
	}

	public void setLstFromTypes(List<VgenIndicatorId> lstFromTypes) {
		this.lstFromTypes = lstFromTypes;
	}

}
