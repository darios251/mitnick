package com.mitnick.utils;

import com.mitnick.utils.dtos.MarcaDto;
import com.mitnick.utils.dtos.TipoDto;

public class MitnickConstants {
	public static final String DATE_FORMAT = PropertiesManager.getProperty("application.date.pattern");
	
	public static final String DATE_MASKFORMAT = "##/##/####";
	public static final String CUIT_MASKFORMAT = "##-########-#";

	
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
	
	public static int VENTA = 1;
	public static int DEVOLUCION = 2;

	public class Role {
		public static final String ADMIN = "ROLE_ADMIN";
		
		public static final String USER = "ROLE_USER";
	}
	
	public static class Medio_Pago {
		public static final String EFECTIVO = PropertiesManager.getProperty("application.medio_pago.efectivo");
		public static final String CUENTA_CORRIENTE = PropertiesManager.getProperty("application.medio_pago.cuenta_corriente");
		public static final String DEBITO = PropertiesManager.getProperty("application.medio_pago.debito");
		public static final String CREDITO = PropertiesManager.getProperty("application.medio_pago.credito");
		public static final String NOTA_CREDITO = PropertiesManager.getProperty("application.medio_pago.nota_credito");
	}
	
	public static class TipoComprador {
		public static final String RESPONSABLE_INSCRIPTO = PropertiesManager.getProperty("application.tipoComprador.responsableInscripto");
		public static final String NO_RESPONSABLE = PropertiesManager.getProperty("application.tipoComprador.noResponsable");
		public static final String MONOTRIBUTISTA = PropertiesManager.getProperty("application.tipoComprador.monotributista");
		public static final String EXENTO = PropertiesManager.getProperty("application.tipoComprador.exento");
		public static final String NO_CATEGORIZADO = PropertiesManager.getProperty("application.tipoComprador.noCategorizado");
		public static final String CONSUMIDOR_FINAL = PropertiesManager.getProperty("application.tipoComprador.consumidorFinal");
		public static final String MONOTRIBUTISTA_SOCIAL = PropertiesManager.getProperty("application.tipoComprador.monotributistaSocial");
		public static final String CONTRIBUYENTE_EVENTUAL = PropertiesManager.getProperty("application.tipoComprador.contribuyenteEventual");
		public static final String CONTRIBUYENTE_EVENTUAL_SOCIAL = PropertiesManager.getProperty("application.tipoComprador.contribuyenteEventualSocial");
		
		public static final String RESPONSABLE_INSCRIPTO_DESC = PropertiesManager.getProperty("application.tipoComprador.descripcion.responsableInscripto");
		public static final String NO_RESPONSABLE_DESC = PropertiesManager.getProperty("application.tipoComprador.descripcion.noResponsable");
		public static final String MONOTRIBUTISTA_DESC = PropertiesManager.getProperty("application.tipoComprador.descripcion.monotributista");
		public static final String EXENTO_DESC = PropertiesManager.getProperty("application.tipoComprador.descripcion.exento");
		public static final String NO_CATEGORIZADO_DESC = PropertiesManager.getProperty("application.tipoComprador.descripcion.noCategorizado");
		public static final String CONSUMIDOR_FINAL_DESC = PropertiesManager.getProperty("application.tipoComprador.descripcion.consumidorFinal");
		public static final String MONOTRIBUTISTA_SOCIAL_DESC = PropertiesManager.getProperty("application.tipoComprador.descripcion.monotributistaSocial");
		public static final String CONTRIBUYENTE_EVENTUAL_DESC = PropertiesManager.getProperty("application.tipoComprador.descripcion.contribuyenteEventual");
		public static final String CONTRIBUYENTE_EVENTUAL_SOCIAL_DESC = PropertiesManager.getProperty("application.tipoComprador.descripcion.contribuyenteEventualSocial");
		
		public static TipoComprador getTipoComprador(String tipoComprador){
			return null;
		}
	}
}


;