package com.mercon.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.mercon.dao.LocationDao;
import com.mercon.entities.VdimLocationId;

@ManagedBean(name = "location")
@ViewScoped
public class LocationBean implements Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -985081527374943481L;

	@ManagedProperty("#{locationService}")
	private LocationDao locationDao;
	
	private List<VdimLocationId> lstLocations;

	public LocationBean() { }

	@PostConstruct
	public void init() {
		/* Loading locations */
		if (lstLocations == null || lstLocations.isEmpty() || lstLocations.size() <= 0) {
			try {
				lstLocations = locationDao.getAllLocation();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public LocationDao getLocationDao() {
		return locationDao;
	}

	public void setLocationDao(LocationDao locationDao) {
		this.locationDao = locationDao;
	}

	public List<VdimLocationId> getLstLocations() {
		return lstLocations;
	}

	public void setLstLocations(List<VdimLocationId> lstLocations) {
		this.lstLocations = lstLocations;
	}
	
}
