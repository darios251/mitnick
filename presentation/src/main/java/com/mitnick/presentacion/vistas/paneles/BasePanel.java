package com.mitnick.presentacion.vistas.paneles;

import java.awt.Color;
import java.awt.Component;
import java.lang.reflect.Field;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.validation.ConstraintViolation;

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
			return mostrarMensajeError(ex);
		}
	}
	
	protected int mostrarMensajeError ( String message ) {
		Object[] options = { PropertiesManager.getProperty( "dialog.error.okbutton" ) };
		
		return JOptionPane.showOptionDialog( currentView, message, PropertiesManager.getProperty( "dialog.error.titulo" ), JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[ 0 ] );
	}
	
	protected int mostrarMensajeError ( PresentationException ex ) {
		//Primero despliego un mensaje para confirmar la operacion
	     Object[] options = { PropertiesManager.getProperty( "dialog.error.okbutton" ) };
	     
	     if(Validator.isNotEmptyOrNull(ex.getConstraintViolations())) {
	    	 String firstError = null;
	    	 
	    	 for(ConstraintViolation<Object> constraintViolation : ex.getConstraintViolations()) {
	    		 String fieldName = constraintViolation.getPropertyPath().toString().substring(0, 1).toUpperCase() + constraintViolation.getPropertyPath().toString().substring(1);
	    		 try {
					Field fieldError = this.getClass().getDeclaredField("txt" + fieldName);
					fieldError.setAccessible(true);
					JTextField field = (JTextField) fieldError.get(this);
					if(firstError == null)
						firstError = "txt" + fieldName;
//					field.setSize(field.getWidth() + 150, field.getHeight() + 20);
//					field.setLocation(field.getX(), field.getY() - 12);
					field.setBorder(BorderFactory.createLineBorder(Color.red));
					
					Field lblFieldError = this.getClass().getDeclaredField("lblErrorTxt" + fieldName);
					lblFieldError.setAccessible(true);
					JLabel lblError = (JLabel) lblFieldError.get(this);
					lblError.setText(constraintViolation.getMessage());
					lblError.setForeground(Color.red);
//					TitledBorder titled;
//
//			        titled = BorderFactory.createTitledBorder(constraintViolation.getMessage());
//			        titled.setTitleColor(Color.red);
//			        titled.setBorder(BorderFactory.createLineBorder(Color.red));
//			        field.setBorder(titled);
				} catch (Exception e) {
					Field fieldError;
					try {
						fieldError = this.getClass().getDeclaredField("cmb" + fieldName);
						fieldError.setAccessible(true);
						@SuppressWarnings("rawtypes")
						JComboBox field = (JComboBox) fieldError.get(this);
						field.setBorder(BorderFactory.createLineBorder(Color.red));
						
						Field lblFieldError = this.getClass().getDeclaredField("lblErrorCmb" + fieldName);
						lblFieldError.setAccessible(true);
						JLabel lblError = (JLabel) lblFieldError.get(this);
						lblError.setText(constraintViolation.getMessage());
						lblError.setForeground(Color.red);
					} catch (Exception e1) {
					}
				}
	    		 
	    	 }
	    	 
	    	 if(firstError != null) {
    			Field fieldError;
				try {
					fieldError = this.getClass().getDeclaredField(firstError);
					fieldError.setAccessible(true);
					JTextField field = (JTextField) fieldError.get(this);
					field.requestFocus();
					field.selectAll();
				} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
	    	 }
	     }
	     
	     return JOptionPane.showOptionDialog( currentView, ex.getMessage(), PropertiesManager.getProperty( "dialog.error.titulo" ), JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[ 0 ] );
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
			setDefaultButton();
		}
		super.setVisible(aFlag);
		
		if(aFlag) {
			this.limpiarCamposPantalla();
//			this.actualizarPantalla();
			setFocusTraversalPolicy();
			if(Validator.isNotNull(defaultFocusField))
				defaultFocusField.requestFocus();
		}
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
	
	protected abstract void setFocusTraversalPolicy();
	
	protected abstract void setDefaultButton();
	

}