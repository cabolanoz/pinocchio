package com.mercon.beans;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.rtf.RTFEditorKit;

import org.jboss.logging.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleEvent;

import com.mercon.cxl.Itinerary;
import com.mercon.cxl.ItineraryCXL;
import com.mercon.cxl.ItineraryNote;
import com.mercon.cxl.ItineraryNoteCXL;
import com.mercon.cxl.MatchObligation;
import com.mercon.cxl.MatchObligationCXL;
import com.mercon.dao.CommodityDao;
import com.mercon.dao.CompanyDao;
import com.mercon.dao.CurrencyDao;
import com.mercon.dao.CurveDao;
import com.mercon.dao.FromTypeDao;
import com.mercon.dao.GenNoteDao;
import com.mercon.dao.ItiDefaultActionDetailDao;
import com.mercon.dao.ItineraryDao;
import com.mercon.dao.LoadDischargeDao;
import com.mercon.dao.LocationDao;
import com.mercon.dao.MeanOfTransportDao;
import com.mercon.dao.PersonDao;
import com.mercon.dao.RefMotDao;
import com.mercon.dao.StorageDao;
import com.mercon.dao.StrategyDao;
import com.mercon.dao.TradeDao;
import com.mercon.dao.UnitOfMeasureDao;
import com.mercon.entities.VbuildDraw;
import com.mercon.entities.VbuildDrawTag;
import com.mercon.entities.VbuildDrawTagId;
import com.mercon.entities.VdimCargo;
import com.mercon.entities.VdimCargoId;
import com.mercon.entities.VdimCommodityId;
import com.mercon.entities.VdimCompanyId;
import com.mercon.entities.VdimCurrencyId;
import com.mercon.entities.VdimLocationId;
import com.mercon.entities.VdimMktQuoteDefinitionId;
import com.mercon.entities.VdimMovementId;
import com.mercon.entities.VdimPersonId;
import com.mercon.entities.VdimStorage;
import com.mercon.entities.VdimStrategyId;
import com.mercon.entities.VdimTransfer;
import com.mercon.entities.VdimTransferId;
import com.mercon.entities.VdimUnitOfMeasureId;
import com.mercon.entities.VgenIndicatorId;
import com.mercon.entities.VgenNote;
import com.mercon.entities.VgenNoteId;
import com.mercon.entities.VopsItiDefaultActionDetail;
import com.mercon.entities.VopsItinerary;
import com.mercon.entities.VopsItineraryId;
import com.mercon.entities.VopsMovement;
import com.mercon.entities.VopsObligationDetail;
import com.mercon.entities.VopsObligationMatch;
import com.mercon.entities.VopsObligationMatchId;
import com.mercon.entities.VrefMotId;
import com.mercon.entities.VtradeHdr;
import com.mercon.entities.VtradeInputId;
import com.mercon.util.DefaultConstants;

