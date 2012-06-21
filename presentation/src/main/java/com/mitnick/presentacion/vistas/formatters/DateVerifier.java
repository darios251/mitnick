package com.mitnick.presentacion.vistas.formatters;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.InputVerifier;

public class DateVerifier extends InputVerifier {
	private static final Color INVALID_COLOR = Color.red;
	private static final Color VALID_COLOR = Color.black;

	SimpleDateFormat sdf = null; // formatted used to check date formats

	public DateVerifier() {
		sdf = new SimpleDateFormat();
	}

	public DateVerifier(String mask) {
		sdf = new SimpleDateFormat(mask);
	}

	public boolean verify(javax.swing.JComponent jc) {
		FormattedDateField fdf = (FormattedDateField) jc;
		try {
			Date d = sdf.parse(fdf.getText()); // note this allows months > 12,
												// days > 31
			jc.setBorder(BorderFactory.createLineBorder(VALID_COLOR));
			return true;
		} catch (Exception e) {
			jc.setBorder(BorderFactory.createLineBorder(INVALID_COLOR));
			return false;
		}

	}

}
