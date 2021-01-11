/**
 * <B>(C) Copyright Freshremix Asia Software Corporation</B><BR>
 * <BR>
 * <B>Description:</B><BR>
 * Describe class or interface.<BR>
 * <BR>
 * <B>Known Bugs:</B>
 * none<BR>
 * <BR>
 * <B>History:</B>
 * <PRE><!-- Do not use tabs in the history table! Do not extend table width! -->
 * date       name            changes
 * ------------------------------------------------------------------------------
 * Feb 17, 2010		pamela		
 */
package com.freshremix.webapp.controller.pmf;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.sojo.interchange.Serializer;
import net.sf.sojo.interchange.json.JsonSerializer;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.freshremix.constants.RoleConstants;
import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.PmfList;
import com.freshremix.model.PmfSkuList;
import com.freshremix.model.User;
import com.freshremix.service.OrderSheetService;
import com.freshremix.service.ProductMasterFileService;
import com.freshremix.service.SKUGroupService;
import com.freshremix.ui.model.TableParameter;
import com.freshremix.util.NumberUtil;
import com.freshremix.util.SessionHelper;
import com.freshremix.util.StringUtil;

/**
 * @author Pammie
 *
 */

public class PmfLoadPmfSkusController implements Controller {
	ProductMasterFileService pmfService;
	private SKUGroupService skuGroupService;

	@SuppressWarnings("unchecked")
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		User user = (User) SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		Integer pmfId = Integer.parseInt(SessionHelper.getAttribute(request, "pmfId").toString());
		Integer userId = pmfService.getUserIdByPmfId(pmfId);
		OrderSheetParam orderSheetParam = (OrderSheetParam) SessionHelper.getAttribute(request, SessionParamConstants.ORDER_SHEET_PARAM);
		Integer categoryId = Integer.valueOf(SessionHelper.getAttribute(request, "categoryId").toString());
		String searchName = "%%";
		
		String json = request.getParameter("_gt_json");
		Serializer serializer = new JsonSerializer();
		TableParameter tableParam = (TableParameter) serializer.deserialize(json, TableParameter.class);
		
		if (user.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER_ADMIN)){
			List<PmfList> pmfList = (List<PmfList>)SessionHelper.getAttribute(request, SessionParamConstants.PMF_LIST_PARAM);
			for(int i=0; i< pmfList.size(); i++){
				PmfList pmf = (PmfList)pmfList.get(i) ;
				if (pmf.getPmfId().equals(pmfId)){
					userId = pmf.getPmfUserId();
				}
			}
		}
		
		if (null != SessionHelper.getAttribute(request, "searchName")){
			searchName = "%" + SessionHelper.getAttribute(request, "searchName").toString().trim().toUpperCase() + "%";
		}
		
//		String skuGroupList = skuGroupService.getSKUGroupList(userId, categoryId);
		System.out.println("CATEGORY ID: " + categoryId);
//		System.out.println("SKU GROUP: " + skuGroupList);
//		SessionHelper.setAttribute(request, SessionParamConstants.SKU_GROUP_LIST_PARAM, skuGroupList);
		List<Map<String, Object>> pmfListMaps = new ArrayList<Map<String, Object>>();
		List<PmfSkuList> pmfSkuList = pmfService.getPmfSkus(pmfId, userId, searchName, categoryId, tableParam.getPageInfo().getStartRowNum(), tableParam.getPageInfo().getPageSize());
		
		for(PmfSkuList pmfObj:pmfSkuList){
			Map<String, Object> pmfMap = new HashMap<String, Object>();
			
			pmfMap.put("skuId", pmfObj.getSkuId());
			pmfMap.put("pmfId", pmfObj.getPmfId());
			pmfMap.put("skuGroup", pmfObj.getSkuGroup());
			pmfMap.put("skuName", StringUtil.nullToBlank((pmfObj.getSkuName())));
			pmfMap.put("sellerProdCode", StringUtil.nullToBlank((pmfObj.getSellerProdCode())));
			pmfMap.put("buyerProdCode", StringUtil.nullToBlank((pmfObj.getBuyerProdCode())));
			pmfMap.put("commentA", StringUtil.nullToBlank((pmfObj.getCommentA())));
			pmfMap.put("commentB", StringUtil.nullToBlank((pmfObj.getCommentB())));
			pmfMap.put("location", StringUtil.nullToBlank((pmfObj.getLocation())));
			pmfMap.put("market", StringUtil.nullToBlank((pmfObj.getMarket())));
			pmfMap.put("grade", StringUtil.nullToBlank((pmfObj.getGrade())));
			pmfMap.put("pmfClass", StringUtil.nullToBlank((pmfObj.getPmfClass())));
			pmfMap.put("price1", NumberUtil.nullToZero((BigDecimal)pmfObj.getPrice1()));
			pmfMap.put("price2", NumberUtil.nullToZero((BigDecimal)pmfObj.getPrice2()));
			pmfMap.put("priceNoTax", NumberUtil.nullToZero((BigDecimal)pmfObj.getPriceNoTax()));
			pmfMap.put("pricewTax", pmfObj.getPricewTax());
			pmfMap.put("pkgQuantity", StringUtil.nullToBlank((pmfObj.getPkgQuantity())));
			pmfMap.put("pkgType", StringUtil.nullToBlank((pmfObj.getPkgType())));
			pmfMap.put("unitOrder", pmfObj.getUnitOrder());
			pmfMap.put("sellerName", StringUtil.nullToBlank((pmfObj.getSellerName())));
			pmfMap.put("categoryId", pmfObj.getCategoryId());
			pmfMap.put("sellerId", userId);
			pmfMap.put("companyName", user.getCompany().getShortName());//);
			pmfListMaps.add(pmfMap);
		}
		
		ModelAndView mav = new ModelAndView("json");
		mav.addObject("data", pmfListMaps);
		mav.addObject("pageInfo", tableParam.getPageInfo());
//		mav.addObject("skuGroup", skuGroupList);
		
		return mav;
	}

	public void setPmfService(ProductMasterFileService pmfService) {
		this.pmfService = pmfService;
	}

	public void setSkuGroupService(SKUGroupService skuGroupService) {
		this.skuGroupService = skuGroupService;
	}
}