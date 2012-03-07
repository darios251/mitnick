package com.mitnick.utils;

import java.util.Collection;

import org.apache.commons.validator.GenericValidator;

/**
 * Esta clase tiene la responsabilidad de representar el validador que se va a utilizar
 * para realizar validaciones de datos.
 * 
 * @author Lucas Garc�a
 *
 */
public class Validator extends GenericValidator{

	private static final long serialVersionUID = 1L;
	
	public static boolean isNull(Object object)
	{
		return object == null;
	}
	
	public static boolean isNotNull(Object object)
	{
		return !Validator.isNull(object);
	}
	
	@SuppressWarnings("unchecked")
	public static boolean isEmpty(Collection collection)
	{
		return collection.isEmpty();
	}
	
	@SuppressWarnings("unchecked")
	public static boolean isEmptyOrNull(Collection collection)
	{
		return Validator.isNull(collection) || Validator.isEmpty(collection);
	}
	
	@SuppressWarnings("unchecked")
	public static boolean isNotEmptyOrNull(Collection collection)
	{
		return !Validator.isEmptyOrNull(collection);
	}

}
