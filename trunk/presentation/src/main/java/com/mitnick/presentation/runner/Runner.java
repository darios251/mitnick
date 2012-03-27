package com.mitnick.presentation.runner;

import org.apache.log4j.Logger;

import com.mitnick.presentation.utils.DBInitialization;
import com.mitnick.presentation.view.PrincipalView;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.locator.BeanLocator;

public class Runner {
	
	private static Logger logger = Logger.getLogger(Runner.class);
	
	public static void main(String[] args) {

		logger.info("Iniciando la aplicación: Applicación del proyecto mitnick...");
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
