package com.mitnick.presentacion.controladores;

import java.lang.reflect.Field;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.log4j.Logger;

import com.mitnick.exceptions.PresentationException;
import com.mitnick.presentacion.vistas.paneles.BasePanel;
import com.mitnick.utils.dtos.BaseDto;

/**
 * Esta clase tiene la responsabilidad de representar la clase base
 * de la cual deben extender los controllers.
 * 
 * @author Lucas Garc�a
 *
 */
public abstract class BaseController {
	protected Logger logger = Logger.getLogger(this.getClass());
	
	protected BasePanel<? extends BaseController> ultimoPanelMostrado = null;
	
	protected static ValidatorFactory entityValidatorFactory; 
	protected static Validator entityValidator;
	
	static {
		entityValidatorFactory = Validation.buildDefaultValidatorFactory();
		entityValidator = entityValidatorFactory.getValidator();
	}
	
	private Set<ConstraintViolation<Object>> validateDto(Object dto, Set<ConstraintViolation<Object>> constraintViolations) throws PresentationException {
		if(constraintViolations == null)
			constraintViolations = entityValidator.validate(dto);
		else
			constraintViolations.addAll(entityValidator.validate(dto));
		
		validateChildrens(dto, constraintViolations);
		
		return constraintViolations;
	}
	
	protected void validateDto(Object dto) throws PresentationException {
		cleanFields();
		Set<ConstraintViolation<Object>> constraintViolations = validateDto(dto, null);
		if(com.mitnick.utils.Validator.isNotEmptyOrNull(constraintViolations))
			throw new PresentationException(constraintViolations);
	}

	@SuppressWarnings("rawtypes")
	public void cleanFields() {
		if(ultimoPanelMostrado != null) {
			Field[] fields = ultimoPanelMostrado.getClass().getDeclaredFields();
			
			for(Field fieldError : fields) {
	    		 try {
					fieldError.setAccessible(true);
					
					Object field = fieldError.get(ultimoPanelMostrado);
					if(field instanceof JTextField) {
						JTextField textField = (JTextField) field;
						textField.setBorder(new JTextField().getBorder());
						if(textField.getBorder() instanceof TitledBorder) {
							textField.setSize(textField.getWidth() - 150, textField.getHeight() - 20);
							textField.setLocation(textField.getX(), textField.getY() + 12);
						}
					}
					else if(field instanceof JComboBox ) {
						JComboBox textField = (JComboBox) field;
						textField.setBorder(new JComboBox().getBorder());
					}
					else if(field instanceof JLabel ) {
						if(fieldError.getName().startsWith("lblError")) {
							JLabel lblError = (JLabel) field;
							lblError.setText("");
						}
					}
				} catch (Exception e) {
				}
			}
		}
	}

	private void validateChildrens(Object dto, Set<ConstraintViolation<Object>> constraintViolations) {
		Field[] fields = dto.getClass().getDeclaredFields();
		
		for(Field field : fields) {
			try {
				field.setAccessible(true);
				Object objectField = field.get(dto);
				if(objectField instanceof BaseDto)
					validateDto((BaseDto) objectField, constraintViolations);
			} catch (Exception e) {}
		}
	}
	
	public BasePanel<? extends BaseController> getUltimoPanelMostrado() {
		return ultimoPanelMostrado;
	}
	
	public void setUltimoPanelMostrado(BasePanel<? extends BaseController> ultimoPanelMostrado) {
		this.ultimoPanelMostrado = ultimoPanelMostrado;
	}
	
	public abstract void mostrarUltimoPanelMostrado();
}
