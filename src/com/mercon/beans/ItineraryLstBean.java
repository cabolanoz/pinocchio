package com.mercon.beans;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.rtf.RTFEditorKit;

import org.primefaces.event.ToggleEvent;
import org.primefaces.model.LazyDataModel;

import com.mercon.cxl.MatchObligation;
import com.mercon.cxl.MatchObligationCXL;
import com.mercon.dao.CompanyDao;
import com.mercon.dao.FromTypeDao;
import com.mercon.dao.GenNoteDao;
import com.mercon.dao.ItiDefaultActionDetailDao;
import com.mercon.dao.ItineraryDao;
import com.mercon.dao.LoadDischargeDao;
import com.mercon.dao.LocationDao;
import com.mercon.dao.MeanOfTransportDao;
import com.mercon.dao.PersonDao;
import com.mercon.dao.StorageDao;
import com.mercon.dao.StrategyDao;
import com.mercon.dao.TradeDao;
import com.mercon.datamodel.ItineraryLazyDataModel;
import com.mercon.entities.VbuildDraw;
import com.mercon.entities.VbuildDrawTag;
import com.mercon.entities.VbuildDrawTagId;
import com.mercon.entities.VdimCompanyId;
import com.mercon.entities.VdimLocationId;
import com.mercon.entities.VdimPersonId;
import com.mercon.entities.VdimStorage;
import com.mercon.entities.VdimStrategyId;
import com.mercon.entities.VdimTransfer;
import com.mercon.entities.VgenIndicatorId;
import com.mercon.entities.VgenNote;
import com.mercon.entities.VopsItiDefaultActionDetail;
import com.mercon.entities.VopsItineraryId;
import com.mercon.entities.VopsMovement;
import com.mercon.entities.VopsObligationDetail;
import com.mercon.entities.VopsObligationMatch;
import com.mercon.entities.VtradeInputId;
import com.mercon.util.DefaultConstants;

