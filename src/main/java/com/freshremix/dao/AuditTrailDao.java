package com.freshremix.dao;

import com.freshremix.model.AuditTrail;

public interface AuditTrailDao {
	
	public long insertAudit(AuditTrail audit);

}
