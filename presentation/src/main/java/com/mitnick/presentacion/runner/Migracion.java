package com.mitnick.presentacion.runner;

import com.mitnick.persistence.dbimport.DBImport;
import com.mitnick.presentacion.utils.DBInitialization;
import com.mitnick.utils.locator.BeanLocator;

public class Migracion {

	public static void migracionDatos(String[] args){
		DBInitialization dbInitialization = (DBInitialization) BeanLocator.getBean("dbInitialization");
		dbInitialization.initializeDB();
		DBImport dbimport = (DBImport) BeanLocator.getBean("dbImport");
//		dbimport.ejecutar(args[0]);
		String path = "C:/project/mitnick/archivosSusmann/Copia (2) de comersis/";
		dbimport.ejecutar(path);

	}
	
	public static void main(String[] args) {
		if (args.length!=1)
			System.out.println("El argumento PATH donde se encuentran los archivos de base de datos es obligatorio!");
		else
			migracionDatos(args);
	}
	
}
