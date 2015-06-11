package com.mercon.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.mercon.dao.CargoDao;
import com.mercon.entities.VdimCargo;

@FacesConverter("cargoConverter")
public class CargoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || value.equals("")) {
			return null;
		} else {
			int index = Integer.parseInt(value);
			
			CargoDao cargoDao = (CargoDao) facesContext.getExternalContext().getApplicationMap().get("cargoService");
			
			VdimCargo rtnValue = null;
			
			if (cargoDao != null) {
				for (VdimCargo dimCargo : cargoDao.getLevelLst()) {
					if (dimCargo.getId().getNumCargo() == index) {
						rtnValue = dimCargo;
						break;
					}
				}
			}
			
//			try {
//				rtnValue = cargoDao.getCargoById(index);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
			
			return rtnValue;
		}
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
		if(object != null && object instanceof VdimCargo) {
			VdimCargo dimCargo = (VdimCargo) object;
            return String.valueOf(dimCargo.getId().getNumCargo());
        } else {
            return null;
        }
	}
	
}
