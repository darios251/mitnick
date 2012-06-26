package org.hibernate.validator.constraints.impl;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.PatternSyntaxException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.constraints.MitnickField;
import org.hibernate.validator.constraints.MitnickField.FieldType;
import org.hibernate.validator.constraints.MitnickField.FieldTypeValues;

import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.Validator;

public class MitnickFieldValidator implements ConstraintValidator<MitnickField, String> {
	
	private MitnickField annotation;
	private java.util.regex.Pattern pattern;

	public void initialize(MitnickField annotation) {
		this.annotation = annotation;
		validateParameters();
		
		MitnickField.RegexFlag flags[] = annotation.flags();
		int intFlag = 0;
		for ( MitnickField.RegexFlag flag : flags ) {
			intFlag = intFlag | flag.getValue();
		}

		if(Validator.isNotBlankOrNull(annotation.regexp())) {
			try {
				pattern = java.util.regex.Pattern.compile( annotation.regexp(), intFlag );
			}
			catch ( PatternSyntaxException e ) {
				throw new IllegalArgumentException( "Invalid regular expression.", e );
			}
		}
	}

	public boolean isValid(String value, ConstraintValidatorContext context) {
		String propertyPath = "";
		
		if(Validator.isBlankOrNull(annotation.propertyName())) {
			try {
				Field propertyPathField = context.getClass().getDeclaredField("propertyPath");
				propertyPathField.setAccessible(true);
				propertyPath = ((org.hibernate.validator.engine.PathImpl) propertyPathField.get(context)).toString();
				propertyPath = propertyPath.substring(0, 1).toUpperCase() + propertyPath.substring(1, propertyPath.length());
			} catch(Exception e){}
		}
		else
			propertyPath = PropertiesManager.getProperty(annotation.propertyName());
		
		// si no es un campo requerido y el valor es nulo o vacio es un campo valido
		if(!annotation.required() && Validator.isBlankOrNull(value))
			return true;
		
		if(annotation.required() && Validator.isBlankOrNull(value)) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(PropertiesManager.getProperty(annotation.requiredMessage(), new Object[]{propertyPath})).addConstraintViolation();
			return false;
		}
		
		int length = value.length();
		
		if(!(length >= annotation.min() && length <= annotation.max())) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(PropertiesManager.getProperty(annotation.sizeMessage(), new Object[]{propertyPath})).addConstraintViolation();
			
			return false;
		}
		
		if(Validator.isNotBlankOrNull(annotation.regexp())) {
			Matcher m = pattern.matcher( value );
			
			if(!m.matches()) {
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate(PropertiesManager.getProperty(annotation.regexp(), new Object[]{propertyPath})).addConstraintViolation();
				
				return false;
			}
		}
		
		if(!annotation.fieldType().equals(FieldType.NONE)) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(PropertiesManager.getProperty(annotation.fieldTypeMessage(), new Object[]{propertyPath})).addConstraintViolation();
			
			switch(annotation.fieldType().getValue()) {
				case FieldTypeValues.INTEGER_VALUE:
					return Validator.isInt(value);
				case FieldTypeValues.DOUBLE_VALUE:
					return Validator.isDouble(value);
				case FieldTypeValues.LONG_VALUE:
					return Validator.isLong(value);
				case FieldTypeValues.FLOAT_VALUE:
					return Validator.isFloat(value);
				case FieldTypeValues.DATE_VALUE:
					return Validator.isDate(value, PropertiesManager.getProperty("application.date.pattern"), true);
				case FieldTypeValues.CREDIT_CARD_VALUE:
					return Validator.isCreditCard(value);
				case FieldTypeValues.EMAIL_VALUE:
					return Validator.isEmail(value);
				case FieldTypeValues.URL_VALUE:
					return Validator.isUrl(value);
				case FieldTypeValues.DOCUMENT_NUMBER_VALUE:
					return Validator.isDocumentNumber(value);
				case FieldTypeValues.CUIT_VALUE:
					return Validator.isCuit(value);
				case FieldTypeValues.PHONE_NUMBER_VALUE:
					return Validator.isPhoneNumber(value);
				case FieldTypeValues.APHANUMERIC_VALUE:
					return Validator.isAlphanumeric(value);
				case FieldTypeValues.ALPHABETIC_VALUE:
					return Validator.isAlphabetic(value);
				case FieldTypeValues.NUMERIC_VALUE:
					return Validator.isName(value);
				case FieldTypeValues.BIGDECIMAL_VALUE:
					return Validator.isBigDecimal(value);
			}
		}
		
		return true;
	}
	
	private void validateParameters() {
		if ( annotation.min() < 0 ) {
			throw new IllegalArgumentException( "The min parameter cannot be negative." );
		}
		if ( annotation.max() < 0 ) {
			throw new IllegalArgumentException( "The max parameter cannot be negative." );
		}
		if ( annotation.max() < annotation.min() ) {
			throw new IllegalArgumentException( "The length cannot be negative." );
		}
	}
}