@ManagedBean(name = "itineraryLst")
@ViewScoped
public class ItineraryLstBean implements Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 7378405863466262209L;

	@ManagedProperty("#{user}")
	private UserBean user;
	
	@ManagedProperty("#{itineraryService}")
	private ItineraryDao itineraryDao;
	
	@ManagedProperty("#{itiDefaultActionDetailService}")
	private ItiDefaultActionDetailDao itiDefaultActionDetailDao;
	
	@ManagedProperty("#{companyService}")
	private CompanyDao companyDao;
	
	@ManagedProperty("#{personService}")
	private PersonDao personDao;
	
	@ManagedProperty("#{motService}")
	private MeanOfTransportDao motDao;
	
	@ManagedProperty("#{locationService}")
	private LocationDao locationDao;
	
	@ManagedProperty("#{strategyService}")
	private StrategyDao strategyDao;
	
	@ManagedProperty("#{loadDischargeService}")
	private LoadDischargeDao loadDischargeDao;
	
	@ManagedProperty("#{fromTypeService}")
	private FromTypeDao fromTypeDao;
	
	@ManagedProperty("#{genNoteService}")
	private GenNoteDao genNoteDao;
	
	@ManagedProperty("#{storageService}")
	private StorageDao storageDao;
	
	private LazyDataModel<VopsItineraryId> opsItinerariesId;
	
	private List<VopsObligationMatch> opsObligationMatch = new ArrayList<VopsObligationMatch>();
	
	private String note = "";
	
	@PostConstruct
	public void init() {
		this.opsItinerariesId = new ItineraryLazyDataModel();
	}

	// On row expand for movement
	public void onRowExpandForMovement(ToggleEvent toggleEvent) {
		if (toggleEvent.getData() != null && toggleEvent.getData() instanceof VopsItineraryId && "VISIBLE".equals(toggleEvent.getVisibility().name())) {
			VopsItineraryId opsItinerary = (VopsItineraryId) toggleEvent.getData();
			
			if (opsItinerary.getMovements() == null) {
				opsItinerary.setMovements(this.itineraryDao.getMovementByItinerary(opsItinerary.getItineraryNum()));
				
				if (!opsItinerary.getMovements().isEmpty() && opsItinerary.getMovements().size() > 0) {
					for (VopsMovement opsMovement : opsItinerary.getMovements()) {
						VopsItiDefaultActionDetail itiDefaultActionDetail = this.itiDefaultActionDetailDao.getByItineraryAndMovement(opsMovement.getId().getItineraryNum(), opsMovement.getId().getMovementNum());
						
						if (itiDefaultActionDetail != null) {
							opsMovement.getId().setActionCompletionDt(itiDefaultActionDetail.getId().getActionCompletionDt());
						} else {
							continue;
						}
					}
				}
			}
		}
	}
	
	// On row expand for cargo
	public void onRowExpandForCargo(ToggleEvent toggleEvent) {
		if (toggleEvent.getData() != null && toggleEvent.getData() instanceof VopsMovement && "VISIBLE".equals(toggleEvent.getVisibility().name())) {
			VopsMovement opsMovement = (VopsMovement) toggleEvent.getData();
			
			if (opsMovement.getId().getCargos() == null) {
				int equipmentNum = opsMovement.getId().getEquipmentNum();

				if (equipmentNum > 0) {
//					TransferCXL transferCXL = TransferCXL.sharedInstance();
//					transferCXL.setUser(this.user);
//					
//					transferCXL.getTransferByEquipment(equipmentNum);
					
					opsMovement.getId().setCargos(this.itineraryDao.getCargoByEquipment(equipmentNum));
					
					if (opsMovement.getId().getCargos() != null && !opsMovement.getId().getCargos().isEmpty() && opsMovement.getId().getCargos().size() > 0 && opsMovement.getId().getTransfers() == null) {
						if (opsMovement.getId().getTransfers() == null || opsMovement.getId().getTransfers().isEmpty() || opsMovement.getId().getTransfers().size() <= 0) {
							List<VdimTransfer> transferLst = this.itineraryDao.getTransferByItineraryEquipmentCargo(opsMovement.getId().getItineraryNum(), equipmentNum, opsMovement.getId().getCargos().get(0).getId().getNumCargo());
							
							if (!transferLst.isEmpty() && transferLst.size() > 0) {
								List<VgenIndicatorId> lstLoadDischarge = this.loadDischargeDao.getLoadDischargeId();
								
								List<VgenIndicatorId> lstFromType = this.fromTypeDao.getFromTypeId();
								
								List<VdimLocationId> lstLocation = this.locationDao.getLocationId();
								
								for (VdimTransfer dimTransfer : transferLst) {
									if (!lstLoadDischarge.isEmpty() && lstLoadDischarge.size() > 0) {
										for (VgenIndicatorId loadDischarge : lstLoadDischarge) {
											if (dimTransfer.getId().getNumToItinerary() > 0 && loadDischarge.getIndValueName().equals(DefaultConstants.C_LOAD_FROM)) {
												dimTransfer.getId().setLoadDischargeObj(loadDischarge);
												break;
											}
											
											if (dimTransfer.getId().getNumToItinerary() <= 0 && loadDischarge.getIndValueName().equals(DefaultConstants.C_DISCHARGE_TO)) {
												dimTransfer.getId().setLoadDischargeObj(loadDischarge);
												break;
											}
										}
									}
									
									if (!lstFromType.isEmpty() && lstFromType.size() > 0) {
										for (VgenIndicatorId fromType : lstFromType) {
											if (dimTransfer.getId().getIndFromType() == fromType.getIndValue()) {
												dimTransfer.getId().setFromTypeObj(fromType);
												break;
											}
										}
									}
									
									if (!lstFromType.isEmpty() && lstFromType.size() > 0) {
										for (VgenIndicatorId toType : lstFromType) {
											if (dimTransfer.getId().getIndToType() == toType.getIndValue()) {
												dimTransfer.getId().setToTypeObj(toType);
												break;
											}
										}
									}
									
									if (!lstLocation.isEmpty() && lstLocation.size() > 0) {
										for (VdimLocationId location : lstLocation) {
											if (dimTransfer.getId().getNumLocation() == location.getNumLocation()) {
												dimTransfer.getId().setLocationObj(location);
												break;
											}
										}
									}

									// Here I should load the location of the transfer - Trade or Storage
									for (VgenIndicatorId toType : lstFromType) {
										if (dimTransfer.getId().getIndToType() == toType.getIndValue()) {
											dimTransfer.getId().setToTypeObj(toType);
											
											if (dimTransfer.getId().getLoadDischargeObj().getIndValue() == DefaultConstants.DISCHARGE_TO_IND && dimTransfer.getId().getToTypeObj().getIndValue() == DefaultConstants.TRADE_IND) {
												VbuildDraw buildDraw = this.itineraryDao.getBuildDrawById(dimTransfer.getId().getNumDraw());
												
												if (buildDraw != null) {
													dimTransfer.getId().setLocationIdentifier(String.valueOf(buildDraw.getId().getTradeNum()));
												}
												
												break;
											} else if (dimTransfer.getId().getLoadDischargeObj().getIndValue() == DefaultConstants.DISCHARGE_TO_IND && dimTransfer.getId().getToTypeObj().getIndValue() == DefaultConstants.STORAGE_IND) {
												VdimStorage dimStorage = this.storageDao.getStorageByEquipment(dimTransfer.getId().getNumToEquipment());
												
												if (dimStorage != null) {
													dimTransfer.getId().setLocationIdentifier(dimStorage.getId().getCodStorage());
												}
												
												break;
											}
											
											break;
										}
									}
									
									if (dimTransfer.getId().getTagLst() == null || dimTransfer.getId().getTagLst().isEmpty() || dimTransfer.getId().getTagLst().size() <= 0) {
										List<VbuildDrawTag> transferTag = this.itineraryDao.getTransferTagByBuild(dimTransfer.getId().getNumToItinerary() > 0 ? dimTransfer.getId().getNumBuild() : dimTransfer.getId().getNumDraw());
										
										if (!transferTag.isEmpty() && transferTag.size() > 0) {
											List<VbuildDrawTagId> transferTagId = new ArrayList<VbuildDrawTagId>();
											
											for (VbuildDrawTag buildDrawTag : transferTag) {
												transferTagId.add(buildDrawTag.getId());
											}
											
											if (!transferTagId.isEmpty() && transferTagId.size() > 0) {
												dimTransfer.getId().setTagLst(transferTagId);
											}
										}
									}
								}
								
								opsMovement.getId().setTransfers(transferLst);
							}
						}
					}
				}
			}
		}
	}
	
	// See note
	public void seeNote(VopsMovement opsMovement) {
		if (opsMovement != null && FacesContext.getCurrentInstance().getRenderResponse()) {
			VgenNote genNoteFound = this.genNoteDao.getByItineraryAndMovement(opsMovement.getId().getItineraryNum(), opsMovement.getId().getMovementNum());
			
			if (genNoteFound != null) {
				this.note = fillNote(opsMovement, genNoteFound);
			} else {
				this.note = "";
			}
		}
	}
	
	private String fillNote(VopsMovement opsMovement, VgenNote genNote) {
		if (genNote != null) {
			String html = "";
			
			String noteText = genNote.getId().getNoteText();
			
			byte[] byteNote = new byte[noteText.length() / 2];
			
			for (int i = 0; i < noteText.length(); i += 2) {
				byteNote[i / 2] = (byte) ((Character.digit(noteText.charAt(i), 16) << 4) + Character.digit(noteText.charAt(i + 1), 16));
			}
			
			RTFEditorKit rtfEditor = new RTFEditorKit();
			Document document = rtfEditor.createDefaultDocument();
			
			BufferedReader br = null;
			
			try {
				rtfEditor.read(new ByteArrayInputStream(byteNote), document, 0);
				
				StringReader sr = new StringReader(document.getText(0, document.getLength()));
				
				br = new BufferedReader(sr);
				
				String sCurrentLine;
				
				int c = 0;
				
				while ((sCurrentLine = br.readLine()) != null) {
					if (sCurrentLine.equals("")) {
						html += "<br />";
					} else {
						if (c == 0) {
							html += (opsMovement != null ? sCurrentLine.substring(String.valueOf(opsMovement.getId().getMovementNum()).length() + 12) : sCurrentLine);
						} else {
							html += sCurrentLine;
						}
						
						c++;
					}
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} catch (BadLocationException ble) {
				ble.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
				}
			}
			
			return html;
		}
		
		return "";
	}
	
	// See MO if exists
	public void seeMatchObligation(int movementNumber) {
		this.opsObligationMatch = this.itineraryDao.getObligationByMovement(movementNumber);
		
		if (this.opsObligationMatch.isEmpty() || this.opsObligationMatch.size() <= 0) {
			this.opsObligationMatch.clear();
		} else {
			for (VopsObligationMatch vOpsObligationMatch : this.opsObligationMatch) {
				for (VdimCompanyId companyId : this.companyDao.getCompanyId()) {
					if (vOpsObligationMatch.getId().getInternalCoNum() == companyId.getNumCompany()) {
						vOpsObligationMatch.getId().setInternalCompany(companyId);
						break;
					}
				}
				
				for (VdimPersonId dimPersonId : this.personDao.getPersonId()) {
					if (vOpsObligationMatch.getId().getOperatorPersonNum() == dimPersonId.getNumperson()) {
						vOpsObligationMatch.getId().setOperatorPerson(dimPersonId);
						break;
					}
				}
				
				for (VgenIndicatorId dimMotId : this.motDao.gettMotId()) {
					if (vOpsObligationMatch.getId().getMotTypeInd() == dimMotId.getIndValue()) {
						vOpsObligationMatch.getId().setMotType(dimMotId);
						break;
					}
				}
				
				if (!vOpsObligationMatch.getId().getOpsObligationDetail().isEmpty() && vOpsObligationMatch.getId().getOpsObligationDetail().size() > 0) {
					TradeDao tradeDao = TradeDao.sharedInstance();
					
					for (VopsObligationDetail opsObligationDetail : vOpsObligationMatch.getId().getOpsObligationDetail()) {
						for (VdimStrategyId dimStrategyId : this.strategyDao.getStrategyId()) {
							if (opsObligationDetail.getId().getStrategyNum() == dimStrategyId.getNumStrategy()) {
								opsObligationDetail.getId().setStrategy(dimStrategyId);
								break;
							}
						}
						
						VtradeInputId tradeInputId = null;
						
						try {
							tradeInputId = tradeDao.getTradeInputByTradeNumber(opsObligationDetail.getId().getTradeNum());
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						if (tradeInputId != null) {
							opsObligationDetail.getId().setCounterpartName(tradeInputId.getCounterpart());
						}
					}
				}
			}
		}
	}
	
	// Void MO
	public void voidMatchObligation(int obligationMatchNumber) {
		if (obligationMatchNumber > 0) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			
			MatchObligation matchObligation = new MatchObligation();
			matchObligation.setMatchObligationNumber(obligationMatchNumber);
			
			MatchObligationCXL matchObligationCXL = MatchObligationCXL.sharedInstance();
			matchObligationCXL.setUser(this.user);
			matchObligationCXL.setMatchObligation(matchObligation);
			
			boolean success = matchObligationCXL.voidOpsBackToBack();
			
			if (!success) {
				facesContext.addMessage(null, new FacesMessage("Unable to void this match obligation. Please contact your system administrator"));
			} else {
				facesContext.addMessage(null, new FacesMessage("Match Obligation successfully voided"));
				facesContext.addMessage(null, new FacesMessage("Match Obligation number given: " + obligationMatchNumber));
			}
		}
	}
	
	// Redirect page to itinerary with proper ID
	public void redirectToItinerary(VopsItineraryId opsItineraryId) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
		if (opsItineraryId != null) {
			int itineraryNum = opsItineraryId.getItineraryNum();
			
			String url = "itinerary.xhtml?id=" + itineraryNum;
			
			try {
				facesContext.getExternalContext().redirect(url);
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		} else {
			String url = "itinerary.xhtml?id=-1";
			
			try {
				facesContext.getExternalContext().redirect(url);
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
	
	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}
	
	public ItineraryDao getItineraryDao() {
		return itineraryDao;
	}

	public void setItineraryDao(ItineraryDao itineraryDao) {
		this.itineraryDao = itineraryDao;
	}
	
	public ItiDefaultActionDetailDao getItiDefaultActionDetailDao() {
		return itiDefaultActionDetailDao;
	}

	public void setItiDefaultActionDetailDao(
			ItiDefaultActionDetailDao itiDefaultActionDetailDao) {
		this.itiDefaultActionDetailDao = itiDefaultActionDetailDao;
	}

	public CompanyDao getCompanyDao() {
		return companyDao;
	}

	public void setCompanyDao(CompanyDao companyDao) {
		this.companyDao = companyDao;
	}

	public PersonDao getPersonDao() {
		return personDao;
	}

	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}
	
	public MeanOfTransportDao getMotDao() {
		return motDao;
	}

	public void setMotDao(MeanOfTransportDao motDao) {
		this.motDao = motDao;
	}
	
	public LocationDao getLocationDao() {
		return locationDao;
	}

	public void setLocationDao(LocationDao locationDao) {
		this.locationDao = locationDao;
	}

	public StrategyDao getStrategyDao() {
		return strategyDao;
	}

	public void setStrategyDao(StrategyDao strategyDao) {
		this.strategyDao = strategyDao;
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

	public GenNoteDao getGenNoteDao() {
		return genNoteDao;
	}

	public void setGenNoteDao(GenNoteDao genNoteDao) {
		this.genNoteDao = genNoteDao;
	}
	
	public StorageDao getStorageDao() {
		return storageDao;
	}

	public void setStorageDao(StorageDao storageDao) {
		this.storageDao = storageDao;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public LazyDataModel<VopsItineraryId> getOpsItinerariesId() {
		return opsItinerariesId;
	}

	public void setOpsItinerariesId(LazyDataModel<VopsItineraryId> opsItinerariesId) {
		this.opsItinerariesId = opsItinerariesId;
	}

	public List<VopsObligationMatch> getOpsObligationMatch() {
		return opsObligationMatch;
	}

	public void setOpsObligationMatch(List<VopsObligationMatch> opsObligationMatch) {
		this.opsObligationMatch = opsObligationMatch;
	}
	
}
