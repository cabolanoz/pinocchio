package com.mercon.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.mercon.dao.TransferAtDao;
import com.mercon.entities.VgenIndicatorId;

@FacesConverter("transferAtConverter")
public class TransferAtConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || value.equals("")) {
			return null;
		} else {
			int index = Integer.parseInt(value);
			
			TransferAtDao transferAtDao = (TransferAtDao) facesContext.getExternalContext().getApplicationMap().get("transferAtService");
			
			VgenIndicatorId rtnValue = null;
			
			for (VgenIndicatorId genIndicator : transferAtDao.getTransferAtId()) {
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
