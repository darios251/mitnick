package com.mitnick.presentacion.runner;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.Logger;

import com.mitnick.presentacion.utils.DBInitialization;
import com.mitnick.presentacion.vistas.InicioView;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.locator.BeanLocator;

public class Runner {
	
	private static Logger logger = Logger.getLogger(Runner.class);
	
	public static void main(String[] args) {
		InicioView inicio = InicioView.getInstance();
		inicio.setVisible(true);
		logger.info("Iniciando la aplicaci�n: Applicaci�n del proyecto mitnick...");
		logger.info("Inicializando el propertiesManager...");
		BeanLocator.getBean("propertiesManager");
		
		logger.info("Iniciando la base de datos...");
		DBInitialization dbInitialization = (DBInitialization) BeanLocator.getBean("dbInitialization");
		dbInitialization.initializeDB();
		
		Thread.currentThread().setName(PropertiesManager.getProperty("application.name"));
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
		}
		
		BeanLocator.getBean("loginView");
	}

	
}
