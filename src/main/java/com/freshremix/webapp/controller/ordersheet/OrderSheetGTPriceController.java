package com.freshremix.webapp.controller.ordersheet;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
/**
 * To be deleted. Not being used
 * @deprecated
 *
 */
public class OrderSheetGTPriceController implements Controller {

//	private OrderSheetService orderSheetService;
//	private BuyerOrderSheetService buyerOrderSheetService;
//	private CategoryService categoryService;
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Map<String,Object> model = new HashMap<String,Object>();
//		OrderSheetParam osParam = (OrderSheetParam) request.getSession().getAttribute(SessionParamConstants.ORDER_SHEET_PARAM);
//		User user = (User) SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
//		
//		List<UsersCategory> catList = categoryService.getCategoryList(user, osParam);
//		List<Integer> categoryId = new ArrayList<Integer>();
//		
//		for (UsersCategory _cat : catList) {
//			categoryId.add(_cat.getCategoryId());
//		}
//		
//		ProfitInfo pi = new ProfitInfo();
//		
//		if (user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER_ADMIN)||
//				user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER)){
//			
//			pi = buyerOrderSheetService.getBuyerTotalPrices(
//					DateFormatter.getDateList(osParam.getStartDate(), osParam.getEndDate()), 
//					OrderSheetUtil.toList(osParam.getSelectedSellerID()),
//					OrderSheetUtil.toList(osParam.getSelectedBuyerID()),
//					categoryId,
//					new Double(1.05),
//					user.getPreference().getProfitPreference().getPriceTaxOption());		
//		} else {
//			pi = orderSheetService.getProfitInfo(
//					DateFormatter.getDateList(osParam.getStartDate(), osParam.getEndDate()), 
//					OrderSheetUtil.toList(osParam.getSelectedSellerID()),
//					OrderSheetUtil.toList(osParam.getSelectedBuyerID()),
//					categoryId,
//					new Double(1.05));
//		}
//		
//		model.put("priceInfo", pi);
		
		return new ModelAndView("json", model);
	}

//	public void setOrderSheetService(OrderSheetService orderSheetService) {
//		this.orderSheetService = orderSheetService;
//	}
//
//	public void setBuyerOrderSheetService(
//			BuyerOrderSheetService buyerOrderSheetService) {
//		this.buyerOrderSheetService = buyerOrderSheetService;
//	}
//	
//	public void setCategoryService(CategoryService categoryService) {
//		this.categoryService = categoryService;
//	}

}