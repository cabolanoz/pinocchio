package com.mercon.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.mercon.dao.CurveDao;
import com.mercon.entities.VdimMktQuoteDefinitionId;

@FacesConverter("curveConverter")
public class CurveConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || value.equals("")) {
			return null;
		} else {
			int index = Integer.parseInt(value);
			
			CurveDao curveDao = (CurveDao) facesContext.getExternalContext().getApplicationMap().get("curveService");
			
			VdimMktQuoteDefinitionId rtnValue = null;
			
			for (VdimMktQuoteDefinitionId commodityStrategyId : curveDao.getCommodityStrategyId()) {
				if (index == commodityStrategyId.getNumdefquote()) {
					rtnValue = commodityStrategyId;
					break;
				}
			}
			
			return rtnValue;
		}
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
		if (object != null && object instanceof VdimMktQuoteDefinitionId) {
			VdimMktQuoteDefinitionId commodityStrategyId = (VdimMktQuoteDefinitionId) object;
			return String.valueOf(commodityStrategyId.getNumdefquote());
		} else {
			return null;
		}
	}

}
