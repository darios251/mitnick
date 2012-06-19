package com.mitnick.utils.locator;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanLocator {
	
	private static BeanFactory factory = null;
	
	public static Object getBean(String name)
	{
		return getFactory().getBean(name);
	}

	private static BeanFactory getFactory() {
		if(factory == null)
		{
			factory = new ClassPathXmlApplicationContext("applicationContext-presentation.xml", "applicationContext-utils.xml",
					"applicationContext-business.xml", "applicationContext-persistence.xml");
		}
		return factory;
	}
}
