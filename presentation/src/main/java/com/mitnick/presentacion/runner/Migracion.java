package com.mitnick.presentacion.runner;

import org.apache.log4j.Logger;

import com.mitnick.persistence.dbimport.DBImport;
import com.mitnick.presentacion.utils.DBInitialization;
import com.mitnick.utils.locator.BeanLocator;

public class Migracion {

	private static Logger logger = Logger.getLogger(Migracion.class);
	
	public static void migracionDatos(String[] args){
		try {
			DBInitialization dbInitialization = (DBInitialization) BeanLocator.getBean("dbInitialization");
			dbInitialization.initializeDB();
			DBImport dbimport = (DBImport) BeanLocator.getBean("dbImport");
			dbimport.ejecutar(args[0]);
		} catch (Exception e) {
			logger.error(e);
		}

	}
	
	public static void main(String[] args) {
		if (args.length!=1)
			logger.error("El argumento PATH donde se encuentran los archivos de base de datos es obligatorio!");
		else
			migracionDatos(args);
	}
	
}
