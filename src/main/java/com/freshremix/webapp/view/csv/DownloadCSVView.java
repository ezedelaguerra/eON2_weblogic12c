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
 * May 20, 2010		Jovino Balunan		
 */
package com.freshremix.webapp.view.csv;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import com.freshremix.util.StringUtil;

/**
 * @author Jovino Balunan
 *
 */
public class DownloadCSVView extends AbstractView {

	@SuppressWarnings("unchecked")
	@Override
	protected void renderMergedOutputModel(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String body = (String) model.get("list");
		String startdate = (String) model.get("startdate");
		String enddate = (String) model.get("enddate");
		String category = (String) model.get("category");
		String sheetname = (String) model.get("sheettype");
		
		String filename = this.getFinalName(sheetname, category, startdate, enddate);
		OutputStreamWriter outputStream = new OutputStreamWriter(response.getOutputStream(), "SJIS");
		PrintWriter out = new PrintWriter(outputStream, true);
		processResponse(response, filename);
		if (!StringUtil.isNullOrEmpty(body))
			out.println(body);		
		
	}
	
	private String getFinalName(String SheetName, String categoryname, String startDate, String endDate) {
		return SheetName + "_" + categoryname + "_" + startDate + "_" + endDate;
	}
	
	private void processResponse(HttpServletResponse response, String filename) {
		response.setContentType("text/csv");
		response.setHeader("Content-disposition", "attachment; filename=" + filename + ".csv");
	}

}
