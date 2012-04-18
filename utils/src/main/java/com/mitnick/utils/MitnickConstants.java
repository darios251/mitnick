package com.mitnick.utils;

import com.mitnick.utils.dtos.MarcaDto;
import com.mitnick.utils.dtos.TipoDto;

public class MitnickConstants {
	public static final String DATE_FORMAT = PropertiesManager.getProperty("application.date.pattern");
	
	public static final TipoDto tipoTodos = new TipoDto(-1l);
	public static final MarcaDto marcaTodos = new MarcaDto(-1l);

	public static final int ACCESS_BAR_BUTTON_WIDTH = Integer.parseInt(PropertiesManager.getProperty("application.accessBar.button.width"));
	public static final int ACCESS_BAR_BUTTON_HEIGHT = Integer.parseInt(PropertiesManager.getProperty("application.accessBar.button.height"));
	
	public static final int ACCESS_BAR_ICON_WIDTH = Integer.parseInt(PropertiesManager.getProperty("application.accessBar.icon.width"));
	public static final int ACCESS_BAR_ICON_HEIGHT = Integer.parseInt(PropertiesManager.getProperty("application.accessBar.icon.height"));
	
	public static final int PANEL_BUTTON_WIDTH = Integer.parseInt(PropertiesManager.getProperty("application.panel.button.width"));
	public static final int PANEL_BUTTON_HEIGHT = Integer.parseInt(PropertiesManager.getProperty("application.panel.button.height"));
	
	public static final int PANEL_ICON_WIDTH = Integer.parseInt(PropertiesManager.getProperty("application.panel.icon.width"));
	public static final int PANEL_ICON_HEIGHT = Integer.parseInt(PropertiesManager.getProperty("application.panel.icon.height"));
}

