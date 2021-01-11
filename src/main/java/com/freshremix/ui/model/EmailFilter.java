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
 * date		name		version		changes
 * ------------------------------------------------------------------------------
 * 20120725	gilwen		v11			Redmine 131 - Change of display in address bar of Comments
 */
package com.freshremix.ui.model;

/**
 * @author gilwen
 *
 */
public class EmailFilter {

	// ENHANCEMENT START 20120725: Lele - Redmine 131
	private Integer companyId;
	private String companyName;
	// ENHANCEMENT END 20120725: Lele
	private String id;
	private String caption;
	private String pcEmailAddress;
	private String mobileAddress;
	
	// ENHANCEMENT START 20120725: Lele - Redmine 131
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	// ENHANCEMENT END 20120725: Lele
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public String getPcEmailAddress() {
		return pcEmailAddress;
	}
	public void setPcEmailAddress(String pcEmailAddress) {
		this.pcEmailAddress = pcEmailAddress;
	}
	public String getMobileAddress() {
		return mobileAddress;
	}
	public void setMobileAddress(String mobileAddress) {
		this.mobileAddress = mobileAddress;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof EmailFilter) {
			EmailFilter tmp = (EmailFilter) obj;
			return tmp.getId().equals(this.id);
		}
		return false;
	}
	@Override
	public int hashCode() {
		return this.id.length();
	}
	
}
