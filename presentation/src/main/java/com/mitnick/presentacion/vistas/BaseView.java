package com.mitnick.presentacion.vistas;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import com.mitnick.utils.PropertiesManager;

public abstract class BaseView extends JPanel {

	private static final long serialVersionUID = 1L;

	protected JPanel currentView = this;
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	protected JButton defaultButtonAction;
	
	protected boolean panelInicializado = false;
	
	public BaseView () {
		this.centrarVentana( null );
		initializePanel();
	}
	
	public BaseView ( Component parent ){
		
		this.centrarVentana(parent);
		initializePanel();
	}
	
	protected void initializePanel () {

	}
	
	protected int mostrarMensajeError ( String message ) {
		//Primero despliego un mensaje para confirmar la operaci�n
	     Object[] options = { PropertiesManager.getProperty( "dialog.error.okbutton" ) };
	     
	     return JOptionPane.showOptionDialog( currentView, message, PropertiesManager.getProperty( "dialog.error.titulo" ), JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[ 0 ] );
	}
	
	protected int mostrarMensajeAdvertencia ( String message ) {
		//Primero despliego un mensaje para confirmar la operaci�n
	     Object[] options = { PropertiesManager.getProperty( "dialog.warning.okbutton" ) };
	     
	     return JOptionPane.showOptionDialog( currentView, message, PropertiesManager.getProperty("dialog.warning.titulo"), JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[ 0 ] );
	}
	
	protected int mostrarMensajeInformativo ( String message ) {
		//Primero despliego un mensaje para confirmar la operaci�n
	     Object[] options = { PropertiesManager.getProperty( "dialog.info.okbutton" ) };
	     
	     return JOptionPane.showOptionDialog(currentView, message, PropertiesManager.getProperty( "dialog.info.titulo" ), JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[ 0 ] );
	}
	
	@Override
	public void setVisible(boolean aFlag) {
		if(!panelInicializado)
			initializeComponents();
		panelInicializado = true;
		super.setVisible(aFlag);
	}
	
	protected void centrarVentana ( Component parent ) {
	}
	
	protected abstract void limpiarCamposPantalla();
	
	protected abstract void initializeComponents();

}