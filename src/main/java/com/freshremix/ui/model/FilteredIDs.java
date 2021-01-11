package com.freshremix.ui.model;

import com.freshremix.util.StringUtil;


public class FilteredIDs {

	private String id;
	private String caption;
	private String mark;
	private String noMarkCaption;
	
	public FilteredIDs() { }
	
	public FilteredIDs(String id, String caption) {
		this.id = id;
		this.caption = caption;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	
	public void updateCaption() {
		noMarkCaption = caption;
		if (!StringUtil.isNullOrEmpty(mark))
			caption = mark + caption;
	}

	public String getNoMarkCaption() {
		return noMarkCaption;
	}

	public void setNoMarkCaption(String noMarkCaption) {
		this.noMarkCaption = noMarkCaption;
	}
}