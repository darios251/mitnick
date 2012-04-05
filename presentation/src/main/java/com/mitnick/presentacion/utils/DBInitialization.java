package com.mitnick.presentacion.utils;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component("dbInitialization")
public class DBInitialization {
	Logger logger = Logger.getLogger(DBInitialization.class);
	
	
	public void initializeDB() {
		
		logger.info("La base de datos se ha inicializado con éxito.");
	}
	

}
