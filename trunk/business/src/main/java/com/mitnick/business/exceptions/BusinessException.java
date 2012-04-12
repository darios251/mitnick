package com.mitnick.business.exceptions;

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

	public BusinessException(String key)
	{
		super(key);
	}
	
	public BusinessException(String key, String log, int type)
	{
		super(key, log, type);
	}
	
	public BusinessException(String key, String log)
	{
		super(key, log);
	}

	public BusinessException(String key, Object[] params)
	{
		super(key, params);
	}
	
	
	public BusinessException(String key, Object[] params, String log)
	{
		super(key, params, log);
	}
	
	public BusinessException(String key, Object[] params, int type)
	{
		super(key, params, type);
	}
	
	
	public BusinessException(String key, Object[] params, String log, int type)
	{
		super(key, params, log, type);
	}
	
	public BusinessException(String key, Throwable cause)
	{
		super(key, cause);
	}

	public BusinessException(String key, Throwable cause, int type)
	{
		super(key, cause, type);
	}
	
	public BusinessException(String key, Object[] params, Throwable cause)
	{
		super(key, params, cause);
	}
	
	public BusinessException(String key, Object[] params, Throwable cause, int type)
	{
		super(key, params, cause, type);
	}
	
	public BusinessException(String key, String log, Throwable cause)
	{
		super(key, log, cause);
	}
	
	public BusinessException(String key, String log, Throwable cause, int type)
	{
		super(key, log, cause, type);
	}
	
	public BusinessException(String key, Object[] params, String log, Throwable cause)
	{
		super(key, params, log, cause);
	}
	
	public BusinessException(String key, Object[] params, String log, Throwable cause, int type)
	{
		super(key, params, log, cause, type);
	}
}