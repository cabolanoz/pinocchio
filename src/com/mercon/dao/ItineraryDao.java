package com.mercon.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.jboss.logging.Logger;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.mercon.entities.ErrorResponse;
import com.mercon.entities.VbuildDraw;
import com.mercon.entities.VbuildDrawTag;
import com.mercon.entities.VdimCargo;
import com.mercon.entities.VdimCommodityId;
import com.mercon.entities.VdimCurrencyId;
import com.mercon.entities.VdimLocationId;
import com.mercon.entities.VdimMktQuoteDefinitionId;
import com.mercon.entities.VdimStrategyId;
import com.mercon.entities.VdimTransfer;
import com.mercon.entities.VdimUnitOfMeasureId;
import com.mercon.entities.VgenIndicatorId;
import com.mercon.entities.VopsItinerary;
import com.mercon.entities.VopsMovement;
import com.mercon.entities.VopsObligationHdr;
import com.mercon.entities.VopsObligationMatch;
import com.mercon.entities.VrefMotId;
import com.mercon.util.DefaultConstants;

@ManagedBean(name = "itineraryService", eager = true)
@ApplicationScoped
public class ItineraryDao implements DefaultConstants {

	private static final Logger log = Logger.getLogger(APP_NAME + "." + ItineraryDao.class.getName());
	
