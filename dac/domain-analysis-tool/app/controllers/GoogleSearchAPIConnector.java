package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import play.Logger;

public class GoogleSearchAPIConnector {

	public static boolean isInGoogle(String queryStr) {
		try {
			URL url = new URL("https://www.googleapis.com/customsearch/v1?key=AIzaSyCiwm4IkH09f4_x4orHE8HZIy5AwX2ICIc&cx=013036536707430787589:_pqjad5hr1a&siteSearch=" + queryStr + "&q=%20&alt=json");

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			while ((output = br.readLine()) != null) {
				if(output.contains("\"totalResults\": "))
					break;
			}

			conn.disconnect();
			
			String result = output.toString();
			int index = result.indexOf("\"totalResults\": ");
			int indexLast = result.indexOf(",", index);
			String totalResults = result.substring(index + "\"totalResults\": ".length() + 1, indexLast-1);
			
			return Integer.parseInt(totalResults) != 0;
		} catch (MalformedURLException e) {
		} catch (IOException e) {
		}
		return false;
	}
	
	public static Boolean isInGoogle2(String domain) {
		Boolean result = null;
		try {
//				Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("193.160.225.13", 8081));
			URL url = new URL("http://www.google.com/search?q=site:" + domain);
			HttpURLConnection uc = (HttpURLConnection)url.openConnection();
			uc.setRequestMethod("GET");
			uc.setRequestProperty("Connection", "keep-alive");
			uc.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			uc.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.52 Safari/537.36");
			uc.setRequestProperty("Accept-Encoding", "utf8");
			uc.setRequestProperty("Accept-Language", "en-US,en;q=0.8");
			uc.connect();
			
			InputStream input = uc.getInputStream();
			
			byte[] buffer = new byte[3000];
			boolean found = false; 
			while(input.read(buffer) > 0) {
				String line = new String(buffer);
				
				if(line.contains("id=resultStats")) {
					found = true;
				}
			}
			
			result = Boolean.FALSE;
			if(found)
				result = Boolean.FALSE;
			
			input.close();
			
		} catch (Exception e) {
			Logger.error(e, "Error trying to determinate if the domain " + domain + "is already indexed by Google", new Object[]{});
		}
		
		return result;
	}
	
}
