package com.mitnick.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public class DateHelper {
	
	protected static Logger logger = Logger.getLogger(DateHelper.class);
	
	public static SimpleDateFormat sdf= new SimpleDateFormat(MitnickConstants.DATE_FORMAT);
	
	public static Date getFecha(String fecha){
		try {
			return sdf.parse(fecha);
		} catch (ParseException e) {
			logger.error(e);
		}
		return null;
	}
	
	public static String getFecha(Date fecha){
		return sdf.format(fecha);		
	}
	
}
