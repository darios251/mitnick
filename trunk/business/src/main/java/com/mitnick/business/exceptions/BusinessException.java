package com.mitnick.business.exceptions;

import com.mitnick.persistence.exceptions.PersistenceException;
import com.mitnick.utils.BaseException;

/**
 * Esta clase tiene la responsabilidad de representar la excepci�n por defecto
 * que debe lanzarse en la capa de negocios.
 * 
 * @author Lucas Garc�a
 *
 */
public class BusinessException extends BaseException {
	
	private static final long serialVersionUID = 1L;

	public BusinessException(PersistenceException e)
	{
		super(e.getMessage(), e);
	}
	
	public BusinessException(String key)
	{
		super(key);
	}
	
	public BusinessException(String key, Object[] params)
	{
		super(key, params);
	}
	
	public BusinessException(String key, String log)
	{
		super(key, log);
	}
	
	public BusinessException(String key, Object[] params, String log)
	{
		super(key, params, log);
	}
	
	public BusinessException(String key, Throwable cause)
	{
		super(key, cause);
	}
	
	public BusinessException(String key, Object[] params, Throwable cause)
	{
		super(key, params, cause);
	}
	
	public BusinessException(String key, String log, Throwable cause)
	{
		super(key, log, cause);
	}
	
	public BusinessException(String key, Object[] params, String log, Throwable cause)
	{
		super(key, params, log, cause);
	}
}
