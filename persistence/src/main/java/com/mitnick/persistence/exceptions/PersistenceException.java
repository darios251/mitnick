package com.mitnick.persistence.exceptions;

import com.mitnick.utils.BaseException;

/**
 * Esta clase tiene la responsabilidad de representar la excepci�n por defecto
 * que debe lanzarse en la capa de persistencia.
 * 
 * @author Lucas Garc�a
 *
 */
public class PersistenceException extends BaseException {
	
	private static final long serialVersionUID = 1L;

	public PersistenceException(String key)
	{
		super(key);
	}
	
	public PersistenceException(String key, Object[] params)
	{
		super(key, params);
	}
	
	public PersistenceException(String key, String log)
	{
		super(key, log);
	}
	
	public PersistenceException(String key, Object[] params, String log)
	{
		super(key, params, log);
	}
	
	public PersistenceException(String key, Throwable cause)
	{
		super(key, cause);
	}
	
	public PersistenceException(String key, Object[] params, Throwable cause)
	{
		super(key, params, cause);
	}
	
	public PersistenceException(String key, String log, Throwable cause)
	{
		super(key, log, cause);
	}
	
	public PersistenceException(String key, Object[] params, String log, Throwable cause)
	{
		super(key, params, log, cause);
	}
}
