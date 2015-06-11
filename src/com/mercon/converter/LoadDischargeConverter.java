package com.mercon.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.mercon.dao.LoadDischargeDao;
import com.mercon.entities.VgenIndicatorId;

@FacesConverter("loadDischargeConverter")
public class LoadDischargeConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || value.equals("")) {
			return null;
		} else {
			int index = Integer.parseInt(value);
			
			LoadDischargeDao loadDischargeDao = (LoadDischargeDao) facesContext.getExternalContext().getApplicationMap().get("loadDischargeService");
			
			VgenIndicatorId rtnValue = null;
			
			for (VgenIndicatorId genIndicator : loadDischargeDao.getLoadDischargeId()) {
				if (index == genIndicator.getIndValue()) {
					rtnValue = genIndicator;
					break;
				}
			}
			
			return rtnValue;
		}
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
		if (object != null && object instanceof VgenIndicatorId) {
			VgenIndicatorId genIndicator = (VgenIndicatorId) object;
			return String.valueOf(genIndicator.getIndValue());
		}
		
		return null;
	}

}
