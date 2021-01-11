package com.freshremix.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.mail.SimpleMailMessage;

import com.freshremix.dao.ForgotPasswordLinkDao;
import com.freshremix.dao.LoginDao;
import com.freshremix.exception.ServiceException;
import com.freshremix.model.LoginInquiry;
import com.freshremix.model.User;
import com.freshremix.service.LoginService;
import com.freshremix.ui.model.EONLocale;
import com.freshremix.util.MailSenderUtil;
import com.freshremix.util.RolesUtil;

public class LoginServiceImpl implements LoginService {
	private LoginDao loginDao;
	
	private ForgotPasswordLinkDao forgotPasswordLinkDao;
	private EONLocale eonLocale;
	private static final String MESSAGE_ERROR_MISSING_USERNAME_PASSWORD = "login.required.username.password";
	private static final String MESSAGE_ERROR_INVALID_USERNAME_PASSWORD= "login.invalid.username.password";
	public void setEonLocale(EONLocale eonLocale) {
		this.eonLocale = eonLocale;
	}

	public void setForgotPasswordLinkDao(ForgotPasswordLinkDao forgotPasswordLinkDao) {
		this.forgotPasswordLinkDao = forgotPasswordLinkDao;
	}

	public void setLoginDao(LoginDao loginDao) {
		this.loginDao = loginDao;
	}

	private SimpleMailMessage messageForgotPassword;
	private SimpleMailMessage messageInquiry;

	public void setMessageInquiry(SimpleMailMessage messageInquiry) {
		this.messageInquiry = messageInquiry;
	}

	public void setMessageForgotPassword(SimpleMailMessage messageForgotPassword) {
		this.messageForgotPassword = messageForgotPassword;
	}

	@Override
	public User getUserByUsernameAndPassword(String username, String password) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("userName", username);
		param.put("password", password);

