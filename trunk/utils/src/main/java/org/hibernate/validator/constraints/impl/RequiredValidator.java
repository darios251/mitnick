package org.hibernate.validator.constraints.impl;

import java.lang.reflect.Field;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.constraints.Required;

import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.Validator;
import com.mitnick.utils.dtos.BaseDto;

public class RequiredValidator implements ConstraintValidator<Required, BaseDto> {
	
	private Required annotation;

	public void initialize(Required annotation) {
		this.annotation = annotation;
	}

	@Override
	public boolean isValid(BaseDto value, ConstraintValidatorContext context) {
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
		
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate(PropertiesManager.getProperty(context.getDefaultConstraintMessageTemplate().substring(1, context.getDefaultConstraintMessageTemplate().length()-1), new Object[]{propertyPath})).addConstraintViolation();
		return Validator.isNotNull(value);
	}
}
