package com.freshremix.util;

/**
 * Stopwatch utility for checking elapsed time
 * @author michael
 *
 */
public class StopWatch {

	private long start;
	private long end;
	private String tagName;
	
	public void start(String tagName){
		this.tagName = tagName;
		start = System.currentTimeMillis();
		System.out.println("Start:"+ this.tagName);
	}

	public void stop(){
		end = System.currentTimeMillis();
		System.out.println("End: "+ this.tagName + " elapsed ms:" + (end-start));
	}

}
