package com.freshremix.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class WSSellerGetOrderSheetRequest extends WSSellerRequest{

	
	private List<String> sellerIds;

	private List<String> buyerIds;
	
	@XmlElement(defaultValue="120")
	private String pageSize;

	@XmlElement(defaultValue="1")
	private String pageNum;
	@XmlTransient
	private List<Integer> sellerIdList;
	@XmlTransient
	private List<Integer> buyerIdList;
	@XmlTransient
	private Integer pageNumInt;
	@XmlTransient
	private Integer pageSizeInt;

	
	public List<String> getSellerIds() {
		return sellerIds;
	}

	public void setSellerIds(List<String> sellerIds) {
		this.sellerIds = sellerIds;
	}

	public List<String> getBuyerIds() {
		return buyerIds;
	}

	public void setBuyerIds(List<String> buyerIds) {
		this.buyerIds = buyerIds;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getPageNum() {
		return pageNum;
	}

	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}

	public List<Integer> getSellerIdList() {
		return sellerIdList;
	}

	public void setSellerIdList(List<Integer> sellerIdList) {
		this.sellerIdList = sellerIdList;
	}

	public List<Integer> getBuyerIdList() {
		return buyerIdList;
	}

	public void setBuyerIdList(List<Integer> buyerIdList) {
		this.buyerIdList = buyerIdList;
	}

	public Integer getPageNumInt() {
		return pageNumInt;
	}

	public void setPageNumInt(Integer pageNumInt) {
		this.pageNumInt = pageNumInt;
	}

	public Integer getPageSizeInt() {
		return pageSizeInt;
	}

	public void setPageSizeInt(Integer pageSizeInt) {
		this.pageSizeInt = pageSizeInt;
	}

	
	
}
