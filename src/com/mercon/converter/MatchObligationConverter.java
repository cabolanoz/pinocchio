package com.mercon.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("matchObligationConverter")
public class MatchObligationConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || value.equals("")) {
			return null;
		} else {
			//int index = Integer.parseInt(value);
			
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
//		if(object != null && object instanceof VopsObligationMatchObj) {
//			VopsObligationMatchObj obligationMatch = (VopsObligationMatchObj) object;
//            return String.valueOf(obligationMatch.getObligationMatchNum());
//        } else {
//            return null;
//        }
		
		return null;
	}
	
}
