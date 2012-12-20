package com.mitnick.presentacion.runner;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

import com.mitnick.persistence.daos.IConfiguracionDAO;
import com.mitnick.persistence.dbimport.DBImport;
import com.mitnick.persistence.entities.Configuracion;
import com.mitnick.presentacion.utils.DBInitialization;
import com.mitnick.presentacion.vistas.InicioView;
import com.mitnick.utils.DiskUtils;
import com.mitnick.utils.MacUtils;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.locator.BeanLocator;

public class Runner {
	
	private static Logger logger = Logger.getLogger(Runner.class);
	
	public static void migracionDatos(String[] args){
		DBInitialization dbInitialization = (DBInitialization) BeanLocator.getBean("dbInitialization");
		dbInitialization.initializeDB();
		DBImport dbimport = (DBImport) BeanLocator.getBean("dbImport");
		dbimport.ejecutar();
	}
	
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
		
		checkForRun();

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
		}
		
		boolean loginRequired = PropertiesManager.getPropertyAsBoolean("application.login.requiredAtStart");
		if(loginRequired)
			BeanLocator.getBean("loginView");
		else
			BeanLocator.getBean("principalView");
	}

	private static void checkForRun() {
		IConfiguracionDAO configuracionDao = (IConfiguracionDAO) BeanLocator.getBean("configuracionDao");
		Configuracion configuracion = configuracionDao.getConfiguracion();
		
		String systemUsername = System.getProperty("user.name");
		String diskSerialNumber = DiskUtils.getSerialNumber("C:");
		String macNumber = MacUtils.getMac();
		
		ShaPasswordEncoder passwordEncoderBean = (ShaPasswordEncoder) BeanLocator.getBean("passwordEncoderBean");
		
		String magicValue = passwordEncoderBean.encodePassword(systemUsername + diskSerialNumber + macNumber, configuracion.getSalt());
		
		if(!configuracion.getMagicValue().equals(magicValue)) {
			javax.swing.JOptionPane.showConfirmDialog((java.awt.Component) null,
					"La copia del software no es legal.\nConsulte con los administradores de http://www.mitnick.com.ar", "Alerta!!",
					javax.swing.JOptionPane.DEFAULT_OPTION);
			System.exit(1);
		}
	}
	
	
	
}
