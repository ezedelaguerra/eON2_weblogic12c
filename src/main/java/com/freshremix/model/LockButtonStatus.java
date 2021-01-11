package com.freshremix.model;

import java.math.BigDecimal;

/**
 * @author raquino
 *
 */
public class LockButtonStatus {
	
	public static final int ENABLED = 0;
	public static final int DISABLED = 1;
	
	private Integer userId; 
	private Integer lockButtonStatus;

	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getLockButtonStatus() {
		return lockButtonStatus;
	}
	public void setLockButtonStatus(Integer lockButtonStatus) {
		this.lockButtonStatus = lockButtonStatus;
	}
	
}

