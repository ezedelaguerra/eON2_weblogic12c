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
package com.freshremix.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import com.freshremix.comments.CompanyFetcher;
import com.freshremix.comments.UserFetcher;
import com.freshremix.comments.UserFetcherHelper;
import com.freshremix.dao.CommentsInboxDao;
import com.freshremix.dao.CommentsOutboxDao;
import com.freshremix.dao.CompanyInformationDao;
import com.freshremix.dao.UserDao;
import com.freshremix.exception.CommentsException;
import com.freshremix.model.Company;
import com.freshremix.model.EmailItem;
import com.freshremix.model.MailDetails;
import com.freshremix.model.MailSender;
import com.freshremix.model.User;
import com.freshremix.service.CommentsService;
import com.freshremix.service.DealingPatternService;
import com.freshremix.treegrid.CommentsTreeGridItem;
import com.freshremix.ui.model.EmailFilter;

/**
 * @author gilwen
 *
 */
public class CommentsServiceImpl implements CommentsService {

	private CommentsInboxDao commentsInboxDao;
	private CommentsOutboxDao commentsOutboxDao;
	private UserDao usersInfoDaos;
	private DealingPatternService dealingPatternService;
	private CompanyInformationDao companyInfoDaos;
	
	public void setDealingPatternService(DealingPatternService dealingPatternService) {
		this.dealingPatternService = dealingPatternService;
	}
	
	public void setUsersInfoDaos(UserDao usersInfoDaos) {
		this.usersInfoDaos = usersInfoDaos;
	}
	
	public void setCommentsInboxDao(CommentsInboxDao commentsInboxDao) {
		this.commentsInboxDao = commentsInboxDao;
	}
	
	public void setCommentsOutboxDao(CommentsOutboxDao commentsOutboxDao) {
		this.commentsOutboxDao = commentsOutboxDao;
	}
	
