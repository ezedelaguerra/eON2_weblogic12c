package com.freshremix.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

public class CollectionUtil {

	/**
	 * @deprecated  Use apache commons CollectionUtils.isEmpty
	 * @param collection
	 * @return
	 */
	public static Boolean isNullOrEmpty(Collection<?> collection) {
		if (collection != null && collection.size() > 0)
			return false;
		else
			return true;
	}
	
	/**
	 * Splits a given list into multiple list.
	 * @param list  - the list to be split
	 * @param groupBy - split by x entries (i.e. group by )
	 * @return A list containing a list of sublist with size <= groupBy
	 */
	public static List<List<?>> splitList(List<?> list, int groupBy){
		if (CollectionUtils.isEmpty(list)){
			return Collections.emptyList();
		}
		
		if (groupBy <= 0 ){
			throw new IllegalArgumentException("Invalid splitBy value");
		}
		
		int totalSize = list.size();
		int numBatches = (totalSize / groupBy); 
		
		if ((totalSize % groupBy) != 0) {
			numBatches+=1;
		}

		List<List<?>> returnList = new ArrayList<List<?>>(numBatches);
		
		for(int index = 0; index < numBatches; index++) {  
            int count = index + 1;  
            int fromIndex = Math.max(((count - 1) * groupBy), 0);  
            int toIndex = Math.min((count * groupBy), totalSize);  
            returnList.add(list.subList(fromIndex, toIndex));  
        }  
		return returnList;
		
	}
}
