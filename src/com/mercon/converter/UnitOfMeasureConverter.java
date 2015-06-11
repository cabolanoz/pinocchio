package com.mercon.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.mercon.dao.UnitOfMeasureDao;
import com.mercon.entities.VdimUnitOfMeasureId;

@FacesConverter("uomConverter")
public class UnitOfMeasureConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || value.equals("")) {
			return null;
		} else {
			UnitOfMeasureDao unitOfMeasureDao = (UnitOfMeasureDao) facesContext.getExternalContext().getApplicationMap().get("uomService");
			
			VdimUnitOfMeasureId rtnValue = null;
			
			for (VdimUnitOfMeasureId unitOfMeasureId : unitOfMeasureDao.getUnitOfMeasureId()) {
				if (unitOfMeasureId.getCodUom().equals(value)) {
					rtnValue = unitOfMeasureId;
					break;
				}
			}
			
			return rtnValue;
		}
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
		if (object != null && object instanceof VdimUnitOfMeasureId) {
			VdimUnitOfMeasureId unitOfMeasureId = (VdimUnitOfMeasureId) object;
			return unitOfMeasureId.getCodUom();
		} else {
			return null;
		}
	}

}
