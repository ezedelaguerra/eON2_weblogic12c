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
 * Mar 27, 2010		Jovino Balunan		
 */
package com.freshremix.webapp.controller.akadensheet;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.sojo.interchange.Serializer;
import net.sf.sojo.interchange.json.JsonSerializer;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.constants.AkadenSessionConstants;
import com.freshremix.constants.SessionParamConstants;
import com.freshremix.dao.AkadenDao;
import com.freshremix.model.AkadenSKU;
import com.freshremix.model.AkadenSheetParams;
import com.freshremix.model.Order;
import com.freshremix.model.User;
import com.freshremix.service.AkadenService;
import com.freshremix.ui.model.AkadenForm;
import com.freshremix.ui.model.AkadenItemUI;
import com.freshremix.ui.model.OrderDetails;
import com.freshremix.util.AkadenSheetUtil;
import com.freshremix.util.OrderSheetUtil;
import com.freshremix.util.SessionHelper;

/**
 * @author Jovino Balunan
 *
 */
public class AkadenSaveImpotedAllocController extends SimpleFormController {

	private AkadenService akadenService;
	private AkadenDao akadenDao;
	
	public void setAkadenDao(AkadenDao akadenDao) {
		this.akadenDao = akadenDao;
	}

	public void setAkadenService(AkadenService akadenService) {
		this.akadenService = akadenService;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		Map<String,Object> model = new HashMap<String,Object>();
		
		String json = request.getParameter("_gt_json");
		Serializer serializer = new JsonSerializer();
		AkadenForm akadenForm = (AkadenForm) serializer.deserialize(json, AkadenForm.class);
		AkadenSheetParams akadenSheetParams = (AkadenSheetParams) request.getSession().getAttribute(AkadenSessionConstants.AKADEN_IMPORT_ALLOC_PARAMS);
		Map<String, Order> mapOrderIds = (Map<String, Order>) request.getSession().getAttribute(AkadenSessionConstants.AKADEN_ALLOC_MAP_ORDERS);
		User user = (User) SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		List<Integer> buyerIds = OrderSheetUtil.toList(akadenSheetParams.getDatesViewBuyerID());
		List<Integer> sellerIds = OrderSheetUtil.toList(akadenSheetParams.getSelectedSellerID());
		String billingDate = akadenSheetParams.getSelectedDate();
		OrderDetails od = new OrderDetails();
		od.setSkuCategoryId(akadenSheetParams.getCategoryId());
//		Integer buyerId = Integer.valueOf(akadenSheetParams.getDatesViewBuyerID());
//		List<Integer> buyerIdList = new ArrayList<Integer>();
//		buyerIdList.add(buyerId);
//		Integer orderId = mapOrderIds.get(buyerId);
		akadenForm.setBuyerColumnIds(buyerIds);
		
		List<AkadenItemUI> orderItemList = akadenForm.getDeletedOrders();
		
		String isNewSKU = "0";
		String typeFlag = "0";
		Integer iExists = 0;
		
		/* this block will save the selected data from allocation 
		 * and save it to the following tables. "EON_ORDER_AKADEN, EON_AKADEN_SKU" */
		
		for(AkadenItemUI oi : orderItemList) {
			
			for(Integer sellerId : sellerIds) {
				for(Integer buyerId : buyerIds) {
					Order order = mapOrderIds.get(billingDate + "_" + buyerId + "_"+ sellerId);					
					if (order == null) continue;
					akadenDao.updateAkadenSaveBy(sellerId, order.getOrderId());						
					
					if(akadenService.checkImportedAllocIfExists(order.getOrderId(), oi.getSkuId()) < 1) {
						oi.setTypeFlag(typeFlag);
						AkadenSKU sku = AkadenSheetUtil.toAkadenSKU(oi, od, user);
						Integer akadenSkuId = akadenService.insertImportedAllocation(sku);
						if (order == null) continue;
						String q_key = "Q_" + buyerId;
						BigDecimal qty = new BigDecimal(0);
						if (oi.getQtyMap().get(q_key) != null)
							qty = oi.getQtyMap().get(q_key);
						akadenService.insertImportedOrderAkaden(order.getOrderId(), oi.getSkuId(), user.getUserId(), oi.getComments(), 
								akadenSkuId, isNewSKU, qty, qty, oi.getPrice1());
					} else {
						iExists = 1;
					}
				}
			}
		}
		model.put("exists", iExists);
		if (errors.hasErrors()) 
			return super.onSubmit(command, errors);
		else
			return new ModelAndView("json",model);
	}
}
