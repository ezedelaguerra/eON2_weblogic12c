package com.freshremix.model;

import java.util.List;

public class CompanyDealingPattern {
	private Integer companyId;
	private List<String> selectedCompanyId;
	private Integer dpRelationId;
	private String description;

	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public List<String> getSelectedCompanyId() {
		return selectedCompanyId;
	}
	public void setSelectedCompanyId(List<String> selectedCompanyId) {
		this.selectedCompanyId = selectedCompanyId;
	}
	public Integer getDpRelationId() {
		return dpRelationId;
	}
	public void setDpRelationId(Integer dpRelationId) {
		this.dpRelationId = dpRelationId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
