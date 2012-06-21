package com.mitnick.presentacion.vistas.formatters;

import java.awt.Color;
import java.io.Serializable;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.border.Border;
import javax.swing.text.DefaultFormatter;

public class CuitFormatter extends DefaultFormatter implements Serializable {
	private static final String CUIT_PATTERN = "^[0-9\\-]{12,13}$";

	private static Pattern pattern = Pattern.compile(CUIT_PATTERN,
			Pattern.CASE_INSENSITIVE);

	private JFormattedTextField field;
	
	private Border defaultBorder;

	/**
	 * Default constructor
	 */
	public CuitFormatter() {

	}

	public CuitFormatter(JFormattedTextField field) {
		this.field = field;
		this.defaultBorder = field.getBorder();
	}

	/**
	 * Converts the value object to a string
	 * 
	 * @param value
	 *            current value of the JFormattedTextField
	 * @return string representation
	 */
	public String valueToString(Object value) {
		if (value != null)
			return value.toString();
		else
			return "";
	}

	/**
	 * Converts the String to an object, verifying its contents. Throws a
	 * ParseException if the string is not in valid email format.
	 * 
	 * @param text
	 *            from the JFormattedTextField
	 * @return
	 * @throws java.text.ParseException
	 */
	public Object stringToValue(String text) throws ParseException {
		Matcher m = pattern.matcher(text);
		if (!m.matches()) {
			field.setBorder(BorderFactory.createLineBorder(Color.red));
			throw new ParseException(text, 0);
		} else {
			field.setBorder(defaultBorder);
			return text;
		}
	}
}
