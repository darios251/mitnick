package com.mitnick.utils;

import java.util.Collection;

import org.apache.commons.validator.GenericValidator;

/**
 * Esta clase tiene la responsabilidad de representar el validador que se va a utilizar
 * para realizar validaciones de datos.
 * 
 * @author Lucas García
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
	
	public static boolean isEmpty(@SuppressWarnings("rawtypes") Collection collection)
	{
		return collection.isEmpty();
	}
	
	public static boolean isEmptyOrNull(@SuppressWarnings("rawtypes") Collection collection)
	{
		return Validator.isNull(collection) || Validator.isEmpty(collection);
	}
	
	public static boolean isNotEmptyOrNull(@SuppressWarnings("rawtypes") Collection collection)
	{
		return !Validator.isEmptyOrNull(collection);
	}

}
