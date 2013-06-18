package controllers;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.protocol.HTTP;

import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.Customsearch.*;
import com.google.api.services.customsearch.model.*;

public class GoogleSearchAPIConnector {

	public static void search(String queryStr) throws IOException {
		Search search = new Search();
		Query query = new Query();
		query.setCx("013036536707430787589:_pqjad5hr1a");
		query.setSearchTerms(queryStr);
		
		//String searchQuery = "https://www.googleapis.com/customsearch/v1?key=AIzaSyCiwm4IkH09f4_x4orHE8HZIy5AwX2ICIc&cx=013036536707430787589:_pqjad5hr1a&q=" + query + "&alt=json";
	}
}
