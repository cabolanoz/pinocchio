package com.mercon.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.mercon.dao.PersonDao;
import com.mercon.entities.VdimPersonId;

@FacesConverter("personConverter")
public class PersonConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || value.equals("")) {
			return null;
		} else {
			int index = Integer.parseInt(value);
			
			PersonDao personDao = (PersonDao) facesContext.getExternalContext().getApplicationMap().get("personService");
			
			VdimPersonId rtnValue = null;
			
			for (VdimPersonId personId : personDao.getPersonId()) {
				if (index == personId.getNumperson()) {
					rtnValue = personId;
					break;
				}
			}
			
			return rtnValue;
		}
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
		if (object != null && object instanceof VdimPersonId) {
			VdimPersonId personId = (VdimPersonId) object;
			return String.valueOf(personId.getNumperson());
		} else {
			return null;
		}
	}

}
