package com.mitnick.presentacion.controladores;

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
}
