package com.mitnick.presentacion.runner;

import org.apache.log4j.Logger;

import com.mitnick.persistence.daos.IConfiguracionDAO;
import com.mitnick.persistence.entities.Configuracion;
import com.mitnick.presentacion.utils.DBInitialization;
import com.mitnick.presentacion.vistas.PrincipalView;
import com.mitnick.utils.DiskUtils;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.locator.BeanLocator;

public class Runner {
	
	private static Logger logger = Logger.getLogger(Runner.class);
	
	public static void main(String[] args) {
		
		checkForRun();
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

	private static void checkForRun() {
		IConfiguracionDAO configuracionDao = (IConfiguracionDAO) BeanLocator.getBean("configuracionDao");
		
		Configuracion configuracion = configuracionDao.getConfiguracion();
		
		if(!configuracion.getNumeroSerieDisco().equals(DiskUtils.getSerialNumber("C:"))) {
			javax.swing.JOptionPane.showConfirmDialog((java.awt.Component) null,
					"La copia del software no es legal.\nConsulte con los administradores de http://www.mitnick.com.ar", "Alerta!!",
					javax.swing.JOptionPane.DEFAULT_OPTION);
			System.exit(1);
		}
	}
	
	
	
}
