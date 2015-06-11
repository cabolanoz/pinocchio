package com.mercon.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.mercon.dao.StrategyDao;
import com.mercon.entities.VdimStrategyId;

@FacesConverter("strategyConverter")
public class StrategyConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || value.equals("")) {
			return null;
		} else {
			int index = Integer.parseInt(value);
			
			StrategyDao strategyDao = (StrategyDao) facesContext.getExternalContext().getApplicationMap().get("strategyService");
			
			VdimStrategyId rtnValue = null;
			
			for (VdimStrategyId strategyId : strategyDao.getStrategyId()) {
				if (index == strategyId.getNumStrategy()) {
					rtnValue = strategyId;
					break;
				}
			}
			
			return rtnValue;
		}
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
		if (object != null && object instanceof VdimStrategyId) {
			VdimStrategyId strategyId = (VdimStrategyId) object;
			return String.valueOf(strategyId.getNumStrategy());
		} else {
			return null;
		}
	}

}
