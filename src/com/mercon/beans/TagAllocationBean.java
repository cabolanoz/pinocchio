package com.mercon.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.mercon.cxl.BuildDrawMatch;
import com.mercon.cxl.BuildDrawMatchDetail;
import com.mercon.cxl.BuildDrawTag;
import com.mercon.cxl.TagAllocation;
import com.mercon.cxl.TagAllocationCXL;
import com.mercon.dao.ItineraryDao;
import com.mercon.datamodel.BuildDrawDataModel;
import com.mercon.datamodel.BuildDrawTagDataModel;
import com.mercon.entities.VbuildDraw;
import com.mercon.entities.VbuildDrawTag;

@ManagedBean(name = "tagAllocation")
@ViewScoped
public class TagAllocationBean implements Serializable {

	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 5392857581729944436L;

	@ManagedProperty("#{user}")
	private UserBean user;
	
	@ManagedProperty("#{itineraryService}")
	private ItineraryDao itineraryDao;

	/**
	 * Origin variables
	 */
	private int equipmentNum;
	private int cargoNum;
	private int strategyNum;
	
	private BuildDrawDataModel lstBuild;
	private VbuildDraw selectBuild;
	private BuildDrawDataModel lstDraw;
	private VbuildDraw selectDraw;
	private BuildDrawTagDataModel lstTag;
	private VbuildDrawTag selectTag;
	private List<VbuildDrawTag> allocatedTags = new ArrayList<VbuildDrawTag>();
	
	private double allocatedQty = 0.0d;
	
	@PostConstruct
	public void init() {
		Map<String, String[]> opt = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterValuesMap();
		
		String[] optVal = opt.get("params");
		
		this.equipmentNum = Integer.parseInt(optVal[0]);
		String cargoNumber = optVal[1];
		this.strategyNum = Integer.parseInt(optVal[2]);
		
		if (cargoNumber != null && !cargoNumber.equals("")) {
			this.cargoNum = Integer.parseInt(cargoNumber);
			
			// Getting builds
			List<VbuildDraw> builds = this.itineraryDao.getBuildByLevelWithOpenQty(this.cargoNum);
			this.lstBuild = new BuildDrawDataModel(builds);
			
			// Getting draws
			List<VbuildDraw> draws = this.itineraryDao.getDrawByLevelToAllocate(this.cargoNum);
			this.lstDraw = new BuildDrawDataModel(draws);
		}
	}
	
