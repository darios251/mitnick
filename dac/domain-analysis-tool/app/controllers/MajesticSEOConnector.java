package controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Domain;
import play.Play;

import com.majesticseo.external.rpc.APIService;
import com.majesticseo.external.rpc.Response;

public class MajesticSEOConnector {

	public static Map<String , String> getBackLinks(String domain) {
		Map parameters = new HashMap();
		parameters.put("item", domain);
		parameters.put("datasource", "fresh");

		APIService service = new APIService(Play.configuration.getProperty("majesticseoapi.key"), Play.configuration.getProperty("majesticseoapi.url"));
		Response response = service.executeCommand("GetBackLinkData", parameters);
		List<Map<String, String>> backLinks = response.getTableForName("BackLinks").getTableRows();
		int refDomianHome = 0;
		String www = "NO";
		for (Map mapa : backLinks){
			String targetURL = (String)mapa.get("TargetURL");
			if (targetURL.endsWith(domain))
				refDomianHome++;
			if (targetURL.toLowerCase().contains("www"))
				www = "YES";
		}
		
		Map<String , String> result = new HashMap<String, String>();
		result.put("refTotals", String.valueOf(backLinks.size()));
		result.put("refDomianHome", String.valueOf(refDomianHome));
		result.put("www", www);
		return result;
	}
	
	public static List<Map<String, String>> getIndexItemInfo(List<Domain> domains) {
		Map parameters = new HashMap();
		
		parameters.put("datasource", "fresh");
		parameters.put("items", domains.size() + "");
		
		for(int i = 0; i < domains.size(); i++) {
			parameters.put("item" + i, domains.get(i).name);
		}

		APIService service = new APIService(Play.configuration.getProperty("majesticseoapi.key"), Play.configuration.getProperty("majesticseoapi.url"));
		Response response = service.executeCommand("GetIndexItemInfo", parameters);
		
		return response.getTableForName("Results").getTableRows();
	}
	
	public static List<Map<String, String>> analizeIndexItem(String domain) {
		Map parameters = new HashMap();
	
		parameters.put("datasource", "fresh");
		parameters.put("items", "1");
		parameters.put("item0", domain);
		parameters.put("NotifyURL", "http://thomas.webfab.co:9000/notify/"+domain);
	
		APIService service = new APIService(Play.configuration.getProperty("majesticseoapi.key"), Play.configuration.getProperty("majesticseoapi.url"));
		Response response = service.executeCommand("AnalyseIndexItem", parameters);
		
		while("QueuedForProcessing".equals(response.getResponseAttributes().get("Code"))) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
			}
			response = service.executeCommand("AnalyseIndexItem", parameters);
		}
		
		return response.getTableForName("TargetURLs").getTableRows();
	}
}
