package com.mitnick.utils.locator;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class BeanLocator {
	
	private static BeanFactory factory = null;
	
	public static Object getBean(String name)
	{
		return getFactory().getBean(name);
	}

	private static BeanFactory getFactory() {
		if(factory == null)
		{
			factory = new FileSystemXmlApplicationContext("../utils/src/main/resources/applicationContext-utils.xml", "src/main/resources/applicationContext-presentation.xml",
					"../business/src/main/resources/applicationContext-business.xml",
					"../persistence/src/main/resources/applicationContext-persistence.xml");
		}
		return factory;
	}
}
