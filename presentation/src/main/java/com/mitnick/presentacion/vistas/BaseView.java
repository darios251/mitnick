package com.mitnick.presentacion.vistas;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

public abstract class BaseView extends JPanel {

	private static final long serialVersionUID = 1L;

	protected JPanel currentView = this;
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	protected boolean initialized = false;
	
	protected abstract void initializeComponents();

}