package com.mitnick.presentacion.vistas.controles;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import com.mitnick.utils.anotaciones.Panel;

@Panel("detailPanel")
public class DetailPanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	public DetailPanel()
	{
		setOpaque(false);
	}

	protected void paintComponent(Graphics g)
	{
	    int x = 5;
	    int y = 7;
	    int w = getWidth() - 11;
	    int h = getHeight() - 15;
	    int arc = 35;

	    Graphics2D g2 = (Graphics2D) g.create();
	    
	    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

	    g2.setColor(new Color(238,238,238));
	    g2.fillRoundRect(x, y, w, h, arc, arc);

	    g2.setStroke(new BasicStroke(3f));
	    g2.setColor(Color.WHITE);
	    g2.drawRoundRect(x, y, w, h, arc, arc); 
	 
	    g2.dispose();
	}
}