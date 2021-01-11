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
package com.freshremix.dao;

import java.util.List;

import com.freshremix.model.EmailItem;

/**
 * @author gilwen
 *
 */
public interface CommentsInboxDao {

	Integer countUnreadMessages(Integer userId);
	List<EmailItem> getUnreadMessages(Integer userId);
	List<EmailItem> getReadMessages(Integer userId);
	void insertNewMessage(Integer senderId, String recipientId, String receivedDate, String subject, String body, String openStatus, Integer userId);
	void deleteMessage(Integer id);
	void updateOpenMailStatus(Integer id);
}
