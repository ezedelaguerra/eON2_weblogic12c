package com.freshremix.webapp.controller.login;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.model.LoginInquiry;
import com.freshremix.service.LoginService;
import com.freshremix.ui.model.EONLocale;
import com.freshremix.util.StringUtil;

public class LoginInquiryController extends SimpleFormController {

	private LoginService loginService;
	private EONLocale eonLocale;

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	public void setEonLocale(EONLocale eonLocale) {
		this.eonLocale = eonLocale;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		LoginInquiry nonMemberInquiry = new LoginInquiry();
		String message = null;
		String getAddress = request.getParameter("hidden");		

		String eon = request.getParameter("chkInqEON");
		String nsystem = request.getParameter("chkInqNSystem");
		String inqOthers = request.getParameter("chkInqOthers");
		String apply = request.getParameter("chkDtlWishApply");
		String docs = request.getParameter("chkDtlReqDocs");
		String exp = request.getParameter("chkDtlWishDtlExp");
		String dtlOthers = request.getParameter("chkDtlOthers");
		String txttelno = request.getParameter("txttelno");	
		String telno = txttelno.replaceAll("-", "");
		String txtmobno = request.getParameter("txtmobno");
		String mobno = txtmobno.replaceAll("-", "");
		String newAddress = request.getParameter("address");;
		
		if (eon == null)
			eon = "0";
		if (nsystem == null)
			nsystem = "0";
		if (inqOthers == null)
			inqOthers = "0";
		if (apply == null)
			apply = "0";
		if (docs == null)
			docs = "0";
		if (exp == null)
			exp = "0";
		if (dtlOthers == null)
			dtlOthers = "0";		
		
		nonMemberInquiry.setInquireEon(eon);
		nonMemberInquiry.setInquireNsystem(nsystem);
		nonMemberInquiry.setInquireOthers(inqOthers);
		nonMemberInquiry.setApplyDetails(apply);
		nonMemberInquiry.setDocsDetails(docs);
		nonMemberInquiry.setExplainDetails(exp);
		nonMemberInquiry.setOtherDetails(dtlOthers);
		
		nonMemberInquiry.setSurname(request.getParameter("txtSurname"));
		nonMemberInquiry.setFirstname(request.getParameter("txtFirstname"));
		nonMemberInquiry.setFuriganaFirstname(request
				.getParameter("txtFurFirstname"));
		nonMemberInquiry.setFuriganaSurname(request
				.getParameter("txtFurSurname"));
		nonMemberInquiry.setCompanyName(request.getParameter("txtCompName"));
		nonMemberInquiry.setStoreName(request.getParameter("txtStoreName"));
		nonMemberInquiry.setDeptName(request.getParameter("txtDeptName"));
		String zipcode = request.getParameter("txtZipCode");
		nonMemberInquiry.setZipcode(Integer.parseInt(zipcode));
		nonMemberInquiry.setEmail(request.getParameter("txtEmail"));
		Long longTelNo = null;			
		if (!StringUtil.isNullOrEmpty(telno))
			longTelNo = Long.parseLong(telno);
		nonMemberInquiry.setContactNo(longTelNo);

		Long longMobNo = null;			
		if (!StringUtil.isNullOrEmpty(mobno))
			longMobNo = Long.parseLong(mobno);			
		nonMemberInquiry.setMobileNo(longMobNo);

		if (getAddress.equals("true")|| newAddress == null || newAddress.isEmpty()) {
			// use ZIPCODE to search the ADDRESS			
			newAddress = loginService.getCityByZip(Integer.parseInt(zipcode));
			if (newAddress == null || newAddress.isEmpty()) {
				message = StringUtil.toUTF8String(getMessageSourceAccessor().getMessage(
						"login.nonmember.zipcode.error", eonLocale.getLocale()));
			}
			nonMemberInquiry.setAddress(newAddress);
			

		} else {	
			
			nonMemberInquiry.setAddress(newAddress);
			//nonMemberInquiry.setContactNo(Long.parseLong(telno));
	
			boolean isSuccess = false;
			System.out.println(nonMemberInquiry.toString());
			loginService.insertNonMemberInquiry(nonMemberInquiry);
			isSuccess = loginService
					.sendMailNonMemberInquiry(nonMemberInquiry);
			if (isSuccess) {
				message = StringUtil.toUTF8String(getMessageSourceAccessor().getMessage(
						"mail.sending.success", eonLocale.getLocale()));
				System.out.println(message);
			} else {
				message = StringUtil.toUTF8String(getMessageSourceAccessor().getMessage(
						"mail.sending.error", eonLocale.getLocale()));
				System.out.println(message);
			}
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("response", message);
		model.put("inqObj", nonMemberInquiry);
		model.put("eon", eon);
		model.put("nsystem", nsystem);
		model.put("inqOthers", inqOthers);
		model.put("apply", apply);
		model.put("docs", docs);
		model.put("exp", exp);
		model.put("dtlOthers", dtlOthers);
		model.put("address", newAddress);
		model.put("txttelno", txttelno);
		model.put("txtmobno", txtmobno);

		return new ModelAndView("login/jsp/inquiry", model);
	}

}