	public void onBuildSelect(SelectEvent selectEvent) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
		if (this.selectBuild != null) {
			List<VbuildDrawTag> tags = this.selectBuild.getLstVBuildDrawTags();
			
			if (tags != null && !tags.isEmpty() && tags.size() > 0) {
				this.allocatedQty = 0.0d;
				
				for (VbuildDrawTag buildDrawTag : tags) {
					buildDrawTag.getId().setQtyToAllocate(buildDrawTag.getId().getTagQty() - buildDrawTag.getId().getTagAllocQty());
					
					this.allocatedQty +=  buildDrawTag.getId().getQtyToAllocate();
					this.allocatedTags.add(buildDrawTag);
				}
				
				this.lstTag = new BuildDrawTagDataModel(tags);
			} else {
				facesContext.addMessage(null, new FacesMessage("No available tags for build " + this.selectBuild.getId().getBuildDrawNum()));
			}
		} else {
			facesContext.addMessage(null, new FacesMessage("None build selected for showing tags"));
		}
	}
	
	public void onChangeAllocateQty(VbuildDrawTag buildDrawTag) {
		if (buildDrawTag.getId().getQtyToAllocate() > buildDrawTag.getId().getTagQty()) {
			buildDrawTag.getId().setQtyToAllocate(0.0d);
			return;
		}
		
		if (buildDrawTag.getId().getQtyToAllocate() > 0) {
			boolean isAlreadyAdded = false;
			
			for (VbuildDrawTag tag : this.allocatedTags) {
				if (tag.getId().getBdTagNum() == buildDrawTag.getId().getBdTagNum()) {
					int index = this.allocatedTags.indexOf(tag);
					
					this.allocatedTags.set(index, tag);
					
					isAlreadyAdded = true;
				}
			}
			
			if (!isAlreadyAdded) {
				this.allocatedTags.add(buildDrawTag);
			}
		}
		
		this.allocatedQty = 0.0d;
		
		for (VbuildDrawTag tag : this.allocatedTags) {
			if (tag.getId().getQtyToAllocate() <= tag.getId().getTagQty()) {
				this.allocatedQty += tag.getId().getQtyToAllocate();
			}
		}
	}
	
	public void onSaveTagAllocation() {
		TagAllocation tagAllocation = new TagAllocation();
		
		BuildDrawMatch buildDrawMatch = new BuildDrawMatch();
		buildDrawMatch.setBd_match_num(-1);
		buildDrawMatch.setEquipment_num(this.equipmentNum);
		buildDrawMatch.setCargo_num(this.cargoNum);
		buildDrawMatch.setStrategy_num(this.strategyNum);
		buildDrawMatch.setMatch_qty(this.allocatedQty);
		buildDrawMatch.setBd_allocation_ind(1);
		buildDrawMatch.setIn_store_transfer_num(-1);
		buildDrawMatch.setCopy_tag_ind(1);
		
		tagAllocation.setBuildDrawMatch(buildDrawMatch);
		
		BuildDrawMatchDetail buildDrawMatchDetail = new BuildDrawMatchDetail();
		buildDrawMatchDetail.setBd_match_num(-1);
		buildDrawMatchDetail.setBuild_draw_num(this.selectDraw.getId().getBuildDrawNum());
		buildDrawMatchDetail.setEquipment_num(this.selectDraw.getId().getEquipmentNum());
		buildDrawMatchDetail.setStrategy_num(this.selectDraw.getId().getStrategyNum());
		buildDrawMatchDetail.setBuild_draw_match_qty(this.selectDraw.getId().getBuildDrawQty());
		buildDrawMatchDetail.setTitle_transfer_dt(this.selectDraw.getId().getTitleTransferDt());
		
		tagAllocation.setBuildDrawMatchDetail(buildDrawMatchDetail);
		
		List<BuildDrawMatchDetail> buildDrawMatchDetailLst = new ArrayList<BuildDrawMatchDetail>();
		
		for (VbuildDraw buildDraw : this.getBuildToAllocate()) {
			BuildDrawMatchDetail bdmDetail = new BuildDrawMatchDetail();
			bdmDetail.setBd_match_num(-1);
			bdmDetail.setBuild_draw_num(buildDraw.getId().getBuildDrawNum());
			bdmDetail.setEquipment_num(buildDraw.getId().getEquipmentNum());
			bdmDetail.setStrategy_num(buildDraw.getId().getStrategyNum());
			bdmDetail.setTitle_transfer_dt(buildDraw.getId().getTitleTransferDt());
			
			double qtyToAllocate = 0.0d;
			
			List<BuildDrawTag> buildDrawTagLst = new ArrayList<BuildDrawTag>();
			
			for (VbuildDrawTag buildDrawTag : buildDraw.getLstVBuildDrawTags()) {
				qtyToAllocate += buildDrawTag.getId().getQtyToAllocate();
				
				BuildDrawTag tag = new BuildDrawTag();
				tag.setBuild_draw_num(buildDrawTag.getId().getBuildDrawNum());
				tag.setTag_type_cd("Chop Data");
				tag.setRef_bd_tag_num(buildDrawTag.getId().getRefBdTagNum());
				tag.setBd_tag_num(buildDrawTag.getId().getBdTagNum());
				tag.setCargo_num(buildDrawTag.getId().getCargoNum());
				tag.setEquipment_num(buildDrawTag.getId().getEquipmentNum());
				tag.setTag_type_ind(0);
				tag.setTag_value1(buildDrawTag.getId().getTagValue1());
				tag.setTag_value2(buildDrawTag.getId().getTagValue2());
				tag.setTag_value3(buildDrawTag.getId().getTagValue3());
				tag.setTag_value4(buildDrawTag.getId().getTagValue4());
				tag.setTag_value5(buildDrawTag.getId().getTagValue5());
				tag.setTag_value6(buildDrawTag.getId().getTagValue6());
				tag.setTag_value7(buildDrawTag.getId().getTagValue7());
				tag.setTag_value8(buildDrawTag.getId().getTagValue8());
				tag.setTag_alloc_qty(buildDrawTag.getId().getTagAllocQty());
				tag.setQty_to_allocate(buildDrawTag.getId().getQtyToAllocate());
				tag.setTag_qty(buildDrawTag.getId().getTagQty());
				tag.setTag_qty_uom_cd(buildDrawTag.getId().getTagQtyUomCd());
				tag.setTag_source_ind(1);
				tag.setBuild_draw_type_ind(buildDraw.getId().getBuildDrawTypeInd());
				tag.setBuild_draw_ind(buildDraw.getId().getBuildDrawInd());
				tag.setChop_id(buildDrawTag.getId().getChopId());
				tag.setTag_adj_qty(buildDrawTag.getId().getTagLossGainAdjQty());
				tag.setLot_certificate_num(buildDrawTag.getId().getLotCertificateNum());
				tag.setGl_ref_bd_tag_num(buildDrawTag.getId().getGlRefBdTagNum());
				tag.setSplit_src_tag_num(buildDrawTag.getId().getSplitSrcTagNum());
				tag.setAdj_ref_build_draw_num(buildDrawTag.getId().getAdjRefBuildDrawNum());
				
				buildDrawTagLst.add(tag);
			}
			
			bdmDetail.setBuild_draw_match_qty(qtyToAllocate);
			bdmDetail.setTags(buildDrawTagLst);
			
			buildDrawMatchDetailLst.add(bdmDetail);
		}
		
		tagAllocation.setBuildDrawMatchDetailLst(buildDrawMatchDetailLst);
		
		TagAllocationCXL tagAllocationCXL = TagAllocationCXL.sharedInstance();
		tagAllocationCXL.setUser(this.user);
		tagAllocationCXL.setTagAllocation(tagAllocation);
		
		String response = tagAllocationCXL.save();
		
		if (!response.equals("")) {
			RequestContext.getCurrentInstance().closeDialog(1);
		} else {
			RequestContext.getCurrentInstance().closeDialog(-1);
		}
	}
	
	private List<VbuildDraw> getBuildToAllocate() {
		List<VbuildDraw> builds = new ArrayList<VbuildDraw>();
		
		List<Integer> buildDrawNum = new ArrayList<Integer>();
		
		for (VbuildDrawTag buildDrawTag : this.allocatedTags) {
			buildDrawNum.add(buildDrawTag.getId().getBuildDrawNum());
		}
		
		HashSet<Integer> hs = new HashSet<Integer>();
		hs.addAll(buildDrawNum);
		
		buildDrawNum.clear();
		buildDrawNum.addAll(hs);
		
		for (VbuildDraw buildDraw : this.lstBuild.getData()) {
			for (Integer id : buildDrawNum) {
				if (id == buildDraw.getId().getBuildDrawNum()) {
					builds.add(buildDraw);
					break;
				}
			}
		}
		
		return builds;
	}
	
	public void onCloseTagAllocation() {
		RequestContext.getCurrentInstance().closeDialog(0);
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

	public BuildDrawDataModel getLstBuild() {
		return lstBuild;
	}

	public void setLstBuild(BuildDrawDataModel lstBuild) {
		this.lstBuild = lstBuild;
	}

	public VbuildDraw getSelectBuild() {
		return selectBuild;
	}

	public void setSelectBuild(VbuildDraw selectBuild) {
		this.selectBuild = selectBuild;
	}

	public BuildDrawDataModel getLstDraw() {
		return lstDraw;
	}

	public void setLstDraw(BuildDrawDataModel lstDraw) {
		this.lstDraw = lstDraw;
	}

	public VbuildDraw getSelectDraw() {
		return selectDraw;
	}

	public void setSelectDraw(VbuildDraw selectDraw) {
		this.selectDraw = selectDraw;
	}

	public BuildDrawTagDataModel getLstTag() {
		return lstTag;
	}

	public void setLstTag(BuildDrawTagDataModel lstTag) {
		this.lstTag = lstTag;
	}

	public VbuildDrawTag getSelectTag() {
		return selectTag;
	}

	public void setSelectTag(VbuildDrawTag selectTag) {
		this.selectTag = selectTag;
	}

	public List<VbuildDrawTag> getAllocatedTags() {
		return allocatedTags;
	}

	public void setAllocatedTags(List<VbuildDrawTag> allocatedTags) {
		this.allocatedTags = allocatedTags;
	}

	public double getAllocatedQty() {
		return allocatedQty;
	}

	public void setAllocatedQty(double allocatedQty) {
		this.allocatedQty = allocatedQty;
	}
	
}
