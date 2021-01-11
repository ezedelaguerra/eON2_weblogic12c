package com.freshremix.dao.impl;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

/**
 * A RowHandler implementation that will save the result into a CSV File.
 * 
 * Subclasses needs to provide the order of fields getFieldOrder() and how to
 * extract data from the specific Entity into a formatted CSV row
 * formatRow(Entity row).
 * 
 * Note: CSV file generated contains BOM bytes so that it could easily be opened
 * in excel without corrupting the double byte characters.
 * 
 * @param <Entity>
 */
public abstract class AbstractCSVFileRowDataHandler<Entity> implements FileRowDataHandler<Entity> {

	private static final String UTF_8 = "UTF-8";
	protected static final String LINE_FEED = "\n";
	protected static final String CSV_SEPARATOR = ",";
	private static final byte[] BOM_BYTES = {(byte)0xEF, (byte)0xBB, (byte)0xBF};

	private File csvFile;

	public File getCsvFile() {
		return csvFile;
	}
	
	/**
	 * If the fileName is not provided, the class will generate a UUID based filename
	 */
	@Override
	public File initializeFile(String fileName) throws Exception{
		if (StringUtils.isBlank(fileName)){
			fileName = createGenericFileName();
		}
		this.csvFile = new File(fileName);
		addFileHeader();
		return this.csvFile;
		
	}

	protected String createGenericFileName() {
		String fullFilePath = FileUtils.getTempDirectoryPath()+UUID.randomUUID().toString()+".csv";
		System.out.println("FullFilePath:"+fullFilePath);
		return fullFilePath;
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.impl.FileRowDataHandler#addFileHeader()
	 */
	@Override
	public void addFileHeader() throws Exception {
		/*
		 * write BOM (byte order mark) chars so that excel can easily open the
		 * file without corrupting Japanese chars. 
		 */
		FileUtils.writeByteArrayToFile(csvFile, BOM_BYTES, true);

		StringBuilder sb = new StringBuilder();
		for (String field : getFieldOrder()) {
			appendValue(sb, field);
		}
		sb.append(LINE_FEED);
		
		FileUtils.writeStringToFile(csvFile, sb.toString(), UTF_8, true);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.impl.FileRowDataHandler#getFieldOrder()
	 */
	@Override
	public abstract List<String> getFieldOrder();

	/**
	 * Convenience class that will append the value to a StringBuilder.
	 * 
	 * This already handles adding CSV separator and escaping the value to a CSV
	 * safe value.
	 * 
	 * @param sb
	 * @param value
	 */
	protected void appendValue(StringBuilder sb, Object value){
		if (sb!=null && sb.length()>0){
			sb.append(CSV_SEPARATOR);
		}
		if (value != null){
			sb.append(StringEscapeUtils.escapeCsv(value.toString()));
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.freshremix.dao.DelegateDataRowHandler#handleRow(java.lang.Object)
	 */
	@Override
	public void handleRow(Entity row) throws Exception {
		String formattedLine = formatRow(row);
		FileUtils.writeStringToFile(csvFile, formattedLine, "UTF-8", true);
	}

	/* (non-Javadoc)
	 * @see com.freshremix.dao.impl.FileRowDataHandler#formatRow(Entity)
	 */
	@Override
	public abstract String formatRow(Entity row);


}
