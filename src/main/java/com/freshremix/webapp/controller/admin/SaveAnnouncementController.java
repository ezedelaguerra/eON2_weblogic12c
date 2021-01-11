package com.freshremix.webapp.controller.admin;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SaveAnnouncementController extends SimpleFormController {

	public static final String FILE_PATH_GENERAL = "C:\\eon\\config\\announcement.properties";
	public static final String FILE_PATH_USER = "C:\\eon\\config\\userAnnouncement.properties";
	public static final String GENERAL = "generalAnnouncement";
	public static final String USER = "userAnnouncement";

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ModelAndView mav = new ModelAndView("json");
		String announcementType = request.getParameter("announcementType");
		String announcementStr = request.getParameter("announcementStr");

		if(announcementType.equalsIgnoreCase(GENERAL)){
			this.writeToFile(FILE_PATH_GENERAL, announcementStr);
		} else if (announcementType.equalsIgnoreCase(USER)){
			this.writeToFile(FILE_PATH_USER, announcementStr);
		}

		Map<String,Object> model = new HashMap<String,Object>();
		mav.addObject("announcement", model);
		
		return mav;

	}

	public void writeToFile(String filepath, String fileContent){
		// Write the content in file
		BufferedWriter bufferedWriter = null;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(filepath));
			bufferedWriter.write(fileContent);
			bufferedWriter.flush();
			bufferedWriter.close();
		} catch (IOException e) {
			// Exception handling
		}
	}

}
