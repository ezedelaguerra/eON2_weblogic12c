package com.freshremix.webapp.controller.allocationsheet;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * To be deleted. Controller not used.
 * @deprecated
 *
 */
public class AllocationSheetGTPriceController implements Controller {

//	private AllocationSheetService allocationSheetService;
//	private CategoryService categoryService;
	
//	public void setAllocationSheetService(
//			AllocationSheetService allocationSheetService) {
//		this.allocationSheetService = allocationSheetService;
//	}
//	
//	public void setCategoryService(CategoryService categoryService) {
//		this.categoryService = categoryService;
//	}
	
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
//		ProfitInfo pi = allocationSheetService.getProfitInfo(
//				DateFormatter.getDateList(osParam.getStartDate(), osParam.getEndDate()), 
//				OrderSheetUtil.toList(osParam.getSelectedSellerID()),
//				OrderSheetUtil.toList(osParam.getSelectedBuyerID()),
//				categoryId,
//				new Double(1.05));
//		
//		model.put("priceWithoutTax", pi.getPriceWithoutTax());
//		model.put("priceWithTax", pi.getPriceWithTax());
		
		return new ModelAndView("json", model);
	}
}