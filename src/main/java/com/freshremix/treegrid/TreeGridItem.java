package com.freshremix.treegrid;

import java.util.ArrayList;
import java.util.List;

public class TreeGridItem {
	protected Long id;
	protected List<Object> cells = new ArrayList<Object>();
	
	public void addCell(Object value) {
		cells.add(value);
	}
	
	public List<Object> getCells() {
		return cells;
	}

	public Long getId() {
		return id;
	}

	public void setCells(List<Object> cells) {
		this.cells = cells;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
