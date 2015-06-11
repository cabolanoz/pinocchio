package com.mercon.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.mercon.dao.StorageDao;
import com.mercon.entities.VdimStorageId;

@FacesConverter("storageConverter")
public class StorageConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || value.equals("")) {
			return null;
		} else {
			int index = Integer.parseInt(value);
			
			StorageDao storageDao = (StorageDao) facesContext.getExternalContext().getApplicationMap().get("storageService");
			
			VdimStorageId rtnValue = null;
			
			for (VdimStorageId storageId : storageDao.getStorageId()) {
				if (index == storageId.getNumEquipment()) {
					rtnValue = storageId;
					break;
				}
			}
			
			return rtnValue;
		}
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
		if (object != null && object instanceof VdimStorageId) {
			VdimStorageId storageId = (VdimStorageId) object;
			return String.valueOf(storageId.getNumEquipment());
		} else {
			return null;
		}
	}

}
