package com.mitnick.business.servicios;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mitnick.util.EntityDTOParser;

/**
 * Esta clase es el administrador de negocios gen�rico de cual deben
 * extender el resto.
 * 
 * @author Lucas Garc�a
 *
 */
public class ServicioBase {
	protected Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	protected EntityDTOParser entityDTOParser;

	public void setEntityDTOParser(EntityDTOParser entityDTOParser) {
		this.entityDTOParser = entityDTOParser;
	}
	
}
