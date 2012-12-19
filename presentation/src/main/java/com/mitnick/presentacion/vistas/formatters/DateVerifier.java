package com.mitnick.presentacion.vistas.formatters;

import java.awt.Color;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.InputVerifier;

import com.mitnick.utils.DateHelper;

public class DateVerifier extends InputVerifier {
	private static final Color INVALID_COLOR = Color.red;
	private static final Color VALID_COLOR = Color.black;

	public DateVerifier() {
	}

	public DateVerifier(String mask) {
	}

	public boolean verify(javax.swing.JComponent jc) {
		FormattedDateField fdf = (FormattedDateField) jc;
		try {
			Date fecha = DateHelper.getFecha(fdf.getText());
			if (fecha!=null){
				jc.setBorder(BorderFactory.createLineBorder(VALID_COLOR));
				return true;
			} else {
				jc.setBorder(BorderFactory.createLineBorder(INVALID_COLOR));
				return false;
			}
		} catch (Exception e) {
			jc.setBorder(BorderFactory.createLineBorder(INVALID_COLOR));
			return false;
		}

	}

}
