package com.mitnick.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;

public class MacUtils {

	public static String getMac() {
		InetAddress ip;
		try {
			ip = InetAddress.getLocalHost();

			NetworkInterface network = NetworkInterface.getByInetAddress(ip);

			byte[] mac = network.getHardwareAddress();

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X", mac[i]));
			}
			return sb.toString();

		} catch (Exception e) {
		}

		return "";
	}
}