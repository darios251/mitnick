package com.mitnick.presentacion.runner;

import org.apache.log4j.Logger;

import com.mitnick.presentacion.utils.DBInitialization;
import com.mitnick.presentacion.vistas.PrincipalView;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.locator.BeanLocator;

public class Runner {
	
	private static Logger logger = Logger.getLogger(Runner.class);
	
	public static void main(String[] args) {

		logger.info("Iniciando la aplicaci�n: Applicaci�n del proyecto mitnick...");
		logger.info("Inicializando el propertiesManager...");
		BeanLocator.getBean("propertiesManager");
		
		logger.info("Iniciando la base de datos...");
		DBInitialization dbInitialization = (DBInitialization) BeanLocator.getBean("dbInitialization");
		dbInitialization.initializeDB();
		
		logger.info("Iniciando la pantalla principal...");
		PrincipalView principalView = (PrincipalView) BeanLocator.getBean("principalView");
		principalView.setVisible(true);
		
		Thread.currentThread().setName(PropertiesManager.getProperty("application.name"));
		
	}
	
}
