package com.mercon.util;

public interface DefaultConstants {

	public static final int APP_ID = 57;
	public static final String APP_NAME = "ShippingTransferManager";
	
	public static final boolean IS_PRODUCTION = true;
	public static final String DEVELOPER_JBOSS_HOST = "http://localhost:8080";
	public static final String PRODUCTION_JBOSS_HOST = "http://localhost:18080"; // http://10.0.1.18:8080
	public static final String DEVELOPER_CXL_HOST =  "http://10.10.31.30:8080"; // "http://10.10.31.5:8080";
	public static final String PRODUCTION_CXL_HOST = "http://10.0.1.13:8080";
	
	public static final String ITINERARY_TABLE_NAME = "OPS_ITINERARY";
	public static final String ITINERARY_KEY_NAME = "itinerary_num";
	
	public static final String LOGIN_SERVICE = "Login";
    public static final String AUTHENTICATE_METHOD = "Authenticate";
    
    public static final String ITINERARY_SERVICE = "PhysOps";
    public static final String GET_ITINERARY_METHOD = "GetItinerary";
    public static final String SAVE_ITINERARY_METHOD = "PutItinerary";
    public static final String DELETE_MOVEMENT_METHOD = "CanDeleteMovement";
    public static final String DELETE_CARGO_METHOD = "CancelCargo";
    
    public static final String CARGO_SERVICE = "PhysOps";
    public static final String GET_CARGO_METHOD = "GetCargos";
    public static final String SAVE_CARGO_METHOD = "PutCargo";
    public static final String GET_BUILD_DRAW_CARGO_DETAIL_METHOD = "GetBuildDrawCargoDetails";
    
    public static final String TRANSFER_SERVICE = "PhysOps";
    public static final String GET_TRANSFER_METHOD = "GetTransfers";
    public static final String SAVE_TRANSFER_METHOD = "PutTransfer";
    public static final String DELETE_TRANSFER_METHOD = "CancelTransfer";
    public static final String DELETE_TAG_ALLOCATION_METHOD = "CancelBuildDrawMatch";
    
    public static final String MATCH_OBLIGATION_SERVICE = "PhysOps";
    public static final String GET_EMPTY_MATCH_OBLIGATION_METHOD = "GetEmptyOpsBackToBack";
    public static final String GET_OBLIGATION_MATCH_LIST_METHOD = "GetObligationMatchList";
    public static final String GET_MATCH_OBLIGATION_METHOD = "GetOpsBackToBack";
    public static final String SAVE_MATCH_OBLIGATION_METHOD = "PutOpsBackToBack"; 
    public static final String DELETE_MATCH_OBLIGATION_METHOD = "CancelOpsBackToBack";
	
    public static final String NOTE_SERVICE = "ReferenceData";
    public static final String SAVE_NOTE_METHOD = "InsertReferenceData";
    public static final String UPDATE_NOTE_METHOD = "UpdateReferenceData";
    
    public static final String BUILD_DRAW_SERVICE = "PhysOps";
    public static final String SAVE_BUILD_DRAW_METHOD = "PutBuildDrawMatch";
    
    public static final String C_LOAD_FROM = "Load From";
    public static final String C_DISCHARGE_TO = "Discharge To";
    public static final String C_TRADE = "Trade";
    public static final String C_VESSEL = "Vessel";
    public static final String C_CHOP_DATA = "Chop Data";
    
    public static final int LOAD_FROM_IND = 0;
    public static final int DISCHARGE_TO_IND = 1;
    
    public static final int TRADE_IND = 0;
    public static final int STORAGE_IND = 1;
    public static final int VESSEL_IND = 2;
    public static final int BARGE_IND = 3;
    public static final int TRUCK_IND = 4;
    public static final int PIPELINE_IND = 5;
    public static final int RAILCAR_IND = 6;
    public static final int STORAGE_SVC_IND = 7;
    
}
