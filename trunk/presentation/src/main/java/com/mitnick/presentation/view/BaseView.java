package com.mitnick.presentation.view;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import com.mitnick.utils.PropertiesManager;

public abstract class BaseView extends JPanel implements ITitlePane {

	private static final long serialVersionUID = 1L;

	protected JPanel currentView = this;
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	protected JButton defaultButtonAction;
	
	protected String title = null;  
	
	public BaseView () {
		
		this.centrarVentana( null );
		initializePanel();
	}
	
	public BaseView ( Component parent ){
		
		this.centrarVentana(parent);
		initializePanel();
	}
	
	protected void initializePanel () {
		
		this.initializeComponents();
	}

	protected void showDialogError ( String message ) {
		
		//Primero despliego un mensaje para confirmar la operaci�n
	     Object[] options = { PropertiesManager.getProperty( "dialog.error.okbutton" ) };
	     
	     JOptionPane.showOptionDialog( currentView, message, PropertiesManager.getProperty( "dialog.error.title" ), JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[ 0 ] );
	}
	
	protected void showDialogWarning ( String message ) {
		
		//Primero despliego un mensaje para confirmar la operaci�n
	     Object[] options = { PropertiesManager.getProperty( "dialog.warning.okbutton" ) };
	     
	     JOptionPane.showOptionDialog( currentView, message, PropertiesManager.getProperty("dialog.warning.title"), JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[ 0 ] );
	}
	
	protected void showDialogInfo ( String message ) {
		
		//Primero despliego un mensaje para confirmar la operaci�n
	     Object[] options = { PropertiesManager.getProperty( "dialog.info.okbutton" ) };
	     
	     JOptionPane.showOptionDialog(currentView, message, PropertiesManager.getProperty( "dialog.info.title" ), JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[ 0 ] );
	}
	
	protected void centrarVentana ( Component parent ) {
	}
	
	protected abstract void limpiarCamposPantalla();
	
	protected abstract void initializeComponents();	
}