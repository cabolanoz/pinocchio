package com.mercon.beans;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;

import com.mercon.dao.CargoDao;
import com.mercon.dao.TradeDao;
import com.mercon.entities.VbuildDrawTag;
import com.mercon.entities.VbuildDrawTagId;
import com.mercon.entities.VdimCargo;
import com.mercon.entities.VdimLocationId;
import com.mercon.entities.VdimStorageId;
import com.mercon.entities.VgenIndicatorId;
import com.mercon.entities.VtradeHdr;
import com.mercon.entities.VtradeInputId;
import com.mercon.util.DefaultConstants;

@ManagedBean(name = "transfer")
@ViewScoped
public class TransferBean implements Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -6804269182338426898L;

	/**
	 * Managed Beans
	 */
	@ManagedProperty("#{user}")
	private UserBean user;
	
	@ManagedProperty("#{tradeService}")
	private TradeDao tradeDao;
	
	@ManagedProperty("#{cargoService}")
	private CargoDao cargoDao;
	
	/**
	 * Variables
	 */
	private VgenIndicatorId fromTypeObj;
	private VgenIndicatorId toTypeObj;
	private Date commencementDt = new Date();
	private VdimLocationId location;
	
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

	private VtradeHdr tradeHdr;
	private VtradeInputId tradeInputId;
	private BigDecimal scheduledQty;
	private BigDecimal nominalQty;
	
	/**
	 * To Variables
	 */
	private boolean toStorage = true;
	private boolean toVessel = false;
	private boolean toBarge = false;
	private boolean toTruck = false;
	private boolean toPipeline = false;
	private boolean toRailcar = false;
	
	private VdimStorageId toStorageId;
	private VdimCargo toLevel;
	private List<VdimCargo> toLevelLst = new ArrayList<VdimCargo>();
	private BigDecimal toStorageQty;
	private VgenIndicatorId toTransferAtId;
	
	/**
	 * Tags
	 */
	private VbuildDrawTag tag;
	private List<VbuildDrawTag> tagLst = new ArrayList<VbuildDrawTag>();
	
	public TransferBean() {	}

	public void onFromTypeSelect() {
		if (this.getFromTypeObj() != null) {
			switch (this.getFromTypeObj().getIndValue()) {
				case DefaultConstants.TRADE_IND:
					this.fromTrade = true;
					this.fromStorage = false;
					this.fromVessel = false;
					this.fromBarge = false;
					this.fromTruck = false;
					this.fromPipeline = false;
					this.fromRailcar = false;
					this.fromStorageSvc = false;
					break;
				case DefaultConstants.STORAGE_IND:
					this.fromTrade = false;
					this.fromStorage = true;
					this.fromVessel = false;
					this.fromBarge = false;
					this.fromTruck = false;
					this.fromPipeline = false;
					this.fromRailcar = false;
					this.fromStorageSvc = false;
					break;
				case DefaultConstants.VESSEL_IND:
					this.fromTrade = false;
					this.fromStorage = false;
					this.fromVessel = true;
					this.fromBarge = false;
					this.fromTruck = false;
					this.fromPipeline = false;
					this.fromRailcar = false;
					this.fromStorageSvc = false;
					break;
				case DefaultConstants.BARGE_IND:
					this.fromTrade = false;
					this.fromStorage = false;
					this.fromVessel = false;
					this.fromBarge = true;
					this.fromTruck = false;
					this.fromPipeline = false;
					this.fromRailcar = false;
					this.fromStorageSvc = false;
					break;
				case DefaultConstants.TRUCK_IND:
					this.fromTrade = false;
					this.fromStorage = false;
					this.fromVessel = false;
					this.fromBarge = false;
					this.fromTruck = true;
					this.fromPipeline = false;
					this.fromRailcar = false;
					this.fromStorageSvc = false;
					break;
				case DefaultConstants.PIPELINE_IND:
					this.fromTrade = false;
					this.fromStorage = false;
					this.fromVessel = false;
					this.fromBarge = false;
					this.fromTruck = false;
					this.fromPipeline = true;
					this.fromRailcar = false;
					this.fromStorageSvc = false;
					break;
				case DefaultConstants.RAILCAR_IND:
					this.fromTrade = false;
					this.fromStorage = false;
					this.fromVessel = false;
					this.fromBarge = false;
					this.fromTruck = false;
					this.fromPipeline = false;
					this.fromRailcar = true;
					this.fromStorageSvc = false;
					break;
				case DefaultConstants.STORAGE_SVC_IND:
					this.fromTrade = false;
					this.fromStorage = false;
					this.fromVessel = false;
					this.fromBarge = false;
					this.fromTruck = false;
					this.fromPipeline = false;
					this.fromRailcar = false;
					this.fromStorageSvc = true;
					break;
			}
		}
	}
	
	public void onToTypeSelect() {
		if (this.getToTypeObj() != null) {
			switch (this.getToTypeObj().getIndValue()) {
				case DefaultConstants.STORAGE_IND:
					this.toStorage = true;
					this.toVessel = false;
					this.toBarge = false;
					this.toTruck = false;
					this.toPipeline = false;
					this.toRailcar = false;
					break;
				case DefaultConstants.VESSEL_IND:
					this.toStorage = false;
					this.toVessel = true;
					this.toBarge = false;
					this.toTruck = false;
					this.toPipeline = false;
					this.toRailcar = false;
					break;
				case DefaultConstants.BARGE_IND:
					this.toStorage = false;
					this.toVessel = false;
					this.toBarge = true;
					this.toTruck = false;
					this.toPipeline = false;
					this.toRailcar = false;
					break;
				case DefaultConstants.TRUCK_IND:
					this.toStorage = false;
					this.toVessel = false;
					this.toBarge = false;
					this.toTruck = true;
					this.toPipeline = false;
					this.toRailcar = false;
					break;
				case DefaultConstants.PIPELINE_IND:
					this.toStorage = false;
					this.toVessel = false;
					this.toBarge = false;
					this.toTruck = false;
					this.toPipeline = true;
					this.toRailcar = false;
					break;
				case DefaultConstants.RAILCAR_IND:
					this.toStorage = false;
					this.toVessel = false;
					this.toBarge = false;
					this.toTruck = false;
					this.toPipeline = false;
					this.toRailcar = true;
					break;
			}
		}
	}
	
	public List<VtradeHdr> onTradeComplete(String tradeNum) {
		List<VtradeHdr> tradeHdrLst = new ArrayList<VtradeHdr>();
		
		if (tradeNum != null && !tradeNum.trim().equals("")) {
			if (this.isNumber(tradeNum)) {
				tradeHdrLst = this.tradeDao.getTradeHdrLikeId(tradeNum);
			}
		}
		
		return tradeHdrLst;
	}
	
	public void onTradeSelect(SelectEvent selectEvent) {
		if (selectEvent.getObject() != null && selectEvent.getObject() instanceof VtradeHdr) {
			VtradeHdr tradeHdr = (VtradeHdr) selectEvent.getObject();
			
			this.tradeInputId = this.tradeDao.getTradeInputByTradeNumber(tradeHdr.getId().getTradeNum());
//			this.scheduledQty = this.tradeInputId.getOpenQty();
//			this.nominalQty = this.tradeInputId.getOpenQty();
//			this.toStorageQty = this.tradeInputId.getOpenQty();
		}
	}
	
	public void onToStorageSelect() {
		if (this.getToStorageId() != null) {
			this.toLevelLst = this.cargoDao.getCargoByEquipment(this.getToStorageId().getNumEquipment());
		}
	}
	
	public void onAddTag(ActionEvent actionEvent) {
		VbuildDrawTagId tagId = new VbuildDrawTagId();
		tagId.setTagTypeCd(DefaultConstants.C_CHOP_DATA);
		tagId.setChopId(this.tradeInputId != null ? this.tradeInputId.getTradeNum() + "-1-1" : "");
		tagId.setTagQty(0d);
		tagId.setTagQtyUomCd(this.tradeInputId != null ? this.tradeInputId.getContractUomCd() : "");
		
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
					tagId.setChopId(this.tradeInputId != null ? this.tradeInputId.getTradeNum() + "-1-1" : "");
					tagId.setTagQtyUomCd(this.tradeInputId != null ? this.tradeInputId.getContractUomCd() : "");
					
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
	
	public void onTransferSave(ActionEvent actionEvent) {
//		
//		
//		Transfer transfer = new Transfer();
//		
//		
//		
//		transfer.setTags(this.tagLst);
//		
//		TransferCXL transferCXL = TransferCXL.sharedInstance();
//		transferCXL.setTransfer(transfer);
//		
//		int transferNum = transferCXL.saveFromTradeToStorage();
//		
//		if (transferNum == -1) {
//			
//		} else {
//			
//		}
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

	public VgenIndicatorId getFromTypeObj() {
		return fromTypeObj;
	}

	public void setFromTypeObj(VgenIndicatorId fromTypeObj) {
		this.fromTypeObj = fromTypeObj;
	}

	public VgenIndicatorId getToTypeObj() {
		return toTypeObj;
	}

	public void setToTypeObj(VgenIndicatorId toTypeObj) {
		this.toTypeObj = toTypeObj;
	}

	public Date getCommencementDt() {
		return commencementDt;
	}

	public void setCommencementDt(Date commencementDt) {
		this.commencementDt = commencementDt;
	}
	
	public VdimLocationId getLocation() {
		return location;
	}

	public void setLocation(VdimLocationId location) {
		this.location = location;
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

	public VtradeHdr getTradeHdr() {
		return tradeHdr;
	}

	public void setTradeHdr(VtradeHdr tradeHdr) {
		this.tradeHdr = tradeHdr;
	}

	public VtradeInputId getTradeInputId() {
		return tradeInputId;
	}

	public void setTradeInputId(VtradeInputId tradeInputId) {
		this.tradeInputId = tradeInputId;
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

	public VdimStorageId getToStorageId() {
		return toStorageId;
	}

	public void setToStorageId(VdimStorageId toStorageId) {
		this.toStorageId = toStorageId;
	}

	public VdimCargo getToLevel() {
		return toLevel;
	}

	public void setToLevel(VdimCargo toLevel) {
		this.toLevel = toLevel;
	}

	public List<VdimCargo> getToLevelLst() {
		return toLevelLst;
	}

	public void setToLevelLst(List<VdimCargo> toLevelLst) {
		this.toLevelLst = toLevelLst;
	}
	
	public BigDecimal getToStorageQty() {
		return toStorageQty;
	}

	public void setToStorageQty(BigDecimal toStorageQty) {
		this.toStorageQty = toStorageQty;
	}

	public VgenIndicatorId getToTransferAtId() {
		return toTransferAtId;
	}

	public void setToTransferAtId(VgenIndicatorId toTransferAtId) {
		this.toTransferAtId = toTransferAtId;
	}
	
	public VbuildDrawTag getTag() {
		return tag;
	}

	public void setTag(VbuildDrawTag tag) {
		this.tag = tag;
	}

	public List<VbuildDrawTag> getTagLst() {
		return tagLst;
	}

	public void setTagLst(List<VbuildDrawTag> tagLst) {
		this.tagLst = tagLst;
	}
	
}
