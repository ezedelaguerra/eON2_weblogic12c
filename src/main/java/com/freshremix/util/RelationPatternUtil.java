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
 * Mar 26, 2010		Jovino Balunan		
 */
package com.freshremix.util;

import com.freshremix.constants.CompanyRelationConstants;

/**
 * @author Jovino Balunan
 *
 */
public class RelationPatternUtil {

	public static Integer dealingIdByRoleType(String roleType) {
		if(roleType.equalsIgnoreCase("seller") || roleType.equalsIgnoreCase("buyer"))
			return CompanyRelationConstants.SELLER_TO_BUYER;
		else if (roleType.equalsIgnoreCase("seller_admin")) 
			return CompanyRelationConstants.SELLER_ADMIN_TO_SELLER;
		else if (roleType.equalsIgnoreCase("buyer_admin"))
			return CompanyRelationConstants.BUYER_ADMIN_TO_BUYER;
		else if (roleType.equalsIgnoreCase("chouai"))
			return CompanyRelationConstants.CHOUAI_TO_SELLER;
		else 
			return 0;
	}
	
	public static Integer dealingIdByRoleId(Integer roleId) {
		if((roleId==1) || (roleId==3))
			return CompanyRelationConstants.SELLER_TO_BUYER;
		else if (roleId.equals(2)) 
			return CompanyRelationConstants.SELLER_ADMIN_TO_SELLER;
		else if (roleId.equals(4))
			return CompanyRelationConstants.BUYER_ADMIN_TO_BUYER;
		else if (roleId.equals(6))
			return CompanyRelationConstants.CHOUAI_TO_SELLER;
		else 
			return 0;
	}
}