		return loginDao.getUserByUsernameAndPassword(param);
	}

	@Override
	public boolean validateUsernameAndPassword(String username, String password) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("userName", username);
		param.put("password", password);

		Integer count = loginDao.validateUsernameAndPassword(param);
		boolean isValid = false;
		if (count == 0)
			isValid = false;
		if (count == 1)
			isValid = true;

		return isValid;
	}

	@Override
	public User getUserByUsernameAndEmail(String username, String email) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("userName", username);
		param.put("email", email);

		return loginDao.getUserByUsernameAndEmail(param);
	}

	@Override
	public boolean validateUsernameAndEmail(String username, String email) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("userName", username);
		param.put("email", email);

		Integer count = loginDao.validateUsernameAndEmail(param);
		boolean isValid = false;
		if (count == 0)
			isValid = false;
		if (count == 1)
			isValid = true;

		return isValid;
	}

	@Override
	public void insertNonMemberInquiry(LoginInquiry nonMemberInquiry) {
		loginDao.insertNonMemberInquiry(nonMemberInquiry);

	}

	@Override
	public boolean sendMailForgotPassword(final User user,String hostName) {
		
		System.out.println("Start email");
		
		//messageForgotPassword.getTo()[0] is the email address of an assigned eON Admin
		//messageForgotPassword.getFrom() is the eON Application email account
		
//		String msgBodyUser = "\tHi " + user.getUserName() + ",\n\n"
//				+ "\tThank you for using EON System. Your password is "
//				+ user.getPassword() + ".";
		//forgotPasswordLinkDao
		
		
		Integer validationCode = forgotPasswordLinkDao.insertValidationId(user.getUserId());
		
		
		String msgBodyUser = "\tHi " + user.getUserName() + ",\n\n"
		+ "\tThank you for using EON System. Please click on the link below to change your password" + ",\n\n"
		+"\t"+ hostName +"/changePassword.do?"
		+"j_userId="+user.getUserId()+"&v_id="+validationCode;
		
		//Mail sending from eON admin to user who forgot his password
		//String emailAddAdmin = messageForgotPassword.getTo()[0];
		boolean isMailUserSent = MailSenderUtil.sendMail(messageForgotPassword
				.getSubject(), msgBodyUser, null,
				user.getPcEmail());

		String statusMailUser;
		if (isMailUserSent) {
			statusMailUser = "successfully";
		} else {
			statusMailUser = "failed";
		}

		String msgBodyAdmin = "\t" + user.getUserName()
				+ " forgot his/her password. Forgot password email sent "
				+ statusMailUser + " to the user.\n\n" + "\tThank you.";
		
		//Mail sending from eON app to admin notifying email send status to user
		String[] to = new String[1];
		to[0] = loginDao.getKenEmailAdd();
		messageForgotPassword.setTo(to);
		System.out.println("messageForgotPassword.getTo()-->" + messageForgotPassword.getTo()[0]);
		boolean isMailSent = MailSenderUtil.sendMail(messageForgotPassword
				.getSubject(), msgBodyAdmin, null,
				messageForgotPassword.getTo(), null);
		System.out.println("End email");
		return isMailSent;
	}

	@Override
	public User getUserByUsername(String username) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("userName", username);

		return loginDao.getUserByUsername(param);
	}

	@Override
	public boolean sendMailNonMemberInquiry(LoginInquiry nonMemberInquiry) {
		System.out.println("Sending Email Inquiry START....");
		String inquiryItems = "";
		if (nonMemberInquiry.getInquireEon().equals("1"))
			inquiryItems = "eON";
		if (nonMemberInquiry.getInquireNsystem().equals("1")) {
			if (inquiryItems.isEmpty())
				inquiryItems = "N-System";
			else
				inquiryItems = inquiryItems + ", N-System";
		}
		if (nonMemberInquiry.getInquireOthers().equals("1")) {
			if (inquiryItems.isEmpty())
				inquiryItems = "Others";
			else
				inquiryItems = inquiryItems + ", Others";
		}
		String inquiryDtls = "";
		if (nonMemberInquiry.getApplyDetails().equals("1"))
			inquiryDtls = "Wish to apply";
		if (nonMemberInquiry.getDocsDetails().equals("1")) {
			if (inquiryDtls.isEmpty())
				inquiryDtls = "Request for docs";
			else
				inquiryDtls = inquiryDtls + ", Request for docs";
		}
		if (nonMemberInquiry.getExplainDetails().equals("1")) {
			if (inquiryDtls.isEmpty())
				inquiryDtls = "Wish for detailed explanation";
			else
				inquiryDtls = inquiryDtls + ", Wish for detailed explanation";
		}
		if (nonMemberInquiry.getOtherDetails().equals("1")) {
			if (inquiryDtls.isEmpty())
				inquiryDtls = "Others";
			else
				inquiryDtls = inquiryDtls + ", Others";
		}
		String body = "\tContents of Inquiry\n\n" + "\tInquiry Items      : "
				+ inquiryItems + "\n" + "\tDetails of Inquiry : " + inquiryDtls
				+ "\n\n\n" + "\tContact Details of Customer\n\n"
				+ "\tName               : " + nonMemberInquiry.getSurname()
				+ " " + nonMemberInquiry.getFirstname() + "\n"
				+ "\tFurigana           : "
				+ nonMemberInquiry.getFuriganaSurname() + " "
				+ nonMemberInquiry.getFuriganaFirstname() + "\n"
				+ "\tCompany Name       : " + nonMemberInquiry.getCompanyName()
				+ "\n" + "\tStore Name         : "
				+ nonMemberInquiry.getStoreName() + "\n"
				+ "\tDepartment Name    : " + nonMemberInquiry.getDeptName()
				+ "\n" + "\tTelephone Number   : "
				+ nonMemberInquiry.getContactNo() + "\n"
				+ "\tMobile Number      : " + nonMemberInquiry.getMobileNo()
				+ "\n" + "\tEmail Address      : "
				+ nonMemberInquiry.getEmail() + "\n"
				+ "\tZip Code           : " + nonMemberInquiry.getZipcode()
				+ "\n" + "\tAddress            : "
				+ nonMemberInquiry.getAddress() + "\n";
		
		String[] to = new String[1];
		to[0] = loginDao.getKenEmailAdd();
		messageInquiry.setTo(to);
		
		boolean sentStatus = MailSenderUtil.sendMail(messageInquiry
				.getSubject(), body, nonMemberInquiry.getEmail(),
				messageInquiry.getTo(), null);
		System.out.println("Sending Email Inquiry END....");
		return sentStatus;
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.LoginService#updateUserLastLoginDate(com.freshremix.model.User)
	 */
	@Override
	public void updateUserLastLoginDate(String username) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("userName", username);
		loginDao.updateUserLastLoginDate(param);
	}

	@Override
	public User getUserByUserId(Integer userId) {
		return loginDao.getUserByUserId(userId);
	}
	
	@Override
	public String getCityByZip(Integer zipCd) {
		return loginDao.getCityByZip(zipCd);
	}

	@Override
	public void validateAdminUser(String username, String password) throws ServiceException
	{
		validateUsername(username);
		validatePassword(password);
		User user = getUserByUsernameAndPassword(username, password);
		if(user==null){
			throw new ServiceException(MESSAGE_ERROR_INVALID_USERNAME_PASSWORD);
		}
			
		if(!RolesUtil.iseONAdmin(user.getRole().getRoleId())){
			throw new ServiceException(MESSAGE_ERROR_INVALID_USERNAME_PASSWORD);
		}
		
			
	}
	
	private void validateUsername(String username) throws ServiceException{
		if(StringUtils.isBlank(username)){
			throw new ServiceException(
					MESSAGE_ERROR_MISSING_USERNAME_PASSWORD);
		}
		
	}
	private void validatePassword(String password) throws ServiceException{
		if(StringUtils.isBlank(password)){
			throw new ServiceException(
					MESSAGE_ERROR_MISSING_USERNAME_PASSWORD);
		}
		
	}
}
