package com.freshremix.service.impl;

import com.freshremix.constants.AuditTrailConstants;
import com.freshremix.dao.AuditTrailDao;
import com.freshremix.model.AuditTrail;
import com.freshremix.service.AuditTrailService;

public class AuditTrailServiceImpl implements AuditTrailService {
	
	private AuditTrailDao auditTrailDao;

	@Override
	public long insertAudit(AuditTrail audit) {
		if(audit.getLog_type().equals(AuditTrailConstants.AUDIT_TRAIL_START)){
			audit.setProcess_id(0);
		}
		long processId = auditTrailDao.insertAudit(audit);
		
		return processId;
	}

	public AuditTrailDao getAuditTrailDao() {
		return auditTrailDao;
	}

	public void setAuditTrailDao(AuditTrailDao auditTrailDao) {
		this.auditTrailDao = auditTrailDao;
	}

}
