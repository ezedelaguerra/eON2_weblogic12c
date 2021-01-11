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
 * Jun 22, 2010		Jovino Balunan		
 */
package com.freshremix.webapp.view.csv;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import au.com.bytecode.opencsv.CSVWriter;

/**
 * @author Jovino Balunan
 *
 */
public class DownloadCSVCompanyInfoView extends AbstractView {
	
	@SuppressWarnings("unchecked")
	@Override
	protected void renderMergedOutputModel(Map map, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		List<String[]> list = (List) map.get("list");
		StringWriter buffer = new StringWriter();
		CSVWriter writer = new CSVWriter(buffer);
		writer.writeAll(list);
		writer.flush();
		writer.close();
		
        response.setContentType("text/csv");
		response.addHeader("Content-disposition", "attachment; filename=output.csv");
		OutputStreamWriter outputStream = new OutputStreamWriter(response.getOutputStream(), "UTF8");
		PrintWriter out = new PrintWriter(outputStream, true);
		out.println(buffer.toString().replace("\"", ""));
	}
}
