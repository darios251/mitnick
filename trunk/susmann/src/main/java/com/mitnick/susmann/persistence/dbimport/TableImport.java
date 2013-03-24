package com.mitnick.susmann.persistence.dbimport;

import java.io.File;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import nl.knaw.dans.common.dbflib.DbfLibException;
import nl.knaw.dans.common.dbflib.Field;
import nl.knaw.dans.common.dbflib.IfNonExistent;
import nl.knaw.dans.common.dbflib.Record;
import nl.knaw.dans.common.dbflib.Table;
import nl.knaw.dans.common.dbflib.ValueTooLargeException;

public class TableImport {

	private static void importDataBase() {
		String path = "C:/project/mitnick/archivosSusmann/Copia (2) de comersis/";
		String file = path + "DEUDORES.DBF";
		// InputStream inputStream = new FileInputStream(file);
		final Table table = new Table(new File(file));
		try {
			table.open(IfNonExistent.ERROR);

			final Format dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			System.out.println("TABLE PROPERTIES");
			System.out.println("Name          : " + table.getName());
			System.out.println("Last Modified : "
					+ dateFormat.format(table.getLastModifiedDate()));
			System.out.println("--------------");
			System.out.println();
			System.out.println("FIELDS (COLUMNS)");

			final List<Field> fields = table.getFields();

			for (final Field field : fields) {
				System.out.println("  Name       : " + field.getName());
				System.out.println("  Type       : " + field.getType());
				System.out.println("  Length     : " + field.getLength());
				System.out.println("  Dec. Count : " + field.getDecimalCount());
				System.out.println();
			}

			System.out.println("--------------");
			System.out.println();
			System.out.println("RECORDS");

			final Iterator<Record> recordIterator = table.recordIterator();
			int count = 0;

			while (recordIterator.hasNext()) {
				final Record record = recordIterator.next();
				System.out.println(count++);

				for (final Field field : fields) {
					try {
						byte[] rawValue = record.getRawValue(field);
						System.out.println(field.getName()
								+ " : "
								+ (rawValue == null ? "<NULL>" : new String(
										rawValue)));
					} catch (ValueTooLargeException vtle) {
						// Cannot happen :)
					}
				}

				System.out.println();
			}

			System.out.println("--------------");
		} catch (IOException ioe) {
			System.out.println("Trouble reading table or table not found");
			ioe.printStackTrace();
		} catch (DbfLibException dbflibException) {
			System.out.println("Problem getting raw value");
			dbflibException.printStackTrace();
		} finally {
			try {
				table.close();
			} catch (IOException ex) {
				System.out.println("Unable to close the table");
			}
		}
	}
	
	public static void main(String[] args) {
		importDataBase();
	}

}
