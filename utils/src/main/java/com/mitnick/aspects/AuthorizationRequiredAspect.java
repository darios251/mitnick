package com.mitnick.aspects;

import java.awt.KeyboardFocusManager;
import java.awt.Window;

import javax.swing.JOptionPane;

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

	@Around("@annotation(autorizationRequired)")
	public Object autorizationRequired(ProceedingJoinPoint pjp, AuthorizationRequired autorizationRequired) throws Throwable {
		Window focusedWindow = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusedWindow();
		
		String input = JOptionPane.showInputDialog(focusedWindow, PropertiesManager.getProperty("dialog.auth.message"),PropertiesManager.getProperty("dialog.warning.titulo"), JOptionPane.DEFAULT_OPTION);
		
		if(Validator.isNull(input))
			throw new PresentationException(PropertiesManager.getProperty("dialog.auth.request.error.null"));
		
		try {
			loginUtils.authenticate((String) SecurityContextHolder.getContext().getAuthentication().getCredentials(), input);
		}
		catch (BadCredentialsException e) {
			throw new PresentationException(PropertiesManager.getProperty("dialog.auth.request.error"));
		}
		
		return pjp.proceed();
	}
}
