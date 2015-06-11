package com.mercon.datamodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.mercon.dao.PersonDao;
import com.mercon.entities.VdimPersonId;
import com.mercon.entities.VopsItinerary;
import com.mercon.entities.VopsItineraryId;
import com.mercon.util.DefaultConstants;

public class ItineraryLazyDataModel extends LazyDataModel<VopsItineraryId> implements DefaultConstants {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 299278486735287893L;
	
	@Override
	public Object getRowKey(VopsItineraryId opsItineraryId) {
		return opsItineraryId.getItineraryNum();
	}
	
	@Override
	public List<VopsItineraryId> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
//		if (!FacesContext.getCurrentInstance().getRenderResponse()) {
//			return null;
//		}
		
		String filter = "";
		
		if (filters != null) {
			if (filters.get("globalFilter") != null) {
				filter = filters.get("globalFilter").toString();
			}
		}
		
		List<VopsItineraryId> opsItinerariesId = new ArrayList<VopsItineraryId>();
		
		ClientRequest clientRequest = new ClientRequest((IS_PRODUCTION ? PRODUCTION_JBOSS_HOST : DEVELOPER_JBOSS_HOST) + "/CXLRWS/Itinerary/getAllByPage?first=" + first + "&pageSize=" + pageSize + "&filter=" + filter);
		clientRequest.accept("application/json;charset=UTF-8");
		
		ClientResponse<Object> clientResponse = null;
		
		try {
			clientResponse = clientRequest.get(Object.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (clientResponse.getStatus() != 200) {
//			try {
//				throw new Exception("Error getting itinerary: " + ((ErrorResponse) clientResponse.getEntity(ErrorResponse.class)).getMessage());
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
		} else {
			VopsItinerary[] opsItinerary = (VopsItinerary[]) clientResponse.getEntity(VopsItinerary[].class);
			
			List<VopsItinerary> opsItineraries = Arrays.asList(opsItinerary);
			
			if (!opsItineraries.isEmpty() && opsItineraries.size() > 0) {
				for (VopsItinerary itinerary : opsItineraries) {
					opsItinerariesId.add(itinerary.getId());
				}
			}
		}
		
		int itinerarySize = this.getItinerarySize(filter);
		
		setRowCount(itinerarySize);
		
		if (!opsItinerariesId.isEmpty() && opsItinerariesId.size() > 0) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			
			PersonDao personDao = (PersonDao) facesContext.getExternalContext().getApplicationMap().get("personService");
			
			List<VdimPersonId> dimPersonsId = null;
			
			try {
				dimPersonsId = personDao.getAllOperators();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			for (VopsItineraryId opsItineraryId : opsItinerariesId) {
				if (dimPersonsId != null) {
					for (VdimPersonId dimPersonId : dimPersonsId) {
						if (opsItineraryId.getOperatorPersonNum() == dimPersonId.getNumperson()) {
							opsItineraryId.setOperatorPerson(dimPersonId);
							break;
						}
					}
				}
			}
		}
		
		return opsItinerariesId;
	}
	
	private int getItinerarySize(String filter) {
		ClientRequest clientRequest = new ClientRequest((IS_PRODUCTION ? PRODUCTION_JBOSS_HOST : DEVELOPER_JBOSS_HOST) + "/CXLRWS/Itinerary/getItinerarySize?filter=" + filter);
		clientRequest.accept("application/json;charset=UTF-8");
		
		ClientResponse<Object> clientResponse = null;
		
		try {
			clientResponse = clientRequest.get(Object.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (clientResponse.getStatus() != 200) {
//			try {
//				throw new Exception("Error getting itinerary: " + ((ErrorResponse) clientResponse.getEntity(ErrorResponse.class)).getMessage());
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
		} else {
			Integer iSize = (Integer) clientResponse.getEntity();
			
			return iSize;
		}
		
		return -1;
	}
	
}
