package com.freshremix.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.freshremix.ui.model.FilteredIDs;

public class FilterIDUtil {

	public static Map<String,String> toNameIDMap(List<FilteredIDs> ids) {
		Map<String,String> nameIDMap = new HashMap<String,String>();
		for (FilteredIDs id : ids) {
			nameIDMap.put(id.getCaption(), String.valueOf(id.getId()));
		}
		return nameIDMap;
	}
	
	public static List<Integer> toIDList(List<FilteredIDs> ids) {
		if (ids == null )
			return null;
		List<Integer> idList = new ArrayList<Integer> ();
		for (FilteredIDs id : ids) {
			idList.add(Integer.parseInt(id.getId()));
		}
		return idList;
	}

	public static Map<Integer,String> toIDNameMap(List<FilteredIDs> ids) {
		Map<Integer,String> idNameMap = new HashMap<Integer,String>();
		for (FilteredIDs id : ids) {
			idNameMap.put(Integer.parseInt(id.getId()), id.getCaption());
		}
		return idNameMap;
	}
		
}