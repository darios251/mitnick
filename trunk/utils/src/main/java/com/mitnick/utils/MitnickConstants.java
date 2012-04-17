package com.mitnick.utils;

import com.mitnick.utils.dtos.MarcaDto;
import com.mitnick.utils.dtos.TipoDto;

public class MitnickConstants {
	public static final String DATE_FORMAT = PropertiesManager.getProperty("application.date.pattern");
	
	public static final TipoDto tipoTodos = new TipoDto(-1l);
	public static final MarcaDto marcaTodos = new MarcaDto(-1l);
}

