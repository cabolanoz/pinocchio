package com.mercon.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.mercon.dao.CounterpartDao;
import com.mercon.entities.VdimCompanyId;

@FacesConverter("counterpartConverter")
public class CounterpartConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || value.equals("")) {
			return null;
		} else {
			int index = Integer.parseInt(value);
			
			CounterpartDao counterpartDao = (CounterpartDao) facesContext.getExternalContext().getApplicationMap().get("counterpartService");
			
			VdimCompanyId rtnValue = null;
			
			for (VdimCompanyId counterpartId : counterpartDao.getCounterpartId()) {
				if (index == counterpartId.getNumCompany()) {
					rtnValue = counterpartId;
					break;
				}
			}
			
			return rtnValue;
		}
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
		if (object != null && object instanceof VdimCompanyId) {
			VdimCompanyId counterpartId = (VdimCompanyId) object;
			return String.valueOf(counterpartId.getNumCompany());
		} else {
			return null;
		}
	}

}
