package com.mitnick.presentacion.controladores;

import java.lang.reflect.Field;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.log4j.Logger;

import com.mitnick.exceptions.PresentationException;
import com.mitnick.utils.dtos.BaseDto;

/**
 * Esta clase tiene la responsabilidad de representar la clase base
 * de la cual deben extender los controllers.
 * 
 * @author Lucas Garc�a
 *
 */
abstract class BaseController {
	protected Logger logger = Logger.getLogger(this.getClass());
	
	protected static ValidatorFactory entityValidatorFactory; 
	protected static Validator entityValidator;
	
	static {
		entityValidatorFactory = Validation.buildDefaultValidatorFactory();
		entityValidator = entityValidatorFactory.getValidator();
	}
	
	private Set<ConstraintViolation<Object>> validateDto(Object dto, Set<ConstraintViolation<Object>> constraintViolations) throws PresentationException {
		if(constraintViolations == null)
			constraintViolations = entityValidator.validate(dto);
		else
			constraintViolations.addAll(entityValidator.validate(dto));
		
		validateChildrens(dto, constraintViolations);
		
		return constraintViolations;
	}
	
	protected void validateDto(Object dto) throws PresentationException {
		Set<ConstraintViolation<Object>> constraintViolations = validateDto(dto, null);
		if(com.mitnick.utils.Validator.isNotEmptyOrNull(constraintViolations))
			throw new PresentationException(constraintViolations);
	}

	private void validateChildrens(Object dto, Set<ConstraintViolation<Object>> constraintViolations) {
		Field[] fields = dto.getClass().getDeclaredFields();
		
		for(Field field : fields) {
			try {
				field.setAccessible(true);
				Object objectField = field.get(dto);
				if(objectField instanceof BaseDto)
					validateDto((BaseDto) objectField, constraintViolations);
			} catch (Exception e) {}
		}
	}
}
