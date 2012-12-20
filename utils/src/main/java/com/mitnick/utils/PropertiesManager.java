package com.mitnick.utils;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.Locale;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component("propertiesManager")
public class PropertiesManager {
	
	private static ResourceBundleMessageSource messageSource;
	
	public static String getProperty(String propertyKey, Object[] args)
	{
		try {
			return new String(messageSource.getMessage(propertyKey, args, Locale.getDefault()).getBytes(), Charset.forName("UTF-8"));
		}
		catch (Exception e) {
			return propertyKey;
		}
	}
	
	public static String getProperty(String propertyKey)
	{
		return getProperty(propertyKey, null);
	}
	
	public static Boolean getPropertyAsBoolean(String propertyKey)
	{
		return new Boolean(getProperty(propertyKey, null));
	}
	
	public static Integer getPropertyAsInteger(String propertyKey)
	{
		return new Integer(getProperty(propertyKey, null));
	}
	
	public static Double getPropertyAsDouble(String propertyKey)
	{
		return new Double(getProperty(propertyKey, null));
	}
	
	public static BigDecimal getPropertyAsBigDecimal(String propertyKey)
	{
		return new BigDecimal(getProperty(propertyKey, null));
	}

	public void setMessageSource(ResourceBundleMessageSource messageSource) {
		PropertiesManager.messageSource = messageSource;
	}

}
