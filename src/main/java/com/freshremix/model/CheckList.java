package com.freshremix.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

@SuppressWarnings("serial")
public class CheckList extends ArrayList<Object> {

	private boolean duplicateValue;
	
	public boolean add(Object object){
		checkForDuplicate(object);
		return super.add(object);
	}
	
	public boolean isDuplicateValue() {
		return duplicateValue;
	}

	public void setDuplicateValue(boolean duplicateValue) {
		this.duplicateValue = duplicateValue;
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	private void checkForDuplicate(Collection<Object> object){
        Iterator itr = object.iterator();
           
        while(itr.hasNext()){
           Object obj = (Object)itr.next();   
           checkForDuplicate(obj);
        }
    }

	 private void checkForDuplicate(Object object){
	        if (this.contains(object)) {
				setDuplicateValue(true);
			}
	    }
}
