package com.freshremix.dao.impl;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.freshremix.constants.AuditTrailConstants;
import com.freshremix.dao.AuditTrailDao;
import com.freshremix.model.AuditTrail;

public class AuditTrailDaoImpl extends SqlMapClientDaoSupport implements
		AuditTrailDao {

	@Override
	public long insertAudit(AuditTrail audit) {
		long processId = -1;

		if (audit.getLog_type().equals(AuditTrailConstants.AUDIT_TRAIL_START)) {
			processId = (Long) getSqlMapClientTemplate().insert("AuditTrail.insertAuditStart", audit);
		} else {
			getSqlMapClientTemplate().insert("AuditTrail.insertAuditFinish",audit);
		}

		return processId;
	}
}
