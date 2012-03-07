package com.mitnick.utils;

import java.util.Locale;

import org.springframework.context.support.ResourceBundleMessageSource;

public class PropertiesManager {
	
	private static ResourceBundleMessageSource messageSource;
	
	public static String getProperty(String propertyKey, Object[] args)
	{
		try {
			return messageSource.getMessage(propertyKey, args, Locale.getDefault());
		}
		catch (Exception e) {
			return propertyKey;
		}
	}
	
	public static String getProperty(String propertyKey)
	{
		return getProperty(propertyKey, null);
	}

	public void setMessageSource(ResourceBundleMessageSource messageSource) {
		PropertiesManager.messageSource = messageSource;
	}

}
