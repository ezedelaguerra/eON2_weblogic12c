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
package com.freshremix.model;

/**
 * @author Pammie
 *
 */

public class PmfList {
	private Integer pmfId;
	private String pmfName;
	private Integer pmfUserId;
	
	public PmfList(){}

	public void setPmfId(Integer pmfId) {
		this.pmfId = pmfId;
	}
	public void setPmfName(String pmfName) {
		this.pmfName = pmfName;
	}
	public void setPmfUserId(Integer pmfUserId) {
		this.pmfUserId = pmfUserId;
	}
	public Integer getPmfId() {
		return pmfId;
	}
	public String getPmfName() {
		return pmfName;
	}
	public Integer getPmfUserId() {
		return pmfUserId;
	}
}
