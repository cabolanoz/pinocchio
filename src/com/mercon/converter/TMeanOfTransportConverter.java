package com.mercon.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.mercon.dao.MeanOfTransportDao;
import com.mercon.entities.VgenIndicatorId;

@FacesConverter("tMotConverter")
public class TMeanOfTransportConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || value.equals("")) {
			return null;
		} else {
			int index = Integer.parseInt(value);
			
			MeanOfTransportDao motDao = (MeanOfTransportDao) facesContext.getExternalContext().getApplicationMap().get("motService");
			
			VgenIndicatorId rtnValue = null;
			
			for (VgenIndicatorId motId : motDao.gettMotId()) {
				if (index == motId.getIndValue()) {
					rtnValue = motId;
					break;
				}
			}
			
			return rtnValue;
		}
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
		if (object != null && object instanceof VgenIndicatorId) {
			VgenIndicatorId tMotId = (VgenIndicatorId) object;
			return String.valueOf(tMotId.getIndValue());
		} else {
			return null;
		}
	}

}
