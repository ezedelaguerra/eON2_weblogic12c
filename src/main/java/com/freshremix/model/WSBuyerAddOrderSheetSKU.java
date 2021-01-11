package com.freshremix.model;

import java.math.BigDecimal;
import java.util.ArrayList;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

public abstract class WSBuyerAddOrderSheetSKU {

	private Integer sellerId;
	private User seller;
	private BigDecimal B_sellingPrice;	
	private BigDecimal B_purchasePrice;
	private String B_skuComment;
	private OrderUnit B_sellingUom;
	private String B_sellingUomName;
	
	
	private SKUBA skuBA;
	
	private ArrayList<WSBuyerAddOrderSheetDetails> details;
	
	public ArrayList<WSBuyerAddOrderSheetDetails> getDetails() {
		return details;
	}
	public void setDetails(ArrayList<WSBuyerAddOrderSheetDetails> details) {
		this.details = details;
	}
	
	@XmlTransient
	public User getSeller() {
		return seller;
	}
	public void setSeller(User seller) {
		this.seller = seller;
	}
	


	public String getB_sellingUomName() {
		return B_sellingUomName;
	}
	public void setB_sellingUomName(String b_sellingUomName) {
		B_sellingUomName = b_sellingUomName;
	}
	
	@XmlElement(required = true)
	public Integer getSellerId() {
		return sellerId;
	}
	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}
	

	public BigDecimal getB_sellingPrice() {
		return B_sellingPrice;
	}
	public void setB_sellingPrice(BigDecimal b_sellingPrice) {
		B_sellingPrice = b_sellingPrice;
	}
	public BigDecimal getB_purchasePrice() {
		return B_purchasePrice;
	}
	public void setB_purchasePrice(BigDecimal b_purchasePrice) {
		B_purchasePrice = b_purchasePrice;
	}
	public String getB_skuComment() {
		return B_skuComment;
	}
	public void setB_skuComment(String b_skuComment) {
		B_skuComment = b_skuComment;
	}
	@XmlTransient
	public OrderUnit getB_sellingUom() {
		return B_sellingUom;
	}
	public void setB_sellingUom(OrderUnit b_sellingUom) {
		B_sellingUom = b_sellingUom;
	}
	
	@XmlTransient
	public SKUBA getSkuBA() {
		return skuBA;
	}
	public void setSkuBA(SKUBA skuBA) {
		this.skuBA = skuBA;
	}

	

}
