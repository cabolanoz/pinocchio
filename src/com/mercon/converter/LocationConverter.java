package com.mercon.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.mercon.dao.LocationDao;
import com.mercon.entities.VdimLocationId;

@FacesConverter("locationConverter")
public class LocationConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || value.equals("")) {
			return null;
		} else {
			int index = Integer.parseInt(value);
			
			LocationDao locationDao = (LocationDao) facesContext.getExternalContext().getApplicationMap().get("locationService");
			
			VdimLocationId rtnValue = null;
			
			for (VdimLocationId locationId : locationDao.getLocationId()) {
				if (index == locationId.getNumLocation()) {
					rtnValue = locationId;
					break;
				}
			}
			
			return rtnValue;
		}
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
		if (object != null && object instanceof VdimLocationId) {
			VdimLocationId locationId = (VdimLocationId) object;
			return String.valueOf(locationId.getNumLocation());
		} else {
			return null;
		}
	}

}
