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

package com.freshremix.webapp.controller.comments;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.MailDetails;
import com.freshremix.model.User;
import com.freshremix.service.CommentsService;
import com.freshremix.util.DateFormatter;
import com.freshremix.util.SessionHelper;

public class SendEmailController extends SimpleFormController {

	private CommentsService commentsService;
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		
		ModelAndView mav = new ModelAndView("json");
		User user = (User) SessionHelper.getAttribute(request, SessionParamConstants.USER_PARAM);
		
		String fromAddress = user.getPcEmail();
		String toAddress = request.getParameter("toAddress");
		String subject = request.getParameter("subject");
		String message = request.getParameter("message");
		String isReply = request.getParameter("replyStatus");
		
		// FORDELETION START 20120725: Lele - Redmine 131
//		boolean isSuccess = commentsService.validateEmailAddress(toAddress, user);
//		
//		if (isSuccess)
//			commentsService.insertNewMessage(user.getUserId(), user.getName(), toAddress,
//								DateFormatter.getDateToday("yyyyMMdd hh:mm aaa"), subject, message, "0", fromAddress,isReply);
//		else {
//			errors.addError(new ObjectError("recipientAddress", "Invalid name found"));
//			return super.onSubmit(command, errors);
//		}
		// FORDELETION END 20120725:
		
		// ENHANCEMENT START 20120725: Lele - Redmine 131
		String toEmail = request.getParameter("toEmail");
		String toId = request.getParameter("toId");
		
		MailDetails mail = new MailDetails();
		mail.setUserId(user.getUserId());
		mail.setUserName(user.getName());
		mail.setToAddress(toAddress);
		mail.setToEmail(toEmail);
		mail.setToId(toId);
		mail.setReceivedDate(DateFormatter.getDateToday("yyyyMMdd hh:mm aaa"));
		mail.setSubject(subject);
		mail.setMessage(message);
		mail.setOpenStatus("0");
		mail.setFromAddress(fromAddress);
		mail.setIsReply(isReply);
		
		commentsService.sendMessage(mail);
		// ENHANCEMENT END 20120725: 
		
		return mav;
	}

	public void setCommentsService(CommentsService commentsService) {
		this.commentsService = commentsService;
	}
}