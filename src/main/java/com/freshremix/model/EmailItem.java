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
 * Apr 7, 2010		gilwen		
 */
package com.freshremix.model;


/**
 * @author gilwen
 *
 */
public class EmailItem {

	private Integer id;
	private String senderId;
	private String recipientId;
	private String recipientsAddress;
	private String senderName;
	private String address;
	private String receivedDate;
	private String subject;
	private String message;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSubject() {
		return subject == null ? "" : subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessage() {
		return message == null ? "" : message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSenderName() {
		return senderName == null ? "" : senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getAddress() {
		return address == null ? "" : address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSenderId() {
		return senderId == null ? "" : senderId;
	}
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	public String getRecipientId() {
		return recipientId == null ? "" : recipientId;
	}
	public void setRecipientId(String recipientId) {
		this.recipientId = recipientId;
	}
	public String getReceivedDate() {
		return receivedDate == null ? "" : receivedDate;
	}
	public void setReceivedDate(String receivedDate) {
		this.receivedDate = receivedDate;
	}
	public String getRecipientsAddress() {
		return recipientsAddress;
	}
	public void setRecipientsAddress(String recipientsAddress) {
		this.recipientsAddress = recipientsAddress;
	}
}
