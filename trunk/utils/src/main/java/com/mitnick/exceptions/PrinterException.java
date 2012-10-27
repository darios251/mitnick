package com.mitnick.exceptions;

import java.util.Set;

import javax.validation.ConstraintViolation;

public class PrinterException extends BaseException {
private static final long serialVersionUID = 1L;
	
	public PrinterException(BusinessException e)
	{
		super(e.getMessage(), e);
	}
	
	public PrinterException(Set<ConstraintViolation<Object>> constraintViolations) {
		super(constraintViolations);
	}

	public PrinterException(String key)
	{
		super(key);
	}
	
	public PrinterException(String key, Object[] params)
	{
		super(key, params);
	}
	
	public PrinterException(String key, String log)
	{
		super(key, log);
	}
	
	public PrinterException(String key, Object[] params, String log)
	{
		super(key, params, log);
	}
	
	public PrinterException(String key, Throwable cause)
	{
		super(key, cause);
	}
	
	public PrinterException(String key, Object[] params, Throwable cause)
	{
		super(key, params, cause);
	}
	
	public PrinterException(String key, String log, Throwable cause)
	{
		super(key, log, cause);
	}
	
	public PrinterException(String key, Object[] params, String log, Throwable cause)
	{
		super(key, params, log, cause);
	}
}
