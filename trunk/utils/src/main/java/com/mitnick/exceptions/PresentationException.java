package com.mitnick.exceptions;

import java.util.Set;

import javax.validation.ConstraintViolation;


/**
 * Esta clase tiene la responsabilidad de representar la excepción por defecto
 * que debe lanzarse en la capa de presentación.
 * 
 * @author Lucas García
 *
 */
public class PresentationException extends BaseException {
	
	private static final long serialVersionUID = 1L;
	
	public PresentationException(BusinessException e)
	{
		super(e.getMessage(), e);
	}
	
	public PresentationException(Set<ConstraintViolation<Object>> constraintViolations) {
		super(constraintViolations);
	}

	public PresentationException(String key)
	{
		super(key);
	}
	
	public PresentationException(String key, Object[] params)
	{
		super(key, params);
	}
	
	public PresentationException(String key, String log)
	{
		super(key, log);
	}
	
	public PresentationException(String key, Object[] params, String log)
	{
		super(key, params, log);
	}
	
	public PresentationException(String key, Throwable cause)
	{
		super(key, cause);
	}
	
	public PresentationException(String key, Object[] params, Throwable cause)
	{
		super(key, params, cause);
	}
	
	public PresentationException(String key, String log, Throwable cause)
	{
		super(key, log, cause);
	}
	
	public PresentationException(String key, Object[] params, String log, Throwable cause)
	{
		super(key, params, log, cause);
	}
}
