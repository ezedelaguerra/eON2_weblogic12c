package com.freshremix.webapp.controller.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.User;

public class LoginSessionController  implements Controller {

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		if(session!=null){
			User user = (User)session.getAttribute(SessionParamConstants.USER_PARAM);
			if(user!=null){
				return new ModelAndView("redirect:/app/login/jsp/sessionError.jsp",null); 
			}
			
		}
		request.getRequestDispatcher("/j_spring_security_check").forward(request, response);

        return null;
	}
}
