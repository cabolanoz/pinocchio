package com.mercon.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.mercon.dao.CommodityDao;
import com.mercon.entities.VdimCommodityId;

@FacesConverter("commodityConverter")
public class CommodityConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || value.equals("")) {
			return null;
		} else {
			CommodityDao commodityDao = (CommodityDao) facesContext.getExternalContext().getApplicationMap().get("commodityService");
			
			VdimCommodityId rtnValue = null;
			
			for (VdimCommodityId commodityId : commodityDao.getCommodityId()) {
				if (commodityId.getCodCommodity().equals(value)) {
					rtnValue = commodityId;
					break;
				}
			}
			
			return rtnValue;
		}
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
		if (object != null && object instanceof VdimCommodityId) {
			VdimCommodityId commodityId = (VdimCommodityId) object;
			return commodityId.getCodCommodity();
		} else {
			return null;
		}
	}

}
