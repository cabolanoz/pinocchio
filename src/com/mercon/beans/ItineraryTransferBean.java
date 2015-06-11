package com.mercon.beans;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;

import com.mercon.cxl.Transfer;
import com.mercon.cxl.TransferCXL;
import com.mercon.dao.CargoDao;
import com.mercon.dao.FromTypeDao;
import com.mercon.dao.ItineraryDao;
import com.mercon.dao.LoadDischargeDao;
import com.mercon.dao.LocationDao;
import com.mercon.dao.TradeDao;
import com.mercon.dao.TransferAtDao;
import com.mercon.dao.UnitOfMeasureDao;
import com.mercon.entities.VbuildDrawTag;
import com.mercon.entities.VbuildDrawTagId;
import com.mercon.entities.VdimCargo;
import com.mercon.entities.VdimCargoId;
import com.mercon.entities.VdimLocationId;
import com.mercon.entities.VdimMovementId;
import com.mercon.entities.VdimStorageId;
import com.mercon.entities.VdimTransferId;
import com.mercon.entities.VgenIndicatorId;
import com.mercon.entities.VtradeHdr;
import com.mercon.entities.VtradeInputId;
import com.mercon.util.DefaultConstants;

@ManagedBean(name = "itineraryTransfer")
@ViewScoped
public class ItineraryTransferBean implements Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 8267464095844719410L;

	/**
	 * Managed Beans
	 */
	@ManagedProperty("#{user}")
	private UserBean user;
	
	@ManagedProperty("#{loadDischargeService}")
	private LoadDischargeDao loadDischargeDao;
	
	@ManagedProperty("#{fromTypeService}")
	private FromTypeDao fromTypeDao;
	
	@ManagedProperty("#{itineraryService}")
	private ItineraryDao itineraryDao;
	
	@ManagedProperty("#{tradeService}")
	private TradeDao tradeDao;
	
	@ManagedProperty("#{cargoService}")
	private CargoDao cargoDao;
	
	@ManagedProperty("#{locationService}")
	private LocationDao locationDao;
	
	@ManagedProperty("#{uomService}")
	private UnitOfMeasureDao unitOfMeasureDao;
	
	@ManagedProperty("#{transferAtService}")
	private TransferAtDao transferAtDao;
	
	/**
	 * Variables
	 */
	private VgenIndicatorId loadDischargeObj;
	private VgenIndicatorId optionObj;
	private VdimLocationId locationObj;
	private Date effectiveDt;
	private Date commencementDt;
	private String blNumber;
	private Date blDate;
	
	/**
	 * Trade variables
	 */
	private VtradeHdr tradeHdr;
	private VtradeInputId tradeInput;
	private BigDecimal scheduledQty;
	private BigDecimal nominalQty;
	
	/**
	 * Storage variables
	 */
	private VdimStorageId storage;
	private VdimCargo level;
	private List<VdimCargo> levelLst = new ArrayList<VdimCargo>();
	private BigDecimal storageQty;
	
	/**
	 * Cargo variables
	 */
	private VdimMovementId vehicle;
	private VdimCargoId cargo;
	private BigDecimal cargoQty;
	private VgenIndicatorId transferAt;
	
	/**
	 * From Variables
	 */
	private boolean fromTrade = true;
	private boolean fromStorage = false;
	private boolean fromVessel = false;
	private boolean fromBarge = false;
	private boolean fromTruck = false;
	private boolean fromPipeline = false;
	private boolean fromRailcar = false;
	private boolean fromStorageSvc = false;
	
	/**
	 * To Variables
	 */
	private boolean toTrade = false;
	private boolean toStorage = false;
	private boolean toVessel = true;
	private boolean toBarge = false;
	private boolean toTruck = false;
	private boolean toPipeline = false;
	private boolean toRailcar = false;
	private boolean toStorageSvc = false;
	
	private List<VbuildDrawTag> tagLst = new ArrayList<VbuildDrawTag>();
	
	public ItineraryTransferBean() { }

	public void onAddTransfer(VdimMovementId movementId) {
		if (movementId.getEquipmentNum() <= 0) {
			RequestContext.getCurrentInstance().execute("hideDlgTransfer()");
			
			RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, "Shipping Manager", "Movement and cargo must be saved first before adding a transfer"));
			
			return;
		}
		
		if (this.vehicle != null && this.vehicle.getMovementNum() != movementId.getMovementNum()) {
			this.tradeInput = null;
		}
		
		if (this.tradeInput == null) {
			for (VgenIndicatorId loadDischargeVal : this.loadDischargeDao.getLoadDischargeId()) {
				if (loadDischargeVal.getIndValue() == DefaultConstants.LOAD_FROM_IND) {
					this.loadDischargeObj = loadDischargeVal;
					break;
				}
			}
			
			for (VgenIndicatorId fromTypeVal : this.fromTypeDao.getFromTypeId()) {
				if (fromTypeVal.getIndValue() == DefaultConstants.TRADE_IND) {
					this.optionObj = fromTypeVal;
					break;
				}
			}
			
			this.tradeHdr = movementId.getItinerary().getTradeHdr();
			this.tradeInput = movementId.getItinerary().getTradeInputId();
			this.scheduledQty = movementId.getCargoLst().get(0).getCapacity();
			this.nominalQty = movementId.getCargoLst().get(0).getCapacity();
			this.storageQty = movementId.getCargoLst().get(0).getCapacity();
			this.vehicle = movementId;
			this.cargo = movementId.getCargoLst().get(0);
			this.cargoQty = movementId.getCargoLst().get(0).getCapacity();
			
			for (VdimLocationId locationId : this.locationDao.getLocationId()) {
				if (this.vehicle.getDepartLocationNum().getNumLocation() == locationId.getNumLocation()) {
					this.locationObj = locationId;
					break;
				}
			}
			
			for (VgenIndicatorId transferAtId : this.transferAtDao.getTransferAtId()) {
				if (transferAtId.getIndValue() == 1) { // 1: Contract Price Plus Costs
					this.transferAt = transferAtId;
					break;
				}
			}
			
			this.effectiveDt = this.vehicle.getDepartDt();
			this.commencementDt = this.vehicle.getDepartDt();
			this.blNumber = "";
			this.blDate = null;
			
			// Changing panels
			this.onLoadDischargeSelect();
			
			// Removing tags if exist
			this.onRemoveAllTag(null);
		}
	}
	
	public void onLoadDischargeSelect() {
		if (this.vehicle != null && this.getLoadDischargeObj() != null) {
			for (VdimLocationId locationId : this.locationDao.getLocationId()) {
				if (this.loadDischargeObj.getIndValue() == DefaultConstants.LOAD_FROM_IND && this.vehicle.getDepartLocationNum().getNumLocation() == locationId.getNumLocation()) {
					this.locationObj = locationId;
					break;
				} else if (this.loadDischargeObj.getIndValue() == DefaultConstants.DISCHARGE_TO_IND && this.vehicle.getArriveLocationNum().getNumLocation() == locationId.getNumLocation()) {
					this.locationObj = locationId;
					break;
				}
			}
		}
		
		this.onFromTypeSelect();
	}
	
	public void onFromTypeSelect() {
		if (this.getLoadDischargeObj().getIndValue() == DefaultConstants.LOAD_FROM_IND) {
			this.effectiveDt = this.vehicle.getDepartDt();
			this.commencementDt = this.vehicle.getDepartDt();
			
			if (this.getOptionObj() != null) {
				switch (this.getOptionObj().getIndValue()) {
					case DefaultConstants.TRADE_IND:
						this.fromTrade = true;
						this.fromStorage = false;
						this.fromVessel = false;

						this.toTrade = false;
						this.toStorage = false;
						this.toVessel = true;
						break;
					case DefaultConstants.STORAGE_IND:
						this.fromTrade = false;
						this.fromStorage = true;
						this.fromVessel = false;

						this.toTrade = false;
						this.toStorage = false;
						this.toVessel = true;
						break;
					case DefaultConstants.VESSEL_IND:
						this.fromTrade = false;
						this.fromStorage = false;
						this.fromVessel = false;

						this.toTrade = false;
						this.toStorage = false;
						this.toVessel = false;
						break;
					case DefaultConstants.BARGE_IND:
						this.fromTrade = false;
						this.fromStorage = false;
						this.fromVessel = false;

						this.toTrade = false;
						this.toStorage = false;
						this.toVessel = false;
						break;
					case DefaultConstants.TRUCK_IND:
						this.fromTrade = false;
						this.fromStorage = false;
						this.fromVessel = false;

						this.toTrade = false;
						this.toStorage = false;
						this.toVessel = false;
						break;
					case DefaultConstants.PIPELINE_IND:
						this.fromTrade = false;
						this.fromStorage = false;
						this.fromVessel = false;

						this.toTrade = false;
						this.toStorage = false;
						this.toVessel = false;
						break;
					case DefaultConstants.RAILCAR_IND:
						this.fromTrade = false;
						this.fromStorage = false;
						this.fromVessel = false;

						this.toTrade = false;
						this.toStorage = false;
						this.toVessel = false;
						break;
					case DefaultConstants.STORAGE_SVC_IND:
						this.fromTrade = false;
						this.fromStorage = false;
						this.fromVessel = false;

						this.toTrade = false;
						this.toStorage = false;
						this.toVessel = false;
						break;
				}
			}
		} else if (this.getLoadDischargeObj().getIndValue() == DefaultConstants.DISCHARGE_TO_IND) {
			if (this.getOptionObj() != null) {
				switch (this.getOptionObj().getIndValue()) {
					case DefaultConstants.TRADE_IND:
						this.fromTrade = false;
						this.fromStorage = false;
						this.fromVessel = true;

						this.toTrade = true;
						this.toStorage = false;
						this.toVessel = false;
						
						this.effectiveDt = this.vehicle.getDepartDt();
						this.commencementDt = this.vehicle.getArriveDt();
						break;
					case DefaultConstants.STORAGE_IND:
						this.fromTrade = false;
						this.fromStorage = false;
						this.fromVessel = true;

						this.toTrade = false;
						this.toStorage = true;
						this.toVessel = false;
						
						this.effectiveDt = this.vehicle.getArriveDt();
						
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(this.vehicle.getArriveDt());
						
						calendar.add(Calendar.DAY_OF_YEAR, 7);
						
						this.commencementDt = calendar.getTime();
						break;
					case DefaultConstants.VESSEL_IND:
						this.fromTrade = false;
						this.fromStorage = false;
						this.fromVessel = false;

						this.toTrade = false;
						this.toStorage = false;
						this.toVessel = false;
						break;
					case DefaultConstants.BARGE_IND:
						this.fromTrade = false;
						this.fromStorage = false;
						this.fromVessel = false;

						this.toTrade = false;
						this.toStorage = false;
						this.toVessel = false;
						break;
					case DefaultConstants.TRUCK_IND:
						this.fromTrade = false;
						this.fromStorage = false;
						this.fromVessel = false;

						this.toTrade = false;
						this.toStorage = false;
						this.toVessel = false;
						break;
					case DefaultConstants.PIPELINE_IND:
						this.fromTrade = false;
						this.fromStorage = false;
						this.fromVessel = false;

						this.toTrade = false;
						this.toStorage = false;
						this.toVessel = false;
						break;
					case DefaultConstants.RAILCAR_IND:
						this.fromTrade = false;
						this.fromStorage = false;
						this.fromVessel = false;

						this.toTrade = false;
						this.toStorage = false;
						this.toVessel = false;
						break;
					case DefaultConstants.STORAGE_SVC_IND:
						this.fromTrade = false;
						this.fromStorage = false;
						this.fromVessel = false;

						this.toTrade = false;
						this.toStorage = false;
						this.toVessel = false;
						break;
				}
			}
		}
	}
	
	public void onQtyChange(String index) {
		switch (index) {
			case "0":
				if (this.scheduledQty.compareTo(BigDecimal.ZERO) == 1) {
					if (this.scheduledQty.compareTo(this.vehicle.getCargoLst().get(0).getCapacity()) == -1 || this.scheduledQty.compareTo(this.vehicle.getCargoLst().get(0).getCapacity()) == 0) {
						this.nominalQty = this.scheduledQty;
						this.cargoQty = this.scheduledQty;
						this.storageQty = this.scheduledQty;
					}
				}
				
				break;
			case "1":
				if (this.cargoQty.compareTo(BigDecimal.ZERO) == 1) {
					if (this.cargoQty.compareTo(this.vehicle.getCargoLst().get(0).getCapacity()) == -1 || this.cargoQty.compareTo(this.vehicle.getCargoLst().get(0).getCapacity()) == 0) {
						this.scheduledQty = this.cargoQty;
						this.nominalQty = this.cargoQty;
						this.storageQty = this.cargoQty;
					}
				}
				
				break;
				
			case "2":
				if (this.storageQty.compareTo(BigDecimal.ZERO) == 1) {
					if (this.storageQty.compareTo(this.vehicle.getCargoLst().get(0).getCapacity()) == -1 || this.storageQty.compareTo(this.vehicle.getCargoLst().get(0).getCapacity()) == 0) {
						this.scheduledQty = this.storageQty;
						this.nominalQty = this.storageQty;
						this.cargoQty = this.storageQty;
					}
				}
				
				break;
		}
	}
	
	public List<VtradeHdr> onTradeComplete(String tradeNum) {
		List<VtradeHdr> tradeHdrLst = new ArrayList<VtradeHdr>();
		
		if (tradeNum != null && !tradeNum.trim().equals("")) {
			if (this.isNumber(tradeNum)) {
				List<VtradeHdr> fakeTradeHdrLst = this.tradeDao.getTradeHdrLikeId(tradeNum);
				
				for (VtradeHdr tradeHdr : fakeTradeHdrLst) {
					if (tradeHdr.getId().getBuySellInd() == -1 && tradeHdr.getId().getOpenQty().compareTo(BigDecimal.ZERO) > 0) { // Only add sell trades
						tradeHdrLst.add(tradeHdr);
					}
				}
			}
		}
		
		return tradeHdrLst;
	}
	
	public void onTradeSelect(SelectEvent selectEvent) {
		if (selectEvent.getObject() != null && selectEvent.getObject() instanceof VtradeHdr) {
			VtradeHdr tradeHdr = (VtradeHdr) selectEvent.getObject();
			
			this.tradeInput = this.tradeDao.getTradeInputByTradeNumber(tradeHdr.getId().getTradeNum());
		}
	}
	
	public void onStorageSelect() {
		if (this.getStorage() != null) {
			this.levelLst = this.cargoDao.getCargoByEquipment(this.getStorage().getNumEquipment());
			
			this.cargoDao.setLevelLst(this.levelLst);
			
			for (VdimCargo dimCargo : this.levelLst) {
				if (dimCargo.getId().getNumCompany().equals(this.tradeInput.getInternalCompanyNum()) && dimCargo.getId().getNumStategy() == this.cargo.getNumStategy()) {
					this.level = dimCargo;
					break;
				}
			}
		}
	}
	
	public void onAddTag(ActionEvent actionEvent) {
		VbuildDrawTagId tagId = new VbuildDrawTagId();
		tagId.setTagTypeCd(DefaultConstants.C_CHOP_DATA);
		tagId.setChopId(this.tradeInput != null ? this.tradeInput.getTradeNum() + "-1-1" : "");
		tagId.setTagQty(0d);
		tagId.setTagQtyUomCd(this.tradeInput != null ? this.tradeInput.getContractUomCd() : "");
		
		VbuildDrawTag tag = new VbuildDrawTag();
		tag.setId(tagId);
		
		this.tagLst.add(tag);
	}
	
	public void onUploadTag(FileUploadEvent fileUploadEvent) {
		InputStream inputStream = null;
		
		try {
			inputStream = fileUploadEvent.getFile().getInputstream();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		if (inputStream != null) {
			Workbook wb = null;

			try {
				wb = WorkbookFactory.create(inputStream);
			} catch (InvalidFormatException ife) {
				ife.printStackTrace();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			
			if (wb != null) {
				Sheet s = wb.getSheetAt(0);
				
				Iterator<Row> rs = s.iterator();
				rs.next(); // Skip the first row which belongs to header and titles
				
				while (rs.hasNext()) {
					Row r = rs.next();
					
					Iterator<Cell> cs = r.cellIterator();
					
					VbuildDrawTagId tagId = new VbuildDrawTagId();
					tagId.setChopId(this.tradeInput != null ? this.tradeInput.getTradeNum() + "-1-1" : "");
					tagId.setTagQtyUomCd(this.tradeInput != null ? this.tradeInput.getContractUomCd() : "");
					
					while(cs.hasNext()) {
						Cell c = cs.next();
						
						switch (c.getColumnIndex()) {
							case 0:
								tagId.setTagTypeCd(c.getRichStringCellValue().getString());
								break;
							case 1:
								tagId.setTagValue1(c.getRichStringCellValue().getString());
								break;
							case 2:
								tagId.setTagValue2(c.getRichStringCellValue().getString());
								break;
							case 3:
								tagId.setTagValue3(c.getRichStringCellValue().getString());
								break;
							case 4:
								tagId.setTagValue4(c.getRichStringCellValue().getString());
								break;
							case 5:
								tagId.setTagValue5(c.getRichStringCellValue().getString());
								break;
							case 6:
								tagId.setTagValue6(c.getRichStringCellValue().getString());
								break;
							case 7:
								tagId.setTagValue7(c.getRichStringCellValue().getString());
								break;
							case 8:
								tagId.setTagValue8(c.getRichStringCellValue().getString());
								break;
							case 9:
								tagId.setTagQty(c.getNumericCellValue());
								break;
							case 10:
								break;
						}
					}
					
					VbuildDrawTag tag = new VbuildDrawTag();
					tag.setId(tagId);
					
					this.tagLst.add(tag);
				}
			}
		}
	}
	
	public void onRemoveAllTag(ActionEvent actionEvent) {
		if (!this.tagLst.isEmpty() && this.tagLst.size() > 0) {
			this.tagLst.clear();
		}
	}
	
	public void onRemoveTag(VbuildDrawTag buildDrawTag) {
		this.tagLst.remove(buildDrawTag);
	}
	
	public void onTransferSave(ActionEvent actionEvent) {
		if (this.loadDischargeObj.getIndValue() == DefaultConstants.LOAD_FROM_IND && this.optionObj.getIndValue() == DefaultConstants.TRADE_IND && this.tagLst.isEmpty() && this.tagLst.size() <= 0) {
			RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, "Shipping Manager", "There are not any tags added"));
			return;
		}
		
		if (this.loadDischargeObj.getIndValue() == DefaultConstants.LOAD_FROM_IND && (this.blNumber.trim().equals("") || this.blDate == null)) {
			RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, "Shipping Manager", "B/L Number and B/L Date cannot be empty"));
			return;
		}
		
