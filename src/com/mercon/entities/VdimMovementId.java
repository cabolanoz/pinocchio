package com.mercon.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;

import com.mercon.beans.ItineraryBean;
import com.mercon.beans.UserBean;
import com.mercon.cxl.Itinerary;
import com.mercon.cxl.ItineraryCXL;
import com.mercon.dao.CommodityDao;
import com.mercon.dao.CurrencyDao;
import com.mercon.dao.CurveDao;
import com.mercon.dao.FromTypeDao;
import com.mercon.dao.LoadDischargeDao;
import com.mercon.dao.LocationDao;
import com.mercon.dao.StrategyDao;
import com.mercon.dao.UnitOfMeasureDao;
import com.mercon.util.DefaultConstants;

@ManagedBean(name = "movementId")
@ViewScoped
public class VdimMovementId implements Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 6469041018451978917L;
	
	private ItineraryBean itinerary;
	
	private StrategyDao strategyDao;
	
	private CommodityDao commodityDao;
	
	private UnitOfMeasureDao unitOfMeasureDao;
	
	private CurrencyDao currencyDao;
	
	private CurveDao curveDao;
	
	private LocationDao locationDao;
	
	private LoadDischargeDao loadDischargeDao;
	
	private FromTypeDao fromTypeDao;
	
	private UserBean user;
	
	private int itineraryNum;
	private int equipmentNum;
	private int movementNum;
	private String motNum = "Pending";
	private VrefMotId motNumId;
	private VgenIndicatorId motTypeInd;
	private VdimLocationId departLocationNum;
	private VdimLocationId arriveLocationNum;
	private Date departDt;
	private int durationTm;
	private Date arriveDt;
	private VgenIndicatorId movementStatusInd;
	private String predecessorStr;
	private String routeCd;
	private int noteNum;
	private String ourRef;
	private String theirRef;
	private int statusInd;
	private Date createDt;
	private int createPersonNum;
	private Date lastModifyDt;
	private int modifyPersonNum;
	private int lockNum;
	private Date arrivalLaycanStartDt;
	private Date arrivalLaycanEndDt;
	private Date departureLaycanStartDt;
	private Date departureLaycanEndDt;
	private int arriveTerminalNum;
	private int departTerminalNum;
	private int arrivePortCompStatusInd;
	private int departPortCompStatusInd;
	private int arrivePortMisApprovInd;
	private int departPortMisApprovInd;
	private String successorStr;
	private int transfer;
	private int actionDetailNum = 0;
	private Date actionCompletionDt;
	private VgenNote note;

	private List<VdimCargoId> cargoLst = new ArrayList<VdimCargoId>();
	private VdimCargoId cargoId;
	
	private List<VdimTransferId> transferLst = new ArrayList<VdimTransferId>();
	private VdimTransferId transferId;
	
	public VdimMovementId() { }
	
	public VdimMovementId(int itineraryNum, int equipmentNum, int movementNum,
			String motNum, VgenIndicatorId motTypeInd, VdimLocationId departLocationNum,
			VdimLocationId arriveLocationNum, Date departDt, Date arriveDt,
			VgenIndicatorId movementStatusInd, String predecessorStr, String routeCd,
			int noteNum, String ourRef, String theirRef, int statusInd,
			Date createDt, int createPersonNum, Date lastModifyDt,
			int modifyPersonNum, int lockNum, Date arrivalLaycanStartDt,
			Date arrivalLaycanEndDt, Date departureLaycanStartDt,
			Date departureLaycanEndDt, int arriveTerminalNum,
			int departTerminalNum, int arrivePortCompStatusInd,
			int departPortCompStatusInd, int arrivePortMisApprovInd,
			int departPortMisApprovInd, String successorStr, int transfer) {
		super();
		this.itineraryNum = itineraryNum;
		this.equipmentNum = equipmentNum;
		this.movementNum = movementNum;
		this.motNum = motNum;
		this.motTypeInd = motTypeInd;
		this.departLocationNum = departLocationNum;
		this.arriveLocationNum = arriveLocationNum;
		this.departDt = departDt;
		this.arriveDt = arriveDt;
		this.movementStatusInd = movementStatusInd;
		this.predecessorStr = predecessorStr;
		this.routeCd = routeCd;
		this.noteNum = noteNum;
		this.ourRef = ourRef;
		this.theirRef = theirRef;
		this.statusInd = statusInd;
		this.createDt = createDt;
		this.createPersonNum = createPersonNum;
		this.lastModifyDt = lastModifyDt;
		this.modifyPersonNum = modifyPersonNum;
		this.lockNum = lockNum;
		this.arrivalLaycanStartDt = arrivalLaycanStartDt;
		this.arrivalLaycanEndDt = arrivalLaycanEndDt;
		this.departureLaycanStartDt = departureLaycanStartDt;
		this.departureLaycanEndDt = departureLaycanEndDt;
		this.arriveTerminalNum = arriveTerminalNum;
		this.departTerminalNum = departTerminalNum;
		this.arrivePortCompStatusInd = arrivePortCompStatusInd;
		this.departPortCompStatusInd = departPortCompStatusInd;
		this.arrivePortMisApprovInd = arrivePortMisApprovInd;
		this.departPortMisApprovInd = departPortMisApprovInd;
		this.successorStr = successorStr;
		this.transfer = transfer;
	}
	
	public ItineraryBean getItinerary() {
		return itinerary;
	}

	public void setItinerary(ItineraryBean itinerary) {
		this.itinerary = itinerary;
	}
	
	public StrategyDao getStrategyDao() {
		return strategyDao;
	}

	public void setStrategyDao(StrategyDao strategyDao) {
		this.strategyDao = strategyDao;
	}

	public CommodityDao getCommodityDao() {
		return commodityDao;
	}

	public void setCommodityDao(CommodityDao commodityDao) {
		this.commodityDao = commodityDao;
	}
	
	public UnitOfMeasureDao getUnitOfMeasureDao() {
		return unitOfMeasureDao;
	}

	public void setUnitOfMeasureDao(UnitOfMeasureDao unitOfMeasureDao) {
		this.unitOfMeasureDao = unitOfMeasureDao;
	}
	
	public CurrencyDao getCurrencyDao() {
		return currencyDao;
	}

	public void setCurrencyDao(CurrencyDao currencyDao) {
		this.currencyDao = currencyDao;
	}
	
	public CurveDao getCurveDao() {
		return curveDao;
	}

	public void setCurveDao(CurveDao curveDao) {
		this.curveDao = curveDao;
	}
	
	public LocationDao getLocationDao() {
		return locationDao;
	}

	public void setLocationDao(LocationDao locationDao) {
		this.locationDao = locationDao;
	}

	public LoadDischargeDao getLoadDischargeDao() {
		return loadDischargeDao;
	}

	public void setLoadDischargeDao(LoadDischargeDao loadDischargeDao) {
		this.loadDischargeDao = loadDischargeDao;
	}

	public FromTypeDao getFromTypeDao() {
		return fromTypeDao;
	}

	public void setFromTypeDao(FromTypeDao fromTypeDao) {
		this.fromTypeDao = fromTypeDao;
	}

	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}

	// Adding a new cargo
	public void addCargo(ActionEvent actionEvent) {
		if (cargoLst.isEmpty() || cargoLst.size() <= 0) {
			VdimCargoId cargoId = new VdimCargoId();
			
			// Setting internal company number
			cargoId.setNumCompany(itinerary.getTradeInputId().getInternalCompanyNum());
			
			// Setting cargo name
			cargoId.setNameCargo(itinerary.getTradeInputId().getTradeNum() + "-" + itinerary.getTradeInputId().getTradeQty().intValueExact() + "-" + itinerary.getTradeInputId().getDestination().substring(0, 1));
			
			VdimStrategyId strategyIdObj = null;
			
			for (VdimStrategyId strategyId : strategyDao.getStrategyId()) {
				if (strategyId.getNameStrategy().equals(itinerary.getTradeInputId().getCmdtyCd())) {
					strategyIdObj = strategyId;
					break;
				}
			}
			
			// Setting strategy
			cargoId.setNumStrategy(strategyIdObj);
			
			VdimCommodityId commodityIdObj = null;
			
			for (VdimCommodityId commodityId : commodityDao.getCommodityId()) {
				if (commodityId.getCodCommodity().equals(itinerary.getTradeInputId().getCmdtyCd())) {
					commodityIdObj = commodityId;
					break;
				}
			}
			
			// Setting commodity
			cargoId.setCommodityCod(commodityIdObj);
			
			VdimUnitOfMeasureId uomIdObj = null;
			
			for (VdimUnitOfMeasureId uomId : unitOfMeasureDao.getUnitOfMeasureId()) {
				if (uomId.getCodUom().equals(itinerary.getTradeInputId().getContractUomCd())) {
					uomIdObj = uomId;
					break;
				}
			}
			
			// Setting unit of measure
			cargoId.setQtyUomCod(uomIdObj);
			
			VdimUnitOfMeasureId prcUomIdObj = null;
			
			for (VdimUnitOfMeasureId uomId : unitOfMeasureDao.getUnitOfMeasureId()) {
				if (uomId.getCodUom().equals(itinerary.getTradeInputId().getPriceUomCd())) {
					prcUomIdObj = uomId;
					break;
				}
			}
			
			// Setting price uom code
			cargoId.setPrcUomCod(prcUomIdObj);
			
			VdimCurrencyId currencyIdObj = null;
			
			for (VdimCurrencyId currencyId : currencyDao.getCurrencyId()) {
				if (currencyId.getCodcurr().equals(itinerary.getTradeInputId().getSettlementCurrCd())) {
					currencyIdObj = currencyId;
					break;
				}
			}
			
			// Setting currency
			cargoId.setCurrPriceCod(currencyIdObj);
			
			VdimMktQuoteDefinitionId commodityStrategyIdObj = null;
			
			for (VdimMktQuoteDefinitionId commodityStrategyId : curveDao.getCommodityStrategyId()) {
				if (commodityStrategyId.getCoddefquote().equals(itinerary.getTradeInputId().getCmdtyCd())) {
					commodityStrategyIdObj = commodityStrategyId;
					break;
				}
			}
			
			// Setting MTM Curve
			cargoId.setMtmCurveNum(commodityStrategyIdObj);
			
			cargoLst.add(cargoId);
		} else {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.addMessage(null, new FacesMessage("You can't add more than one cargo"));
		}
	}
	
	// Removing a cargo
	public void removeCargo(VdimCargoId cargoId) {
		if (cargoId != null) {
			if (!itinerary.getId().equals("-1")) {
				// Set itinerary
				Itinerary it = new Itinerary();
				it.setItineraryNumber(Integer.parseInt(itinerary.getId()));
				it.setCargoId(cargoId);
				
				// Build xml
				ItineraryCXL itineraryCXL = new ItineraryCXL();
				itineraryCXL.setUser(user);
				itineraryCXL.setItinerary(it);
				
				boolean success = itineraryCXL.removeCargo();
				
				if (!success) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Unable to delete the cargo. Please contact your system administrator"));
				} else {
					BigDecimal availableCapacity = itinerary.getMissingTradeQty();
					BigDecimal currentCapacity = cargoId.getCapacity() == null ? BigDecimal.ZERO : cargoId.getCapacity();
					
					// Adding the subtracted amount 
					BigDecimal newAvailableCapacity = availableCapacity.add(currentCapacity);
					
					//Setting the new calculated capacity
					itinerary.setMissingTradeQty(newAvailableCapacity);
					
					// Removing the row
					cargoLst.remove(cargoId);
				}
			} else {
				BigDecimal availableCapacity = itinerary.getMissingTradeQty();
				BigDecimal currentCapacity = cargoId.getCapacity() == null ? BigDecimal.ZERO : cargoId.getCapacity();
				
				// Adding the subtracted amount 
				BigDecimal newAvailableCapacity = availableCapacity.add(currentCapacity);
				
				//Setting the new calculated capacity
				itinerary.setMissingTradeQty(newAvailableCapacity);
				
				// Removing the row
				cargoLst.remove(cargoId);
			}
		}
	}
	
	// On row edit
	public void onRowEdit(RowEditEvent rowEditEvent) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
		if (rowEditEvent.getObject() instanceof VdimCargoId) {
			VdimCargoId cargoId = (VdimCargoId) rowEditEvent.getObject();
			
			BigDecimal currentCapacity = itinerary.getMissingTradeQty();
			BigDecimal newCapacity = cargoId.getCapacity();
			
			if (newCapacity.compareTo(currentCapacity) == 1) {
				facesContext.validationFailed();
				facesContext.addMessage(null, new FacesMessage("There's not available quantity for this cargo"));
			} else {
				// Deducting the inserted capacity from the available capacity
				BigDecimal newAvailableCapacity = currentCapacity.subtract(newCapacity);
				
				// Setting the new calculated capacity
				itinerary.setMissingTradeQty(newAvailableCapacity);
			}
		}
	}
	
	// On row cancel
	public void onRowCancel(RowEditEvent rowEditEvent) {
		if (rowEditEvent.getObject() instanceof VdimCargoId) {
			VdimCargoId cargoId = (VdimCargoId) rowEditEvent.getObject();
			
			BigDecimal currentCapacity = itinerary.getMissingTradeQty();
			BigDecimal newCapacity = cargoId.getCapacity() == null ? BigDecimal.ZERO : cargoId.getCapacity();
			
			if(newCapacity.compareTo(currentCapacity) == 1) {
				cargoId.setCapacity(null);
			}
		}
	}
	
	public void onChangeDepartDate(SelectEvent selectEvent) {
		if (selectEvent.getObject() != null) {
			this.departDt = (Date) selectEvent.getObject();
		}
	}
	
	public void onChangeDuration() {
		if (this.departDt != null) {
			Date departDt = this.departDt;
			
			if (this.durationTm > 0) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(departDt);
				calendar.add(Calendar.DATE, this.durationTm);
				
				this.arriveDt = calendar.getTime();
			}
		}
	}
	
	public void onCellEditForCargo(CellEditEvent cellEditEvent) {
		String[] clientId = cellEditEvent.getColumn().getClientId().split(":");
		String columnId = clientId[clientId.length - 1];
		
		if ("txtQuantity".equals(columnId)) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			
			BigDecimal oldValue = cellEditEvent.getOldValue() != null ? new BigDecimal(cellEditEvent.getOldValue().toString()) : BigDecimal.ZERO;
			BigDecimal newValue = cellEditEvent.getNewValue() != null ? new BigDecimal(cellEditEvent.getNewValue().toString()) : BigDecimal.ZERO;
			
			BigDecimal currentCapacity = itinerary.getMissingTradeQty();
			
			if (newValue.compareTo(currentCapacity) == 1) {
				facesContext.validationFailed();
				facesContext.addMessage(null, new FacesMessage("There's not available quantity for this cargo"));
			} else {
				if ((oldValue.compareTo(BigDecimal.ZERO) == 0 || oldValue.compareTo(BigDecimal.ZERO) == 1) && (newValue.compareTo(BigDecimal.ZERO) == 0 || newValue.compareTo(BigDecimal.ZERO) == 1)) {
					BigDecimal newAvailableCapacity = currentCapacity.add(oldValue).subtract(newValue);
					
					itinerary.setMissingTradeQty(newAvailableCapacity);
				} else {
					facesContext.validationFailed();
					facesContext.addMessage(null, new FacesMessage("You must set a positive value for this cargo"));
				}
			}
		}
	}
	
	// Adding a new transfer
	public void addTransfer(ActionEvent actionEvent) {
		VdimTransferId dimTransferId = new VdimTransferId();
		
		dimTransferId.setItinerary(this.itinerary);
		
		for (VgenIndicatorId loadDischarge : this.loadDischargeDao.getLoadDischargeId()) {
			if (loadDischarge.getIndValueName().equals(DefaultConstants.C_LOAD_FROM)) {
				dimTransferId.setLoadDischargeObj(loadDischarge);
				break;
			}
		}
		
		for (VgenIndicatorId fromType : this.fromTypeDao.getFromTypeId()) {
			if (fromType.getIndValueName().equals(DefaultConstants.C_TRADE)) {
				dimTransferId.setFromTypeObj(fromType);
				break;
			}
		}
		
		for (VgenIndicatorId toType : this.fromTypeDao.getFromTypeId()) {
			if (toType.getIndValueName().equals(DefaultConstants.C_VESSEL)) {
				dimTransferId.setToTypeObj(toType);
				break;
			}
		}
		
		Double convertionFactor = this.unitOfMeasureDao.getConversionFactor(this.itinerary.getTradeInputId().getContractUomCd(), this.getCargoLst().get(0).getQtyUomCod().getCodUom());
		
		dimTransferId.setQtyTo(new BigDecimal(this.itinerary.getTradeInputId().getTradeQty().doubleValue() * convertionFactor));
		
		dimTransferId.setLocationObj(this.departLocationNum);
		
		dimTransferId.setDtTransferComm(this.departDt);
		
		this.transferLst.add(dimTransferId);
		
		// Add default tag
		dimTransferId.addTag(actionEvent);
	}
	
	public void removeTransfer(VdimTransferId transferId) {
		if (transferId != null) {
			this.transferLst.remove(transferId);
		}
	}
	
	public void onOpenTagAllocation() {
		Map<String, Object> opt = new HashMap<String, Object>();
		opt.put("contentWidth", 1024);
		opt.put("draggable", false);
		opt.put("modal", true);
		
		List<String> optLst = new ArrayList<String>();
		optLst.add(String.valueOf(this.equipmentNum)); // Equipment number
		optLst.add(String.valueOf(this.getCargoLst().get(0).getNumCargo())); // Cargo number
		optLst.add(String.valueOf(this.getCargoLst().get(0).getNumStategy())); // Strategy number
		
		Map<String, List<String>> optView = new HashMap<String, List<String>>();
		optView.put("params", optLst);
		
		RequestContext.getCurrentInstance().openDialog("itineraryfake", opt, optView);
	}
	
	public void onSavedTagAllocation(SelectEvent selectEvent) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
		int success = (int) selectEvent.getObject();
		
		if (success == 1) {
			facesContext.addMessage(null, new FacesMessage("Tags allocation successfully saved"));
		} else if (success == -1) {
			facesContext.addMessage(null, new FacesMessage("Unable to allocate tags. Please contact your system administrator"));
		}
		
		if (success == 1 || success == -1) {
			RequestContext.getCurrentInstance().update(":frmItinerary:tblMovement");
		}
	}
	
	public List<VdimTransferId> getTransferLst() {
		return transferLst;
	}

	public void setTransferLst(List<VdimTransferId> transferLst) {
		this.transferLst = transferLst;
	}
	
	public VdimTransferId getTransferId() {
		return transferId;
	}

	public void setTransferId(VdimTransferId transferId) {
		this.transferId = transferId;
	}

	public List<VdimCargoId> getCargoLst() {
		return cargoLst;
	}

	public void setCargoLst(List<VdimCargoId> cargoLst) {
		this.cargoLst = cargoLst;
	}	

	public VdimCargoId getCargoId() {
		return cargoId;
	}

	public void setCargoId(VdimCargoId cargoId) {
		this.cargoId = cargoId;
	}

	public int getItineraryNum() {
		return itineraryNum;
	}
	public void setItineraryNum(int itineraryNum) {
		this.itineraryNum = itineraryNum;
	}
	public int getEquipmentNum() {
		return equipmentNum;
	}
	public void setEquipmentNum(int equipmentNum) {
		this.equipmentNum = equipmentNum;
	}
	public int getMovementNum() {
		return movementNum;
	}
	public void setMovementNum(int movementNum) {
		this.movementNum = movementNum;
	}
	public String getMotNum() {
		return motNum;
	}
	public void setMotNum(String motNum) {
		this.motNum = motNum;
	}
	
	public VrefMotId getMotNumId() {
		return motNumId;
	}

	public void setMotNumId(VrefMotId motNumId) {
		this.motNumId = motNumId;
	}

	public VgenIndicatorId getMotTypeInd() {
		return motTypeInd;
	}
	public void setMotTypeInd(VgenIndicatorId motTypeInd) {
		this.motTypeInd = motTypeInd;
	}
	public VdimLocationId getDepartLocationNum() {
		return departLocationNum;
	}
	public void setDepartLocationNum(VdimLocationId departLocationNum) {
		this.departLocationNum = departLocationNum;
	}
	public VdimLocationId getArriveLocationNum() {
		return arriveLocationNum;
	}
	public void setArriveLocationNum(VdimLocationId arriveLocationNum) {
		this.arriveLocationNum = arriveLocationNum;
	}
	public Date getDepartDt() {
		return departDt;
	}
	public void setDepartDt(Date departDt) {
		this.departDt = departDt;
	}
	public int getDurationTm() {
		return durationTm;
	}
	public void setDurationTm(int durationTm) {
		this.durationTm = durationTm;
	}

	public Date getArriveDt() {
		return arriveDt;
	}
	public void setArriveDt(Date arriveDt) {
		this.arriveDt = arriveDt;
	}
	public VgenIndicatorId getMovementStatusInd() {
		return movementStatusInd;
	}
	public void setMovementStatusInd(VgenIndicatorId movementStatusInd) {
		this.movementStatusInd = movementStatusInd;
	}
	public String getPredecessorStr() {
		return predecessorStr;
	}
	public void setPredecessorStr(String predecessorStr) {
		this.predecessorStr = predecessorStr;
	}
	public String getRouteCd() {
		return routeCd;
	}
	public void setRouteCd(String routeCd) {
		this.routeCd = routeCd;
	}
	public int getNoteNum() {
		return noteNum;
	}
	public void setNoteNum(int noteNum) {
		this.noteNum = noteNum;
	}
	public String getOurRef() {
		return ourRef;
	}
	public void setOurRef(String ourRef) {
		this.ourRef = ourRef;
	}
	public String getTheirRef() {
		return theirRef;
	}
	public void setTheirRef(String theirRef) {
		this.theirRef = theirRef;
	}
	public int getStatusInd() {
		return statusInd;
	}
	public void setStatusInd(int statusInd) {
		this.statusInd = statusInd;
	}
	public Date getCreateDt() {
		return createDt;
	}
	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}
	public int getCreatePersonNum() {
		return createPersonNum;
	}
	public void setCreatePersonNum(int createPersonNum) {
		this.createPersonNum = createPersonNum;
	}
	public Date getLastModifyDt() {
		return lastModifyDt;
	}
	public void setLastModifyDt(Date lastModifyDt) {
		this.lastModifyDt = lastModifyDt;
	}
	public int getModifyPersonNum() {
		return modifyPersonNum;
	}
	public void setModifyPersonNum(int modifyPersonNum) {
		this.modifyPersonNum = modifyPersonNum;
	}
	public int getLockNum() {
		return lockNum;
	}
	public void setLockNum(int lockNum) {
		this.lockNum = lockNum;
	}
	public Date getArrivalLaycanStartDt() {
		return arrivalLaycanStartDt;
	}
	public void setArrivalLaycanStartDt(Date arrivalLaycanStartDt) {
		this.arrivalLaycanStartDt = arrivalLaycanStartDt;
	}
	public Date getArrivalLaycanEndDt() {
		return arrivalLaycanEndDt;
	}
	public void setArrivalLaycanEndDt(Date arrivalLaycanEndDt) {
		this.arrivalLaycanEndDt = arrivalLaycanEndDt;
	}
	public Date getDepartureLaycanStartDt() {
		return departureLaycanStartDt;
	}
	public void setDepartureLaycanStartDt(Date departureLaycanStartDt) {
		this.departureLaycanStartDt = departureLaycanStartDt;
	}
	public Date getDepartureLaycanEndDt() {
		return departureLaycanEndDt;
	}
	public void setDepartureLaycanEndDt(Date departureLaycanEndDt) {
		this.departureLaycanEndDt = departureLaycanEndDt;
	}
	public int getArriveTerminalNum() {
		return arriveTerminalNum;
	}
	public void setArriveTerminalNum(int arriveTerminalNum) {
		this.arriveTerminalNum = arriveTerminalNum;
	}
	public int getDepartTerminalNum() {
		return departTerminalNum;
	}
	public void setDepartTerminalNum(int departTerminalNum) {
		this.departTerminalNum = departTerminalNum;
	}
	public int getArrivePortCompStatusInd() {
		return arrivePortCompStatusInd;
	}
	public void setArrivePortCompStatusInd(int arrivePortCompStatusInd) {
		this.arrivePortCompStatusInd = arrivePortCompStatusInd;
	}
	public int getDepartPortCompStatusInd() {
		return departPortCompStatusInd;
	}
	public void setDepartPortCompStatusInd(int departPortCompStatusInd) {
		this.departPortCompStatusInd = departPortCompStatusInd;
	}
	public int getArrivePortMisApprovInd() {
		return arrivePortMisApprovInd;
	}
	public void setArrivePortMisApprovInd(int arrivePortMisApprovInd) {
		this.arrivePortMisApprovInd = arrivePortMisApprovInd;
	}
	public int getDepartPortMisApprovInd() {
		return departPortMisApprovInd;
	}
	public void setDepartPortMisApprovInd(int departPortMisApprovInd) {
		this.departPortMisApprovInd = departPortMisApprovInd;
	}
	public String getSuccessorStr() {
		return successorStr;
	}
	public void setSuccessorStr(String successorStr) {
		this.successorStr = successorStr;
	}
	
	public int getTransfer() {
		return transfer;
	}

	public void setTransfer(int transfer) {
		this.transfer = transfer;
	}

	public int getActionDetailNum() {
		return actionDetailNum;
	}

	public void setActionDetailNum(int actionDetailNum) {
		this.actionDetailNum = actionDetailNum;
	}

	public Date getActionCompletionDt() {
		return actionCompletionDt;
	}

	public void setActionCompletionDt(Date actionCompletionDt) {
		this.actionCompletionDt = actionCompletionDt;
	}

	public VgenNote getNote() {
		return note;
	}

	public void setNote(VgenNote note) {
		this.note = note;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((arrivalLaycanEndDt == null) ? 0 : arrivalLaycanEndDt
						.hashCode());
		result = prime
				* result
				+ ((arrivalLaycanStartDt == null) ? 0 : arrivalLaycanStartDt
						.hashCode());
		result = prime * result
				+ ((arriveDt == null) ? 0 : arriveDt.hashCode());
		result = prime * result + arrivePortCompStatusInd;
		result = prime * result + arrivePortMisApprovInd;
		result = prime * result + arriveTerminalNum;
		result = prime * result
				+ ((createDt == null) ? 0 : createDt.hashCode());
		result = prime * result + createPersonNum;
		result = prime * result
				+ ((departDt == null) ? 0 : departDt.hashCode());
		result = prime * result + departPortCompStatusInd;
		result = prime * result + departPortMisApprovInd;
		result = prime * result + departTerminalNum;
		result = prime
				* result
				+ ((departureLaycanEndDt == null) ? 0 : departureLaycanEndDt
						.hashCode());
		result = prime
				* result
				+ ((departureLaycanStartDt == null) ? 0
						: departureLaycanStartDt.hashCode());
		result = prime * result + equipmentNum;
		result = prime * result + itineraryNum;
		result = prime * result
				+ ((lastModifyDt == null) ? 0 : lastModifyDt.hashCode());
		result = prime * result + lockNum;
		result = prime * result + modifyPersonNum;
		result = prime * result + movementNum;
		result = prime * result + noteNum;
		result = prime * result + ((ourRef == null) ? 0 : ourRef.hashCode());
		result = prime * result
				+ ((predecessorStr == null) ? 0 : predecessorStr.hashCode());
		result = prime * result + ((routeCd == null) ? 0 : routeCd.hashCode());
		result = prime * result
				+ ((successorStr == null) ? 0 : successorStr.hashCode());
		result = prime * result
				+ ((theirRef == null) ? 0 : theirRef.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VdimMovementId other = (VdimMovementId) obj;
		if (arrivalLaycanEndDt == null) {
			if (other.arrivalLaycanEndDt != null)
				return false;
		} else if (!arrivalLaycanEndDt.equals(other.arrivalLaycanEndDt))
			return false;
		if (arrivalLaycanStartDt == null) {
			if (other.arrivalLaycanStartDt != null)
				return false;
		} else if (!arrivalLaycanStartDt.equals(other.arrivalLaycanStartDt))
			return false;
		if (arriveDt == null) {
			if (other.arriveDt != null)
				return false;
		} else if (!arriveDt.equals(other.arriveDt))
			return false;
		if (arriveLocationNum != other.arriveLocationNum)
			return false;
		if (arrivePortCompStatusInd != other.arrivePortCompStatusInd)
			return false;
		if (arrivePortMisApprovInd != other.arrivePortMisApprovInd)
			return false;
		if (arriveTerminalNum != other.arriveTerminalNum)
			return false;
		if (createDt == null) {
			if (other.createDt != null)
				return false;
		} else if (!createDt.equals(other.createDt))
			return false;
		if (createPersonNum != other.createPersonNum)
			return false;
		if (departDt == null) {
			if (other.departDt != null)
				return false;
		} else if (!departDt.equals(other.departDt))
			return false;
		if (departLocationNum != other.departLocationNum)
			return false;
		if (departPortCompStatusInd != other.departPortCompStatusInd)
			return false;
		if (departPortMisApprovInd != other.departPortMisApprovInd)
			return false;
		if (departTerminalNum != other.departTerminalNum)
			return false;
		if (departureLaycanEndDt == null) {
			if (other.departureLaycanEndDt != null)
				return false;
		} else if (!departureLaycanEndDt.equals(other.departureLaycanEndDt))
			return false;
		if (departureLaycanStartDt == null) {
			if (other.departureLaycanStartDt != null)
				return false;
		} else if (!departureLaycanStartDt.equals(other.departureLaycanStartDt))
			return false;
		if (equipmentNum != other.equipmentNum)
			return false;
		if (itineraryNum != other.itineraryNum)
			return false;
		if (lastModifyDt == null) {
			if (other.lastModifyDt != null)
				return false;
		} else if (!lastModifyDt.equals(other.lastModifyDt))
			return false;
		if (lockNum != other.lockNum)
			return false;
		if (modifyPersonNum != other.modifyPersonNum)
			return false;
		if (motNum != other.motNum)
			return false;
		if (motTypeInd != other.motTypeInd)
			return false;
		if (movementNum != other.movementNum)
			return false;
		if (movementStatusInd != other.movementStatusInd)
			return false;
		if (noteNum != other.noteNum)
			return false;
		if (ourRef == null) {
			if (other.ourRef != null)
				return false;
		} else if (!ourRef.equals(other.ourRef))
			return false;
		if (predecessorStr == null) {
			if (other.predecessorStr != null)
				return false;
		} else if (!predecessorStr.equals(other.predecessorStr))
			return false;
		if (routeCd == null) {
			if (other.routeCd != null)
				return false;
		} else if (!routeCd.equals(other.routeCd))
			return false;
		if (statusInd != other.statusInd)
			return false;
		if (successorStr == null) {
			if (other.successorStr != null)
				return false;
		} else if (!successorStr.equals(other.successorStr))
			return false;
		if (theirRef == null) {
			if (other.theirRef != null)
				return false;
		} else if (!theirRef.equals(other.theirRef))
			return false;
		return true;
	}
	
}
