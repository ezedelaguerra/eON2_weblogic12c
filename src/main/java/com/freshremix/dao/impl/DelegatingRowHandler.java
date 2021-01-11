package com.freshremix.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;


import com.freshremix.dao.DelegateDataRowHandler;
import com.ibatis.sqlmap.client.event.RowHandler;

/**
 * 
 * This is a custom Database Access Row Handler.
 * 
 * Accepts objects for call back whenever a row is fetched. 
 * 
 * 
 *
 */
public class DelegatingRowHandler<Entity> implements RowHandler {

	private List<DelegateDataRowHandler<Entity>> delegateList;
	
	public void addDelegate(DelegateDataRowHandler<Entity> delegate){
		if (delegateList == null){
			delegateList = new ArrayList<DelegateDataRowHandler<Entity>>();
		}
		if (delegate == null){
			throw new IllegalArgumentException("Delegate is null");
		}
		delegateList.add(delegate);
	}
	
	public List<DelegateDataRowHandler<Entity>> getDelegateList() {
		return delegateList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void handleRow(Object row) {

		if (CollectionUtils.isEmpty(delegateList)) {
			throw new RuntimeException("No delegate row handler registered");
		}
		
		Entity entityCast = null;
		
		if (row != null){
			entityCast = (Entity) row;
		}
		

		for (DelegateDataRowHandler<Entity> delegate : delegateList) {
			try {
				delegate.handleRow(entityCast);
			} catch (Exception e) {
				throw new RuntimeException("Unable to delegate row handling", e);
			}
		}
	}

}
