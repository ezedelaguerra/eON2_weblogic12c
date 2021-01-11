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
package com.freshremix.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.freshremix.dao.CommentsInboxDao;
import com.freshremix.model.EmailItem;

/**
 * @author gilwen
 *
 */
public class CommentsInboxDaoImpl extends SqlMapClientDaoSupport implements CommentsInboxDao {

	/* (non-Javadoc)
	 * @see com.freshremix.dao.CommentsInboxDao#countUnreadMessages(java.lang.Integer)
	 */
	@Override
	public Integer countUnreadMessages(Integer userId) {
		return (Integer) getSqlMapClientTemplate().queryForObject("CommentsInbox.countUnreadMessages", userId);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.CommentsInboxDao#getReadMessages(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<EmailItem> getReadMessages(Integer userId) {
		return getSqlMapClientTemplate().queryForList("CommentsInbox.selectReadMessages", userId);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.CommentsInboxDao#getUnreadMessages(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<EmailItem> getUnreadMessages(Integer userId) {
		return getSqlMapClientTemplate().queryForList("CommentsInbox.selectUnreadMessages", userId);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.CommentsInboxDao#insertNewMessage(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void insertNewMessage(Integer senderId, String recipientId,
			String receivedDate, String subject, String body, String openStatus, Integer userId) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("senderId", senderId);
		param.put("recipientId", recipientId);
		param.put("receivedDate", receivedDate);
		param.put("subject", subject);
		param.put("message", body);
		param.put("openStatus", openStatus);
		param.put("userId", userId);
		getSqlMapClientTemplate().insert("CommentsInbox.insertNewMessage", param);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.CommentsInboxDao#deleteMessage(java.lang.Integer)
	 */
	@Override
	public void deleteMessage(Integer id) {
		getSqlMapClientTemplate().delete("CommentsInbox.deleteMessage", id);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.CommentsInboxDao#updateOpenMailStatus(java.lang.Integer)
	 */
	@Override
	public void updateOpenMailStatus(Integer id) {
		getSqlMapClientTemplate().update("CommentsInbox.updateOpenStatus", id);
	}
}