	public void setCompanyInfoDaos(CompanyInformationDao companyInfoDaos) {
		this.companyInfoDaos = companyInfoDaos;
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.CommentsService#countUnreadMessages(java.lang.Integer)
	 */
	@Override
	public Integer countUnreadMessages(Integer userId) {
		return commentsInboxDao.countUnreadMessages(userId);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.CommentsService#getMessages(java.lang.Integer)
	 */
	@Override
	public List<CommentsTreeGridItem> getInboxMessages(Integer userId) {
		List<CommentsTreeGridItem> items = new ArrayList<CommentsTreeGridItem>();
		List<EmailItem> unreadMsg = commentsInboxDao.getUnreadMessages(userId);
		List<EmailItem> readMsg = commentsInboxDao.getReadMessages(userId);
		
		for (EmailItem _unreadMsg : unreadMsg) {
			CommentsTreeGridItem it = new CommentsTreeGridItem();
			it.setId(Long.valueOf(_unreadMsg.getId()));
			it.setCellStyle("text-align:center;font-weight:bold;color:blue;");
			it.addCell(_unreadMsg.getSenderName());
			it.addCell(_unreadMsg.getSubject());
			it.addCell(_unreadMsg.getAddress());
			it.addCell(_unreadMsg.getReceivedDate());
			it.addCell(_unreadMsg.getId());
			it.addCell(_unreadMsg.getMessage());
			it.addCell(_unreadMsg.getSenderId());
			it.addCell(_unreadMsg.getRecipientId());
			items.add(it);
		}
		
		for (EmailItem _readMsg : readMsg) {
			CommentsTreeGridItem it = new CommentsTreeGridItem();
			it.setId(Long.valueOf(_readMsg.getId()));
			it.setCellStyle("text-align:center;");
			it.addCell(_readMsg.getSenderName());
			it.addCell(_readMsg.getSubject());
			it.addCell(_readMsg.getAddress());
			it.addCell(_readMsg.getReceivedDate());
			it.addCell(_readMsg.getId());
			it.addCell(_readMsg.getMessage());
			it.addCell(_readMsg.getSenderId());
			it.addCell(_readMsg.getRecipientId());
			items.add(it);
		}
		
		return items;
	}
	
	/* (non-Javadoc)
	 * @see com.freshremix.service.CommentsService#getMessages(java.lang.Integer)
	 */
	@Override
	public List<CommentsTreeGridItem> getInboxMessages(Integer userId, Integer count) {
		List<CommentsTreeGridItem> items = new ArrayList<CommentsTreeGridItem>();
		
		List<EmailItem> unreadMsg = commentsInboxDao.getUnreadMessages(userId);
		int _count = 0;
//		boolean addMoreLabel = false;
		
		for (EmailItem _unreadMsg : unreadMsg) {
			CommentsTreeGridItem it = new CommentsTreeGridItem();
			it.setId(Long.valueOf(_unreadMsg.getId()));
			it.setCellStyle("text-align:center;font-weight:bold;color:blue;");
			it.addCell(_unreadMsg.getSenderName());
			it.addCell(_unreadMsg.getSubject());
			it.addCell(_unreadMsg.getAddress());
			it.addCell(_unreadMsg.getReceivedDate());
			it.addCell(_unreadMsg.getId());
			it.addCell(_unreadMsg.getMessage());
			it.addCell(_unreadMsg.getSenderId());
			it.addCell(_unreadMsg.getRecipientId());
			items.add(it);
			_count++;
			if (count == _count) {
//				addMoreLabel = true;
				break;
			}
		}
		
		if (_count < count) {
			List<EmailItem> readMsg = commentsInboxDao.getReadMessages(userId);
			for (EmailItem _readMsg : readMsg) {
				CommentsTreeGridItem it = new CommentsTreeGridItem();
				it.setId(Long.valueOf(_readMsg.getId()));
				it.setCellStyle("text-align:center;font-weight:normal;");
				it.addCell(_readMsg.getSenderName());
				it.addCell(_readMsg.getSubject());
				it.addCell(_readMsg.getAddress());
				it.addCell(_readMsg.getReceivedDate());
				it.addCell(_readMsg.getId());
				it.addCell(_readMsg.getMessage());
				it.addCell(_readMsg.getSenderId());
				it.addCell(_readMsg.getRecipientId());
				items.add(it);
				_count++;
				if (count == _count) {
//					addMoreLabel = true;
					break;
				}
			}
		}
		
//		if (addMoreLabel) {
//			CommentsTreeGridItem it = new CommentsTreeGridItem();
//			it.setId(Long.valueOf(999999));
//			it.setCellStyle("text-align:left;font-weight:normal;font-style:italic; border:0");
//			it.addCell("More...");
//			items.add(it);
//		}
		
		return items;
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.CommentsService#getUsersByCompanyId(java.lang.Integer)
	 */
	@Override
	public List<EmailFilter> getUsersByCompanyId(User user, List<Integer> companyId) throws CommentsException {
		
		Set<EmailFilter> userList = new HashSet<EmailFilter>();
		
		Class<?> clazz;
		UserFetcher uf;
		final String userFetcherName = 
			"com.freshremix.comments.UserFetcher" + user.getRole().getRoleId() + "Impl";
		
		try {
			clazz = Class.forName(userFetcherName);
			uf = (UserFetcher) clazz.newInstance();
		} catch (ClassNotFoundException e) {
			throw new CommentsException (e.getMessage());
		} catch (InstantiationException e) {
			throw new CommentsException (e.getMessage());
		} catch (IllegalAccessException e) {
			throw new CommentsException (e.getMessage());
		}
		
		UserFetcherHelper ufh = new UserFetcherHelper(user, companyId, companyInfoDaos);
		List<User> list = uf.getUser(user, ufh, dealingPatternService, usersInfoDaos);
		
		// ENHANCEMENT START 20120725: Lele - Redmine 131
		Map<Integer,String> companyIdNameMap = new HashMap<Integer,String>();
		for (Company company : companyInfoDaos.getCompanyList(companyId)) {
			companyIdNameMap.put(company.getCompanyId(), company.getCompanyName());
		}
		// ENHANCEMENT END 20120725: Lele
		
		for (User _user : list) {
			EmailFilter item = new EmailFilter();
			if (!_user.getUserId().equals(user.getUserId())) {
				item.setId(_user.getUserId().toString());
				item.setCaption(_user.getName());
				item.setPcEmailAddress(_user.getPcEmail());
				item.setMobileAddress(_user.getMobileEmail());
				// ENHANCEMENT START 20120725: Lele - Redmine 131
				item.setCompanyId(_user.getCompany().getCompanyId());
				item.setCompanyName(companyIdNameMap.get(_user.getCompany().getCompanyId()));
				// ENHANCEMENT END 20120725: Lele
				userList.add(item);
			}
		}
		return new ArrayList<EmailFilter>(userList);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.CommentsService#sendEmail(java.lang.String, java.lang.String[], java.lang.String, java.lang.String)
	 */
	@Override
	public boolean sendEmail(String fromAddress, String[] toAddress,
			String subject, String message) {
		
		MailSender ms = new MailSender();
		ms.setFromAddress(fromAddress);
		ms.setMessage(message);
		ms.setSubject(subject);
		ms.setToAddress(toAddress);
		Thread msThread = new Thread(ms);
		msThread.start();
		return ms.isSuccess();
	}

	// FORDELETION START 20120725: Lele - Redmine 131
//	private String[] toStringArray(String toAddress) {
//		StringTokenizer st = new StringTokenizer(toAddress,";");
//		List<String> tmp = new ArrayList<String>();
//		while (st.hasMoreTokens()) {
//			String addr = st.nextToken();
//			tmp.add(addr.trim());
//		}
//		String addrArray[] = new String[tmp.size()];
//		for (int i=0; i<addrArray.length; i++) {
//			addrArray[i] = tmp.get(i);
//		}
//		return addrArray;
//	}
	// FORDELETION END 20120725: 
	
	// ENHANCEMENT START 20120725: Lele - Redmine 131
	private String[] toStringArray (String param) {
		
		StringTokenizer st = new StringTokenizer(param,";");
		List<String> tmp = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			String addr = st.nextToken();
			tmp.add(addr.trim());
		}
		return tmp.toArray(new String[0]);
	}
	// ENHANCEMENT END 20120725: Lele

//	/* (non-Javadoc)
//	 * @see com.freshremix.service.CommentsService#insertNewMessage(java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
//	 */
//	@Override
//	public void insertNewMessage(Integer senderId, String senderName, String recipientsId, String recipientsAddress, 
//			String receivedDate, String subject, String body, String openStatus, Integer userId, 
//			String emailJson, String senderEmailAdd) {
//		String addressList[] = toStringArray(recipientsId);
//		for (int i=0; i<addressList.length; i++) {
//			String recipient = addressList[i];
//			commentsInboxDao.insertNewMessage(senderId, recipient, receivedDate, subject, body, openStatus, userId);
//		}
//		commentsOutboxDao.insertSentMessage(senderId, subject, body, recipientsAddress, receivedDate);
//		
//		// send email notification
//		Serializer serializer = new JsonSerializer();
//		List<Map<String,Object>> param = extracted(emailJson, serializer);
//		
//		for (Map<String,Object> _param : param) {
//			List<String> emails = extracted(_param);
//			String emailsArr[] = new String[emails.size()];
//			int i=0;
//			for (String email : emails) {
//				emailsArr[i] = email;
//				i++;
//			}
//			String mailSubject = "New Comment \"" + subject + "\" to " + recipientsAddress + " from " + senderName + " Sent";
//			String mailContent = "New Comment \"" + subject + "\" sent to " + recipientsAddress + " from " + senderName + " as of " + receivedDate;
//			this.sendEmail(senderEmailAdd, emailsArr, mailSubject, mailContent);
//		}
//	}
//
//	@SuppressWarnings("unchecked")
//	private List<String> extracted(Map<String, Object> _param) {
//		return (List<String>)_param.get("emails");
//	}
//	
//	@SuppressWarnings("unchecked")
//	private List<Map<String, Object>> extracted(String arrayJson,
//			Serializer serializer) {
//		return (List<Map<String,Object>>) serializer.deserialize(arrayJson);
//	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.CommentsService#getOutboxMessages(java.lang.Integer)
	 */
	@Override
	public List<CommentsTreeGridItem> getOutboxMessages(Integer userId) {
		List<EmailItem> msg = commentsOutboxDao.getMessages(userId);
		List<CommentsTreeGridItem> items = new ArrayList<CommentsTreeGridItem>();
		
		for (EmailItem _msg : msg) {
			CommentsTreeGridItem it = new CommentsTreeGridItem();
			it.setId(Long.valueOf(_msg.getId()));
			it.setCellStyle("text-align:center;");
			it.addCell(_msg.getRecipientsAddress());
			it.addCell(_msg.getSubject());
			it.addCell(_msg.getAddress());
			it.addCell(_msg.getReceivedDate());
			it.addCell(_msg.getId());
			it.addCell(_msg.getMessage());
			items.add(it);
		}
		
		return items;
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.CommentsService#deleteInboxMessage(java.lang.Integer)
	 */
	@Override
	public void deleteInboxMessage(Integer id) {
		commentsInboxDao.deleteMessage(id);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.CommentsService#deleteOutboxMessage(java.lang.Integer)
	 */
	@Override
	public void deleteOutboxMessage(Integer id) {
		commentsOutboxDao.deleteMessage(id);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.CommentsService#updateOpenMailStatus(java.lang.Integer)
	 */
	@Override
	public void updateOpenMailStatus(Integer id) {
		commentsInboxDao.updateOpenMailStatus(id);
	}

	// FORDELETION START 20120725: Lele - Redmine 131
//	public void insertNewMessage(Integer senderId, String senderName,
//			String recipientsAddress, String receivedDate, String subject,
//			String body, String openStatus, String senderEmailAdd,String replyStatus) {
//		String addressList[] = toStringArray(recipientsAddress);
//		List<Integer> addressId = getUserId(addressList);
//		for (Integer id : addressId) {
//			commentsInboxDao.insertNewMessage(senderId, id.toString(), null, subject, body, openStatus, senderId);
//		}
//		commentsOutboxDao.insertSentMessage(senderId, subject, body, recipientsAddress, null);
//		sendEmailNotification(senderName, senderEmailAdd, subject, recipientsAddress, receivedDate,replyStatus);
//	}
	// FORDELETION END 20120725:
	
	// ENHANCEMENT START 20120725: Lele - Redmine 131
	public void sendMessage(MailDetails mail) {
		String emailAdd[] = null;
		String id[] = toStringArray(mail.getToId());
		
		for (int i=0; i<id.length; i++) {
			commentsInboxDao.insertNewMessage(mail.getUserId(), id[i], null, mail.getSubject(), mail.getMessage(), mail.getOpenStatus(), mail.getUserId());
		}
		commentsOutboxDao.insertSentMessage(mail.getUserId(), mail.getSubject(), mail.getMessage(), mail.getToAddress(), null);
		
		// get email add of sender
		if(mail.getIsReply().equals("1")) {
			User sender = usersInfoDaos.getUserById(Integer.parseInt(id[0]));
			emailAdd = new String[] { sender.getPcEmail() };
		} else {
			emailAdd = toStringArray(mail.getToEmail());
		}
		
		sendEmailNotification(mail.getUserName(), mail.getFromAddress(), mail.getSubject(), emailAdd, mail.getReceivedDate(), mail.getIsReply());
	}
	// ENHANCEMENT END 20120725:
	
	// FORDELETION START 20120725: Lele - Redmine 131
//	private List<Integer> getUserId (String[] addressList) {
//		List<Integer> idList = new ArrayList<Integer>();
//		for (int i=0; i<addressList.length; i++) {
//			String recipient = addressList[i];
//			if (recipient.indexOf("@") == -1) {
//				User user = usersInfoDaos.getUserByName(recipient);
//				idList.add(user.getUserId());
//			} else {
//				List<User> user = usersInfoDaos.getUserByEmail(recipient);
//				if (user != null)
//					for (User _user : user)	
//						idList.add(_user.getUserId());
//			}
//		}
//		return idList;
//	}
	// FORDELETION END 20120725:
	
	private boolean getName (String[] addressList, List<String> list) {
		for (int i=0; i<addressList.length; i++) {
			String recipient = addressList[i];
			if (recipient.indexOf("@") == -1) {
				User user = usersInfoDaos.getUserByName(recipient);
				if (user != null)
					list.add(user.getName());
				else 
					return false;
			} else {
				List<User> user = usersInfoDaos.getUserByEmail(recipient);
				if (user != null)
					for (User _user : user)	
						list.add(_user.getName());
				else 
					return false;
			}
		}
		return true;
	}

	// FORDELETION START 20120725: Lele - Redmine 131
//	private void sendEmailNotification(String senderName, String senderEmailAdd, String subject, 
//			String recipientsAddress, String receivedDate,String replyStatus) {
//		
//		StringTokenizer st = new StringTokenizer(recipientsAddress,";");
//		while(st.hasMoreTokens()) {
//			String recipient = st.nextToken();
//			String emailsArr[] = null;
//			if (recipient.indexOf("@") == -1) {
//				//get email add
//				User user = usersInfoDaos.getUserByName(recipient.trim());
//				List<String> emailList = new ArrayList<String>();
//				if (!StringUtil.isNullOrEmpty(user.getMobileEmail())) emailList.add(user.getMobileEmail());
//				if (!StringUtil.isNullOrEmpty(user.getPcEmail())) emailList.add(user.getPcEmail());
//				emailsArr = new String[emailList.size()];
//				for (int i=0; i<emailList.size(); i++) {
//					emailsArr[i] = emailList.get(i);
//				}
//			} else {
//				emailsArr = new String[1];
//				emailsArr[0] = recipient;
//			}
//			
//			String header = "New Comment";
//			
//			if(replyStatus.equals("1")){
//				header = "Reply to Comment";
//			}
//			
//			String mailSubject = header + "\"" + subject + "\" to " + recipientsAddress + " from " + senderName + " Sent";
//			String mailContent = header + "\"" + subject + "\" sent to " + recipientsAddress + " from " + senderName + " as of " + receivedDate;
//			
//			this.sendEmail(senderEmailAdd, emailsArr, mailSubject, mailContent);
//		}
//	}
	// FORDELETION END 20120725: Lele - Redmine 131
	
	// ENHANCEMENT START 20120725: Lele - Redmine 131
	private void sendEmailNotification(String senderName, String senderEmailAdd, String subject, 
			String[] recipientsAddress, String receivedDate,String replyStatus) {
		
		for (String recipient : recipientsAddress) {
			String emailsArr[] = {recipient};
			String header = "New Comment";
			
			if(replyStatus.equals("1")){
				header = "Reply to Comment";
			}
			
			String mailSubject = header + "\"" + subject + "\" to " + recipientsAddress + " from " + senderName + " Sent";
			String mailContent = header + "\"" + subject + "\" sent to " + recipientsAddress + " from " + senderName + " as of " + receivedDate;
			
			this.sendEmail(senderEmailAdd, emailsArr, mailSubject, mailContent);
		}
	}
	// ENHANCEMENT END 20120725: 

	/* (non-Javadoc)
	 * @see com.freshremix.service.CommentsService#validateEmailAddress(java.lang.String)
	 */
	@Override
	public boolean validateEmailAddress(String recipients, User user) throws CommentsException {
		String userCompanyId = user.getCompany().getCompanyId().toString();
		List<Company> ids = this.getAssociatedCompany(user);
		
		List<Integer> companyId = new ArrayList<Integer>();
		for (Company _company : ids) {
			Integer id = _company.getCompanyId();
			companyId.add(id);
		}
		companyId.add(Integer.valueOf(userCompanyId));
		List<EmailFilter> tmp = getUsersByCompanyId(user, companyId);
		List<String> validNamesList = new ArrayList<String>();
		for (EmailFilter ef : tmp) {
			validNamesList.add(ef.getCaption());
		}
		
		boolean isValid = true;
		String addressList[] = toStringArray(recipients);
		List<String> list = new ArrayList<String>();
		isValid = getName(addressList,list);
		
		if (isValid) {
			for (String _name : list) {
				if (!validNamesList.contains(_name)) {
					isValid = false;
					break;
				}
			}
		}
		
		return isValid;
	}

	@Override
	public List<Company> getAssociatedCompany(User user) throws CommentsException {
		
		Class<?> clazz;
		CompanyFetcher cf;
		final String companyFetcherName = 
			"com.freshremix.comments.CompanyFetcher" + user.getRole().getRoleId() + "Impl";
		
		try {
			clazz = Class.forName(companyFetcherName);
			cf = (CompanyFetcher) clazz.newInstance();
		} catch (ClassNotFoundException e) {
			throw new CommentsException (e.getMessage());
		} catch (InstantiationException e) {
			throw new CommentsException (e.getMessage());
		} catch (IllegalAccessException e) {
			throw new CommentsException (e.getMessage());
		}
		
		return cf.getCompanyFetcher(user, dealingPatternService);
		
	}
}