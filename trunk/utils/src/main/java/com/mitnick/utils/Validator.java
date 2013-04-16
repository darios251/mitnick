package com.mitnick.utils;

import java.math.BigDecimal;
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
	
	public static boolean isMoreThanZero(BigDecimal value)
	{
		return value.compareTo(new BigDecimal(0))>0;
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
			cuit = cuit.trim();
			if (cuit.startsWith("-"))
				return true;
			return Cuit.validar(cuit);
		}
		catch(Exception e) {
			return false;
		}
	}
	
	public static boolean isBlankOrNullCuit(String cuit) {
		try {
			if (cuit==null)
				return true;
			cuit = cuit.trim();
			if (cuit.startsWith("-"))
				return true;
			if ("".equals(cuit))
				return true;
			else
			return false;
		}
		catch(Exception e) {
			return false;
		}
	}

	public static boolean isPhoneNumber(String telefono) {
		return true;
	}
	
	public static boolean isAlphanumeric(String value) {
		if(isNull(value))
			return false;
		if(isBlankOrNull(value))
			return true;
		
		return value.matches(PropertiesManager.getProperty("application.alphanumeric.pattern"));
	}
	
	public static boolean isAlphabetic(String value) {
		if(isNull(value))
			return false;
		if(isBlankOrNull(value))
			return true;
		
		return value.matches(PropertiesManager.getProperty("application.alphabetic.pattern"));
	}
	
	public static boolean isDate(String value, String datePattern, boolean strict) {
		value = value.trim();
		if (value.startsWith("/"))
				return true;
		return GenericValidator.isDate(value, datePattern, strict);
	}
	
	public static boolean isNumeric(String value) {
		if(isBlankOrNull(value))
			return false;
		
		return value.matches(PropertiesManager.getProperty("application.numeric.pattern"));
	}
	
	public static boolean isName(String value) {
		if(isNull(value))
			return false;
		if(isBlankOrNull(value))
			return true;
		
		return value.matches(PropertiesManager.getProperty("application.name.pattern"));
	}
	
	public static boolean isBigDecimal(String value) {
		if(isBlankOrNull(value))
			return false;
		
		try {
			new BigDecimal(value);
		}
		catch(Exception e) {
			return false;
		}
		
		return true;
	}
	
	
}
