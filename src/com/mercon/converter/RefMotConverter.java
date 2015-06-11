package com.mercon.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.mercon.dao.RefMotDao;
import com.mercon.entities.VrefMotId;

@FacesConverter("refMotConverter")
public class RefMotConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || value.equals("")) {
			return null;
		} else {
			int index = Integer.parseInt(value);
			
			RefMotDao refMotDao = (RefMotDao) facesContext.getExternalContext().getApplicationMap().get("refMotService");
			
			VrefMotId rtnValue = null;
			
			for (VrefMotId refMotId : refMotDao.getRefMotId()) {
				if (index == refMotId.getMotNum()) {
					rtnValue = refMotId;
					break;
				}
			}
			
			return rtnValue;
		}
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
		if (object != null && object instanceof VrefMotId) {
			VrefMotId refMotId = (VrefMotId) object;
			return String.valueOf(refMotId.getMotNum());
		} else {
			return null;
		}
	}

}
