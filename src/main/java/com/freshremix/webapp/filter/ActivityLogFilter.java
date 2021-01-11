package com.freshremix.webapp.filter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import net.sf.sojo.interchange.Serializer;
import net.sf.sojo.interchange.json.JsonSerializer;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.dao.ActivityLogDao;
import com.freshremix.dao.impl.ActivityLogDaoImpl;
import com.freshremix.model.ActivityLog;
import com.freshremix.model.OrderSheetParam;
import com.freshremix.model.User;
import com.freshremix.ui.model.OrderForm;
import com.freshremix.util.SessionHelper;

public class ActivityLogFilter implements Filter{

	public static final String SAVED = "SAVED";
	public static final String PUBLISHED = "PUBLISHED";
	public static final String UNPUBLISHED = "UNPUBLISHED";
	public static final String FINALIZED = "FINALIZED";
	public static final String UNFINALIZED = "UNFINALIZED";
	
	//ORDER PATH
	public static final String ORDER_SAVED_PATH = "/order/saveOrder.json";
	public static final String ORDER_PUBLISHED_PATH = "/seller/publish.json";
	public static final String ORDER_UNPUBLISHED_PATH = "/seller/unpublish.json";
	public static final String ORDER_FINALIZED_PATH = "/seller/finalize.json";
	public static final String ORDER_UNFINALIZED_PATH = "/seller/unfinalize.json";

	//ALLOCATION PATH
	public static final String ALLOCATION_SAVED_PATH = "/allocation/saveOrder.json";
	public static final String ALLOCATION_PUBLISHED_PATH = "/seller/publishAllocation.json";
	public static final String ALLOCATION_UNPUBLISHED_PATH = "/seller/unpublishAllocation.json";
	public static final String ALLOCATION_FINALIZED_PATH = "/seller/finalizeAllocation.json";
	public static final String ALLOCATION_UNFINALIZED_PATH = "/seller/unfinalizeAllocation.json";
	
	ActivityLogDao activityLogDao;
	

	public void setActivityLogDao(ActivityLogDao activityLogDao) {
		this.activityLogDao = activityLogDao;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		System.out.println("Activity Log Before");
		HttpServletRequest req = (HttpServletRequest) request;
		
		
		User user = (User)SessionHelper.getAttribute(req, SessionParamConstants.USER_PARAM);
		if(StringUtils.equals(user.getRole().getRoleName(), "SELLER") || StringUtils.equals(user.getRole().getRoleName(), "SELLER_ADMIN")){
			Serializer serializer = new JsonSerializer();
			String json = request.getParameter("_gt_json");
			
			String sheetName = this.getSheetName(req);
			String action = this.getAction(req);
			String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
			String targetDeliveryDate = "";
			String targetBuyerIds = "";
			String targetSellerIds = "";
			if(json == null){
				targetDeliveryDate = request.getParameter("targetDeliveryDate");
				targetBuyerIds = request.getParameter("targetBuyerIds");
				targetSellerIds = request.getParameter("targetSellerIds");
			} else {
				OrderForm orderForm = (OrderForm) serializer.deserialize(json, OrderForm.class);
				Map<String, Object> params = orderForm.getParameters();
				targetDeliveryDate = (String) params.get("targetDeliveryDate");
				targetBuyerIds = (String) params.get("targetBuyerIds");
				targetSellerIds = (String) params.get("targetSellerIds");
			}
			
			
			ActivityLog aLog = new ActivityLog();
			aLog.setUserId(user.getUserId());
			aLog.setUsername(user.getUserName());
			aLog.setDate(date);
			aLog.setSheetName(sheetName);
			aLog.setAction(action);
			aLog.setDeliveryDate(targetDeliveryDate);
			aLog.setTargetBuyerId(targetBuyerIds);
			aLog.setTargetSellerId(targetSellerIds);
			activityLogDao.saveActivityLog(aLog);
		}
		
		chain.doFilter(request,response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
	private String getSheetName(HttpServletRequest request){
		String pathString = request.getServletPath();
		if(StringUtils.equals(pathString, ORDER_SAVED_PATH) ||
				StringUtils.equals(pathString, ORDER_PUBLISHED_PATH) ||
				StringUtils.equals(pathString, ORDER_UNPUBLISHED_PATH) ||
				StringUtils.equals(pathString, ORDER_FINALIZED_PATH) ||
				StringUtils.equals(pathString, ORDER_UNFINALIZED_PATH)) {
			return "ORDER";
		} else {
			return "ALLOCATION";
		}
		
	}
	
	private String getAction(HttpServletRequest request){
		String pathString = request.getServletPath();
		if(StringUtils.equals(pathString, ORDER_SAVED_PATH) || StringUtils.equals(pathString, ALLOCATION_SAVED_PATH)){
			return SAVED;
		} else if(StringUtils.equals(pathString, ORDER_PUBLISHED_PATH) || StringUtils.equals(pathString, ALLOCATION_PUBLISHED_PATH)){
			return PUBLISHED;
		} else if(StringUtils.equals(pathString, ORDER_UNPUBLISHED_PATH) || StringUtils.equals(pathString, ALLOCATION_UNPUBLISHED_PATH)){
			return UNPUBLISHED;
		} else if(StringUtils.equals(pathString, ORDER_FINALIZED_PATH) || StringUtils.equals(pathString, ALLOCATION_FINALIZED_PATH)){
			return FINALIZED;
		} else {
			return UNFINALIZED;
		} 
	}
	
}
