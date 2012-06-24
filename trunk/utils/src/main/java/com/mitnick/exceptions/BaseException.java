package com.mitnick.exceptions;

import java.lang.reflect.Field;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.apache.log4j.Logger;

import com.mitnick.utils.PropertiesManager;

/**
 * Esta clase tiene la responsabilidad de representar la excepci�n base
 * que todas las dem�s excepciones deber�an extender.
 * 
 * @author Lucas Garc�a
 *
 */
public class BaseException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public static final int ERROR = 1;
	
	public static final int WARNING = 3;
	
	private int type;
	
	private Logger logger = Logger.getLogger(BaseException.class);
	
	private Set<ConstraintViolation<Object>> constraintViolations;
	
	public BaseException(String key)
	{
		super(PropertiesManager.getProperty(key));
		this.type = ERROR;
		logException(PropertiesManager.getProperty(key), null);
	}
	
	public BaseException(Set<ConstraintViolation<Object>> constraintViolations)
	{
		if(com.mitnick.utils.Validator.isNotEmptyOrNull(constraintViolations)) {
			this.constraintViolations = constraintViolations;
			
			StringBuffer buffer = new StringBuffer();
			for(ConstraintViolation<?> constraint : constraintViolations)
				buffer.append(constraint.getMessage()).append("\n");
			
			fillInStackTrace();
			try {
				Field detailMessageField = this.getClass().getSuperclass().getSuperclass().getSuperclass().getSuperclass().getDeclaredField("detailMessage");
				detailMessageField.setAccessible(true);
				detailMessageField.set(this, buffer.toString());
				
				this.type = ERROR;
				logException(buffer.toString(), null);
			}
			catch(Exception e) {}
		}
	}
	
	public BaseException(String key, String log, int type)
	{
		super(PropertiesManager.getProperty(key));
		this.type = type;
		logException(log, null);
	}
	
	public BaseException(String key, String log)
	{
		super(PropertiesManager.getProperty(key));
		this.type = ERROR;
		logException(log, null);
	}

	public BaseException(String key, Object[] params)
	{
		super(PropertiesManager.getProperty(key, params));
		this.type = ERROR;
		logException(PropertiesManager.getProperty(key, params), null);
	}
	
	
	public BaseException(String key, Object[] params, String log)
	{
		super(PropertiesManager.getProperty(key, params));
		this.type = ERROR;
		logException(log, null);
	}
	
	public BaseException(String key, Object[] params, int type)
	{
		super(PropertiesManager.getProperty(key, params));
		this.type = type;
		logException(PropertiesManager.getProperty(key, params), null);
	}
	
	
	public BaseException(String key, Object[] params, String log, int type)
	{
		super(PropertiesManager.getProperty(key, params));
		this.type = type;
		logException(log, null);
	}
	
	public BaseException(String key, Throwable cause)
	{
		super(PropertiesManager.getProperty(key), cause);
		this.type = ERROR;
		logException(PropertiesManager.getProperty(key), cause);
	}

	public BaseException(String key, Throwable cause, int type)
	{
		super(PropertiesManager.getProperty(key), cause);
		this.type = type;
		logException(PropertiesManager.getProperty(key), cause);
	}
	
	public BaseException(String key, Object[] params, Throwable cause)
	{
		super(PropertiesManager.getProperty(key, params), cause);
		this.type = ERROR;
		logException(PropertiesManager.getProperty(key, params), cause);
	}
	
	public BaseException(String key, Object[] params, Throwable cause, int type)
	{
		super(PropertiesManager.getProperty(key, params), cause);
		this.type = type;
		logException(PropertiesManager.getProperty(key, params), cause);
	}
	
	public BaseException(String key, String log, Throwable cause)
	{
		super(PropertiesManager.getProperty(key), cause);
		this.type = ERROR;
		logException(log, cause);
	}
	
	public BaseException(String key, String log, Throwable cause, int type)
	{
		super(PropertiesManager.getProperty(key), cause);
		this.type = type;
		logException(log, cause);
	}
	
	public BaseException(String key, Object[] params, String log, Throwable cause)
	{
		super(PropertiesManager.getProperty(key, params), cause);
		this.type = ERROR;
		logException(log, cause);
	}
	
	public BaseException(String key, Object[] params, String log, Throwable cause, int type)
	{
		super(PropertiesManager.getProperty(key, params), cause);
		this.type = type;
		logException(log, cause);
	}
	
	private void logException(String message, Throwable cause){
		switch (this.type) {
		case ERROR:
			logger.error(message, cause);
			this.printStackTrace();
			break;
		case WARNING:
			logger.warn(message, cause);
			break;
		default:
			break;
		}
	}

	
	public int getType() {
		return type;
	}
	
	public Set<ConstraintViolation<Object>> getConstraintViolations() {
		return constraintViolations;
	}

}
