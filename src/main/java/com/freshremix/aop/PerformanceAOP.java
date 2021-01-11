package com.freshremix.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;

import com.freshremix.util.StringUtil;

public class PerformanceAOP implements MethodInterceptor {

	private static Logger logger = Logger.getLogger(PerformanceAOP.class);

	@Override
	public Object invoke(MethodInvocation method) throws Throwable {
		
		if(method.getMethod().getName().equals("insertAudit")){
			return method.proceed();
		}

		long start = System.currentTimeMillis();
		try {
			return method.proceed();
		} finally {
			// compute the processing time
			long end = System.currentTimeMillis();
			long timeMs = end - start;

			// do logs
			StringBuffer sb = new StringBuffer();
			sb.append("[").append(timeMs).append("ms] ");
			sb.append(method.getMethod().getDeclaringClass().getSimpleName()).append(".");
			sb.append(method.getMethod().getName());
			sb.append(" --> ");
			
			Object[] param = method.getArguments();
			sb.append("{");
			for (int i = 0; i < param.length; i++) {
				Object prm = param[i];				
				sb.append(StringUtil.nullToDefaultValue(prm, "null"));
				if(i!=param.length-1) sb.append(",");				
			}
			sb.append("}");
			
			logger.info(sb.toString());
		}

	}

}
