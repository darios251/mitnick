package com.mitnick.utils;

import java.text.SimpleDateFormat;

import com.mitnick.utils.dtos.MarcaDto;
import com.mitnick.utils.dtos.TipoDto;

public class MitnickConstants {
	public static final String DATE_FORMAT = PropertiesManager.getProperty("application.date.pattern");
	public static final SimpleDateFormat dateFormatter = new SimpleDateFormat(MitnickConstants.DATE_FORMAT);
	
	public static final TipoDto tipoTodos = new TipoDto(-1l);
	public static final MarcaDto marcaTodos = new MarcaDto(-1l);
	
	public static final int IVA_RESPONSABLE_INSCRIPTO = 1;
	public static final int CONSUMIDOR_FINAL = 2;

	public static final int ACCESS_BAR_BUTTON_WIDTH = Integer.parseInt(PropertiesManager.getProperty("application.accessBar.button.width"));
	public static final int ACCESS_BAR_BUTTON_HEIGHT = Integer.parseInt(PropertiesManager.getProperty("application.accessBar.button.height"));
	
	public static final int ACCESS_BAR_ICON_WIDTH = Integer.parseInt(PropertiesManager.getProperty("application.accessBar.icon.width"));
	public static final int ACCESS_BAR_ICON_HEIGHT = Integer.parseInt(PropertiesManager.getProperty("application.accessBar.icon.height"));
	
	public static final int PANEL_BUTTON_WIDTH = Integer.parseInt(PropertiesManager.getProperty("application.panel.button.width"));
	public static final int PANEL_BUTTON_HEIGHT = Integer.parseInt(PropertiesManager.getProperty("application.panel.button.height"));
	
	public static final int PANEL_ICON_WIDTH = Integer.parseInt(PropertiesManager.getProperty("application.panel.icon.width"));
	public static final int PANEL_ICON_HEIGHT = Integer.parseInt(PropertiesManager.getProperty("application.panel.icon.height"));
	
	public class Role {
		public static final String ADMIN = "ROLE_ADMIN";
		
		public static final String USER = "ROLE_USER";
	}
	
	public static class Medio_Pago {
		public static final String EFECTIVO = PropertiesManager.getProperty("application.medio_pago.efectivo");
		public static final String CUENTA_CORRIENTE = PropertiesManager.getProperty("application.medio_pago.cuenta_corriente");
		public static final String DEBITO = PropertiesManager.getProperty("application.medio_pago.debito");
		public static final String CREDITO = PropertiesManager.getProperty("application.medio_pago.credito");
	}
}

