package com.freshremix.model;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class TermsOfService extends AbstractBaseModel<String> {

	@AbstractBaseModel.PrimaryKey
	private String tosId;
	private String content;
	private String emailList;
	private Date createdDate;
	private String createdBy;
	private Date modifiedDate;
	private String modifiedBy;
	private Boolean resetAcceptance;
	private Boolean isMinorChange;
	

	/**
	 * 
	 * NEW state when created date is <= max number of days tagged as new
	 * OLD state otherwise
	 */
	public static enum TOSState {
		NEW, OLD;
	}	
	
	
	
	public Boolean getIsMinorChange() {
		return isMinorChange;
	}

	public void setIsMinorChange(Boolean isMinorChange) {
		this.isMinorChange = isMinorChange;
	}

	public String getTosId() {
		return tosId;
	}

	public void setTosId(String tosId) {
		this.tosId = tosId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getEmailList() {
		return emailList;
	}

	public void setEmailList(String emailList) {
		this.emailList = emailList;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Boolean getResetAcceptance() {
		return resetAcceptance;
	}

	public void setResetAcceptance(Boolean resetAcceptance) {
		this.resetAcceptance = resetAcceptance;
	}


	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("ID", this.getTosId())
				.append("Content",
						StringUtils.substring(StringUtils.trimToEmpty(this.getContent()), 0, 10))
				.append("Email List", this.getEmailList())
				.append("Created By", this.getCreatedBy())
				.append("Created Date", this.getCreatedDate())
				.append("Modified By", this.getModifiedBy())
				.append("Modified Date", this.getModifiedDate())
				.append("Version", this.getVersion()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.getTosId()).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TermsOfService other = (TermsOfService) obj;
		return new EqualsBuilder().append(this.getTosId(), other.getTosId())
				.isEquals();
	}

}
