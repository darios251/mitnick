package com.mitnick.presentacion.vistas.paneles;

import java.awt.Color;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.validation.ConstraintViolation;

import org.apache.log4j.Logger;

import com.mitnick.exceptions.BaseException;
import com.mitnick.exceptions.PresentationException;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.Validator;

public class BaseDialog extends JDialog  implements KeyEventDispatcher {

	private static final long serialVersionUID = 1L;
	
	private static BaseDialog currentDialog = null;
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	public static void setCurrentDialog(BaseDialog currentDial) {
		currentDialog = currentDial;
	}
	
	public static BaseDialog getCurrentDialog() {
		return currentDialog;
	}
	
	public BaseDialog(JFrame frame, boolean b) {
		super(frame, b);
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
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
		
		return JOptionPane.showOptionDialog( this, PropertiesManager.getProperty(message), PropertiesManager.getProperty( "dialog.error.titulo" ), JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[ 0 ] );
	}
	
	protected int mostrarMensajeError ( PresentationException ex ) {
		//Primero despliego un mensaje para confirmar la operacion
	     Object[] options = { PropertiesManager.getProperty( "dialog.error.okbutton" ) };
	     boolean popup = true;
    	 
	     if(Validator.isNotEmptyOrNull(ex.getConstraintViolations())) {
	    	 popup = false;
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
					logger.error(e);
				}
	    	 }
	     }
	     if (popup)	     
	    	 return JOptionPane.showOptionDialog( this, ex.getMessage(), PropertiesManager.getProperty( "dialog.error.titulo" ), JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[ 0 ] );
	     
	     return 1;
	}
	
	protected int mostrarMensajeAdvertencia ( String message ) {
		//Primero despliego un mensaje para confirmar la operacion
	     Object[] options = { PropertiesManager.getProperty( "dialog.warning.okbutton" ) };
	     
	     return JOptionPane.showOptionDialog( this, message, PropertiesManager.getProperty("dialog.warning.titulo"), JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[ 0 ] );
	}
	
	protected int mostrarMensajeConsulta( String message ) {
		//Primero despliego un mensaje para confirmar la operación
	     Object[] options = { PropertiesManager.getProperty( "dialog.info.okbutton" ), PropertiesManager.getProperty( "dialog.info.cancelbutton" ) };
	     
	     return JOptionPane.showOptionDialog(this, message, PropertiesManager.getProperty( "dialog.info.titulo" ), JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[ 0 ] );
	}
	
	protected int mostrarMensajeInformativo ( String message ) {
		//Primero despliego un mensaje para confirmar la operación
	     Object[] options = { PropertiesManager.getProperty( "dialog.info.okbutton" ) };
	     
	     return JOptionPane.showOptionDialog(this, message, PropertiesManager.getProperty( "dialog.info.titulo" ), JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[ 0 ] );
	}
	
	@Override
	public void setVisible(boolean b) {
		if(b)
			currentDialog = this;
		super.setVisible(b);
	}
	
	private static List<Integer> objectIds = new ArrayList<Integer>();
	
	@Override
	public synchronized boolean dispatchKeyEvent(KeyEvent e) {
		if(this.isVisible() && e.getID() == KeyEvent.KEY_RELEASED) {
			int identityHashCode = System.identityHashCode(e);
			if(!objectIds.contains(identityHashCode)) {
				objectIds.add(identityHashCode);
				switch (e.getKeyCode()) {
					case KeyEvent.VK_SUBTRACT: keySubstract(); break;
					case KeyEvent.VK_MULTIPLY: keyMultiply(); break;
					case KeyEvent.VK_ENTER: keyIntro(); break;
					case KeyEvent.VK_ADD: keyAdd();	break;
					case KeyEvent.VK_F1: keyF1(); break;
					case KeyEvent.VK_F2: keyF2(); break;
					case KeyEvent.VK_F3: keyF3(); break;
					case KeyEvent.VK_F4: keyF4(); break;
					case KeyEvent.VK_F5: keyF5(); break;
					case KeyEvent.VK_F6: keyF6(); break;
					case KeyEvent.VK_F7: keyF7(); break;
					case KeyEvent.VK_F8: keyF8(); break;
					case KeyEvent.VK_F9: keyF9(); break;
					case KeyEvent.VK_F10: keyF10();	break;
					case KeyEvent.VK_F11: keyF11(); break;
					case KeyEvent.VK_F12: keyF12();	break;
					case KeyEvent.VK_ESCAPE: keyEscape(); break;
					case KeyEvent.VK_PAGE_DOWN:	keyPageDown(); break;
					case KeyEvent.VK_PAGE_UP: keyPageUp(); break;
					case KeyEvent.VK_HOME: keyHome(); break;
					case KeyEvent.VK_END: keyEnd();	break;
					case KeyEvent.VK_INSERT: keyInsert(); break;
					case KeyEvent.VK_DELETE: keySupr(); break;
					default: break;
				}
			}
		}
		return false;
	}
	
	protected void keyPageDown() {}	
	protected void keyPageUp() {}
	protected void keyHome() {}
	protected void keyEnd() {}	
	protected void keyInsert() {}	
	protected void keySupr() {
		keySubstract();
	}
	protected void keyEscape() {}	
	protected void keyIntro() {}
	protected void keyAdd() {}	
	protected void keyMultiply() {}	
	protected void keySubstract() {}	
	protected void keyF1() {}	
	protected void keyF2() {}	
	protected void keyF3() {}	
	protected void keyF4() {}	
	protected void keyF5() {}	
	protected void keyF6() {}	
	protected void keyF7() {}	
	protected void keyF8() {}	
	protected void keyF9() {}	
	protected void keyF10() {}	
	protected void keyF11() {}	
	protected void keyF12() {}
	
}