	public VopsItinerary getItineraryById(int itineraryNum) {
		long time = System.nanoTime();
		
		log.info("Starting getting info for itinerary -> " + itineraryNum);
		
		ClientRequest clientRequest = new ClientRequest((IS_PRODUCTION ? PRODUCTION_JBOSS_HOST : DEVELOPER_JBOSS_HOST) + "/CXLRWS/Itinerary/getItineraryById/" + itineraryNum);
		clientRequest.accept("application/json;charset=UTF-8");
		
		log.info("Getting itinerary info took -> " + ((double) System.nanoTime() - time) / 1000000000.0 + "s");
		
		time = System.nanoTime();
		
		ClientResponse<Object> clientResponse = null;
		
		try {
			clientResponse = clientRequest.get(Object.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (clientResponse.getStatus() != 200) {
			try {
				throw new Exception("Error getting itinerary: " + ((ErrorResponse) clientResponse.getEntity(ErrorResponse.class)).getMessage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			VopsItinerary opsItinerary = clientResponse.getEntity(VopsItinerary.class);
			
			log.info("Retrieving response for itinerary found took -> " + ((double) System.nanoTime() - time) / 1000000000.0 + "s");
			
			return opsItinerary;
		}
		
		return null;
	}
	
	public List<VopsItinerary> getItineraryByName(String itineraryName) {
		List<VopsItinerary> opsItineraries = new ArrayList<VopsItinerary>();
		
		ClientRequest clientRequest = new ClientRequest((IS_PRODUCTION ? PRODUCTION_JBOSS_HOST : DEVELOPER_JBOSS_HOST) + "/CXLRWS/Itinerary/getItineraryByName/" + itineraryName);
		clientRequest.accept("application/json;charset=UTF-8");
		
		ClientResponse<Object> clientResponse = null;
		
		try {
			clientResponse = clientRequest.get(Object.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (clientResponse.getStatus() != 200) {
			try {
				throw new Exception("Error getting itinerary: " + ((ErrorResponse) clientResponse.getEntity(ErrorResponse.class)).getMessage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			VopsItinerary[] opsItinerary = (VopsItinerary[]) clientResponse.getEntity(VopsItinerary[].class);
			
			opsItineraries = Arrays.asList(opsItinerary);
			
			return opsItineraries;
		}
		
		return opsItineraries;
	}
	
	public List<VopsMovement> getMovementByItinerary(int itineraryNum) {
		long time = System.nanoTime();
		
		log.info("Starting getting movements for itinerary -> " + itineraryNum);
		
		List<VopsMovement> opsMovements = new ArrayList<VopsMovement>();
		
		ClientRequest clientRequest = new ClientRequest((IS_PRODUCTION ? PRODUCTION_JBOSS_HOST : DEVELOPER_JBOSS_HOST) + "/CXLRWS/Movement/getMovementByItinerary/" + itineraryNum);
		clientRequest.accept("application/json;charset=UTF-8");
		
		log.info("Getting movements took -> " + ((double) System.nanoTime() - time) / 1000000000.0 + "s");
		
		time = System.nanoTime();
		
		ClientResponse<Object> clientResponse = null;
		
		try {
			clientResponse = clientRequest.get(Object.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (clientResponse.getStatus() != 200) {
			try {
				throw new Exception("Error getting movement: " + ((ErrorResponse) clientResponse.getEntity(ErrorResponse.class)).getMessage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			VopsMovement[] opsMovement = (VopsMovement[]) clientResponse.getEntity(VopsMovement[].class);
			
			log.info("Retrieving response for movements found took -> " + ((double) System.nanoTime() - time) / 1000000000.0 + "s");
			
			opsMovements = Arrays.asList(opsMovement);
			
			if (!opsMovements.isEmpty() && opsMovements.size() > 0) {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				
				MeanOfTransportDao motDao = (MeanOfTransportDao) facesContext.getExternalContext().getApplicationMap().get("motService");
				
				List<VgenIndicatorId> dimMotsId = null;
				
				dimMotsId = motDao.gettMotId();
				
				List<VgenIndicatorId> dimStatusesId = null;
				
				dimStatusesId = motDao.getsMotId();
				
				RefMotDao refMotDao = (RefMotDao) facesContext.getExternalContext().getApplicationMap().get("refMotService");
				
				List<VrefMotId> refMotsId = null;
				
				refMotsId = refMotDao.getRefMotId();
				
				LocationDao locationDao = (LocationDao) facesContext.getExternalContext().getApplicationMap().get("locationService");
				
				List<VdimLocationId> dimLocationsId = null;
				
				dimLocationsId = locationDao.getLocationId();
				
				for (VopsMovement movement : opsMovements) {
					time = System.nanoTime();
					
					log.info("Starting loading dependencies for movement -> " + movement.getId().getMovementNum());
					
					if (dimMotsId != null) {
						for (VgenIndicatorId dimMotId : dimMotsId) {
							if (movement.getId().getMotTypeInd() == dimMotId.getIndValue()) {
								movement.getId().setMotId(dimMotId);
								break;
							}
						}
					}
					
					if (dimStatusesId != null) {
						for (VgenIndicatorId dimMotId : dimStatusesId) {
							if (movement.getId().getMovementStatusInd() == dimMotId.getIndValue()) {
								movement.getId().setMovementStatus(dimMotId);
								break;
							}
						}
					}
					
					if (refMotsId != null) {
						for (VrefMotId refMotId : refMotsId) {
							if (movement.getId().getMotNum() == refMotId.getMotNum()) {
								movement.getId().setMotNumId(refMotId);
								break;
							}
						}
					}
					
					if (dimLocationsId != null) {
						for (VdimLocationId dimLocationId : dimLocationsId) {
							if (movement.getId().getDepartLocationNum() == dimLocationId.getNumLocation()) {
								movement.getId().setDepartLocation(dimLocationId);
							}
							
							if (movement.getId().getArriveLocationNum() == dimLocationId.getNumLocation()) {
								movement.getId().setArriveLocation(dimLocationId);
							}
							
							if (movement.getId().getDepartLocation() != null && movement.getId().getArriveLocation() != null) {
								break;
							}
						}
					}
					
					log.info("Loading dependencies for movement -> " + movement.getId().getMovementNum() + " took -> " + ((double) System.nanoTime() - time) / 1000000000.0 + "s");
				}
				
				return opsMovements;
			}
		}
		
		return opsMovements;
	}
	
	public List<VdimCargo> getCargoByEquipment(int equipmentNum) {
		List<VdimCargo> dimCargos = new ArrayList<VdimCargo>();
		
		ClientRequest clientRequest = new ClientRequest((IS_PRODUCTION ? PRODUCTION_JBOSS_HOST : DEVELOPER_JBOSS_HOST) + "/CXLRWS/Cargo/getLevelsByStorage/" + equipmentNum);
		clientRequest.accept("application/json;charset=UTF-8");
		
		ClientResponse<Object> clientResponse = null;
		
		try {
			clientResponse = clientRequest.get(Object.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (clientResponse.getStatus() != 200) {
	//		try {
	//			throw new Exception("Error getting cargo: " + ((ErrorResponse) clientResponse.getEntity(ErrorResponse.class)).getMessage());
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//		}
		} else {
			VdimCargo[] dimCargoArr = (VdimCargo[]) clientResponse.getEntity(VdimCargo[].class);
			
			dimCargos = Arrays.asList(dimCargoArr);
			
			if (!dimCargos.isEmpty() && dimCargos.size() > 0) {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				
				// Strategy
				StrategyDao strategyDao = (StrategyDao) facesContext.getExternalContext().getApplicationMap().get("strategyService");
				
				List<VdimStrategyId> dimStrategiesId = strategyDao.getStrategyId();
				
				// Commodity
				CommodityDao commodityDao = (CommodityDao) facesContext.getExternalContext().getApplicationMap().get("commodityService");
				
				List<VdimCommodityId> dimCommoditiesId = commodityDao.getCommodityId();
				
				// Unit of measure
				UnitOfMeasureDao unitOfMeasureDao = (UnitOfMeasureDao) facesContext.getExternalContext().getApplicationMap().get("uomService");
				
				List<VdimUnitOfMeasureId> dimUnitOfMeasuresId = unitOfMeasureDao.getUnitOfMeasureId();
				
				// Currency
				CurrencyDao currencyDao = (CurrencyDao) facesContext.getExternalContext().getApplicationMap().get("currencyService");
				
				List<VdimCurrencyId> dimCurrenciesId = currencyDao.getCurrencyId();
				
				// Curve
				CurveDao curveDao = (CurveDao) facesContext.getExternalContext().getApplicationMap().get("curveService");
				
				List<VdimMktQuoteDefinitionId> dimCommodityStrategiesId = curveDao.getCommodityStrategyId();
				
				for (VdimCargo dimCargo : dimCargos) {
					if (dimStrategiesId != null) {
						for (VdimStrategyId dimStrategyId : dimStrategiesId) {
							if (dimCargo.getId().getNumStategy() == dimStrategyId.getNumStrategy()) {
								dimCargo.getId().setNumStrategy(dimStrategyId);
								break;
							}
						}
					}
					
					if (dimCommoditiesId != null) {
						for (VdimCommodityId dimCommodityId : dimCommoditiesId) {
							if (dimCargo.getId().getCodCommodity().equals(dimCommodityId.getCodCommodity())) {
								dimCargo.getId().setCommodityCod(dimCommodityId);
								break;
							}
						}
					}
					
					if (dimUnitOfMeasuresId != null) {
						for (VdimUnitOfMeasureId dimUnitOfMeasureId : dimUnitOfMeasuresId) {
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
					}
					
					if (dimCurrenciesId != null) {
						for (VdimCurrencyId dimCurrencyId : dimCurrenciesId) {
							if (dimCargo.getId().getCodPrcCur().equals(dimCurrencyId.getCodcurr())) {
								dimCargo.getId().setCurrPriceCod(dimCurrencyId);
								break;
							}
						}
					}
					
					if (dimCommodityStrategiesId != null) {
						for (VdimMktQuoteDefinitionId dimCommodityStrategyId : dimCommodityStrategiesId) {
							if (dimCargo.getId().getNumMtmcurve() == dimCommodityStrategyId.getNumdefquote()) {
								dimCargo.getId().setMtmCurveNum(dimCommodityStrategyId);
								break;
							}
						}
					}
				}
			}
		}
		
		return dimCargos;
	}
	
	public VopsObligationHdr getObligationHdrByTrade(int tradeNum) {
		ClientRequest clientRequest = new ClientRequest((IS_PRODUCTION ? PRODUCTION_JBOSS_HOST : DEVELOPER_JBOSS_HOST) + "/CXLRWS/ObligationMatch/getObligationHdrByTrade/" + tradeNum);
		clientRequest.accept("application/json;charset=UTF-8");
		
		ClientResponse<Object> clientResponse = null;
		
		try {
			clientResponse = clientRequest.get(Object.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (clientResponse.getStatus() != 200) {
			try {
				throw new Exception("Error getting obligation header: " + ((ErrorResponse) clientResponse.getEntity(ErrorResponse.class)).getMessage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			VopsObligationHdr obligationHdr = clientResponse.getEntity(VopsObligationHdr.class);
			
			return obligationHdr;
		}
		
		return null;
	}
	
	public List<VdimTransfer> getByTrade(int tradeNum) {
		ClientRequest clientRequest = new ClientRequest((IS_PRODUCTION ? PRODUCTION_JBOSS_HOST : DEVELOPER_JBOSS_HOST) + "/CXLRWS/ObligationMatch/getObligationHdrByTrade/" + tradeNum);
		clientRequest.accept("application/json;charset=UTF-8");
		
		ClientResponse<Object> clientResponse = null;
		
		try {
			clientResponse = clientRequest.get(Object.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (clientResponse.getStatus() != 200) {
			try {
				throw new Exception("Error getting obligation header: " + ((ErrorResponse) clientResponse.getEntity(ErrorResponse.class)).getMessage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			VopsObligationHdr obligationHdr = clientResponse.getEntity(VopsObligationHdr.class);
			
			if (obligationHdr != null) {
				ClientRequest cr = new ClientRequest((IS_PRODUCTION ? PRODUCTION_JBOSS_HOST : DEVELOPER_JBOSS_HOST) + "/CXLRWS/Transfer/getByObligation/" + obligationHdr.getId().getObligationNum());
				cr.accept("application/json;charset=UTF-8");
				
				ClientResponse<Object> crs = null;
				
				try {
					crs = cr.get(Object.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				if (crs.getStatus() == 200) {
					VdimTransfer[] transfer = (VdimTransfer[]) crs.getEntity(VdimTransfer[].class);
					
					if (transfer != null) {
						return Arrays.asList(transfer);
					}
				}
			}
		}
		
		return new ArrayList<VdimTransfer>();
	}
	
	public List<VdimTransfer> getTransferByItineraryEquipmentCargo(int itineraryNum, int equipmentNum, int cargoNum) {
		long time = System.nanoTime();
		
		log.info("Starting getting transfers for itinerary -> " + itineraryNum + ", equipment -> " + equipmentNum + ", cargo -> " + cargoNum);
		
		ClientRequest clientRequest = new ClientRequest((IS_PRODUCTION ? PRODUCTION_JBOSS_HOST : DEVELOPER_JBOSS_HOST) + "/CXLRWS/Transfer/getByItineraryEquipmentCargo/{itineraryNum}/{equipmentNum}/{cargoNum}");
		clientRequest.accept("application/json;charset=UTF-8");
		clientRequest.pathParameter("itineraryNum", itineraryNum);
		clientRequest.pathParameter("equipmentNum", equipmentNum);
		clientRequest.pathParameter("cargoNum", cargoNum);
		
		log.info("Getting transfers took -> " + ((double) System.nanoTime() - time) / 1000000000.0 + "s");
		
		time = System.nanoTime();
		
		ClientResponse<Object> clientResponse = null;
		
		try {
			clientResponse = clientRequest.get(Object.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (clientResponse.getStatus() != 200) {
			try {
				throw new Exception("Error getting match obligation: " + ((ErrorResponse) clientResponse.getEntity(ErrorResponse.class)).getMessage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			VdimTransfer[] dimTransfer = (VdimTransfer[]) clientResponse.getEntity(VdimTransfer[].class);
			
			log.info("Retrieving response for transfers found took -> " + ((double) System.nanoTime() - time) / 1000000000.0 + "s");
			
			return Arrays.asList(dimTransfer);
		}
		
		return new ArrayList<VdimTransfer>();
	}
	
	public List<VbuildDrawTag> getTransferTagByBuild(int buildNum) {
		ClientRequest clientRequest = new ClientRequest((IS_PRODUCTION ? PRODUCTION_JBOSS_HOST : DEVELOPER_JBOSS_HOST) + "/CXLRWS/BuildDraw/getTagsByBuildDrawNumber/" + buildNum);
		clientRequest.accept("application/json;charset=UTF-8");
		
		ClientResponse<Object> clientResponse = null;
		
		try {
			clientResponse = clientRequest.get(Object.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (clientResponse.getStatus() != 200) {
//			try {
//				throw new Exception("Error getting tags for transfer: " + ((ErrorResponse) clientResponse.getEntity(ErrorResponse.class)).getMessage());
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
		} else {
			VbuildDrawTag[] buildDrawTag = (VbuildDrawTag[]) clientResponse.getEntity(VbuildDrawTag[].class);
			
			return Arrays.asList(buildDrawTag);
		}
		
		return new ArrayList<VbuildDrawTag>();
	}
	
	public List<VopsObligationMatch> getObligationByMovement(int movementNumber) {
		ClientRequest clientRequest = new ClientRequest((IS_PRODUCTION ? PRODUCTION_JBOSS_HOST : DEVELOPER_JBOSS_HOST) + "/CXLRWS/ObligationMatch/getObligationMatchByDescription/" + movementNumber);
		clientRequest.accept("application/json;charset=UTF-8");
		
		ClientResponse<Object> clientResponse = null;
		
		try {
			clientResponse = clientRequest.get(Object.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (clientResponse.getStatus() != 200) {
			try {
				throw new Exception("Error getting match obligation: " + ((ErrorResponse) clientResponse.getEntity(ErrorResponse.class)).getMessage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			VopsObligationMatch[] opsObligationMatch = (VopsObligationMatch[]) clientResponse.getEntity(VopsObligationMatch[].class);
			
			return Arrays.asList(opsObligationMatch);
		}
		
		return new ArrayList<VopsObligationMatch>();
	}
	
	public VopsObligationMatch getObligationMatchById(int obligationMatchNumber) {
		ClientRequest clientRequest = new ClientRequest((IS_PRODUCTION ? PRODUCTION_JBOSS_HOST : DEVELOPER_JBOSS_HOST) + "/CXLRWS/ObligationMatch/getObligationMatchById/" + obligationMatchNumber);
		clientRequest.accept("application/json;charset=UTF-8");
		
		ClientResponse<Object> clientResponse = null;
		
		try {
			clientResponse = clientRequest.get(Object.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (clientResponse.getStatus() != 200) {
			try {
				throw new Exception("Error getting match obligation: " + ((ErrorResponse) clientResponse.getEntity(ErrorResponse.class)).getMessage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			VopsObligationMatch opsObligationMatch = clientResponse.getEntity(VopsObligationMatch.class);
			
			return opsObligationMatch;
		}
		
		return null;
	}
	
	public List<VopsObligationMatch> getObligationMatchByTrade(int tradeNumber) {
		ClientRequest clientRequest = new ClientRequest((IS_PRODUCTION ? PRODUCTION_JBOSS_HOST : DEVELOPER_JBOSS_HOST) + "/CXLRWS/ObligationMatch/getObligationMatchByTrade/" + tradeNumber);
		clientRequest.accept("application/json;charset=UTF-8");
		
		ClientResponse<Object> clientResponse = null;
		
		try {
			clientResponse = clientRequest.get(Object.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (clientResponse.getStatus() != 200) {
			try {
				throw new Exception("Error getting match obligation: " + ((ErrorResponse) clientResponse.getEntity(ErrorResponse.class)).getMessage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			VopsObligationMatch[] opsObligationMatch = (VopsObligationMatch[]) clientResponse.getEntity(VopsObligationMatch[].class);
			
			return Arrays.asList(opsObligationMatch);
		}
		
		return new ArrayList<VopsObligationMatch>();
	}
	
	public VbuildDraw getBuildDrawById(int buildDrawNumber) {
		ClientRequest clientRequest = new ClientRequest((IS_PRODUCTION ? PRODUCTION_JBOSS_HOST : DEVELOPER_JBOSS_HOST) + "/CXLRWS/BuildDraw/getByBuildDrawNum?buildDrawNum=" + buildDrawNumber);
		clientRequest.accept("application/json;charset=UTF-8");
		
		ClientResponse<Object> clientResponse = null;
		
		try {
			clientResponse = clientRequest.get(Object.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (clientResponse.getStatus() != 200) {
			try {
				throw new Exception("Error getting build/draw: " + ((ErrorResponse) clientResponse.getEntity(ErrorResponse.class)).getMessage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			VbuildDraw buildDraw = (VbuildDraw) clientResponse.getEntity(VbuildDraw.class);
			
			return buildDraw;
		}
		
		return null;
	}
	
	public List<VbuildDraw> getBuildByLevelWithOpenQty(int cargoNumber) {
		ClientRequest clientRequest = new ClientRequest((IS_PRODUCTION ? PRODUCTION_JBOSS_HOST : DEVELOPER_JBOSS_HOST) + "/CXLRWS/BuildDraw/getBuildsWithOpenQty/" + cargoNumber);
		clientRequest.accept("application/json;charset=UTF-8");
		
		ClientResponse<Object> clientResponse = null;
		
		try {
			clientResponse = clientRequest.get(Object.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (clientResponse.getStatus() != 200) {
//			try {
//				throw new Exception("Error getting builds: " + ((ErrorResponse) clientResponse.getEntity(ErrorResponse.class)).getMessage());
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
		} else {
			VbuildDraw[] build = (VbuildDraw[]) clientResponse.getEntity(VbuildDraw[].class);
			
			return Arrays.asList(build);
		}
		
		return new ArrayList<VbuildDraw>();
	}
	
	public List<VbuildDraw> getDrawByLevelToAllocate(int cargoNumber) {
		ClientRequest clientRequest = new ClientRequest((IS_PRODUCTION ? PRODUCTION_JBOSS_HOST : DEVELOPER_JBOSS_HOST) + "/CXLRWS/BuildDraw/getDrawsToAllocate/" + cargoNumber);
		clientRequest.accept("application/json;charset=UTF-8");
		
		ClientResponse<Object> clientResponse = null;
		
		try {
			clientResponse = clientRequest.get(Object.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (clientResponse.getStatus() != 200) {
//			try {
//				throw new Exception("Error getting draws: " + ((ErrorResponse) clientResponse.getEntity(ErrorResponse.class)).getMessage());
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
		} else {
			VbuildDraw[] draw = (VbuildDraw[]) clientResponse.getEntity(VbuildDraw[].class);
			
			return Arrays.asList(draw);
		}
		
		return new ArrayList<VbuildDraw>();
	}
	
}
