package com.freshremix.util;

import com.freshremix.model.ThreadContext;

/**
 * Util to access the ThreadLocal variable.
 * 
 * @author michael
 *
 */
public class ThreadContextUtil {

	private static final ThreadLocal<ThreadContext> threadLocalContext = new ThreadLocal<ThreadContext>() {

		@Override
		protected ThreadContext initialValue() {
			return new ThreadContext();
		}
		
	};
	
	public static ThreadContext getThreadContext() {
		return threadLocalContext.get();
	}

	public static void setThreadContext(ThreadContext tc) {
		threadLocalContext.set(tc);
	}

}
