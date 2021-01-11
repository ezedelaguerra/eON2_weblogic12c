
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
 * Mar 11, 2010		Jovino Balunan		
 */
package com.freshremix.util;

import com.freshremix.constants.RoleConstants;
import com.freshremix.model.Role;
import com.freshremix.model.User;

/**
 * @author jabalunan
 * 
 */
public class RolesUtil {
	// this is to check if roleid is seller-admin, buyer-admin, admin, chouai
	private static Integer[] admin = { 2, 4, 5, 6, 10 };

	public static final Integer ROLEID_SELLER = new Integer(1);
	public static final Integer ROLEID_SELLER_ADMIN = new Integer(2);
	public static final Integer ROLEID_BUYER = new Integer(3);
	public static final Integer ROLEID_BUYER_ADMIN = new Integer(4);
	public static final Integer ROLEID_CHOUAI = new Integer(6);
	public static final Integer ROLEID_ADMIN = new Integer(5);
	public static final Integer ROLEID_KEN = new Integer(10);

	public static final String ROLENAME_SELLER = "SELLER";
	public static final String ROLENAME_SELLER_ADMIN = "SELLER ADMIN";
	public static final String ROLENAME_BUYER = "BUYER";
	public static final String ROLENAME_BUYER_ADMIN = "BUYER ADMIN";
	public static final String ROLENAME_CHOUAI = "CHOUAI";
	public static final String ROLENAME_ADMIN = "ADMIN";
	public static final String ROLENAME_KEN = "KEN";

	public static Integer getRoleId(String rolename) {
		if (rolename.equalsIgnoreCase(ROLENAME_SELLER_ADMIN))
			return ROLEID_SELLER_ADMIN;
		else if (rolename.equalsIgnoreCase(ROLENAME_BUYER_ADMIN))
			return ROLEID_BUYER_ADMIN;
		else if (rolename.equalsIgnoreCase(ROLENAME_SELLER))
			return ROLEID_SELLER;
		else if (rolename.equalsIgnoreCase(ROLENAME_BUYER))
			return ROLEID_BUYER;
		else
			return ROLEID_CHOUAI;
	}



	public static boolean roleAdmin(Integer role) {
		for (Integer i : admin) {
			if (i == role)
				return true;
		}
		return false;
	}

	public static boolean iseONAdmin(Long role) {
		if (role.intValue() == ROLEID_ADMIN.intValue()
				|| role.intValue() == ROLEID_KEN.intValue()) {
			return true;
		}
		return false;
	}

	public static boolean isBuyerType(String companyType) {
		if (companyType.equalsIgnoreCase("buyer"))
			return true;
		return false;
	}

	public static boolean isSellerType(String companyType) {
		if (companyType.equalsIgnoreCase("seller"))
			return true;
		return false;
	}

	public static boolean isBuyerType(Role role) {
		return (role.getBuyerFlag().equals("1") || role.getBuyerAdminFlag()
				.equals("1"));

	}

	public static boolean isSellerType(Role role) {
		return (role.getSellerFlag().equals("1") || role.getSellerAdminFlag()
				.equals("1"));

	}

	public static boolean isBuyerByRoleId(Integer roleId) {
		if (roleId.equals(3))
			return true;
		return false;
	}

	public static boolean isSellerByRoleId(Integer roleId) {
		if (roleId.equals(1))
			return true;
		return false;
	}

	public static Integer roleBuyer() {
		return 3;
	}

	public static Integer roleSeller() {
		return 1;
	}

	public static boolean isUserRoleBuyerAdmin(User user) {
		if ((user == null) //
				|| (user.getRole() == null) //
				|| (user.getRole().getRoleId() == null)) {
			throw new IllegalArgumentException("Expected user detail is null");
		}
		return user.getRole().getRoleId()
				.equals(RoleConstants.ROLE_BUYER_ADMIN);
	}

	public static boolean isUserRoleBuyer(User user) {
		if ((user == null)//
				|| (user.getRole() == null)//
				|| (user.getRole().getRoleId() == null)) {
			throw new IllegalArgumentException("Expected user detail is null");
		}
		return user.getRole().getRoleId().equals(RoleConstants.ROLE_BUYER);
	}

	public static boolean isUserRoleSeller(User user) {
		if ((user == null)//
				|| (user.getRole() == null)//
				|| (user.getRole().getRoleId() == null)) {
			throw new IllegalArgumentException("Expected user detail is null");
		}
		return user.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER);
	}

	public static boolean isUserRoleSellerAdmin(User user) {
		if ((user == null)//
				|| (user.getRole() == null)//
				|| (user.getRole().getRoleId() == null)) {
			throw new IllegalArgumentException("Expected user detail is null");
		}
		return user.getRole().getRoleId().equals(RoleConstants.ROLE_SELLER_ADMIN);
	}

}
