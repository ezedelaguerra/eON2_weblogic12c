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
 * Nov 9, 2010		raquino		
 */
package com.freshremix.model;

/**
 * @author raquino
 *
 */
public class LoginInfo {
	
	private String username;
    private String password;
    
    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
    
    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
