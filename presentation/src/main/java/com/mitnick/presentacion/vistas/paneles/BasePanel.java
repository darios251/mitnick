package com.mitnick.presentacion.vistas.paneles;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.mitnick.exceptions.BaseException;
import com.mitnick.exceptions.PresentationException;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.Validator;

public abstract class BasePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	protected JPanel currentView = this;
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	protected JButton defaultButtonAction;
	
	protected boolean panelInicializado = false;
	
	protected JTextField defaultFocusField;
	
	public BasePanel () {
		this.centrarVentana( null );
		initializePanel();
	}
	
	public BasePanel ( Component parent ){
		
		this.centrarVentana(parent);
		initializePanel();
	}
	
	protected void initializePanel () {
		setFocusCycleRoot(true);
	}
	
	protected int mostrarMensaje(PresentationException ex) {
		switch(ex.getType()) {
		case BaseException.WARNING:
			return mostrarMensajeAdvertencia(ex.getMessage());
		case BaseException.ERROR:
		default:
			return mostrarMensajeError(ex.getMessage());
		}
	}
	
	protected int mostrarMensajeError ( String message ) {
		//Primero despliego un mensaje para confirmar la operacion
	     Object[] options = { PropertiesManager.getProperty( "dialog.error.okbutton" ) };
	     
	     return JOptionPane.showOptionDialog( currentView, message, PropertiesManager.getProperty( "dialog.error.titulo" ), JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[ 0 ] );
	}
	
	protected int mostrarMensajeAdvertencia ( String message ) {
		//Primero despliego un mensaje para confirmar la operacion
	     Object[] options = { PropertiesManager.getProperty( "dialog.warning.okbutton" ) };
	     
	     return JOptionPane.showOptionDialog( currentView, message, PropertiesManager.getProperty("dialog.warning.titulo"), JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[ 0 ] );
	}
	
	protected int mostrarMensajeConsulta( String message ) {
		//Primero despliego un mensaje para confirmar la operaci�n
	     Object[] options = { PropertiesManager.getProperty( "dialog.info.okbutton" ), PropertiesManager.getProperty( "dialog.info.cancelbutton" ) };
	     
	     return JOptionPane.showOptionDialog(currentView, message, PropertiesManager.getProperty( "dialog.info.titulo" ), JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[ 0 ] );
	}
	
	protected int mostrarMensajeInformativo ( String message ) {
		//Primero despliego un mensaje para confirmar la operaci�n
	     Object[] options = { PropertiesManager.getProperty( "dialog.info.okbutton" ) };
	     
	     return JOptionPane.showOptionDialog(currentView, message, PropertiesManager.getProperty( "dialog.info.titulo" ), JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[ 0 ] );
	}
	
	@Override
	public void setVisible(boolean aFlag) {
		if(aFlag) {
			if(!panelInicializado) {
				initializeComponents();
				setDefaultFocusField();
			}
			panelInicializado = true;
		}
		super.setVisible(aFlag);
		
		if(aFlag)
			if(Validator.isNotNull(defaultFocusField))
				defaultFocusField.requestFocus();
	}
	
	protected void centrarVentana ( Component parent ) {
	}
	
	protected abstract void limpiarCamposPantalla();
	
	protected abstract void initializeComponents();
	
	public abstract void actualizarPantalla();

	protected JTextField getDefaultFocusField() {
		return defaultFocusField;
	}

	public abstract void setDefaultFocusField();

}