package com.mercon.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.mercon.dao.TradeDao;
import com.mercon.entities.VtradeHdr;

@FacesConverter("tradeConverter")
public class TradeConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || value.equals("")) {
			return null;
		} else {
			int index = Integer.parseInt(value);
			
			TradeDao tradeDao = (TradeDao) facesContext.getExternalContext().getApplicationMap().get("tradeService");
			
			VtradeHdr rtnValue = null;
			
			try {
				rtnValue = tradeDao.getTradeHdrById(index);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return rtnValue;
		}
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
		if(object != null && object instanceof VtradeHdr) {
			VtradeHdr tradeInputId = (VtradeHdr) object;
            return String.valueOf(tradeInputId.getId().getTradeNum());
        } else {
            return null;
        }
	}
	
}
