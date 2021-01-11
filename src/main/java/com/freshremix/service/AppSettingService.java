package com.freshremix.service;

import java.util.Map;

import com.freshremix.model.Category;
import com.freshremix.model.EONHeader;

public interface AppSettingService {

	EONHeader getEONHeader();
	Map<String,Category> getCategoryMasterList();
	
}
