package com.mitnick.presentation.exceptions;

import com.mitnick.business.exceptions.BusinessException;
import com.mitnick.utils.BaseException;

/**
 * Esta clase tiene la responsabilidad de representar la excepci�n por defecto
 * que debe lanzarse en la capa de presentaci�n.
 * 
 * @author Lucas Garc�a
 *
 */
public class PresentationException extends BaseException {
	
	private static final long serialVersionUID = 1L;
	
	public PresentationException(BusinessException e)
	{
		super(e.getMessage(), e);
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
