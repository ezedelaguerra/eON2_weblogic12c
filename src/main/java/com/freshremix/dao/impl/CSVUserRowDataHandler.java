package com.freshremix.dao.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

public class CSVUserRowDataHandler extends AbstractCSVFileRowDataHandler<Map<String, String>> {

	
	private static final List<String> fieldOrder = Arrays.asList(
			"COMPANY_ID",
			"COMPANY_NAME",
			"COMPANY_SHORT_NAME",
			"COMPANY_TYPE_ID",
			"COMPANY_CONTACT_PERSON",
			"COMPANY_SOX_FLAG",
			"COMPANY_ADDRESS1",
			"COMPANY_ADDRESS2",
			"COMPANY_ADDRESS3",
			"COMPANY_TELEPHONE_NUMBER",
			"COMPANY_FAX_NUMBER",
			"COMPANY_EMAIL_ADDRESS",
			"COMPANY_COMMENTS",
			"COMPANY_CREATE_TIMESTAMP",
			"COMPANY_CREATED_BY",
			"COMPANY_UPDATE_TIMESTAMP",
			"COMPANY_UPDATED_BY",
			"USER_ID",
			"USER_ROLE_ID",
			"USER_USERNAME",
			"USER_PASSWORD",
			"USER_NAME",
			"USER_SHORTNAME",
			"USER_ADDRESS1",
			"USER_ADDRESS2",
			"USER_ADDRESS3",
			"USER_MOBILE_NUMBER",
			"USER_TELEPHONE_NUMBER",
			"USER_FAX_NUMBER",
			"USER_MOBILE_EMAIL_ADDRESS",
			"USER_PC_EMAIL_ADDRESS",
			"USER_COMMENTS",
			"USER_USE_BMS",
			"USER_DATE_LAST_LOGIN",
			"USER_DATE_PASSWORD_CHANGE",
			"USER_CREATE_TIMESTAMP",
			"USER_CREATED_BY",
			"USER_UPDATE_TIMESTAMP",
			"USER_UPDATED_BY",
			"USER_USER_ID_OLD",
			"USER_TOS_FLAG",
			"USER_TOS_FLAG_BY",
			"USER_TOS_TIMESTAMP"
			);

	@Override
	public String formatRow(Map<String, String> row) {
		if (MapUtils.isEmpty(row)){
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		for (String field : fieldOrder) {
			appendValue(sb, StringUtils.trimToEmpty(row.get(field)));
		}
		sb.append(LINE_FEED);
		return sb.toString();
	}

	@Override
	public List<String> getFieldOrder() {
		return fieldOrder;
	}
}
