package com.freshremix.abstractControllers;

import static com.freshremix.constants.SystemConstants.CATEGORY_MASTER_LIST;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.constants.RoleConstants;
import com.freshremix.model.Category;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.User;
import com.freshremix.model.UsersCategory;
import com.freshremix.service.CategoryService;
import com.freshremix.service.DealingPatternService;

public abstract class AbstractCategoryTabs 
	extends SimpleFormController 
	implements ServletContextAware {

	private CategoryService categoryService;
	private DealingPatternService dealingPatternService;
	
	private static final String GIF =".gif";
	private static final String IMG_LOCATION ="../../common/img/";

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	public void setDealingPatternService(DealingPatternService dealingPatternService) {
		this.dealingPatternService = dealingPatternService;
	}
	
	// for sheet tabs (Order, Alloc, Received)
	public Map<String,Object> getUserCategories(User user,OrderSheetParam osParam){
		//List<UsersCategory> categoryList = categoryService.getCategoryListByUserId(id.toString());
		List<UsersCategory> categoryList = getCategoryList(user,osParam);
		List<Map<String,String>>mapList = new ArrayList<Map<String,String>>();
		Map<String,Object> model = new HashMap<String,Object>();
		int i =0;
		String index = "1";
		List<Integer> checkList = new ArrayList<Integer>();
		for (UsersCategory category : categoryList) {
			Map<String,String> categoryMap=new HashMap<String,String>();
			if(!checkList.contains(category.getCategoryId())){
				
				if(i == 0){
					index = category.getCategoryId()+"";
				}
				categoryMap.put("id", category.getCategoryId()+"" );
				categoryMap.put("caption", getCaption(category.getCategoryId()));
				categoryMap.put("image", appendLocation(category.getCategoryAvailable()));
				mapList.add(categoryMap);
				checkList.add(category.getCategoryId());
				i++;
			}
				
		}
		model.put("index", index);
		model.put("categories", mapList);
		return model;
	}	
	
	// for maintenance tab (SKU Group Maintenance and Sort)
	public Map<String,Object> getUserCategories(User user){
		//List<UsersCategory> categoryList = categoryService.getCategoryListByUserId(id.toString());
		List<UsersCategory> categoryList = getCategoryList(user);
		List<Map<String,String>>mapList = new ArrayList<Map<String,String>>();
		Map<String,Object> model = new HashMap<String,Object>();
		int i =0;
		String index = "1";
		List<Integer> checkList = new ArrayList<Integer>();
		for (UsersCategory category : categoryList) {
			Map<String,String> categoryMap=new HashMap<String,String>();
			if(!checkList.contains(category.getCategoryId())){
				
				if(i == 0){
					index = category.getCategoryId()+"";
				}
				categoryMap.put("id", category.getCategoryId()+"" );
				categoryMap.put("caption", getCaption(category.getCategoryId()));
				categoryMap.put("image", appendLocation(category.getCategoryAvailable()));
				mapList.add(categoryMap);
				checkList.add(category.getCategoryId());
				i++;
			}
				
		}
		model.put("index", index);
		model.put("categories", mapList);
		return model;
	}	
	
	
	
	/**
	 * retrieves registered categories for a user
	 * @param user
	 * @param osParam
	 * @return
	 */
	private List<UsersCategory> getCategoryList(User user,OrderSheetParam osParam){
		
		return categoryService.getCategoryList(user, osParam);
	}
	
	
	
	/**
	 * Use for preference only
	 * retrieves registered categories for a user
	 * @param user
	 * @param osParam
	 * @return
	 */
	private List<UsersCategory> getCategoryList(User user){
		
		List<UsersCategory> categoryList = new ArrayList<UsersCategory>();
		if(user.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER)){
			categoryList = 
				categoryService.getCategoryListByUserId(user.getUserId().toString());
		}else{
			List<Integer> sellerIds = new ArrayList<Integer>();
			if (user.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER_ADMIN)) {
				sellerIds = dealingPatternService.getSellerIdsOfSellerAdminId(user.getUserId());
			}
			else if (user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER_ADMIN)) {
				sellerIds = dealingPatternService.getSellerIdsOfBuyerAdminId(user.getUserId());
			}
			else if (user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER)) {
				sellerIds = dealingPatternService.getSellerIdsOfBuyerId(user.getUserId());
			}
			categoryList = 
				categoryService.getCategoryListBySelectedIds(sellerIds);
			
		}
		
		return categoryList;
	}
	
	/**
	* 
	* @param categoryNames
	* @return
	*/
	private String appendLocation(String categoryNames){
		StringBuffer sb = new StringBuffer();
		sb.append(IMG_LOCATION);
		sb.append(categoryNames.toLowerCase());
		sb.append(GIF);
		return sb.toString();
	}

	/**
	* @param categoryNames
	* @return
	*/
	private String getCaption(Integer categoryId){
		Map<String, Category> map = extractCategoryMasterList();
		return map.get(categoryId.toString()).getTabName();
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, Category> extractCategoryMasterList() {
		return (Map<String, Category>)
			super.getServletContext().getAttribute(CATEGORY_MASTER_LIST);
	}	

}
