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
 * date		name		version		changes
 * ------------------------------------------------------------------------------
 * 20120725	gilwen		v11			Redmine 131 - Change of display in address bar of Comments
 */

package com.freshremix.service;

import java.util.List;

import com.freshremix.exception.CommentsException;
import com.freshremix.model.Company;
import com.freshremix.model.MailDetails;
import com.freshremix.model.User;
import com.freshremix.treegrid.CommentsTreeGridItem;
import com.freshremix.ui.model.EmailFilter;

/**
 * @author gilwen
 *
 */
public interface CommentsService {

	Integer countUnreadMessages(Integer userId);
	List<CommentsTreeGridItem> getInboxMessages(Integer userId);
	List<CommentsTreeGridItem> getInboxMessages(Integer userId, Integer count);
	List<CommentsTreeGridItem> getOutboxMessages(Integer userId);
	List<EmailFilter> getUsersByCompanyId(User user, List<Integer> companyId) throws CommentsException;
	boolean sendEmail(String fromAddress, String toAddress[], String subject, String message);
	//void insertNewMessage(Integer senderId, String senderName, String recipientsId, String recipientsAddress, String receivedDate, String subject, String body, String openStatus, Integer userId, String emailJson, String senderEmailAdd);
	// FORDELETION START 20120725: Lele - Redmine 131
//	void insertNewMessage(Integer senderId, String senderName, String recipientsAddress, String receivedDate, String subject, 
//			String body, String openStatus, String senderEmailAdd,String replyStatus);
	// FORDELETION END 20120725:
	void deleteInboxMessage(Integer id);
	void deleteOutboxMessage(Integer id);
	void updateOpenMailStatus(Integer id);
	boolean validateEmailAddress(String recipients, User user) throws CommentsException;
	List<Company> getAssociatedCompany(User user) throws CommentsException;
	
	// ENHANCEMENT 20120725: Lele - Redmine 131
	void sendMessage(MailDetails mail);
}
