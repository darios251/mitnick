package com.mitnick.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public class DateHelper {

	protected static Logger logger = Logger.getLogger(DateHelper.class);

	private static SimpleDateFormat sdf;

	public static Date getFecha(String fecha) {
		try {
			return getParser().parse(fecha);
		} catch (ParseException e) {
			logger.error(e);
		}
		return null;
	}

	public static String getFecha(Date fecha) {
		return getParser().format(fecha);
	}

	public static SimpleDateFormat getParser() {
		if (sdf == null) {
			sdf = new SimpleDateFormat(MitnickConstants.DATE_FORMAT);
			sdf.setLenient(false);
		}
		return sdf;
	}

	public static String getMes(int i) {
		switch (i) {
		case 0:
			return "Enero";
		case 1:
			return "Febrero";
		case 2:
			return "Marzo";
		case 3:
			return "Abril";
		case 4:
			return "Mayo";
		case 5:
			return "Junio";
		case 6:
			return "Julio";
		case 7:
			return "Agosto";
		case 8:
			return "Septiembre";
		case 9:
			return "Octubre";
		case 10:
			return "Noviembre";
		case 11:
			return "Diciembre";
		}
		return "";
	}
}
