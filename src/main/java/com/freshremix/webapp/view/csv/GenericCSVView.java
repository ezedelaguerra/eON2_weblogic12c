package com.freshremix.webapp.view.csv;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.util.FileCopyUtils;
//import org.apache.commons.io.FileUtils;
import org.springframework.web.servlet.view.AbstractView;

public class GenericCSVView extends AbstractView {
	private static final Logger LOGGER = Logger.getLogger(GenericCSVView.class);

	@Override
	protected void renderMergedOutputModel(Map model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LOGGER.info("Downloading csv...started");

		String filePath = (String) model.get("filePath");
		String fileName = (String) model.get("fileName");
		
		
		InputStream in = null;
		File file = null;
		try {
			
			file = new File(filePath );
			in = new FileInputStream(file);
		} catch (Exception e) {
			LOGGER.error("Downloading csv error : " + e.getMessage());
			fileName="error.txt";
			
			in = new ByteArrayInputStream(ExceptionUtils.getStackTrace(e).getBytes());
		}

		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ fileName + "\"");

		FileCopyUtils.copy(in, response.getOutputStream());

		response.flushBuffer();
		in.close();
		FileUtils.deleteQuietly(file);
		LOGGER.info("Downloading csv...end");
	}

}