//		if (this.loadDischargeObj.getIndValue() == DefaultConstants.LOAD_FROM_IND && this.optionObj.getIndValue() == DefaultConstants.TRADE_IND && this.nominalQty.compareTo(this.tradeHdr.getId().getOpenQty()) == 1) {
//		if (this.loadDischargeObj.getIndValue() == DefaultConstants.LOAD_FROM_IND && this.optionObj.getIndValue() == DefaultConstants.TRADE_IND && this.nominalQty.compareTo(this.tradeInput.getOpenQty()) == 1) {
//			RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, "Shipping Manager", "The trade does not have more open quantity for doing a transfer"));
//			return;
//		}
		
		double transferQty = 0.0;
		
		if (!this.tagLst.isEmpty() && this.tagLst.size() > 0) {
			for (VbuildDrawTag buildDrawTag : this.tagLst) {
				if (buildDrawTag.getId().getTagValue1() == null || buildDrawTag.getId().getTagValue1().equals("")) {
					RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, "Shipping Manager", "Container on each tag cannot be empty"));
					return;
				}
				
				if (buildDrawTag.getId().getTagValue2() == null || buildDrawTag.getId().getTagValue2().equals("")) {
					RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, "Shipping Manager", "ICO on each tag cannot be empty"));
					return;
				}
				
				if (buildDrawTag.getId().getTagQty() <= 0) {
					RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, "Shipping Manager", "Tag quantity cannot be less than zero"));
					return;
				} else {
					transferQty += buildDrawTag.getId().getTagQty();
				}
			}
		}

		if (this.loadDischargeObj.getIndValue() == DefaultConstants.LOAD_FROM_IND && this.optionObj.getIndValue() == DefaultConstants.TRADE_IND && this.nominalQty.compareTo(new BigDecimal(transferQty)) != 0) {
			RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, "Shipping Manager", "Total tag quantity cannot be less or greater than scheduled quantity"));
			return;
		}
		
		Transfer transfer = new Transfer();
		
		if (this.loadDischargeObj.getIndValue() == DefaultConstants.LOAD_FROM_IND && this.optionObj.getIndValue() == DefaultConstants.TRADE_IND) {
			transfer.setFromTrade(this.tradeInput);
			transfer.setFromScheduledQty(this.nominalQty);
			transfer.setFromObligation(this.itineraryDao.getObligationHdrByTrade(this.tradeInput.getTradeNum()));
			transfer.setFromMovement(this.vehicle);
			transfer.setLocation(this.locationObj);
			transfer.setEffectiveDt(this.effectiveDt);
			transfer.setCommencementDt(this.commencementDt);
			transfer.setBlNumber(this.blNumber);
			transfer.setBlDate(this.blDate);
			transfer.setToLevel(this.cargo);
			transfer.setToCargoQty(this.nominalQty);
			transfer.setToTransferAt(this.transferAt);
			transfer.setFromConversionFactor(this.unitOfMeasureDao.getConversionFactor(this.tradeInput.getContractUomCd(), this.cargo.getQtyUomCod().getCodUom()));
			transfer.setTags(this.tagLst);
			
			TransferCXL transferCXL = TransferCXL.sharedInstance();
			transferCXL.setUser(this.user);
			transferCXL.setTransfer(transfer);
			
			int transferNumber = transferCXL.saveFromTradeToVessel();
			
			if (transferNumber != -1) {
				RequestContext.getCurrentInstance().execute("hideDlgTransfer()");
				
				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, "Shipping Manager", "Transfer successfully saved. Transfer number given: " + transferNumber));
			} else {
				RequestContext.getCurrentInstance().execute("hideDlgTransfer()");
				
				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, "Shipping Manager", "Unable to save transfer. Please contact your system administrator"));
			}
		} else if (this.loadDischargeObj.getIndValue() == DefaultConstants.DISCHARGE_TO_IND && this.optionObj.getIndValue() == DefaultConstants.TRADE_IND) {
			transfer.setLocation(this.locationObj);
			transfer.setEffectiveDt(this.effectiveDt);
			transfer.setCommencementDt(this.commencementDt);
			transfer.setBlNumber(this.blNumber);
			transfer.setBlDate(this.blDate);
			transfer.setFromLevel(this.cargo);
			transfer.setFromCargoQty(this.nominalQty);
			transfer.setToTrade(this.tradeInput);
			transfer.setToScheduledQty(this.nominalQty);
			transfer.setToObligation(this.itineraryDao.getObligationHdrByTrade(this.tradeInput.getTradeNum()));
			transfer.setTags(this.tagLst);
			
			TransferCXL transferCXL = TransferCXL.sharedInstance();
			transferCXL.setUser(this.user);
			transferCXL.setTransfer(transfer);
			
			int transferNumber = transferCXL.saveFromVesselToTrade();
			
			if (transferNumber != -1) {
				RequestContext.getCurrentInstance().execute("hideDlgTransfer()");
				
				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, "Shipping Manager", "Transfer successfully saved. Transfer number given: " + transferNumber));
			} else {
				RequestContext.getCurrentInstance().execute("hideDlgTransfer()");
				
				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, "Shipping Manager", "Unable to save transfer. Please contact your system administrator"));
			}
		} else if (this.loadDischargeObj.getIndValue() == DefaultConstants.DISCHARGE_TO_IND && this.optionObj.getIndValue() == DefaultConstants.STORAGE_IND) {
			transfer.setLocation(this.locationObj);
			transfer.setEffectiveDt(this.effectiveDt);
			transfer.setCommencementDt(this.commencementDt);
			transfer.setFromTrade(this.tradeInput);
			transfer.setFromMovement(this.vehicle);
			transfer.setFromLevel(this.cargo);
			transfer.setFromCargoQty(this.nominalQty);
			transfer.setToStorage(this.storage);
			transfer.setToLevel(this.level.getId());
			transfer.setToStorageQty(this.nominalQty);
			
			TransferCXL transferCXL = TransferCXL.sharedInstance();
			transferCXL.setUser(this.user);
			transferCXL.setTransfer(transfer);
			
			int transferNumber = transferCXL.saveFromVesselToStorage();
			
			if (transferNumber != -1) {
				RequestContext.getCurrentInstance().execute("hideDlgTransfer()");
				
				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, "Shipping Manager", "Transfer successfully saved. Transfer number given: " + transferNumber));
			} else {
				RequestContext.getCurrentInstance().execute("hideDlgTransfer()");
				
				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, "Shipping Manager", "Unable to save transfer. Please contact your system administrator"));
			}
		}
	}
	
	public void onTransferVoid(VdimTransferId transferId) {
		if (transferId != null) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			
			boolean areTagAllocated = false;
			
			if (!transferId.getTagLst().isEmpty() && transferId.getTagLst().size() > 0) {
				for (VbuildDrawTagId tagId : transferId.getTagLst()) {
					if (transferId.getNumToItinerary() > 0 && tagId.getAllocStatusInd()) {
						facesContext.addMessage(null, new FacesMessage("Tags of selected transfer are allocated. Please do a break allocation on its corresponding draw before voiding it"));
						return;
					} else if (transferId.getNumToItinerary() <= 0 && tagId.getAllocStatusInd()) {
						areTagAllocated = true;
						break;
					}
				}
				
				if (areTagAllocated) {
					TransferCXL transferCXL = TransferCXL.sharedInstance();
					transferCXL.setUser(this.user);
					
					int bdMatchNumber = transferCXL.breakAllocation(transferId.getNumDraw());
					
					if (bdMatchNumber == -1) {
						facesContext.addMessage(null, new FacesMessage("Unable to do a break allocation. Please contact your system administrator"));
						return;
					}
				}
			}

			Transfer transfer = new Transfer();
			transfer.setTransferNum(transferId.getNumTransfer());
			
			TransferCXL transferCXL = TransferCXL.sharedInstance();
			transferCXL.setUser(this.user);
			transferCXL.setTransfer(transfer);
			
			int transferNumber = transferCXL.voidTransfer();
			
			if (transferNumber != -1) {
				facesContext.addMessage(null, new FacesMessage("Transfer successfully voided"));
			} else {
				facesContext.addMessage(null, new FacesMessage("Unable to save transfer. Please contact your system administrator"));
			}
		}
	}
	
	public boolean isNumber(String _str) {
		try {
			@SuppressWarnings("unused")
			int n = Integer.parseInt(_str);
		} catch(NumberFormatException nfe) {
			return false;
		}
		
		return true;
	}
	
	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
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

	public ItineraryDao getItineraryDao() {
		return itineraryDao;
	}

	public void setItineraryDao(ItineraryDao itineraryDao) {
		this.itineraryDao = itineraryDao;
	}

	public TradeDao getTradeDao() {
		return tradeDao;
	}

	public void setTradeDao(TradeDao tradeDao) {
		this.tradeDao = tradeDao;
	}

	public CargoDao getCargoDao() {
		return cargoDao;
	}

	public void setCargoDao(CargoDao cargoDao) {
		this.cargoDao = cargoDao;
	}
	
	public LocationDao getLocationDao() {
		return locationDao;
	}

	public void setLocationDao(LocationDao locationDao) {
		this.locationDao = locationDao;
	}

	public UnitOfMeasureDao getUnitOfMeasureDao() {
		return unitOfMeasureDao;
	}

	public void setUnitOfMeasureDao(UnitOfMeasureDao unitOfMeasureDao) {
		this.unitOfMeasureDao = unitOfMeasureDao;
	}
	
	public TransferAtDao getTransferAtDao() {
		return transferAtDao;
	}

	public void setTransferAtDao(TransferAtDao transferAtDao) {
		this.transferAtDao = transferAtDao;
	}

	public VgenIndicatorId getLoadDischargeObj() {
		return loadDischargeObj;
	}

	public void setLoadDischargeObj(VgenIndicatorId loadDischargeObj) {
		this.loadDischargeObj = loadDischargeObj;
	}

	public VgenIndicatorId getOptionObj() {
		return optionObj;
	}

	public void setOptionObj(VgenIndicatorId optionObj) {
		this.optionObj = optionObj;
	}

	public VdimLocationId getLocationObj() {
		return locationObj;
	}

	public void setLocationObj(VdimLocationId locationObj) {
		this.locationObj = locationObj;
	}
	
	public Date getEffectiveDt() {
		return effectiveDt;
	}

	public void setEffectiveDt(Date effectiveDt) {
		this.effectiveDt = effectiveDt;
	}

	public Date getCommencementDt() {
		return commencementDt;
	}

	public void setCommencementDt(Date commencementDt) {
		this.commencementDt = commencementDt;
	}
	
	public String getBlNumber() {
		return blNumber;
	}

	public void setBlNumber(String blNumber) {
		this.blNumber = blNumber;
	}

	public Date getBlDate() {
		return blDate;
	}

	public void setBlDate(Date blDate) {
		this.blDate = blDate;
	}

	public VtradeHdr getTradeHdr() {
		return tradeHdr;
	}

	public void setTradeHdr(VtradeHdr tradeHdr) {
		this.tradeHdr = tradeHdr;
	}

	public VtradeInputId getTradeInput() {
		return tradeInput;
	}

	public void setTradeInput(VtradeInputId tradeInput) {
		this.tradeInput = tradeInput;
	}

	public BigDecimal getScheduledQty() {
		return scheduledQty;
	}

	public void setScheduledQty(BigDecimal scheduledQty) {
		this.scheduledQty = scheduledQty;
	}

	public BigDecimal getNominalQty() {
		return nominalQty;
	}

	public void setNominalQty(BigDecimal nominalQty) {
		this.nominalQty = nominalQty;
	}

	public VdimStorageId getStorage() {
		return storage;
	}

	public void setStorage(VdimStorageId storage) {
		this.storage = storage;
	}

	public VdimCargo getLevel() {
		return level;
	}

	public void setLevel(VdimCargo level) {
		this.level = level;
	}

	public List<VdimCargo> getLevelLst() {
		return levelLst;
	}

	public void setLevelLst(List<VdimCargo> levelLst) {
		this.levelLst = levelLst;
	}

	public BigDecimal getStorageQty() {
		return storageQty;
	}

	public void setStorageQty(BigDecimal storageQty) {
		this.storageQty = storageQty;
	}

	public VdimMovementId getVehicle() {
		return vehicle;
	}

	public void setVehicle(VdimMovementId vehicle) {
		this.vehicle = vehicle;
	}

	public VdimCargoId getCargo() {
		return cargo;
	}

	public void setCargo(VdimCargoId cargo) {
		this.cargo = cargo;
	}

	public BigDecimal getCargoQty() {
		return cargoQty;
	}

	public void setCargoQty(BigDecimal cargoQty) {
		this.cargoQty = cargoQty;
	}

	public VgenIndicatorId getTransferAt() {
		return transferAt;
	}

	public void setTransferAt(VgenIndicatorId transferAt) {
		this.transferAt = transferAt;
	}

	public boolean isFromTrade() {
		return fromTrade;
	}

	public void setFromTrade(boolean fromTrade) {
		this.fromTrade = fromTrade;
	}

	public boolean isFromStorage() {
		return fromStorage;
	}

	public void setFromStorage(boolean fromStorage) {
		this.fromStorage = fromStorage;
	}

	public boolean isFromVessel() {
		return fromVessel;
	}

	public void setFromVessel(boolean fromVessel) {
		this.fromVessel = fromVessel;
	}

	public boolean isFromBarge() {
		return fromBarge;
	}

	public void setFromBarge(boolean fromBarge) {
		this.fromBarge = fromBarge;
	}

	public boolean isFromTruck() {
		return fromTruck;
	}

	public void setFromTruck(boolean fromTruck) {
		this.fromTruck = fromTruck;
	}

	public boolean isFromPipeline() {
		return fromPipeline;
	}

	public void setFromPipeline(boolean fromPipeline) {
		this.fromPipeline = fromPipeline;
	}

	public boolean isFromRailcar() {
		return fromRailcar;
	}

	public void setFromRailcar(boolean fromRailcar) {
		this.fromRailcar = fromRailcar;
	}

	public boolean isFromStorageSvc() {
		return fromStorageSvc;
	}

	public void setFromStorageSvc(boolean fromStorageSvc) {
		this.fromStorageSvc = fromStorageSvc;
	}

	public boolean isToTrade() {
		return toTrade;
	}

	public void setToTrade(boolean toTrade) {
		this.toTrade = toTrade;
	}

	public boolean isToStorage() {
		return toStorage;
	}

	public void setToStorage(boolean toStorage) {
		this.toStorage = toStorage;
	}

	public boolean isToVessel() {
		return toVessel;
	}

	public void setToVessel(boolean toVessel) {
		this.toVessel = toVessel;
	}

	public boolean isToBarge() {
		return toBarge;
	}

	public void setToBarge(boolean toBarge) {
		this.toBarge = toBarge;
	}

	public boolean isToTruck() {
		return toTruck;
	}

	public void setToTruck(boolean toTruck) {
		this.toTruck = toTruck;
	}

	public boolean isToPipeline() {
		return toPipeline;
	}

	public void setToPipeline(boolean toPipeline) {
		this.toPipeline = toPipeline;
	}

	public boolean isToRailcar() {
		return toRailcar;
	}

	public void setToRailcar(boolean toRailcar) {
		this.toRailcar = toRailcar;
	}

	public boolean isToStorageSvc() {
		return toStorageSvc;
	}

	public void setToStorageSvc(boolean toStorageSvc) {
		this.toStorageSvc = toStorageSvc;
	}

	public List<VbuildDrawTag> getTagLst() {
		return tagLst;
	}

	public void setTagLst(List<VbuildDrawTag> tagLst) {
		this.tagLst = tagLst;
	}
	
}
