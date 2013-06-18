package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

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
}
