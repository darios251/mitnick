package com.mitnick.presentation.runner;

import java.util.Locale;

import org.apache.log4j.Logger;

import com.mitnick.presentation.utils.DBInitialization;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.locator.BeanLocator;

public class Runner {
	
	private static Logger logger = Logger.getLogger(Runner.class);
	
	public static void main(String[] args) {
		Locale locale = Locale.ENGLISH;
		
		Locale.setDefault(locale);
		logger.info("Iniciando la aplicaci�n: Applicaci�n del proyecto mitnick...");
		logger.info("Inicializando el propertiesManager...");
		BeanLocator.getBean("propertiesManager");
		
		logger.info("Iniciando la base de datos...");
		DBInitialization dbInitialization = (DBInitialization) BeanLocator.getBean("dbInitialization");
		dbInitialization.initializeDB();
		
		Thread.currentThread().setName(PropertiesManager.getProperty("application.name"));
		
	}
	
}
