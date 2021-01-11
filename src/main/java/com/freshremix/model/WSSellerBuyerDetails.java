package com.freshremix.model;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class WSSellerBuyerDetails {

	@XmlElement(required=true)
	private String buyerId;
	private String quantity;
	private String visibility;
	@XmlTransient
	private User buyer;
	@XmlTransient	
	private Order order;
	@XmlTransient
	private BigDecimal qtyDec;
	@XmlTransient
	private OrderItem oItem;
	
	
	

	public OrderItem getoItem() {
		return oItem;
	}
	public void setoItem(OrderItem oItem) {
		this.oItem = oItem;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public BigDecimal getQtyDec() {
		return qtyDec;
	}
	public void setQtyDec(BigDecimal qtyDec) {
		this.qtyDec = qtyDec;
	}
	public User getBuyer() {
		return buyer;
	}
	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}
	public String getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getVisibility() {
		return visibility;
	}
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}
	
	
	
}
