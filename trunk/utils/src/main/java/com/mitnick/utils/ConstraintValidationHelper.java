package com.mitnick.utils;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.springframework.stereotype.Component;

@Component("constraintValidationHelper")
public class ConstraintValidationHelper<T> {

	public String getMessage(Set<ConstraintViolation<T>> constraintViolations) {
		StringBuffer buffer = new StringBuffer();
		
		if(Validator.isNotNull(constraintViolations)) {
			for(ConstraintViolation<T> constraintMessage : constraintViolations)
				buffer.append(constraintMessage.getMessage()).append("\n");
		}
		
		return buffer.toString();
	}
}
