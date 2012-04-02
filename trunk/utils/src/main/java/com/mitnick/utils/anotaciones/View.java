package com.mitnick.utils.anotaciones;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
* Indicates that an annotated class is a "Controller" (e.g. a web controller).
*
* <p>This annotation serves as a specialization of {@link Component @Component},
* allowing for implementation classes to be autodetected through classpath scanning.
* It is typically used in combination with annotated handler methods based on the
* {@link org.springframework.web.bind.annotation.RequestMapping} annotation.
*
* @author Lucas García
* @since 1.0
* @see Component
* @see org.springframework.web.bind.annotation.RequestMapping
* @see org.springframework.context.annotation.ClassPathBeanDefinitionScanner
*/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface View {

	/**
	 * The value may indicate a suggestion for a logical component name,
	 * to be turned into a Spring bean in case of an autodetected component.
	 * @return the suggested component name, if any
	 */
	String value() default "";

}
