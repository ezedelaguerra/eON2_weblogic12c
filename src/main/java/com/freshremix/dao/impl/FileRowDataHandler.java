package com.freshremix.dao.impl;

import java.io.File;
import java.util.List;

import com.freshremix.dao.DelegateDataRowHandler;

public interface FileRowDataHandler<Entity> extends DelegateDataRowHandler<Entity>  {

	
	/**
	 * Initializes the file.
	 * E.g. for CSV, file is created and headers are added
	 * 
	 * @param fileName should contain full path and file name
	 * @return File 
	 */
	public abstract File initializeFile(String fileName) throws Exception;
	
	/**
	 * Adds the file header row to the file during class initialization
	 * 
	 * @throws Exception
	 */
	public abstract void addFileHeader() throws Exception;

	/**
	 * Needs to be implemented by the Entity specific CSV Row Handler.  
	 * 
	 * This should return the order of columns
	 * 
	 * @return
	 */
	public abstract List<String> getFieldOrder();

	/*
	 * (non-Javadoc)
	 * @see com.freshremix.dao.DelegateDataRowHandler#handleRow(java.lang.Object)
	 */
	public abstract void handleRow(Entity row) throws Exception;

	/**
	 * This should convert the entity into a row that will be appended to the
	 * end of the CSV file.
	 * 
	 * Needs to be implemented by the Entity specific CSV Row Handler.
	 * 
	 * @param row
	 * @return
	 */
	public abstract String formatRow(Entity row);


}