package com.freshremix.model;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class UsersTOS extends AbstractBaseModel<Integer> {

	@AbstractBaseModel.PrimaryKey
	private Integer userId;
	
	//Valid values 0=Disagree(default) 1=Agree
	private String flag = "0";
	private String flagSetBy;
	
	private Date flagDate;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getFlagSetBy() {
		return flagSetBy;
	}

	public void setFlagSetBy(String flagSetBy) {
		this.flagSetBy = flagSetBy;
	}

	public Date getFlagDate() {
		return flagDate;
	}

	public void setFlagDate(Date flagDate) {
		this.flagDate = flagDate;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Flag", this.getFlag())
				.append("Flag Date", this.getFlagDate())
				.append("Flag Set By", this.getFlagSetBy())
				.append("User Id", this.getUserId())
				.append("Version", this.getVersion()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.getUserId()).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsersTOS other = (UsersTOS) obj;
		return new EqualsBuilder().append(this.getUserId(), other.getUserId())
				.isEquals();
	}

	// Convenience methos for primary key setting
	@Override
	public Integer getPrimaryKey() {
		return getUserId();
	}

	@Override
	public void setPrimaryKey(Integer pk) {
		setUserId(pk);
	}

}