@ManagedBean(name = "itinerary")
@ViewScoped
public class ItineraryBean implements Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -4357248377493349654L;

	private String id;
	
	private VopsItinerary opsItinerary;
	private VtradeHdr tradeHdr;
	private VtradeInputId tradeInputId;
	private String itineraryName;
	private VdimCompanyId companyId;
	private VdimPersonId personId;
	
	private BigDecimal tradeQty = new BigDecimal(0);
	private BigDecimal missingTradeQty = new BigDecimal(0);
	private BigDecimal realOpenQty = BigDecimal.ZERO;
	private String contractUomCd;
	
	private boolean thereIsTradeNumber = false;
	private boolean tradeContract = false;
	
	private List<VdimMovementId> movementLst = new ArrayList<VdimMovementId>();
	
	@ManagedProperty("#{user}")
	private UserBean user;
	
	@ManagedProperty("#{companyService}")
	private CompanyDao companyDao;
	
	@ManagedProperty("#{movementId}")
	private VdimMovementId movementId;
	
	@ManagedProperty("#{itineraryService}")
	private ItineraryDao itineraryDao;
	
	@ManagedProperty("#{personService}")
	private PersonDao personDao;
	
	@ManagedProperty("#{motService}")
	private MeanOfTransportDao motDao;
	
	@ManagedProperty("#{refMotService}")
	private RefMotDao refMotDao;
	
	@ManagedProperty("#{locationService}")
	private LocationDao locationDao;
	
	@ManagedProperty("#{strategyService}")
	private StrategyDao strategyDao;
	
	@ManagedProperty("#{commodityService}")
	private CommodityDao commodityDao;
	
	@ManagedProperty("#{uomService}")
	private UnitOfMeasureDao unitOfMeasureDao;
	
	@ManagedProperty("#{currencyService}")
	private CurrencyDao currencyDao;
	
	@ManagedProperty("#{curveService}")
	private CurveDao curveDao;
	
	@ManagedProperty("#{loadDischargeService}")
	private LoadDischargeDao loadDischargeDao;
	
	@ManagedProperty("#{fromTypeService}")
	private FromTypeDao fromTypeDao;
	
	@ManagedProperty("#{itiDefaultActionDetailService}")
	private ItiDefaultActionDetailDao itiDefaultActionDetailDao;
	
	@ManagedProperty("#{genNoteService}")
	private GenNoteDao genNoteDao;
	
	@ManagedProperty("#{storageService}")
	private StorageDao storageDao;
	
	private String tradeContractType = "Sell";
	
	private String note = "";
	private VdimMovementId genMovementId = null;
	private VgenNoteId genNoteId = null;
	
	/* Match Obligation */
	private List<VopsObligationMatch> matchObligations = new ArrayList<VopsObligationMatch>();
	private VopsObligationMatch matchObligationSource;
	private VopsObligationMatch matchObligationSelected;
	private List<Integer> movementsNumber = new ArrayList<Integer>();
	
	private List<VopsObligationMatch> matchObligationLst = new ArrayList<VopsObligationMatch>();
	
	private static final Logger log = Logger.getLogger(DefaultConstants.APP_NAME + "." + ItineraryBean.class.getName());
	
	public ItineraryBean() { }
	
	public void init() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
		if (!facesContext.isPostback()) {
			if (!this.id.equals("-1")) {
				if (isNumber(this.id)) {
					int itineraryNum = Integer.parseInt(this.id);
					
					long time = System.nanoTime();
					
					log.info("Starting getting info for itinerary -> " + itineraryNum);
					
					this.opsItinerary = this.itineraryDao.getItineraryById(itineraryNum);
					
					log.info("Getting itinerary info took -> " + ((double) System.nanoTime() - time) / 1000000000.0 + "s");
					
					time = System.nanoTime();
					
					if (this.opsItinerary != null) {
						VopsItineraryId opsItineraryId = this.opsItinerary.getId();
						
						// Clear all tables
						this.movementLst.clear();
						
						log.info("Starting getting info for trade");
						
						// Setting default values
						TradeDao tradeDao = TradeDao.sharedInstance();
						
						// Trade number
						try {
							String tradeNumber = opsItineraryId.getItineraryName().split("-")[0];
							if (isNumber(tradeNumber)) {
								this.tradeHdr = tradeDao.getTradeHdrById(Integer.parseInt(tradeNumber));
								log.info("Getting trade header info took -> " + ((double) System.nanoTime() - time) / 1000000000.0 + "s");
								time = System.nanoTime();
								this.tradeInputId = tradeDao.getTradeInputByTradeNumber(Integer.parseInt(tradeNumber));
								log.info("Getting trade info took -> " + ((double) System.nanoTime() - time) / 1000000000.0 + "s");
								time = System.nanoTime();
								this.matchObligations = this.itineraryDao.getObligationMatchByTrade(Integer.parseInt(tradeNumber));
								log.info("Getting match obligation info took -> " + ((double) System.nanoTime() - time) / 1000000000.0 + "s");
								time = System.nanoTime();
								
								if (!this.matchObligations.isEmpty() && this.matchObligations.size() > 0) {
									this.loadMatchObligationDetail();
									log.info("Loading match obligation info took -> " + ((double) System.nanoTime() - time) / 1000000000.0 + "s");
									time = System.nanoTime();
								}
							} else {
								facesContext.addMessage(null, new FacesMessage("Unable to edit itinerary. The trade number provided for this itinerary does not exist"));
							}
						} catch (NumberFormatException nfe) {
							nfe.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						log.info("Starting loading references");
						
						// Itinerary name
						this.itineraryName = opsItineraryId.getItineraryName();
						
						// Internal company
						for (VdimCompanyId companyId : this.companyDao.getCompanyId()) {
							if (opsItineraryId.getInternalCompanyNum() == companyId.getNumCompany()) {
								this.companyId = companyId;
								break;
							}
						}
						
						// Operator
						for (VdimPersonId dimPersonId : this.personDao.getPersonId()) {
							if (opsItineraryId.getOperatorPersonNum() == dimPersonId.getNumperson()) {
								this.personId = dimPersonId;
								break;
							}
						}
						
						// Trade, Open quantity, UOM code
						if (this.tradeInputId != null) {
							BigDecimal openQty = BigDecimal.ZERO;
							
							List<VdimTransfer> transfers = this.itineraryDao.getByTrade(this.tradeInputId.getTradeNum());
							
							if (transfers != null && !transfers.isEmpty() && transfers.size() > 0) {
								for (VdimTransfer transfer : transfers) {
									if (transfer != null) {
										BigDecimal scheduledQty = transfer.getId().getQtyTradeSched();
										
										openQty = openQty.add(scheduledQty);
									}
								}
							}
							
							BigDecimal availableOpenQty = BigDecimal.ZERO;
							availableOpenQty = this.tradeInputId.getTradeQty().subtract(openQty);
							
							this.tradeQty = this.tradeInputId.getTradeQty();
							this.missingTradeQty = availableOpenQty;
							this.realOpenQty = availableOpenQty;
							this.contractUomCd = this.tradeInputId.getContractUomCd();
							
							this.tradeContractType = this.tradeInputId.getBuySell();
							
							this.thereIsTradeNumber = true;
						}
						
						// Movements
						List<VopsMovement> opsMovements = opsItineraryId.getMovements();
						
						if (!opsMovements.isEmpty() && opsMovements.size() > 0) {
							for (VopsMovement opsMovement : opsMovements) {
								VdimMovementId dimMovementId = new VdimMovementId();
								
								// Action info
								VopsItiDefaultActionDetail itiDefaultActionDetail = this.itiDefaultActionDetailDao.getByItineraryAndMovement(opsMovement.getId().getItineraryNum(), opsMovement.getId().getMovementNum());
								
								if (itiDefaultActionDetail != null) {
									dimMovementId.setActionDetailNum(itiDefaultActionDetail.getId().getActionDetailNum());
									dimMovementId.setActionCompletionDt(itiDefaultActionDetail.getId().getActionCompletionDt());
								}
								
								// Adding movement number
								this.movementsNumber.add(opsMovement.getId().getMovementNum());
								
								// Movement number
								dimMovementId.setMovementNum(opsMovement.getId().getMovementNum());
								
								// Equipment number
								dimMovementId.setEquipmentNum(opsMovement.getId().getEquipmentNum());
								
								// MOT
								for (VgenIndicatorId dimMotId : this.motDao.gettMotId()) {
									if (opsMovement.getId().getMotTypeInd() == dimMotId.getIndValue()) {
										dimMovementId.setMotTypeInd(dimMotId);
										break;
									}
								}
								
								// Vehicle
								for (VrefMotId refMotId : this.refMotDao.getRefMotId()) {
									if (opsMovement.getId().getMotNum() == refMotId.getMotNum()) {
										dimMovementId.setMotNumId(refMotId);
										break;
									}
								}
								
								// Movement Status
								for (VgenIndicatorId dimMovementStatus : this.motDao.getsMotId()) {
									if (opsMovement.getId().getMovementStatusInd() == dimMovementStatus.getIndValue()) {
										dimMovementId.setMovementStatusInd(dimMovementStatus);
										break;
									}
								}
								
								// Depart, Arrive location
								List<VdimLocationId> dimLocationsId = this.locationDao.getLocationId();
								
								for (VdimLocationId dimLocationId : dimLocationsId) {
									if (opsMovement.getId().getDepartLocationNum() == dimLocationId.getNumLocation()) {
										dimMovementId.setDepartLocationNum(dimLocationId);
									}
									
									if (opsMovement.getId().getArriveLocationNum() == dimLocationId.getNumLocation()) {
										dimMovementId.setArriveLocationNum(dimLocationId);
									}
									
									if (dimMovementId.getDepartLocationNum() != null && dimMovementId.getArriveLocationNum() != null) {
										break;
									}
								}
								
								// Duration time
								Date departDt = opsMovement.getId().getDepartDt();
								Date arriveDt = opsMovement.getId().getArriveDt();
								
								long departTm = departDt.getTime();
								long arriveTm = arriveDt.getTime();
								
								long diffDay = (arriveTm - departTm) / (1000 * 60 * 60 * 24);
								
								dimMovementId.setDurationTm((int) (long) diffDay);
								
								// Depart on
								dimMovementId.setDepartDt(departDt);
								
								// Depart port status ind
								dimMovementId.setDepartPortCompStatusInd(opsMovement.getId().getDepartPortCompStatusInd());
								
								// Depart port approved ind
								dimMovementId.setDepartPortMisApprovInd(opsMovement.getId().getDepartPortMisApprovInd());
								
								// Arrive on
								dimMovementId.setArriveDt(arriveDt);
								
								// Arrive port status ind
								dimMovementId.setArrivePortCompStatusInd(opsMovement.getId().getArrivePortCompStatusInd());
								
								// Arrive port approved ind
								dimMovementId.setArrivePortMisApprovInd(opsMovement.getId().getArrivePortMisApprovInd());
								
								// Status ind
								dimMovementId.setStatusInd(opsMovement.getId().getStatusInd());
								
								// Setting transfer
								dimMovementId.setTransfer(opsMovement.getId().getTransfer());
								
								// Cargos
								List<VdimCargo> dimCargos = opsMovement.getId().getCargos();
								
								if (dimCargos != null && !dimCargos.isEmpty() && dimCargos.size() > 0) {
									List<VdimCargoId> dimCargosId = new ArrayList<VdimCargoId>();
									
									for (VdimCargo dimCargo : dimCargos) {
										// Strategy
										for (VdimStrategyId dimStrategyId : this.strategyDao.getStrategyId()) {
											if (dimCargo.getId().getNumStategy() == dimStrategyId.getNumStrategy()) {
												dimCargo.getId().setNumStrategy(dimStrategyId);
												break;
											}
										}
										
										// Commodity
										for (VdimCommodityId dimCommodityId : this.commodityDao.getCommodityId()) {
											if (dimCargo.getId().getCodCommodity().equals(dimCommodityId.getCodCommodity())) {
												dimCargo.getId().setCommodityCod(dimCommodityId);
												break;
											}
										}
										
										// Quantity, Price uom
										for (VdimUnitOfMeasureId dimUnitOfMeasureId : this.unitOfMeasureDao.getUnitOfMeasureId()) {
											if (dimCargo.getId().getCodQtyUom().equals(dimUnitOfMeasureId.getCodUom())) {
												dimCargo.getId().setQtyUomCod(dimUnitOfMeasureId);
											}
											
											if (dimCargo.getId().getCodPrcUom().equals(dimUnitOfMeasureId.getCodUom())) {
												dimCargo.getId().setPrcUomCod(dimUnitOfMeasureId);
											}
											
											if (dimCargo.getId().getQtyUomCod() != null && dimCargo.getId().getPrcUomCod() != null) {
												break;
											}
										}
										
										// Currency
										for (VdimCurrencyId dimCurrencyId : this.currencyDao.getCurrencyId()) {
											if (dimCargo.getId().getCodPrcCur().equals(dimCurrencyId.getCodcurr())) {
												dimCargo.getId().setCurrPriceCod(dimCurrencyId);
												break;
											}
										}
										
										// MTM Curve
										for (VdimMktQuoteDefinitionId dimCommodityStrategyId : this.curveDao.getCommodityStrategyId()) {
											if (dimCargo.getId().getNumMtmcurve() == dimCommodityStrategyId.getNumdefquote()) {
												dimCargo.getId().setMtmCurveNum(dimCommodityStrategyId);
												break;
											}
										}
										
										// Adding cargo
										dimCargosId.add(dimCargo.getId());
									}
									
									// Adding cargo list
									dimMovementId.setCargoLst(dimCargosId);
								}
								
								// Adding parent reference bean
								dimMovementId.setItinerary(this);
								
								// Adding strategy service reference
								dimMovementId.setStrategyDao(this.strategyDao);
								
								// Adding commodity service reference
								dimMovementId.setCommodityDao(this.commodityDao);
								
								// Adding unit of measure service reference
								dimMovementId.setUnitOfMeasureDao(this.unitOfMeasureDao);
								
								// Adding currency service reference
								dimMovementId.setCurrencyDao(this.currencyDao);
								
								// Adding MTM Curve service reference
								dimMovementId.setCurveDao(this.curveDao);
								
								// Adding location service reference
								dimMovementId.setLocationDao(this.locationDao);
								
								// Adding load/discharge service reference
								dimMovementId.setLoadDischargeDao(this.loadDischargeDao);
								
								// Adding from type service reference
								dimMovementId.setFromTypeDao(this.fromTypeDao);
								
								// Adding user bean reference
								dimMovementId.setUser(this.user);
								
								this.movementLst.add(dimMovementId);
								
								// Set Obligation Match
								if (opsMovement.getId().getObligationMatch() > 0) {
									List<VopsObligationMatch> opsObligationMatches = this.itineraryDao.getObligationByMovement(opsMovement.getId().getMovementNum());
									
									if (!opsObligationMatches.isEmpty() && opsObligationMatches.size() > 0) {
										for (VopsObligationMatch opsObligationMatch : opsObligationMatches) {
											if (!opsObligationMatch.getId().getOpsObligationDetail().isEmpty() && opsObligationMatch.getId().getOpsObligationDetail().size() > 0) {
												for (VopsObligationDetail opsObligationDetail : opsObligationMatch.getId().getOpsObligationDetail()) {
													if (opsObligationDetail.getId().getBuySellInd() == -1) { // Sell
														VtradeInputId tradeInputIdSell = null;
														
														try {
															tradeInputIdSell = tradeDao.getTradeInputByTradeNumber(opsObligationDetail.getId().getTradeNum());
														} catch (Exception e) {
															e.printStackTrace();
														}
														
														if (tradeInputIdSell != null) {
															tradeInputIdSell.setScheduledQty(opsObligationDetail.getId().getScheduledQty());
															tradeInputIdSell.setObligationDetailNum(opsObligationDetail.getId().getObligationDetailNum());
															
															opsObligationMatch.getId().setsTradeInputId(tradeInputIdSell);
															
															BigDecimal openQty = BigDecimal.ZERO;
															
															List<VdimTransfer> transfers = this.itineraryDao.getByTrade(tradeInputIdSell.getTradeNum());
															
															if (transfers != null && !transfers.isEmpty() && transfers.size() > 0) {
																for (VdimTransfer transfer : transfers) {
																	BigDecimal scheduledQty = transfer.getId().getQtyTradeSched();
																	
																	openQty = openQty.add(scheduledQty);
																}
															}
															
															BigDecimal availableOpenQty = BigDecimal.ZERO;
															availableOpenQty = tradeInputIdSell.getTradeQty().subtract(openQty);
															
															opsObligationMatch.getId().getsTradeInputId().setOpenQty(availableOpenQty);
														}
													}
													
													if (opsObligationDetail.getId().getBuySellInd() == 1) { // Buy
														VtradeInputId tradeInputIdBuy = null;
														
														try {
															tradeInputIdBuy = tradeDao.getTradeInputByTradeNumber(opsObligationDetail.getId().getTradeNum());
														} catch (Exception e) {
															e.printStackTrace();
														}
														
														if (tradeInputIdBuy != null) {
															tradeInputIdBuy.setScheduledQty(opsObligationDetail.getId().getScheduledQty());
															tradeInputIdBuy.setObligationDetailNum(opsObligationDetail.getId().getObligationDetailNum());
															
															opsObligationMatch.getId().setpTradeInputId(tradeInputIdBuy);
															opsObligationMatch.getId().getpTradeInputId().setOpenQty(this.realOpenQty);
														}
													}
												}
											}
											
											this.matchObligationLst.add(opsObligationMatch);
										}
									}
								}
							}
						}
						
						log.info("Loading references took -> " + ((double) System.nanoTime() - time) / 1000000000.0 + "s");
					}
				}
			}
		}
	}
	
	private void reInit() {
		if (isNumber(this.id)) {
			int itineraryNum = Integer.parseInt(this.id);
			
			this.opsItinerary = this.itineraryDao.getItineraryById(itineraryNum);
			
			if (this.opsItinerary != null) {
				VopsItineraryId opsItineraryId = this.opsItinerary.getId();
				
				// Clear all tables
				this.movementLst.clear();
				
				// Setting default values
				TradeDao tradeDao = TradeDao.sharedInstance();
				
				// Trade number
				try {
					String tradeNumber = opsItineraryId.getItineraryName().split("-")[0];
					if (isNumber(tradeNumber)) {
						this.tradeHdr = tradeDao.getTradeHdrById(Integer.parseInt(tradeNumber));
						this.tradeInputId = tradeDao.getTradeInputByTradeNumber(Integer.parseInt(tradeNumber));
						this.matchObligations = this.itineraryDao.getObligationMatchByTrade(Integer.parseInt(tradeNumber));
						
						if (!this.matchObligations.isEmpty() && this.matchObligations.size() > 0) {
							this.loadMatchObligationDetail();
						}
					}
				} catch (NumberFormatException nfe) {
					nfe.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				// Itinerary name
				this.itineraryName = opsItineraryId.getItineraryName();
				
				// Internal company
				for (VdimCompanyId companyId : this.companyDao.getCompanyId()) {
					if (opsItineraryId.getInternalCompanyNum() == companyId.getNumCompany()) {
						this.companyId = companyId;
						break;
					}
				}
				
				// Operator
				for (VdimPersonId dimPersonId : this.personDao.getPersonId()) {
					if (opsItineraryId.getOperatorPersonNum() == dimPersonId.getNumperson()) {
						this.personId = dimPersonId;
						break;
					}
				}
				
				// Trade, Open quantity, UOM code
				if (this.tradeInputId != null) {
					BigDecimal openQty = BigDecimal.ZERO;
					
					List<VdimTransfer> transfers = this.itineraryDao.getByTrade(this.tradeInputId.getTradeNum());
					
					if (transfers != null && !transfers.isEmpty() && transfers.size() > 0) {
						for (VdimTransfer transfer : transfers) {
							if (transfer != null) {
								BigDecimal scheduledQty = transfer.getId().getQtyTradeSched();
								
								openQty = openQty.add(scheduledQty);
							}
						}
					}
					
					BigDecimal availableOpenQty = BigDecimal.ZERO;
					availableOpenQty = this.tradeInputId.getTradeQty().subtract(openQty);
					
					this.tradeQty = this.tradeInputId.getTradeQty();
					this.missingTradeQty = availableOpenQty;
					this.realOpenQty = availableOpenQty;
					this.contractUomCd = this.tradeInputId.getContractUomCd();
					
					this.tradeContractType = this.tradeInputId.getBuySell();
					
					this.thereIsTradeNumber = true;
				}
				
				// Movements
				List<VopsMovement> opsMovements = opsItineraryId.getMovements();
				
				if (!opsMovements.isEmpty() && opsMovements.size() > 0) {
					for (VopsMovement opsMovement : opsMovements) {
						VdimMovementId dimMovementId = new VdimMovementId();
						
						// Action info
						VopsItiDefaultActionDetail itiDefaultActionDetail = this.itiDefaultActionDetailDao.getByItineraryAndMovement(opsMovement.getId().getItineraryNum(), opsMovement.getId().getMovementNum());
						
						if (itiDefaultActionDetail != null) {
							dimMovementId.setActionDetailNum(itiDefaultActionDetail.getId().getActionDetailNum());
							dimMovementId.setActionCompletionDt(itiDefaultActionDetail.getId().getActionCompletionDt());
						}
						
						// Adding movement number
						this.movementsNumber.add(opsMovement.getId().getMovementNum());
						
						// Movement number
						dimMovementId.setMovementNum(opsMovement.getId().getMovementNum());
						
						// Equipment number
						dimMovementId.setEquipmentNum(opsMovement.getId().getEquipmentNum());
						
						// MOT
						for (VgenIndicatorId dimMotId : this.motDao.gettMotId()) {
							if (opsMovement.getId().getMotTypeInd() == dimMotId.getIndValue()) {
								dimMovementId.setMotTypeInd(dimMotId);
								break;
							}
						}
						
						// Vehicle
						for (VrefMotId refMotId : this.refMotDao.getRefMotId()) {
							if (opsMovement.getId().getMotNum() == refMotId.getMotNum()) {
								dimMovementId.setMotNumId(refMotId);
								break;
							}
						}
						
						// Movement Status
						for (VgenIndicatorId dimMovementStatus : this.motDao.getsMotId()) {
							if (opsMovement.getId().getMovementStatusInd() == dimMovementStatus.getIndValue()) {
								dimMovementId.setMovementStatusInd(dimMovementStatus);
								break;
							}
						}
						
						// Depart, Arrive location
						List<VdimLocationId> dimLocationsId = this.locationDao.getLocationId();
						
						for (VdimLocationId dimLocationId : dimLocationsId) {
							if (opsMovement.getId().getDepartLocationNum() == dimLocationId.getNumLocation()) {
								dimMovementId.setDepartLocationNum(dimLocationId);
							}
							
							if (opsMovement.getId().getArriveLocationNum() == dimLocationId.getNumLocation()) {
								dimMovementId.setArriveLocationNum(dimLocationId);
							}
							
							if (dimMovementId.getDepartLocationNum() != null && dimMovementId.getArriveLocationNum() != null) {
								break;
							}
						}
						
						// Duration time
						Date departDt = opsMovement.getId().getDepartDt();
						Date arriveDt = opsMovement.getId().getArriveDt();
						
						long departTm = departDt.getTime();
						long arriveTm = arriveDt.getTime();
						
						long diffDay = (arriveTm - departTm) / (1000 * 60 * 60 * 24);
						
						dimMovementId.setDurationTm((int) (long) diffDay);
						
						// Depart on
						dimMovementId.setDepartDt(departDt);
						
						// Depart port status ind
						dimMovementId.setDepartPortCompStatusInd(opsMovement.getId().getDepartPortCompStatusInd());
						
						// Depart port approved ind
						dimMovementId.setDepartPortMisApprovInd(opsMovement.getId().getDepartPortMisApprovInd());
						
						// Arrive on
						dimMovementId.setArriveDt(arriveDt);
						
						// Arrive port status ind
						dimMovementId.setArrivePortCompStatusInd(opsMovement.getId().getArrivePortCompStatusInd());
						
						// Arrive port approved ind
						dimMovementId.setArrivePortMisApprovInd(opsMovement.getId().getArrivePortMisApprovInd());
						
						// Status ind
						dimMovementId.setStatusInd(opsMovement.getId().getStatusInd());
						
						// Setting transfer
						dimMovementId.setTransfer(opsMovement.getId().getTransfer());
						
						// Cargos
						List<VdimCargo> dimCargos = opsMovement.getId().getCargos();
						
						if (dimCargos != null && !dimCargos.isEmpty() && dimCargos.size() > 0) {
							List<VdimCargoId> dimCargosId = new ArrayList<VdimCargoId>();
							
							for (VdimCargo dimCargo : dimCargos) {
								// Strategy
								for (VdimStrategyId dimStrategyId : this.strategyDao.getStrategyId()) {
									if (dimCargo.getId().getNumStategy() == dimStrategyId.getNumStrategy()) {
										dimCargo.getId().setNumStrategy(dimStrategyId);
										break;
									}
								}
								
								// Commodity
								for (VdimCommodityId dimCommodityId : this.commodityDao.getCommodityId()) {
									if (dimCargo.getId().getCodCommodity().equals(dimCommodityId.getCodCommodity())) {
										dimCargo.getId().setCommodityCod(dimCommodityId);
										break;
									}
								}
								
								// Quantity, Price uom
								for (VdimUnitOfMeasureId dimUnitOfMeasureId : this.unitOfMeasureDao.getUnitOfMeasureId()) {
									if (dimCargo.getId().getCodQtyUom().equals(dimUnitOfMeasureId.getCodUom())) {
										dimCargo.getId().setQtyUomCod(dimUnitOfMeasureId);
									}
									
									if (dimCargo.getId().getCodPrcUom().equals(dimUnitOfMeasureId.getCodUom())) {
										dimCargo.getId().setPrcUomCod(dimUnitOfMeasureId);
									}
									
									if (dimCargo.getId().getQtyUomCod() != null && dimCargo.getId().getPrcUomCod() != null) {
										break;
									}
								}
								
								// Currency
								for (VdimCurrencyId dimCurrencyId : this.currencyDao.getCurrencyId()) {
									if (dimCargo.getId().getCodPrcCur().equals(dimCurrencyId.getCodcurr())) {
										dimCargo.getId().setCurrPriceCod(dimCurrencyId);
										break;
									}
								}
								
								// MTM Curve
								for (VdimMktQuoteDefinitionId dimCommodityStrategyId : this.curveDao.getCommodityStrategyId()) {
									if (dimCargo.getId().getNumMtmcurve() == dimCommodityStrategyId.getNumdefquote()) {
										dimCargo.getId().setMtmCurveNum(dimCommodityStrategyId);
										break;
									}
								}
								
								// Adding cargo
								dimCargosId.add(dimCargo.getId());
							}
							
							// Adding cargo list
							dimMovementId.setCargoLst(dimCargosId);
						}
						
						// Adding parent reference bean
						dimMovementId.setItinerary(this);
						
						// Adding strategy service reference
						dimMovementId.setStrategyDao(this.strategyDao);
						
						// Adding commodity service reference
						dimMovementId.setCommodityDao(this.commodityDao);
						
						// Adding unit of measure service reference
						dimMovementId.setUnitOfMeasureDao(this.unitOfMeasureDao);
						
						// Adding currency service reference
						dimMovementId.setCurrencyDao(this.currencyDao);
						
						// Adding MTM Curve service reference
						dimMovementId.setCurveDao(this.curveDao);
						
						// Adding location service reference
						dimMovementId.setLocationDao(this.locationDao);
						
						// Adding load/discharge service reference
						dimMovementId.setLoadDischargeDao(this.loadDischargeDao);
						
						// Adding from type service reference
						dimMovementId.setFromTypeDao(this.fromTypeDao);
						
						// Adding user bean reference
						dimMovementId.setUser(this.user);
						
						this.movementLst.add(dimMovementId);
						
						// Set Obligation Match
						if (opsMovement.getId().getObligationMatch() > 0) {
							List<VopsObligationMatch> opsObligationMatches = this.itineraryDao.getObligationByMovement(opsMovement.getId().getMovementNum());
							
							if (!opsObligationMatches.isEmpty() && opsObligationMatches.size() > 0) {
								for (VopsObligationMatch opsObligationMatch : opsObligationMatches) {
									if (!opsObligationMatch.getId().getOpsObligationDetail().isEmpty() && opsObligationMatch.getId().getOpsObligationDetail().size() > 0) {
										for (VopsObligationDetail opsObligationDetail : opsObligationMatch.getId().getOpsObligationDetail()) {
											if (opsObligationDetail.getId().getBuySellInd() == -1) { // Sell
												VtradeInputId tradeInputIdSell = null;
												
												try {
													tradeInputIdSell = tradeDao.getTradeInputByTradeNumber(opsObligationDetail.getId().getTradeNum());
												} catch (Exception e) {
													e.printStackTrace();
												}
												
												if (tradeInputIdSell != null) {
													tradeInputIdSell.setScheduledQty(opsObligationDetail.getId().getScheduledQty());
													tradeInputIdSell.setObligationDetailNum(opsObligationDetail.getId().getObligationDetailNum());
													
													opsObligationMatch.getId().setsTradeInputId(tradeInputIdSell);
													
													BigDecimal openQty = BigDecimal.ZERO;
													
													List<VdimTransfer> transfers = this.itineraryDao.getByTrade(tradeInputIdSell.getTradeNum());
													
													if (transfers != null && !transfers.isEmpty() && transfers.size() > 0) {
														for (VdimTransfer transfer : transfers) {
															BigDecimal scheduledQty = transfer.getId().getQtyTradeSched();
															
															openQty = openQty.add(scheduledQty);
														}
													}
													
													BigDecimal availableOpenQty = BigDecimal.ZERO;
													availableOpenQty = tradeInputIdSell.getTradeQty().subtract(openQty);
													
													opsObligationMatch.getId().getsTradeInputId().setOpenQty(availableOpenQty);
												}
											}
											
											if (opsObligationDetail.getId().getBuySellInd() == 1) { // Buy
												VtradeInputId tradeInputIdBuy = null;
												
												try {
													tradeInputIdBuy = tradeDao.getTradeInputByTradeNumber(opsObligationDetail.getId().getTradeNum());
												} catch (Exception e) {
													e.printStackTrace();
												}
												
												if (tradeInputIdBuy != null) {
													tradeInputIdBuy.setScheduledQty(opsObligationDetail.getId().getScheduledQty());
													tradeInputIdBuy.setObligationDetailNum(opsObligationDetail.getId().getObligationDetailNum());
													
													opsObligationMatch.getId().setpTradeInputId(tradeInputIdBuy);
													opsObligationMatch.getId().getpTradeInputId().setOpenQty(this.realOpenQty);
												}
											}
										}
									}
									
									this.matchObligationLst.add(opsObligationMatch);
								}
							}
						}
					}
				}
			}
		}
	}
	
	// Load all trades according to a given trade number
	public List<VtradeHdr> onTradeComplete(String _tradeNumber) {
		if (this.id.equals("-1")) {
			List<VtradeHdr> lstTradesId = new ArrayList<VtradeHdr>();
			
	        if (_tradeNumber != null && !_tradeNumber.trim().equals("")) {
	        	if (isNumber(_tradeNumber)) {
	        		try {
	        			TradeDao tradeDao = TradeDao.sharedInstance();
	        			
	        			//lstTradesId = tradeDao.getTradeInputByTradeNumber(Integer.parseInt(_tradeNumber));
//	        			lstTradesId = tradeDao.getTradeHdrLikeId(_tradeNumber);
	        			List<VtradeHdr> fakeTradeHdrLst = tradeDao.getTradeHdrLikeId(_tradeNumber);
	        			
	        			for (VtradeHdr tradeHdr : fakeTradeHdrLst) {
	    					if (tradeHdr.getId().getBuySellInd() == 1 && tradeHdr.getId().getOpenQty().compareTo(BigDecimal.ZERO) > 0) { // Only add buy trades
	    						lstTradesId.add(tradeHdr);
	    					}
	    				}
	        		} catch (Exception e) {
	        			e.printStackTrace();
	        		}
	        	}
	        }
			
	        return lstTradesId;
		} else {
			return null;
		}
	}
	
	// On trade selected, change some fields
	public void onTradeSelect(SelectEvent selectEvent) {
		if (selectEvent.getObject() != null && selectEvent.getObject() instanceof VtradeHdr) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			
			VtradeHdr tradeHdr = (VtradeHdr) selectEvent.getObject();
			
			TradeDao tradeDao = TradeDao.sharedInstance();
			
			VtradeInputId tradeInputId = tradeDao.getTradeInputByTradeNumber(tradeHdr.getId().getTradeNum());
			
			if (this.id.equals("-1")) {
				BigDecimal openQty = BigDecimal.ZERO;
				
				List<VdimTransfer> transfers = this.itineraryDao.getByTrade(tradeInputId.getTradeNum());
				
				if (transfers != null && !transfers.isEmpty() && transfers.size() > 0) {
					for (VdimTransfer transfer : transfers) {
						BigDecimal scheduledQty = transfer.getId().getQtyTradeSched();
						
						openQty = openQty.add(scheduledQty);
					}
				}
				
				BigDecimal availableOpenQty = BigDecimal.ZERO;
				availableOpenQty = tradeInputId.getTradeQty().subtract(openQty);
				
				if (availableOpenQty.compareTo(BigDecimal.ZERO) == 1) {
					List<VopsItinerary> opsItineraries = this.itineraryDao.getItineraryByName(String.valueOf(tradeInputId.getTradeNum()) + "-1-" + String.valueOf(availableOpenQty.intValue()));
					
					if (!opsItineraries.isEmpty() && opsItineraries.size() > 0) {
						facesContext.addMessage(null, new FacesMessage("The Trade Number you selected already have an itinerary"));
						return;
					} else {
						// Updating hidden input of trade contract type
						this.tradeContractType = tradeInputId.getBuySell();
						
						this.itineraryName = tradeInputId.getTradeNum() + "-1-" + availableOpenQty.intValue();
						
						for (VdimCompanyId companyId : this.companyDao.getCompanyId()) {
							if (tradeInputId.getInternalCompanyNum() == companyId.getNumCompany()) {
								this.companyId = companyId;
							}
						}
						
						this.tradeQty = tradeInputId.getTradeQty();
						this.missingTradeQty = availableOpenQty;
						this.realOpenQty = availableOpenQty;
						this.contractUomCd = tradeInputId.getContractUomCd();
						this.thereIsTradeNumber = true;
						
						// Resetting movement table
						if (!this.movementLst.isEmpty() && this.movementLst.size() > 0) {
							for (int i = 0; i < this.movementLst.size(); i++) {
								this.movementLst.remove(i);
							}
							
							this.movementLst.clear();
						}
						
						// Initializing allocated quantity
						tradeInputId.setScheduledQty(BigDecimal.ZERO);
						
						// Set the trade selected
						this.setTradeInputId(tradeInputId);
						
						// Add a default movement
						//addMovement(null);
						
						if (!this.tradeContractType.equals("Sell")) {
							this.matchObligations = this.itineraryDao.getObligationMatchByTrade(tradeInputId.getTradeNum());
							
							if (!this.matchObligations.isEmpty() && this.matchObligations.size() > 0) {
								this.loadMatchObligationDetail();
							}
						}
						
						// Hiding match obligation popup if visible
						RequestContext.getCurrentInstance().execute("hideMatchObligation()");
					}
				} else {
					facesContext.addMessage(null, new FacesMessage("Trade has no more open quantity"));
					return;
				}
			}
		}
	}
	
	// Load match obligation detail
	private void loadMatchObligationDetail() {
		for (VopsObligationMatch opsObligationMatch : this.matchObligations) {
			if (opsObligationMatch.getId().getOpsObligationDetail().isEmpty() || opsObligationMatch.getId().getOpsObligationDetail().size() <= 0) {
				opsObligationMatch = this.itineraryDao.getObligationMatchById(opsObligationMatch.getId().getObligationMatchNum());
			}
			
			if (!opsObligationMatch.getId().getOpsObligationDetail().isEmpty() && opsObligationMatch.getId().getOpsObligationDetail().size() > 0) {
				for (VopsObligationDetail opsObligationDetail : opsObligationMatch.getId().getOpsObligationDetail()) {
					
					TradeDao tradeDao = TradeDao.sharedInstance();
					
					if (opsObligationDetail.getId().getBuySellInd() == 1) { // Purchase
						VtradeInputId tradeInputIdPurchase = null;
						
						try {
							tradeInputIdPurchase = tradeDao.getTradeInputByTradeNumber(opsObligationDetail.getId().getTradeNum());
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						if (tradeInputIdPurchase != null) {
							opsObligationMatch.getId().setpTradeInputId(tradeInputIdPurchase);
						}
						
						continue;
					}
					
					if (opsObligationDetail.getId().getBuySellInd() == -1) { // Sell
						VtradeInputId tradeInputIdSell = null;
						
						try {
							tradeInputIdSell = tradeDao.getTradeInputByTradeNumber(opsObligationDetail.getId().getTradeNum());
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						if (tradeInputIdSell != null) {
							opsObligationMatch.getId().setsTradeInputId(tradeInputIdSell);
						}
						
						continue;
					}
				}
			}
		}
	}
	
	// On itinerary name change
	public void onChangeItineraryName() {
		if (this.getItineraryName() != null) {
			this.itineraryName = this.getItineraryName();
		}
	}
	
	// On person selected
	public void onPersonSelected() {
		if (this.getPersonId() != null) {
			this.personId = this.getPersonId();
		}
	}
	
	// Adding a new movement
	public void addMovement(ActionEvent actionEvent) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
		if (this.tradeInputId == null) {
			facesContext.addMessage(null, new FacesMessage("Trade Number does not exist"));
		} else {
			if (this.tradeInputId.getTradeNum() > 0) {
				VdimMovementId movementId = new VdimMovementId();
				
				// Mean of transport - vehicle
				List<VgenIndicatorId> tMotIdLst = this.motDao.gettMotId();
				
				VgenIndicatorId tMotIdObj = null;
				
				for (VgenIndicatorId motId : tMotIdLst) {
					if (motId.getIndValue() == 0) { // 0: Vessel
						tMotIdObj = motId;
						break;
					}
				}
				
				// Setting mean of transport - vehicle
				movementId.setMotTypeInd(tMotIdObj);
				
				// Ref Mot Id - vehicle
				List<VrefMotId> refMotIdLst = this.refMotDao.getRefMotId();
						
				VrefMotId refMotIdObj = null;
				
				for (VrefMotId refMotId : refMotIdLst) {
					if (refMotId.getMotNum() == 2098) { // 2098: Pending
						refMotIdObj = refMotId;
						break;
					}
				}
				
				// Setting ref mot id - vehicle
				movementId.setMotNumId(refMotIdObj);
				
				// Mean of transport - status
				List<VgenIndicatorId>  sMotIdLst = this.motDao.getsMotId();
				
				VgenIndicatorId sMotIdObj = null;
				
				for (VgenIndicatorId motId : sMotIdLst) {
					if (motId.getIndValue() == 1) { // 1: Scheduled
						sMotIdObj = motId;
						break;
					}
				}
				
				// Setting mean of transport - status
				movementId.setMovementStatusInd(sMotIdObj);
				
				// Depart location
				List<VdimLocationId> locationIdLst = this.locationDao.getLocationId();
				
				int departFromId = this.tradeInputId.getLocation();
				VdimLocationId departFromObj = null;
				
				for (VdimLocationId locationId : locationIdLst) {
					if (locationId.getNumLocation() == departFromId) {
						departFromObj = locationId;
						break;
					}
				}
				
				// Setting depart location
				movementId.setDepartLocationNum(departFromObj);
				
				// Arrive location
				int arriveAtId = this.tradeInputId.getDestinaton();
				VdimLocationId arriveAtObj = null;
				
				for (VdimLocationId locationId : locationIdLst) {
					if (locationId.getNumLocation() == arriveAtId) {
						arriveAtObj = locationId;
						break;
					}
				}
				
				// Setting arrive location
				movementId.setArriveLocationNum(arriveAtObj);
				
				// Adding parent reference bean
				movementId.setItinerary(this);
				
				// Adding strategy service reference
				movementId.setStrategyDao(this.strategyDao);
				
				// Adding commodity service reference
				movementId.setCommodityDao(this.commodityDao);
				
				// Adding unit of measure service reference
				movementId.setUnitOfMeasureDao(this.unitOfMeasureDao);
				
				// Adding currency service reference
				movementId.setCurrencyDao(this.currencyDao);
				
				// Adding MTM Curve service reference
				movementId.setCurveDao(this.curveDao);
				
				// Adding location service reference
				movementId.setLocationDao(this.locationDao);
				
				// Adding load/discharge service reference
				movementId.setLoadDischargeDao(this.loadDischargeDao);
				
				// Adding from type service reference
				movementId.setFromTypeDao(this.fromTypeDao);

				// Adding user bean reference
				movementId.setUser(this.user);
				
				// Adding a default cargo
				//movementId.addCargo(actionEvent);
				
				this.movementLst.add(movementId);
			} else {
				facesContext.addMessage(null, new FacesMessage("Trade Number can not be empty"));
			}
		}
	}
	
	// Removing a movement
	public void removeMovement(VdimMovementId movementId) {
		if (movementId != null) {
			if (!this.id.equals("-1")) {
				if (movementId.getMovementNum() > 0) {
					
					List<VdimCargoId> cargoLst = movementId.getCargoLst();
					if (cargoLst != null && !cargoLst.isEmpty() && cargoLst.size() > 0) {
						List<VdimMovementId> dimMovementIds = new ArrayList<VdimMovementId>();
						dimMovementIds.add(movementId);
						
						// Set itinerary
						Itinerary itinerary = new Itinerary();
						itinerary.setItineraryNumber(Integer.parseInt(this.id));
						itinerary.setMovementIds(dimMovementIds);
						
						// Build xml
						ItineraryCXL itineraryCXL = ItineraryCXL.sharedInstance();
						
						for (VdimCargoId cargoId : cargoLst) {
							itinerary.setCargoId(cargoId);
							
							itineraryCXL.setUser(this.user);
							itineraryCXL.setItinerary(itinerary);
							
							boolean couldRemoveCargo = itineraryCXL.removeCargo();
							
							if (couldRemoveCargo) {
								boolean couldRemoveMovement = itineraryCXL.removeMovement();
								
								if (!couldRemoveMovement) {
									FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Unable to delete the movement. Please contact your system administrator"));
								} else {
									BigDecimal capacity = cargoId.getCapacity() == null ? BigDecimal.ZERO : cargoId.getCapacity();
									
									// Adding the removing values to available capacity
									this.missingTradeQty = this.missingTradeQty.add(capacity);
									
									// Removing movement
									this.movementLst.remove(movementId);
									
									FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Movement successfully removed"));
								}
							} else {
								FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Unable to delete the movement. Please contact your system administrator"));
							}
							
							break;
						}
					} else {
						List<VdimMovementId> dimMovementIds = new ArrayList<VdimMovementId>();
						dimMovementIds.add(movementId);
						
						// Set itinerary
						Itinerary itinerary = new Itinerary();
						itinerary.setItineraryNumber(Integer.parseInt(this.id));
						itinerary.setMovementIds(dimMovementIds);
						
						// Build xml
						ItineraryCXL itineraryCXL = ItineraryCXL.sharedInstance();
						itineraryCXL.setUser(this.user);
						itineraryCXL.setItinerary(itinerary);
						
						boolean couldRemoveMovement = itineraryCXL.removeMovement();
						
						if (!couldRemoveMovement) {
							FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Unable to delete the movement. Please contact your system administrator"));
						} else {
							FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Movement successfully removed"));
							
							// Removing movement
							this.movementLst.remove(movementId);
						}
					}
				} else {
					List<VdimCargoId> cargoLst = movementId.getCargoLst();
					if (cargoLst != null && !cargoLst.isEmpty() && cargoLst.size() > 0) {
						for (VdimCargoId cargoId : cargoLst) {
							BigDecimal capacity = cargoId.getCapacity() == null ? BigDecimal.ZERO : cargoId.getCapacity();
							
							// Adding the removing values to available capacity
							this.missingTradeQty = this.missingTradeQty.add(capacity);
						}
					}
					
					// Removing movement
					this.movementLst.remove(movementId);
				}
				
			} else {
				List<VdimCargoId> cargoLst = movementId.getCargoLst();
				if (cargoLst != null && !cargoLst.isEmpty() && cargoLst.size() > 0) {
					for (VdimCargoId cargoId : cargoLst) {
						BigDecimal capacity = cargoId.getCapacity() == null ? BigDecimal.ZERO : cargoId.getCapacity();
						
						// Adding the removing values to available capacity
						this.missingTradeQty = this.missingTradeQty.add(capacity);
					}
				}
				
				// Removing movement
				this.movementLst.remove(movementId);
			}
		}
	}
	
	// Retrieving note data
	public void retrieveNote(VdimMovementId movementId) {
		this.genMovementId = movementId;
		
		VgenNote genNoteFound = this.genNoteDao.getByItineraryAndMovement(Integer.parseInt(this.id), movementId.getMovementNum());
		
		if (genNoteFound != null) {
			this.genNoteId = genNoteFound.getId();
			
			movementId.setNote(genNoteFound);
			
			this.note = fillNote(genNoteFound);
		} else {
			this.genNoteId = null;
			
			this.note = "";
		}
	}
	
	private String fillNote(VgenNote genNote) {
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
							html += "<p>" + (this.genMovementId != null ? sCurrentLine.substring(String.valueOf(this.genMovementId.getMovementNum()).length() + 12) : sCurrentLine) + "</p>";
						} else {
							html += "<p>" + sCurrentLine + "</p>";
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
	
	// On row expand for movement
	public void onRowExpandForMovement(ToggleEvent toggleEvent) {
		if (toggleEvent.getData() != null && toggleEvent.getData() instanceof VdimMovementId && "VISIBLE".equals(toggleEvent.getVisibility().name())) {
			VdimMovementId movementId = (VdimMovementId) toggleEvent.getData();
			
			if (!movementId.getCargoLst().isEmpty() && movementId.getCargoLst().size() > 0) {
//				if (this.opsItinerary == null) {
//					this.opsItinerary = this.itineraryDao.getItineraryById(Integer.parseInt(this.id));
//				}
				
				int itineraryNum = this.opsItinerary.getId().getItineraryNum(); // Itinerary number
				int equipmentNum = movementId.getEquipmentNum(); // Equipment number
				int cargoNum = movementId.getCargoLst().get(0).getNumCargo(); // Cargo number - get the first child of the movement
				
				List<VdimTransfer> transfer = this.itineraryDao.getTransferByItineraryEquipmentCargo(itineraryNum, equipmentNum, cargoNum);
				
				if (!transfer.isEmpty() && transfer.size() > 0 && movementId.getTransferLst().size() < transfer.size()) {
					List<VdimTransferId> transferId = new ArrayList<VdimTransferId>();
					
					List<VgenIndicatorId> lstLoadDischarge = this.loadDischargeDao.getLoadDischargeId();
					
					List<VgenIndicatorId> lstFromType = this.fromTypeDao.getFromTypeId();
					
					List<VdimLocationId> lstLocation = this.locationDao.getLocationId();
					
					for (VdimTransfer dimTransfer : transfer) {
						if (movementId.getTransferLst().contains(dimTransfer)) {
							continue;
						}
						
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
						}
						
						if (!lstLocation.isEmpty() && lstLocation.size() > 0) {
							for (VdimLocationId location : lstLocation) {
								if (dimTransfer.getId().getNumLocation() == location.getNumLocation()) {
									dimTransfer.getId().setLocationObj(location);
									break;
								}
							}
						}
						
						dimTransfer.getId().setItinerary(this);
						
						transferId.add(dimTransfer.getId());
					}
					
					movementId.setTransferLst(transferId);
				}
			}
		}
	}
	
	// On row expand for transfer
	public void onRowExpandForTransfer(ToggleEvent toggleEvent) {
		if (toggleEvent.getData() != null && toggleEvent.getData() instanceof VdimTransferId && "VISIBLE".equals(toggleEvent.getVisibility().name())) {
			VdimTransferId transferId = (VdimTransferId) toggleEvent.getData();
			
			if (transferId.getTagLst() == null || transferId.getTagLst().isEmpty() || transferId.getTagLst().size() <= 0) {
				List<VbuildDrawTag> transferTag = this.itineraryDao.getTransferTagByBuild(transferId.getNumToItinerary() > 0 ?transferId.getNumBuild() : transferId.getNumDraw());
				
				if (!transferTag.isEmpty() && transferTag.size() > 0) {
					List<VbuildDrawTagId> transferTagId = new ArrayList<VbuildDrawTagId>();
					
					BigDecimal tagQty = BigDecimal.ZERO;
					BigDecimal tagAllocQty = BigDecimal.ZERO;
					
					for (VbuildDrawTag buildDrawTag : transferTag) {
						tagQty = tagQty.add(new BigDecimal(buildDrawTag.getId().getTagQty()));
						tagAllocQty = tagAllocQty.add(new BigDecimal(buildDrawTag.getId().getTagAllocQty()));
						
						transferTagId.add(buildDrawTag.getId());
					}
					
					if (!transferTagId.isEmpty() && transferTagId.size() > 0) {
						transferId.setTagQty(tagQty);
						transferId.setTagAllocQty(tagAllocQty);
						transferId.setTagLst(transferTagId);
					}
				}
			}
		}
	}
	
	// On show match obligation
	public void onShowMatchObligation(ActionEvent actionEvent) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
		if (this.tradeInputId != null) {
			if (this.tradeInputId.getBuySell().equals("Sell")) {
				facesContext.validationFailed();
				facesContext.addMessage(null, new FacesMessage("You can't do a Match Obligation of a sell trade contract"));
			}
		} else {
			facesContext.validationFailed();
			facesContext.addMessage(null, new FacesMessage("Match Obligation can't be done with an empty trade contract"));
		}
	}
	
	// Add trade contract of sell
	public void addSellTradeContract(VopsObligationMatch opsObligationMatch) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
		if (opsObligationMatch != null) {
			if (this.tradeInputId != null) {
				if (opsObligationMatch.getId().getTradeContractNumber() == null || opsObligationMatch.getId().getTradeContractNumber() <= 0) {
					facesContext.addMessage(null, new FacesMessage("The trade number specified for the sell does not exist"));
				} else {
					if (opsObligationMatch.getId().getTradeContractNumber() != this.tradeInputId.getTradeNum()) {
						if (opsObligationMatch.getId().getsTradeInputId() == null) {
							try {
								TradeDao tradeDao = TradeDao.sharedInstance();
								
								//List<VtradeInputId> vTradeInputIdLst = tradeDao.getTradeInputByTradeNumber(opsObligationMatch.getId().getTradeContractNumber());
								//if (!vTradeInputIdLst.isEmpty() && vTradeInputIdLst.size() > 0) {
									VtradeInputId vTradeInputId = tradeDao.getTradeInputByTradeNumber(opsObligationMatch.getId().getTradeContractNumber());
									if (vTradeInputId != null) {
										if (vTradeInputId.getBuySell().equals("Sell")) {
											vTradeInputId.setScheduledQty(BigDecimal.ZERO);
											
											BigDecimal openQty = BigDecimal.ZERO;
											
											List<VdimTransfer> transfers = this.itineraryDao.getByTrade(vTradeInputId.getTradeNum());
											
											if (transfers != null && !transfers.isEmpty() && transfers.size() > 0) {
												for (VdimTransfer transfer : transfers) {
													BigDecimal scheduledQty = transfer.getId().getQtyTradeSched();
													
													openQty = openQty.add(scheduledQty);
												}
											}
											
											BigDecimal availableOpenQty = BigDecimal.ZERO;
											availableOpenQty = vTradeInputId.getTradeQty().subtract(openQty);
											
											vTradeInputId.setOpenQty(availableOpenQty);
											
											opsObligationMatch.getId().setsTradeInputId(vTradeInputId);
											
											this.tradeContract = true;
											
											opsObligationMatch.getId().setTradeContractNumber(null);
										} else {
											facesContext.addMessage(null, new FacesMessage("The trade contract selected must be a sell"));
										}
									} else {
										facesContext.addMessage(null, new FacesMessage("The trade number specified for the sell does not exist"));
									}
//								} else {
//									facesContext.addMessage(null, new FacesMessage("The trade number specified for the sell does not exist"));
//								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else {
							facesContext.addMessage(null, new FacesMessage("You can not add more lines than existed in movement table"));
						}
					} else {
						facesContext.addMessage(null, new FacesMessage("Trade number of the purchase and trade number of the sell must be differents"));
					}
				}
			} else {
				facesContext.addMessage(null, new FacesMessage("You must specified a trade number of the purchase first"));
			}
		}
	}
	
	// Remove trade contract of sell
	public void removeSellTradeContract(VopsObligationMatch opsObligationMatch) {
		if (opsObligationMatch != null) {
			if (opsObligationMatch.getId().getsTradeInputId() != null) {
				this.tradeContract = false;
				
				opsObligationMatch.getId().setsTradeInputId(null);
			}
		}
	}
	
	// On cell edit of the purchase table	
	public void onCellEditPurchase(VtradeInputId tradeInputId) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
		if (tradeInputId != null) {
			BigDecimal openQty = tradeInputId.getOpenQty() == null ? BigDecimal.ZERO : tradeInputId.getOpenQty();
			BigDecimal scheduledQty = tradeInputId.getScheduledQty() == null ? BigDecimal.ZERO : tradeInputId.getScheduledQty();
			
			if (scheduledQty.compareTo(openQty) == 1) {
				facesContext.validationFailed();
				facesContext.addMessage(null, new FacesMessage("There's not sufficient open quantity to schedule"));
			}
		}
	}
	
	// On cell edit of the sell table
	public void onCellEditSell(VtradeInputId tradeInputIdPurchase, VtradeInputId tradeInputIdSell) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
		if (tradeInputIdPurchase != null && tradeInputIdSell != null) {
			BigDecimal openQty = tradeInputIdSell.getOpenQty() == null ? BigDecimal.ZERO : tradeInputIdSell.getOpenQty();
			BigDecimal scheduledSellQty = tradeInputIdSell.getScheduledQty() == null ? BigDecimal.ZERO : tradeInputIdSell.getScheduledQty();
			
			if (scheduledSellQty.compareTo(openQty) == 1) {
				facesContext.validationFailed();
				facesContext.addMessage(null, new FacesMessage("There's not sufficient open quantity to schedule"));
			}
			
			BigDecimal scheduledPurchaseQty = tradeInputIdPurchase.getScheduledQty() == null ? BigDecimal.ZERO : tradeInputIdPurchase.getScheduledQty();
			
			if (scheduledSellQty.compareTo(scheduledPurchaseQty) == 1) {
				facesContext.validationFailed();
				facesContext.addMessage(null, new FacesMessage("You can't schedule more quantity than the scheduled quantity in the purchase trade contract"));
			}
		}
	}
	
	// Save itinerary
	public void saveItinerary(ActionEvent actionEvent) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
		// If there's not any trade number, we do not save anything
		if (this.tradeInputId != null) {
			// If there's not any movement, we do not save anything
			if (this.movementLst != null && !this.movementLst.isEmpty() && this.movementLst.size() > 0) {
				int counter = 0;
				
				// Iterate through movement list
				for (VdimMovementId movementId : this.movementLst) {
					// MOT
					if (movementId.getMotTypeInd() == null) {
						facesContext.addMessage(null, new FacesMessage("MOT on each movement cannot be empty"));
						return;
					}
					
					// Vehicle
					if (movementId.getMotNumId() == null) {
						facesContext.addMessage(null, new FacesMessage("Vehicle on each movement cannot be empty"));
						return;
					} else {
						for (int i = counter + 1; i < this.movementLst.size(); ++i) {
							if (movementId.getMotNumId().getMotCd().equals(this.movementLst.get(i).getMotNumId().getMotCd())) {
								facesContext.addMessage(null, new FacesMessage("Vehicle on each movement must be different"));
								return;
							}
						}
					}
					
					// Status
					if (movementId.getMovementStatusInd() == null) {
						facesContext.addMessage(null, new FacesMessage("Status on each movement cannot be empty"));
						return;
					}
					
					// Depart From
					if (movementId.getDepartLocationNum() == null) {
						facesContext.addMessage(null, new FacesMessage("Depart From on each movement cannot be empty"));
						return;
					}
					
					// Depart On
					if (movementId.getDepartDt() == null) {
						facesContext.addMessage(null, new FacesMessage("Depart On on each movement cannot be empty"));
						return;
					}
					
					// Arrive At
					if (movementId.getArriveLocationNum() == null) {
						facesContext.addMessage(null, new FacesMessage("Arrive At on each movement cannot be empty"));
						return;
					}
					
					// Arrive On
					if (movementId.getArriveDt() == null) {
						facesContext.addMessage(null, new FacesMessage("Arrive On on each movement cannot be empty"));
						return;
					}
					
					// If there's not any cargo on each movement, we do not save anything
					if (movementId.getCargoLst() == null || movementId.getCargoLst().isEmpty() || movementId.getCargoLst().size() == 0) {
						facesContext.addMessage(null, new FacesMessage("Each movement must have at least one cargo item"));
						return;
					} else {
						// Check that each cargo has no empty values
						for (VdimCargoId cargoId : movementId.getCargoLst()) {
							if (cargoId.getNameCargo() == null || cargoId.getNameCargo().trim().equals("")) {
								facesContext.addMessage(null, new FacesMessage("Cargo Name on each cargo cannot be empty"));
								return;
							}
							
							if (cargoId.getNumStrategy() == null) {
								facesContext.addMessage(null, new FacesMessage("Strategy on each cargo cannot be empty"));
								return;
							}
							
							if (cargoId.getCommodityCod() == null) {
								facesContext.addMessage(null, new FacesMessage("Commodity on each cargo cannot be empty"));
								return;
							}
							
							if (cargoId.getQtyUomCod() == null) {
								facesContext.addMessage(null, new FacesMessage("Quantity UOM on each cargo cannot be empty"));
								return;
							}
							
							if (cargoId.getCurrPriceCod() == null) {
								facesContext.addMessage(null, new FacesMessage("Price Currency on each cargo cannot be empty"));
								return;
							}
							
							if (cargoId.getPrcUomCod() == null) {
								facesContext.addMessage(null, new FacesMessage("Price UOM on each cargo cannot be empty"));
								return;
							}
							
							if (cargoId.getMtmCurveNum() == null) {
								facesContext.addMessage(null, new FacesMessage("MTM Curve on each cargo cannot be empty"));
								return;
							}
							
							if (cargoId.getCapacity() == null) {
								facesContext.addMessage(null, new FacesMessage("Trade Quantity on each cargo cannot be empty"));
								return;
							}
						}
					}
					
					// Verifying transfer table
//					if (!movementId.getTransferLst().isEmpty() && movementId.getTransferLst().size() > 0) {
//						for (VdimTransferId transferId : movementId.getTransferLst()) {
//							if (transferId.getDtTransferComm() == null) {
//								facesContext.addMessage(null, new FacesMessage("Commencement Date on each transfer cannot be empty"));
//								return;
//							}
//							
//							if (transferId.getLoadDischargeObj().getIndValueName().equals(DefaultConstants.C_LOAD_FROM) && transferId.getTagLst().isEmpty() && transferId.getTagLst().size() <= 0) {
//								facesContext.addMessage(null, new FacesMessage("A transfer with 'Load From' type must have at least one tag"));
//								return;
//							}
//							
//							if (!transferId.getTagLst().isEmpty() && transferId.getTagLst().size() > 0) {
//								for (VbuildDrawTagId buildDrawTagId : transferId.getTagLst()) {
//									if (buildDrawTagId.getTagValue1() == null || buildDrawTagId.getTagValue1().equals("")) {
//										facesContext.addMessage(null, new FacesMessage("Container on each tag cannot be empty"));
//										return;
//									}
//									
//									if (buildDrawTagId.getTagValue2() == null || buildDrawTagId.getTagValue2().equals("")) {
//										facesContext.addMessage(null, new FacesMessage("ICO on each tag cannot be empty"));
//										return;
//									}
//									
//									if (buildDrawTagId.getTagQty() <= 0) {
//										facesContext.addMessage(null, new FacesMessage("Tag Quantity on each tag cannot be empty"));
//										return;
//									}
//								}
//							}
//						}
//					}
					
					counter++;
				}
				
				// Set itinerary
				Itinerary itinerary = new Itinerary();
				itinerary.setItineraryNumber(Integer.parseInt(this.id));
				itinerary.setItineraryName(this.itineraryName);
				itinerary.setPersonId(this.personId);
				itinerary.setCompanyId(this.companyId);
				
				if (!this.id.equals("-1")) {
					if (this.opsItinerary == null) {
						this.opsItinerary = this.itineraryDao.getItineraryById(Integer.parseInt(this.id));
					}
					
					itinerary.setItineraryStatusInd(this.opsItinerary.getId().getItineraryStatusInd());
					itinerary.setStatusInd(this.opsItinerary.getId().getStatusInd());
					itinerary.setCreateDt(this.opsItinerary.getId().getCreateDt());
					itinerary.setModifyDt(this.opsItinerary.getId().getLastModifyDt());
					itinerary.setModifyPersonId(this.opsItinerary.getId().getModifyPersonNum());
				}
				
				itinerary.setMovementIds(this.movementLst);
				
				// Build xml
				ItineraryCXL itineraryCXL = ItineraryCXL.sharedInstance();
				itineraryCXL.setUser(this.user);
				itineraryCXL.setItinerary(itinerary);
				
				int itineraryNumber = this.id.equals("-1") ? itineraryCXL.save() : itineraryCXL.update();
				
				if (itineraryNumber != -1) {
					facesContext.addMessage(null, new FacesMessage("Itinerary successfully " + (this.id.equals("-1") ? "saved" : "updated")));
					facesContext.addMessage(null, new FacesMessage("Itinerary number given: " + itineraryNumber));
					
					boolean reload = false;
					
					// Show the movement number given, so we can use these numbers for the MO
					this.movementsNumber = itineraryCXL.getMovementByItinerary(itineraryNumber);
					
					if (!this.movementsNumber.isEmpty() && this.movementsNumber.size() > 0) {
						String message = "";
						
						for (int i = 0; i < this.movementsNumber.size(); i++) {
							if (this.movementLst.get(i).getMovementNum() <= 0) {
								reload = true;
							}
							
							Integer movementNumber = this.movementsNumber.get(i); 
							
							message += movementNumber + ", ";
							
							this.movementLst.get(i).setMovementNum(movementNumber);
							
							// Action info
							VopsItiDefaultActionDetail itiDefaultActionDetail = this.itiDefaultActionDetailDao.getByItineraryAndMovement(itineraryNumber, movementNumber);
							
							if (itiDefaultActionDetail != null) {
								this.movementLst.get(i).setActionDetailNum(itiDefaultActionDetail.getId().getActionDetailNum());
							}
						}
						
						facesContext.addMessage(null, new FacesMessage("Movement (s) number given: " + message.substring(0, message.length() - 2)));
					}
					
					// We intent to save the cargos now with the new equipment number given
					List<Integer> cargosNumber = this.id.equals("-1") ? itineraryCXL.saveCargo(itineraryNumber) : itineraryCXL.updateCargo(itineraryNumber);
					if (cargosNumber.isEmpty() || cargosNumber.size() <= 0) {
						facesContext.addMessage(null, new FacesMessage("Unable to " + (this.id.equals("-1") ? "save" : "update") + " the cargos. Please contact your system administrator"));
					} else {
						facesContext.addMessage(null, new FacesMessage("Cargo (s) successfully " + (this.id.equals("-1") ? "saved" : "updated")));
						
						String message = "";
						
						for (int i = 0; i < cargosNumber.size(); i++) {
							Integer cargoNumber = cargosNumber.get(i);
							
							message += cargoNumber + ", ";
							
							this.movementLst.get(i).getCargoLst().get(0).setNumCargo(cargoNumber);
						}
						
						facesContext.addMessage(null, new FacesMessage("Cargo (s) number given: " + message.substring(0, message.length() - 2)));
					}
					
					if (this.id.equals("-1") || reload) {
						this.id = String.valueOf(itineraryNumber);
						
						this.reInit();
					}
				} else {
					facesContext.addMessage(null, new FacesMessage("Unable to " + (this.id.equals("-1") ? "save" : "update") + " the itinerary. Please contact your system administrator"));
				}
			} else {
				facesContext.addMessage(null, new FacesMessage("There are not any movements that can be " + (this.id.equals("-1") ? "saved" : "updated")));
			}
		} else {
			facesContext.addMessage(null, new FacesMessage("Trade Number does not exist"));
		}
	}
	
	// Update match obligation list
	public void updateMatchObligationRef(ActionEvent actionEvent) {
		if (this.tradeInputId != null) {
			this.matchObligations = this.itineraryDao.getObligationMatchByTrade(this.tradeInputId.getTradeNum());
			
			if (!this.matchObligations.isEmpty() && this.matchObligations.size() > 0) {
				this.loadMatchObligationDetail();
			}
		}
	}
	
	// Adding match obligation
	public void addMatchObligation(ActionEvent actionEvent) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
		if (this.movementsNumber.isEmpty() || this.movementsNumber.size() <= 0) {
			facesContext.addMessage(null, new FacesMessage("There are not any movements to do a match obligation with"));
		} else {
			VopsObligationMatchId opsObligationMatchId = new VopsObligationMatchId();
			
			if (this.tradeInputId != null) {
				opsObligationMatchId.setpTradeInputId(this.tradeInputId);
				opsObligationMatchId.getpTradeInputId().setOpenQty(this.realOpenQty);
			}
			
			VopsObligationMatch opsObligationMatch = new VopsObligationMatch();
			opsObligationMatch.setId(opsObligationMatchId);
			
			this.matchObligationLst.add(opsObligationMatch);
		}
	}
	
	// On selecting match obligation number
	public void onMatchObligationSelected() {
		if (this.matchObligationSelected != null && this.matchObligationSource != null) {
			this.matchObligationSource.getId().setObligationMatchNum(this.matchObligationSelected.getId().getObligationMatchNum());
			this.matchObligationSource.getId().setMatchDesc(this.matchObligationSelected.getId().getMatchDesc());
			this.matchObligationSource.getId().setMatchDt(this.matchObligationSelected.getId().getMatchDt());
			this.matchObligationSource.getId().setLastModifyDt(this.matchObligationSelected.getId().getLastModifyDt());
			this.matchObligationSource.getId().setModifyPersonNum(this.matchObligationSelected.getId().getModifyPersonNum());
			
			if (!this.matchObligationSelected.getId().getOpsObligationDetail().isEmpty() && this.matchObligationSelected.getId().getOpsObligationDetail().size() > 0) {
				for (VopsObligationDetail opsObligationDetail : this.matchObligationSelected.getId().getOpsObligationDetail()) {
					if (opsObligationDetail.getId().getBuySellInd() == -1) { // Sell
						VtradeInputId tradeInputIdSell = this.matchObligationSelected.getId().getsTradeInputId();
						
						if (tradeInputIdSell != null) {
							tradeInputIdSell.setScheduledQty(opsObligationDetail.getId().getScheduledQty());
							tradeInputIdSell.setObligationDetailNum(opsObligationDetail.getId().getObligationDetailNum());
							
							this.matchObligationSource.getId().setsTradeInputId(tradeInputIdSell);
							
							BigDecimal openQty = BigDecimal.ZERO;
							
							List<VdimTransfer> transfers = this.itineraryDao.getByTrade(tradeInputIdSell.getTradeNum());
							
							if (transfers != null && !transfers.isEmpty() && transfers.size() > 0) {
								for (VdimTransfer transfer : transfers) {
									BigDecimal scheduledQty = transfer.getId().getQtyTradeSched();
									
									openQty = openQty.add(scheduledQty);
								}
							}
							
							BigDecimal availableOpenQty = BigDecimal.ZERO;
							availableOpenQty = tradeInputIdSell.getTradeQty().subtract(openQty);
							
							this.matchObligationSource.getId().getsTradeInputId().setOpenQty(availableOpenQty);
						}
					}
					
					if (opsObligationDetail.getId().getBuySellInd() == 1) { // Buy
						VtradeInputId tradeInputIdBuy = null;
						
						if (this.tradeInputId.getObligationNum() != opsObligationDetail.getId().getObligationNum()) {
							tradeInputIdBuy = TradeDao.sharedInstance().getTradeByIdAndObligation(opsObligationDetail.getId().getTradeNum(), opsObligationDetail.getId().getObligationNum());
						} else {
							tradeInputIdBuy = this.matchObligationSelected.getId().getpTradeInputId();
						}
						
						if (tradeInputIdBuy != null) {
							tradeInputIdBuy.setScheduledQty(opsObligationDetail.getId().getScheduledQty());
							tradeInputIdBuy.setObligationDetailNum(opsObligationDetail.getId().getObligationDetailNum());
							
							this.matchObligationSource.getId().setpTradeInputId(tradeInputIdBuy);
							this.matchObligationSource.getId().getpTradeInputId().setOpenQty(this.realOpenQty);
						}
					}
				}
			}
		}
		
		RequestContext.getCurrentInstance().execute("hideDlgMatchObligation()");
	}
	
	// Remove match obligation
	public void removeMatchObligation(VopsObligationMatch opsObligationMatch) {
		if (opsObligationMatch != null) {
			this.matchObligationLst.remove(opsObligationMatch);
		}
	}
	
	// Void MO
	public void voidMatchObligation(VopsObligationMatch opsObligationMatch) {
		if (opsObligationMatch.getId().getObligationMatchNum() > 0) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			
			MatchObligation matchObligation = new MatchObligation();
			matchObligation.setMatchObligationNumber(opsObligationMatch.getId().getObligationMatchNum());
			
			MatchObligationCXL matchObligationCXL = MatchObligationCXL.sharedInstance();
			matchObligationCXL.setUser(this.user);
			matchObligationCXL.setMatchObligation(matchObligation);
			
			boolean success = matchObligationCXL.voidOpsBackToBack();
			
			if (!success) {
				facesContext.addMessage(null, new FacesMessage("Unable to void this match obligation. Please contact your system administrator"));
			} else {
				facesContext.addMessage(null, new FacesMessage("Match Obligation successfully voided"));
				facesContext.addMessage(null, new FacesMessage("Match Obligation number given: " + opsObligationMatch.getId().getObligationMatchNum()));
				
				this.matchObligationLst.remove(opsObligationMatch);
			}
		}
	}
	
	// Update match obligation source
	public void updateMatchObligationSource(VopsObligationMatch opsObligationMatch) {
		this.matchObligationSource = opsObligationMatch;
	}
	
	// On cell edit of match obligation
	public void onMatchObligationCellEdit(CellEditEvent cellEditEvent) {
		String[] clientId = cellEditEvent.getColumn().getClientId().split(":");
		String columnId = clientId[clientId.length - 1];
		
		if ("txtMatchNumber".equals(columnId)) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			
			Integer newValue = Integer.parseInt(cellEditEvent.getNewValue().toString());
			
			VopsObligationMatch opsObligationMatch = this.itineraryDao.getObligationMatchById(newValue);
			
			if (opsObligationMatch != null) {
				if (this.tradeInputId != null) {
					Integer itineraryTradeNumber = this.tradeInputId.getTradeNum();
					
					VopsObligationDetail opsObligationDetail = null;
					
					for (VopsObligationDetail vOpsObligationDetail : opsObligationMatch.getId().getOpsObligationDetail()) {
						if (vOpsObligationDetail.getId().getBuySellInd() == 1) {
							opsObligationDetail = vOpsObligationDetail;
							break;
						}
					}
					
					if (opsObligationDetail != null) {
						Integer matchObligationTradeNumber = opsObligationDetail.getId().getTradeNum();
						
						if (itineraryTradeNumber != matchObligationTradeNumber) {
							facesContext.validationFailed();
							facesContext.addMessage(null, new FacesMessage("You can not add a match obligation with a different trade number"));
						} else {
							TradeDao tradeDao = TradeDao.sharedInstance();
							
							for (VopsObligationDetail opsObligationDetails : opsObligationMatch.getId().getOpsObligationDetail()) {
								if (opsObligationDetails.getId().getBuySellInd() == -1) { // Sell
									VtradeInputId tradeInputIdSell = null;
									
									try {
										tradeInputIdSell = tradeDao.getTradeInputByTradeNumber(opsObligationDetails.getId().getTradeNum());
									} catch (Exception e) {
										e.printStackTrace();
									}
									
									if (tradeInputIdSell != null) {
										tradeInputIdSell.setScheduledQty(opsObligationDetails.getId().getScheduledQty());
										tradeInputIdSell.setObligationDetailNum(opsObligationDetails.getId().getObligationDetailNum());
										
										opsObligationMatch.getId().setsTradeInputId(tradeInputIdSell);
									}
								}
								
								if (opsObligationDetails.getId().getBuySellInd() == 1) { // Buy
									VtradeInputId tradeInputIdBuy = null;
									
									try {
										tradeInputIdBuy = tradeDao.getTradeInputByTradeNumber(opsObligationDetails.getId().getTradeNum());
									} catch (Exception e) {
										e.printStackTrace();
									}
									
									if (tradeInputIdBuy != null) {
										tradeInputIdBuy.setScheduledQty(opsObligationDetails.getId().getScheduledQty());
										tradeInputIdBuy.setObligationDetailNum(opsObligationDetails.getId().getObligationDetailNum());
										
										opsObligationMatch.getId().setpTradeInputId(tradeInputIdBuy);
									}
								}
							}
							
							this.matchObligationLst.add(opsObligationMatch);
						}
					}
				} else {
					facesContext.addMessage(null, new FacesMessage("Impossible to add match obligation to an unexisted itinerary"));
				}
			} else {
				facesContext.addMessage(null, new FacesMessage("The obligation match does not exist"));
			}
		}
	}
	
	// Save match obligation
	public void saveMatchObligation(ActionEvent actionEvent) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
		// If there's not any trade number, we do not continue
		if (this.tradeInputId != null) {
			if (this.matchObligationLst.isEmpty() || this.matchObligationLst.size() <= 0) {
				facesContext.addMessage(null, new FacesMessage("There is not any obligation match to save"));
			} else {
				for (VopsObligationMatch opsObligationMatch : this.matchObligationLst) {
					// Checking if match description is filled
					if (opsObligationMatch.getId().getMatchDesc() == null || opsObligationMatch.getId().getMatchDesc().equals("")) {
						facesContext.addMessage(null, new FacesMessage("Match Description on each match obligation cannot be empty"));
						return;
					}
				}
				
				for (VopsObligationMatch opsObligationMatch : this.matchObligationLst) {
					// Set match obligation
					MatchObligation matchObligation = new MatchObligation();
					
					if (opsObligationMatch.getId().getObligationMatchNum() > 0) {
						matchObligation.setMatchObligationNumber(opsObligationMatch.getId().getObligationMatchNum());
						matchObligation.setMatchDt(opsObligationMatch.getId().getMatchDt());
						matchObligation.setModifyDt(opsObligationMatch.getId().getLastModifyDt());
						matchObligation.setModifyPersonId(opsObligationMatch.getId().getModifyPersonNum());
					}
					
					matchObligation.setCompanyId(this.companyId);
					matchObligation.setPersonId(this.personId);
					matchObligation.setMatchDescription(opsObligationMatch.getId().getMatchDesc());
					matchObligation.setTradeInputIdPurchase(opsObligationMatch.getId().getpTradeInputId());
					matchObligation.setTradeInputIdSell(opsObligationMatch.getId().getsTradeInputId());
					
					// Build xml
					MatchObligationCXL matchObligationCXL = new MatchObligationCXL();
					matchObligationCXL.setUser(this.user);
					matchObligationCXL.setMatchObligation(matchObligation);
					
					int matchObligationNumber = (opsObligationMatch.getId().getObligationMatchNum() > 0) ? matchObligationCXL.update() : matchObligationCXL.save();
					
					if (matchObligationNumber != -1) {
						opsObligationMatch.getId().setObligationMatchNum(matchObligationNumber);
						
						facesContext.addMessage(null, new FacesMessage("Match Obligation successfully " + ((opsObligationMatch.getId().getObligationMatchNum() > 0) ? "updated" : "saved")));
						facesContext.addMessage(null, new FacesMessage("Match Obligation number given: " + matchObligationNumber));
					}  else {
						facesContext.addMessage(null, new FacesMessage("Unable to " + ((opsObligationMatch.getId().getObligationMatchNum() > 0) ? "update" : "create") + " the match obligation. Please contact your system administrator"));
					}
				}
			}
		} else {
			facesContext.addMessage(null, new FacesMessage("Trade Number does not exist"));
		}
	}
	
	// Save note
	public void saveNote(ActionEvent actionEvent) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
		if (this.genMovementId != null) {
			if (this.genMovementId.getMovementNum() > 0) {
				if (this.note.trim().equals("")) {
					facesContext.addMessage(null, new FacesMessage("Note can not be saved with empty value"));
				} else {
					ItineraryNote itineraryNote = new ItineraryNote();
					
					this.note = "[MOVEMENT=#" + String.valueOf(this.genMovementId.getMovementNum()) + "] " + this.note;
					
					if (this.genNoteId == null) {
						VgenNoteId genNoteId = new VgenNoteId();
						genNoteId.setNotePreview(this.note.trim().substring(0, this.note.trim().length() >= 250 ? 250 : this.note.trim().length()));
						genNoteId.setKeyvalue(this.id);
						genNoteId.setNoteText(this.note.trim());
						
						itineraryNote.setGenNoteId(genNoteId);
					} else {
						this.genNoteId.setNotePreview(this.note.trim().substring(0, this.note.trim().length() >= 250 ? 250 : this.note.trim().length()));
						this.genNoteId.setNoteText(this.note.trim());
						
						itineraryNote.setGenNoteId(this.genNoteId);
					}
					
					ItineraryNoteCXL itineraryNoteCXL = ItineraryNoteCXL.sharedInstance();
					itineraryNoteCXL.setUser(this.user);
					itineraryNoteCXL.setItineraryNote(itineraryNote);
					
					int noteNumber = this.genNoteId == null ? itineraryNoteCXL.save() : itineraryNoteCXL.update();
					
					if (noteNumber == -1) {
						facesContext.addMessage(null, new FacesMessage("Unable to " + (this.genNoteId == null ? "save" : "update") + " the note. Please contact your system administrator"));
					} else {
						facesContext.addMessage(null, new FacesMessage("Note successfully " + (this.genNoteId == null ? "saved" : "updated")));
					}
					
					RequestContext.getCurrentInstance().execute("hideDlgNote()");
				}
			} else {
				facesContext.addMessage(null, new FacesMessage("Note cannot be saved with an unexisting movement number"));
			}
		} else {
			facesContext.addMessage(null, new FacesMessage("None movement specified for this note"));
		}
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public VopsItinerary getOpsItinerary() {
		return opsItinerary;
	}

	public void setOpsItinerary(VopsItinerary opsItinerary) {
		this.opsItinerary = opsItinerary;
	}

	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}
	
	public CompanyDao getCompanyDao() {
		return companyDao;
	}

	public void setCompanyDao(CompanyDao companyDao) {
		this.companyDao = companyDao;
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

	public String getItineraryName() {
		return itineraryName;
	}

	public void setItineraryName(String itineraryName) {
		this.itineraryName = itineraryName;
	}
	
	public VdimCompanyId getCompanyId() {
		return companyId;
	}

	public void setCompanyId(VdimCompanyId companyId) {
		this.companyId = companyId;
	}

	public VdimPersonId getPersonId() {
		return personId;
	}

	public void setPersonId(VdimPersonId personId) {
		this.personId = personId;
	}
	
	public BigDecimal getTradeQty() {
		return tradeQty;
	}

	public void setTradeQty(BigDecimal tradeQty) {
		this.tradeQty = tradeQty;
	}

	public BigDecimal getMissingTradeQty() {
		return missingTradeQty;
	}

	public void setMissingTradeQty(BigDecimal missingTradeQty) {
		this.missingTradeQty = missingTradeQty;
	}

	public String getContractUomCd() {
		return contractUomCd;
	}

	public void setContractUomCd(String contractUomCd) {
		this.contractUomCd = contractUomCd;
	}
	
	public boolean isThereIsTradeNumber() {
		return thereIsTradeNumber;
	}

	public void setThereIsTradeNumber(boolean thereIsTradeNumber) {
		this.thereIsTradeNumber = thereIsTradeNumber;
	}
	
	public boolean isTradeContract() {
		return tradeContract;
	}

	public void setTradeContract(boolean tradeContract) {
		this.tradeContract = tradeContract;
	}

	public List<VdimMovementId> getMovementLst() {
		return movementLst;
	}

	public void setMovementLst(List<VdimMovementId> movementLst) {
		this.movementLst = movementLst;
	}

	public VdimMovementId getMovementId() {
		return movementId;
	}

	public void setMovementId(VdimMovementId movementId) {
		this.movementId = movementId;
	}
	
	public ItineraryDao getItineraryDao() {
		return itineraryDao;
	}

	public void setItineraryDao(ItineraryDao itineraryDao) {
		this.itineraryDao = itineraryDao;
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
	
	public RefMotDao getRefMotDao() {
		return refMotDao;
	}

	public void setRefMotDao(RefMotDao refMotDao) {
		this.refMotDao = refMotDao;
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

	public ItiDefaultActionDetailDao getItiDefaultActionDetailDao() {
		return itiDefaultActionDetailDao;
	}

	public void setItiDefaultActionDetailDao(
			ItiDefaultActionDetailDao itiDefaultActionDetailDao) {
		this.itiDefaultActionDetailDao = itiDefaultActionDetailDao;
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

	public String getTradeContractType() {
		return tradeContractType;
	}

	public void setTradeContractType(String tradeContractType) {
		this.tradeContractType = tradeContractType;
	}
	
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	public VdimMovementId getGenMovementId() {
		return genMovementId;
	}

	public void setGenMovementId(VdimMovementId genMovementId) {
		this.genMovementId = genMovementId;
	}

	public VgenNoteId getGenNoteId() {
		return genNoteId;
	}

	public void setGenNoteId(VgenNoteId genNoteId) {
		this.genNoteId = genNoteId;
	}

	public List<Integer> getMovementsNumber() {
		return movementsNumber;
	}

	public void setMovementsNumber(List<Integer> movementsNumber) {
		this.movementsNumber = movementsNumber;
	}
	
	public List<VopsObligationMatch> getMatchObligations() {
		return matchObligations;
	}

	public void setMatchObligations(List<VopsObligationMatch> matchObligations) {
		this.matchObligations = matchObligations;
	}
	
	public VopsObligationMatch getMatchObligationSource() {
		return matchObligationSource;
	}

	public void setMatchObligationSource(VopsObligationMatch matchObligationSource) {
		this.matchObligationSource = matchObligationSource;
	}

	public VopsObligationMatch getMatchObligationSelected() {
		return matchObligationSelected;
	}

	public void setMatchObligationSelected(
			VopsObligationMatch matchObligationSelected) {
		this.matchObligationSelected = matchObligationSelected;
	}

	public List<VopsObligationMatch> getMatchObligationLst() {
		return matchObligationLst;
	}

	public void setMatchObligationLst(List<VopsObligationMatch> matchObligationLst) {
		this.matchObligationLst = matchObligationLst;
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
	
}
