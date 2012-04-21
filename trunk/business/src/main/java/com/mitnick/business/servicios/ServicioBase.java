package com.mitnick.business.servicios;

import org.apache.log4j.Logger;
import org.appfuse.model.BaseObject;
import org.springframework.beans.factory.annotation.Autowired;

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
	
}
