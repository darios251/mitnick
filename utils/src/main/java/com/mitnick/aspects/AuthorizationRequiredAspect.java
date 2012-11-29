package com.mitnick.aspects;

import java.awt.KeyboardFocusManager;
import java.awt.Window;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.mitnick.exceptions.PresentationException;
import com.mitnick.utils.LoginUtils;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.Validator;
import com.mitnick.utils.anotaciones.AuthorizationRequired;

@Aspect
@Component
public class AuthorizationRequiredAspect {
	
	@Autowired
	LoginUtils loginUtils;

	@SuppressWarnings("deprecation")
	@Around("@annotation(autorizationRequired)")
	public Object autorizationRequired(ProceedingJoinPoint pjp, AuthorizationRequired autorizationRequired) throws Throwable {
		Window focusedWindow = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusedWindow();
		
		
		String password = null;
		JPasswordField passwordField = new JPasswordField();
		Object[] obj = {PropertiesManager.getProperty("dialog.auth.message") + ":\n\n", passwordField};
		Object stringArray[] = {"OK","Cancel"};
		if (JOptionPane.showOptionDialog(focusedWindow, obj, PropertiesManager.getProperty("dialog.warning.titulo"),
		JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, stringArray, obj) == JOptionPane.YES_OPTION)
		password = passwordField.getText();
		
		if(Validator.isNull(password))
			return null;//throw new PresentationException(PropertiesManager.getProperty("dialog.auth.request.error.null"));
		
		try {
			loginUtils.authenticate((String) SecurityContextHolder.getContext().getAuthentication().getCredentials(), password);
		}
		catch (BadCredentialsException e) {
			throw new PresentationException(PropertiesManager.getProperty("dialog.auth.request.error"));
		}
		
		return pjp.proceed();
	}
}
