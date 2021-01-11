package com.freshremix.dao;


public interface DelegateDataRowHandler<Entity> {

	/**
	 * handles the row returned by the Ibatis framework
	 * 
	 * @param row
	 * @throws Exception
	 */
	public void handleRow(Entity row) throws Exception;
}
