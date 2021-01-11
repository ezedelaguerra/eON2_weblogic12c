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
 * May 19, 2010		Jovino Balunan		
 */
package com.freshremix.util;

import java.util.Map;

import com.freshremix.constants.CategoryConstants;
import com.freshremix.model.Category;

/**
 * @author Jovino Balunan
 *
 */
public class CategoryUtil {

	public static Integer getCategoryId(String categoryName) {
		if (categoryName.trim().equalsIgnoreCase("FRUITS"))
			return CategoryConstants.FRUITS;
		else if (categoryName.trim().equalsIgnoreCase("VEGETABLES"))
			return CategoryConstants.VEGETABLES;
		if (categoryName.trim().equalsIgnoreCase("FISH"))
			return CategoryConstants.FISH;		
//		else if (categoryName.trim().equalsIgnoreCase("SIDEDISH"))
//			return CategoryConstants.SIDEDISH;
//		else if (categoryName.trim().equalsIgnoreCase("MEAT"))
//			return CategoryConstants.MEAT;
//		else if (categoryName.trim().equalsIgnoreCase("PROCESSEDFOOD"))
//			return CategoryConstants.PROCESSEDFOOD;
//		else if (categoryName.trim().equalsIgnoreCase("FLOWERS"))
//			return CategoryConstants.FLOWERS;
//		else if (categoryName.trim().equalsIgnoreCase("STANDARDGOODS"))
//			return CategoryConstants.STANDARDGOODS;
//		else if (categoryName.trim().equalsIgnoreCase("FLYERLEAFLET"))
//			return CategoryConstants.FLYERLEAFLET;
//		else if (categoryName.trim().equalsIgnoreCase("SPOT"))
//			return CategoryConstants.SPOT;
//		else if (categoryName.trim().equalsIgnoreCase("PLAN"))
//			return CategoryConstants.PLAN;
//		else if (categoryName.trim().equalsIgnoreCase("FLIGHT1"))
//			return CategoryConstants.FLIGHT1;
//		else if (categoryName.trim().equalsIgnoreCase("FLIGHT2"))
//			return CategoryConstants.FLIGHT2;
//		else if (categoryName.trim().equalsIgnoreCase("FLIGHT3"))
//			return CategoryConstants.FLIGHT3;
//		else if (categoryName.trim().equalsIgnoreCase("IMPORTED"))
//			return CategoryConstants.IMPORTED;
//		else if (categoryName.trim().equalsIgnoreCase("DOMESTIC"))
//			return CategoryConstants.DOMESTIC;
//		else if (categoryName.trim().equalsIgnoreCase("IMPORTEDFRUITS"))
//			return CategoryConstants.IMPORTEDFRUITS;
//		else if (categoryName.trim().equalsIgnoreCase("IMPORTEDVEGETABLES"))
//			return CategoryConstants.IMPORTEDVEGETABLES;
//		else if (categoryName.trim().equalsIgnoreCase("DOMESTICFRUITS"))
//			return CategoryConstants.DOMESTICFRUITS;
//		else if (categoryName.trim().equalsIgnoreCase("DOMESTICVEGETABLES"))
//			return CategoryConstants.DOMESTICVEGETABLES;
//		else if (categoryName.trim().equalsIgnoreCase("SUBDIVISION"))
//			return CategoryConstants.SUBDIVISION;
//		else if (categoryName.trim().equalsIgnoreCase("REQUEST"))
//			return CategoryConstants.REQUEST;
//		else if (categoryName.trim().equalsIgnoreCase("AM"))
//			return CategoryConstants.AM;
//		else if (categoryName.trim().equalsIgnoreCase("PM"))
//			return CategoryConstants.PM;
//		else if (categoryName.trim().equalsIgnoreCase("INFLOW"))
//			return CategoryConstants.INFLOW;
//		else if (categoryName.trim().equalsIgnoreCase("RAWMATERIAL"))
//			return CategoryConstants.RAWMATERIAL;
//		else if (categoryName.trim().equalsIgnoreCase("GROCERY"))
//			return CategoryConstants.GROCERY;
//		else if (categoryName.trim().equalsIgnoreCase("DAILYPRODUCTDISTRIBUTION"))
//			return CategoryConstants.DAILYPRODUCTDISTRIBUTION;
//		else if (categoryName.trim().equalsIgnoreCase("DRINK"))
//			return CategoryConstants.DRINK;
//		else if (categoryName.trim().equalsIgnoreCase("ALCOHOL"))
//			return CategoryConstants.ALCOHOL;
//		else if (categoryName.trim().equalsIgnoreCase("FROZENFOOD"))
//			return CategoryConstants.FROZENFOOD;
//		else if (categoryName.trim().equalsIgnoreCase("CONVENIENCEGOODS"))
//			return CategoryConstants.CONVENIENCEGOODS;
//		else if (categoryName.trim().equalsIgnoreCase("PASTRY"))
//			return CategoryConstants.PASTRY;
//		else if (categoryName.trim().equalsIgnoreCase("CLOTHING"))
//			return CategoryConstants.CLOTHING;
//		else if (categoryName.trim().equalsIgnoreCase("TEST"))
//			return CategoryConstants.TEST;
//		else if (categoryName.trim().equalsIgnoreCase("BCLASS"))
//			return CategoryConstants.BCLASS;
//		else if (categoryName.trim().equalsIgnoreCase("PERISHABLES"))
//			return CategoryConstants.PERISHABLES;
//		else if (categoryName.trim().equalsIgnoreCase("Others"))
//			return CategoryConstants.Others;
//		else if (categoryName.trim().equalsIgnoreCase("A"))
//			return CategoryConstants.A;
//		else if (categoryName.trim().equalsIgnoreCase("B"))
//			return CategoryConstants.B;
//		else if (categoryName.trim().equalsIgnoreCase("C"))
//			return CategoryConstants.B;
//		else if (categoryName.trim().equalsIgnoreCase("D"))
//			return CategoryConstants.C;
//		else if (categoryName.trim().equalsIgnoreCase("E"))
//			return CategoryConstants.E;
//		else if (categoryName.trim().equalsIgnoreCase("F"))
//			return CategoryConstants.F;
		else
			return 0000;
	}
	
	public static String getCategoryDesc(Integer categoryId, Map<String,Category> categoryMap) {
		String name = null;
		if (categoryId.equals(0)) {
			name = "all";
		} else {
			for (String key : categoryMap.keySet()) {
				Category _cat = categoryMap.get(key);
				if (categoryId.equals(_cat.getCategoryId()))
					name = _cat.getDescription();
			}
		}
		return name;
	}
}
