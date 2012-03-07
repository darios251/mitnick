package com.mitnick.presentation.controller;

import org.apache.log4j.Logger;

/**
 * Esta clase tiene la responsabilidad de representar la clase base
 * de la cual deben extender los controllers.
 * 
 * @author Lucas Garc�a
 *
 */
public abstract class BaseController {
	protected Logger logger = Logger.getLogger(this.getClass());
}
