package com.freshremix.webapp.controller.login;

import java.util.Map;


public class ChainedControllerProcessor {
	
	private Map<String, Map<String, String>> chainedControllers;

	public Map<String, Map<String, String>> getChainedControllers() {
		return chainedControllers;
	}

	public void setChainedControllers(
			Map<String, Map<String, String>> chainedControllers) {
		this.chainedControllers = chainedControllers;
	}

	public String getUrltoForward(String role, Map parametermap)
	{
		if(chainedControllers.get(role)!=null )
		{
			for (Map.Entry<String, String> entry : chainedControllers.get(role).entrySet())
			{
				String[] key = (String[])parametermap.get(entry.getKey());
				if(key==null || key.length==0 || key[0].equals("false"))
				{
					return entry.getValue();
				}
			}
		}
		return null;
	}
	
	
	

}
