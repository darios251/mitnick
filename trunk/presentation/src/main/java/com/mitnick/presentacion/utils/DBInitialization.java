package com.mitnick.presentacion.utils;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mitnick.persistence.daos.IConfiguracionDAO;
import com.mitnick.persistence.entities.Configuracion;

@Component("dbInitialization")
public class DBInitialization {
	Logger logger = Logger.getLogger(DBInitialization.class);

	@Autowired
	private IConfiguracionDAO configuracionDao;
	
	public void initializeDB() {
		// se hacen inserts para actualizar el número de secuencia
		Configuracion configuracion = null;
		
		while (configuracion == null) {
			configuracion = new Configuracion();
			configuracion.setMagicValue("");
			configuracion.setSalt("");
			
			try {
				configuracion = configuracionDao.save(configuracion);
			}
			catch (Exception e) {
				configuracion = null;
			}
		}
		
		configuracionDao.remove(configuracion.getId());
		
		logger.info("La base de datos se ha inicializado con éxito.");
	}

}
