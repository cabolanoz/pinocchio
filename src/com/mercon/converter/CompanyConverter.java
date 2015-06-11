package com.mercon.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.mercon.dao.CompanyDao;
import com.mercon.entities.VdimCompanyId;

@FacesConverter("companyConverter")
public class CompanyConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || value.equals("")) {
			return null;
		} else {
			int index = Integer.parseInt(value);
			
			CompanyDao companyDao = (CompanyDao) facesContext.getExternalContext().getApplicationMap().get("companyService");
			
			VdimCompanyId rtnValue = null;
			
			for (VdimCompanyId companyId : companyDao.getCompanyId()) {
				if (index == companyId.getNumCompany()) {
					rtnValue = companyId;
					break;
				}
			}
			
			return rtnValue;
		}
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
		if (object != null && object instanceof VdimCompanyId) {
			VdimCompanyId companyId = (VdimCompanyId) object;
			return String.valueOf(companyId.getNumCompany());
		} else {
			return null;
		}
	}

}
