package com.mercon.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.mercon.dao.CurrencyDao;
import com.mercon.entities.VdimCurrencyId;

@FacesConverter("currencyConverter")
public class CurrencyConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || value.equals("")) {
			return null;
		} else {
			CurrencyDao currencyDao = (CurrencyDao) facesContext.getExternalContext().getApplicationMap().get("currencyService");
			
			VdimCurrencyId rtnValue = null;
			
			for (VdimCurrencyId currencyId : currencyDao.getCurrencyId()) {
				if (currencyId.getCodcurr().equals(value)) {
					rtnValue = currencyId;
					break;
				}
			}
			
			return rtnValue;
		}
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
		if (object != null && object instanceof VdimCurrencyId) {
			VdimCurrencyId currencyId = (VdimCurrencyId) object;
			return currencyId.getCodcurr();
		} else {
			return null;
		}
	}

}
