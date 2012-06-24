package com.mitnick.business.servicios;

import java.lang.reflect.Field;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.log4j.Logger;
import org.appfuse.model.BaseObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.mitnick.exceptions.BusinessException;
import com.mitnick.util.EntityDTOParser;
import com.mitnick.utils.dtos.BaseDto;

/**
 * Esta clase es el administrador de negocios gen�rico de cual deben
 * extender el resto.
 * 
 * @author Lucas Garc�a
 *
 */
public class ServicioBase<E extends BaseObject, D extends BaseDto> {
	protected Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	protected EntityDTOParser<E, D> entityDTOParser;

	public void setEntityDTOParser(EntityDTOParser<E, D> entityDTOParser) {
		this.entityDTOParser = entityDTOParser;
	}
	
	protected static ValidatorFactory entityValidatorFactory; 
	protected static Validator entityValidator;
	
	static {
		entityValidatorFactory = Validation.buildDefaultValidatorFactory();
		entityValidator = entityValidatorFactory.getValidator();
	}
	
	private Set<ConstraintViolation<Object>> validateDto(Object object, Set<ConstraintViolation<Object>> constraintViolations) {
		if(constraintViolations == null)
			constraintViolations = entityValidator.validate(object);
		else
			constraintViolations.addAll(entityValidator.validate(object));
		
		validateChildrens(object, constraintViolations);
		
		return constraintViolations;
	}
	
	protected void validateEntity(Object entity) {
		Set<ConstraintViolation<Object>> constraintViolations = validateDto(entity, null);
		if(com.mitnick.utils.Validator.isNotEmptyOrNull(constraintViolations))
			throw new BusinessException(constraintViolations);
	}

	private void validateChildrens(Object dto, Set<ConstraintViolation<Object>> constraintViolations) {
		Field[] fields = dto.getClass().getDeclaredFields();
		
		for(Field field : fields) {
			try {
				field.setAccessible(true);
				Object objectField = field.get(dto);
				if(objectField instanceof BaseObject)
					validateDto((BaseObject) objectField, constraintViolations);
			} catch (Exception e) {}
		}
	}
}
