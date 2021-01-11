package com.freshremix.service.impl;

import java.text.MessageFormat;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.support.MessageSourceAccessor;

import com.freshremix.exception.EONError;
import com.freshremix.service.MessageI18NService;
import com.freshremix.ui.model.EONLocale;
import com.freshremix.util.StringUtil;

public class MessageI18NServiceImpl implements MessageI18NService {
	
	private static final String PLACEHOLDER_SUFFIX = "}";
	private static final String PLACEHOLDER_PREFIX = "{:";
	private MessageSourceAccessor messageSourceAccessor;
	private EONLocale eONlocale;

	public EONLocale geteONlocale() {
		return eONlocale;
	}

	@Required
	public void seteONlocale(EONLocale eONlocale) {
		this.eONlocale = eONlocale;
	}

	public MessageSourceAccessor getMessageSourceAccessor() {
		return messageSourceAccessor;
	}

	@Required
	public void setMessageSourceAccessor(MessageSourceAccessor messageSourceAccessor) {
		this.messageSourceAccessor = messageSourceAccessor;
	}

	/* (non-Javadoc)
	 * @see com.freshremix.service.impl.MessageI18NService#getPropertyMessage(java.lang.String)
	 */
	@Override
	public String getPropertyMessage(String key) {
		return StringUtil.toUTF8String(messageSourceAccessor
				.getMessage(key, eONlocale.getLocale()));
	}

	@Override
	public String getPropertyMessage(String key, Object[] arguments) {
		 String pattern = messageSourceAccessor
				.getMessage(key, eONlocale.getLocale());

		 return StringUtil.toUTF8String(MessageFormat.format(pattern, arguments));
	}
	
	@Override
	public String getPropertyMessage(String key, Map<String, String> arguments) {
		 String message = messageSourceAccessor
				.getMessage(key, eONlocale.getLocale());
		return StringUtil.toUTF8String(StrSubstitutor.replace(message,
				arguments, PLACEHOLDER_PREFIX, PLACEHOLDER_SUFFIX));
	}
	
	@Override
	public String getErrorMessage(EONError error){
		String message = StringUtils.EMPTY;
		if (error.getArguments() != null && error.getArguments().length >0) {
			message = getPropertyMessage(error.getErrorCode(), error.getArguments());
		} else {
			message = getPropertyMessage(error.getErrorCode());
		}
		
		if (StringUtils.isBlank(message)) {
			message = error.getErrorCode();
		}
		return message;
	}
	

}
