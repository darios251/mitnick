package com.mitnick.presentacion.controladores;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.log4j.Logger;

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
}
