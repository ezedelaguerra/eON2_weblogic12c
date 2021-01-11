package com.freshremix.util;

import org.springframework.context.support.MessageSourceAccessor;

import com.freshremix.ui.model.EONLocale;

/**
 * 
 * As much as possible, use MessageI18NService class instead of this class.
 * MessageI18NService is more unit testing friendly, i.e. can be easily Mocked
 *
 */
public class MessageUtil {
	
	//place here the property file variables
	public static final String publishMark = "sheet.header.column.mark.published";
	public static final String checkDetails = "label.button.checkDetails"; 
	public static final String fontFace = "label.excel.fontface";
	public static final String finalizeFilterMark = "label.filter.finalize";
	public static final String publishFilterMark = "label.filter.publish";
	
	private static MessageSourceAccessor messageSourceAccessor;
	private static EONLocale eONlocale;
	
	
	public void setMessageSourceAccessor(MessageSourceAccessor messageSourceAccessor) {
		MessageUtil.messageSourceAccessor = messageSourceAccessor;
	}

	public void seteONlocale(EONLocale eONlocale) {
		MessageUtil.eONlocale = eONlocale;
	}

	/**
	 * This method is deprecated.
	 * 
	 * Use MessageI18NService instead.
	 * 
	 * @param message
	 * @return
	 */
	@Deprecated
	public static String getPropertyMessage(String message) {

//		if (MessageUtil.messageSourceAccessor == null) {
//			MessageSource msgSource = (MessageSource) SpringContext
//					.getApplicationContext().getBean("messageSource");
//			MessageUtil.messageSourceAccessor = new MessageSourceAccessor(
//					msgSource);
//		}
//
//		EONLocale locale = (EONLocale) SpringContext.getApplicationContext()
//				.getBean("eonLocale");

		return StringUtil.toUTF8String(MessageUtil.messageSourceAccessor
				.getMessage(message, MessageUtil.eONlocale.getLocale()));

	}
}
