package com.mitnick.utils;

import java.util.Collection;

import org.apache.commons.validator.GenericValidator;

/**
 * Esta clase tiene la responsabilidad de representar el validador que se va a utilizar
 * para realizar validaciones de datos.
 * 
 * @author Lucas GarcÃ­a
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
	
	public static boolean isNotBlankOrNull(String object)
	{
		return !Validator.isBlankOrNull(object);
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

	public static boolean isDocumentNumber(String documento) {
		if(isNull(documento))
			return false;
		try {
			Long documentoLong = Long.parseLong(documento);
			
			return documentoLong > 1000000 && documentoLong < 99999999;
		}
		catch(Exception e) {
			return false;
		}
	}

	public static boolean isCuit(String cuit) {
		try {
			return Cuit.validar(cuit);
		}
		catch(Exception e) {
			return false;
		}
	}

	public static boolean isPhoneNumber(String telefono) {
		// TODO implementar esta validación
		return true;
	}
	
}
