package com.mitnick.utils;

import org.apache.log4j.Logger;
import org.apache.log4j.lf5.LogLevel;

/**
 * Esta clase tiene la responsabilidad de representar la excepci�n base
 * que todas las dem�s excepciones deber�an extender.
 * 
 * @author Lucas Garc�a
 *
 */
public class BaseException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private Logger logger = Logger.getLogger(BaseException.class);
	
	public BaseException(String key)
	{
		super(PropertiesManager.getProperty(key));
	}
	
	public BaseException(String key, Object[] params)
	{
		super(PropertiesManager.getProperty(key, params));
	}
	
	public BaseException(String key, String log)
	{
		super(PropertiesManager.getProperty(key));
		logger.error(log);
	}
	
	public BaseException(String key, String log, LogLevel logLevel)
	{
		super(PropertiesManager.getProperty(key));
		if(LogLevel.ERROR.equals(logLevel))
			logger.error(log);
		else if(LogLevel.INFO.equals(logLevel))
			logger.info(log);
		else if(LogLevel.DEBUG.equals(logLevel))
			logger.debug(log);
		else if(LogLevel.WARNING.equals(logLevel))
			logger.warn(log);
	}
	
	public BaseException(String key, Object[] params, String log)
	{
		super(PropertiesManager.getProperty(key, params));
		logger.error(log);
	}
	
	public BaseException(String key, Throwable cause)
	{
		super(PropertiesManager.getProperty(key), cause);
	}
	
	public BaseException(String key, Object[] params, Throwable cause)
	{
		super(PropertiesManager.getProperty(key, params), cause);
	}
	
	public BaseException(String key, String log, Throwable cause)
	{
		super(PropertiesManager.getProperty(key), cause);
		logger.error(log, cause);
	}
	
	public BaseException(String key, Object[] params, String log, Throwable cause)
	{
		super(PropertiesManager.getProperty(key, params), cause);
		logger.error(log, cause);
	}

}
