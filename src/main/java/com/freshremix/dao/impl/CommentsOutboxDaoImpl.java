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
 * Apr 21, 2010		gilwen		
 */
package com.freshremix.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.freshremix.dao.CommentsOutboxDao;
import com.freshremix.model.EmailItem;

/**
 * @author gilwen
 *
 */
public class CommentsOutboxDaoImpl extends SqlMapClientDaoSupport implements CommentsOutboxDao {

	/* (non-Javadoc)
	 * @see com.freshremix.dao.CommentsOutboxDao#getReadMessages(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<EmailItem> getMessages(Integer userId) {
		return getSqlMapClientTemplate().queryForList("CommentsOutbox.selectMessages", userId);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.CommentsOutboxDao#insertSentMessage(java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void insertSentMessage(Integer senderId, String subject,
			String body, String recipientsAddress, String dateSent) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("senderId", senderId);
		param.put("subject", subject);
		param.put("body", body);
		param.put("recipientsAddress", recipientsAddress);
		param.put("dateSent", dateSent);
		getSqlMapClientTemplate().insert("CommentsOutbox.insertNewOutboxMsg", param);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.CommentsOutboxDao#deleteMessage(java.lang.Integer)
	 */
	@Override
	public void deleteMessage(Integer id) {
		getSqlMapClientTemplate().delete("CommentsOutbox.deleteMessage", id);
	}

}
