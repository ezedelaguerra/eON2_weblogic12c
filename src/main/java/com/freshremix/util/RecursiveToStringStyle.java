package com.freshremix.util;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * Performs the recursive displaying of object to String.
 * 
 * Used in ToStringBuilder to display the contents of the objects via
 * reflection.
 * 
 * Note: Except for its intentional use in ExceptionReportHandler, do not leave
 * this as part of the code itself. Performance will become slow if this is
 * always executed.
 * 
 * Use this for debugging purposes only.
 * 
 */

public class RecursiveToStringStyle extends ToStringStyle {
	private static final long serialVersionUID = 1L;

	private static final int INFINITE_DEPTH = -1;

	/**
	 * Setting {@link #maxDepth} to 0 will have the same effect as using
	 * original {@link #ToStringStyle}: it will print all 1st level values
	 * without traversing into them. Setting to 1 will traverse up to 2nd level
	 * and so on.
	 */
	private int maxDepth;

	private int depth;

	public RecursiveToStringStyle() {
		this(INFINITE_DEPTH);
	}

	public RecursiveToStringStyle(int maxDepth) {
		setUseShortClassName(true);
		setUseIdentityHashCode(false);

		this.maxDepth = maxDepth;
	}

	@Override
	protected void appendInternal(StringBuffer buffer, String fieldName,
			Object value, boolean detail) {
		super.appendInternal(buffer, fieldName, value, detail);
	}

	@Override
	protected void appendDetail(StringBuffer buffer, String fieldName,
			Object value) {
		if (value.getClass().getName().startsWith("java.lang.")
				|| (maxDepth != INFINITE_DEPTH && depth >= maxDepth)) {
			buffer.append(value);
		} else {
			depth++;
			buffer.append(ReflectionToStringBuilder.toString(value, this));
			depth--;
		}
	}

	// another helpful method
	@SuppressWarnings("rawtypes")
	@Override
	protected void appendDetail(StringBuffer buffer, String fieldName,
			Collection coll) {
		if (coll != null) {
			depth++;
			buffer.append(ReflectionToStringBuilder.toString(coll.toArray(),
					this, true, true));
			depth--;
		} else {
			buffer.append("null collection");
		}

	}

	@SuppressWarnings("rawtypes")
	@Override
	protected void appendDetail(StringBuffer buffer, String fieldName, Map map) {
		if (map != null && map.keySet() != null) {
			Set keySet = map.keySet();
			buffer.append("{");
			for (Object key : keySet) {
				depth++;
				buffer.append("[key="
						+ ReflectionToStringBuilder.toString(key, this, true,
								true));
				buffer.append(" value ="
						+ ReflectionToStringBuilder.toString(map.get(key),
								this, true, true) + "]");
				depth--;
			}
			buffer.append("}");
		} else {
			buffer.append("null map");
		}
	}

	@Override
	protected void appendDetail(StringBuffer buffer, String fieldName,
			char[] array) {
		buffer.append(array);
	}

}
