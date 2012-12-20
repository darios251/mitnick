package com.mitnick.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public class DateHelper {
	
	protected static Logger logger = Logger.getLogger(DateHelper.class);
	
	private static SimpleDateFormat sdf;
	
	public static Date getFecha(String fecha){
		try {			
			return getParser().parse(fecha);
		} catch (ParseException e) {
			logger.error(e);
		}
		return null;
	}
	
	public static String getFecha(Date fecha){	
		return getParser().format(fecha);		
	}
	
	public static SimpleDateFormat getParser(){
		if (sdf==null){
			sdf= new SimpleDateFormat(MitnickConstants.DATE_FORMAT);
			sdf.setLenient(false);
		}
		return sdf;
	}
	
}
