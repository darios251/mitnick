package com.mitnick.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import com.mitnick.exceptions.BusinessException;
import com.mitnick.exceptions.PersistenceException;
import com.mitnick.exceptions.PresentationException;

@Aspect
@Component
public class ExceptionAspect {

	@AfterThrowing(pointcut = "execution(* com.mitnick.presentacion.controladores..*(..))", throwing = "exception")
	public void presentationExceptionAspect(JoinPoint joinPoint, final Exception exception) throws Throwable {
		if(exception instanceof AccessDeniedException) {
			throw new PresentationException("error.access.denied", "El usuario no tiene los permisos necesarios para realizar la acción");
		}
		else if(!(exception instanceof PresentationException))
			throw new PresentationException("error.unknown", exception.getMessage());
    }
	
	@AfterThrowing(pointcut = "execution(* com.mitnick.business.servicios..*(..))", throwing = "exception")
	public void businessExceptionAspect(JoinPoint joinPoint, final Exception exception) throws Throwable {
		if(exception instanceof AccessDeniedException) {
			throw new BusinessException("error.access.denied", "El usuario no tiene los permisos necesarios para realizar la acción");
		}
		else if(!(exception instanceof BusinessException))
			throw new BusinessException("error.unknown", exception.getMessage());
    }
	
	@AfterThrowing(pointcut = "execution(* com.mitnick.persistence.daos..*(..))", throwing = "exception")
	public void persistenceExceptionAspect(JoinPoint joinPoint, final Exception exception) throws Throwable {
		if(exception instanceof AccessDeniedException) {
			throw new PersistenceException("error.access.denied", "El usuario no tiene los permisos necesarios para realizar la acción");
		}
		else if(!(exception instanceof PersistenceException))
			throw new PersistenceException("error.unknown", exception.getMessage());
    }
	
}
