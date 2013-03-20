package com.mitnick.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

public class DiskUtils {

	private DiskUtils() {
	}

	public static String getSerialNumber(String drive) {
		final Logger logger = Logger.getLogger(DiskUtils.class);
		String result = "";
		try {
			File file = File.createTempFile("realhowto", ".vbs");
			file.deleteOnExit();
			FileWriter fw = new java.io.FileWriter(file);

			String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
					+ "Set colDrives = objFSO.Drives\n"
					+ "Set objDrive = colDrives.item(\""
					+ drive
					+ "\")\n"
					+ "Wscript.Echo objDrive.SerialNumber"; // see note
			fw.write(vbs);
			fw.close();
			Process p = Runtime.getRuntime().exec(
					"cscript //NoLogo " + file.getPath());
			BufferedReader input = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line;
			while ((line = input.readLine()) != null) {
				result += line;
			}
			input.close();
		} catch (Exception e) {
			logger.error(e);
		}
		return result.trim();
	}

	public static void main(String[] args) {
		String sn = DiskUtils.getSerialNumber("C");
		javax.swing.JOptionPane.showConfirmDialog((java.awt.Component) null,
				sn, "Serial Number of C:",
				javax.swing.JOptionPane.DEFAULT_OPTION);
	}
}