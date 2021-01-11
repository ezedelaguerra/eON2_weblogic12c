package com.freshremix.webapp.controller.admin;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class GetAnnouncementController extends SimpleFormController {

	public static final String FILE_PATH_GENERAL = "C:\\eon\\config\\announcement.properties";
	public static final String FILE_PATH_USER = "C:\\eon\\config\\userAnnouncement.properties";
	public static final String GENERAL = "generalAnnouncement";
	public static final String USER = "userAnnouncement";

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ModelAndView mav = new ModelAndView("json");
		String announcementType = request.getParameter("announcementType");
		String announcement = "";

		if(announcementType.equalsIgnoreCase(GENERAL)){
			announcement = this.readToFile(FILE_PATH_GENERAL);
		} else if (announcementType.equalsIgnoreCase(USER)){
			announcement = this.readToFile(FILE_PATH_USER);
		}

		mav.addObject("announcement", announcement);
		
		return mav;

	}


	public String readToFile(String filepath){
		String line = "";
		BufferedReader bufferedReader = null;
		// Read the content from file
		try{
			bufferedReader = new BufferedReader(new FileReader(filepath));
			line = bufferedReader.readLine();
		} catch (FileNotFoundException e) {
			line = "Properties file not found. Please contact customer support.";
		} catch (IOException e) {
			line = "Please contact customer support.";
		}

		return line;
	}

}
