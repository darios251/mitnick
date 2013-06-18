package controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Domain;

import com.majesticseo.external.rpc.APIService;
import com.majesticseo.external.rpc.Response;

public class MajesticSEOConnector {

	public static Map<String , String> getBackLinks(String domain) {
		Map parameters = new HashMap();
		parameters.put("item", domain);

		APIService service = new APIService("94E7D8E2D627A278CE7B07FA5AF06A5D", "http://developer.majesticseo.com/api_command");
		Response response = service.executeCommand("GetBackLinkData", parameters);
		List<Map<String, String>> backLinks = response.getTableForName("BackLinks").getTableRows();
		int refDomianHome = 0;
		String www = "N";
		for (Map mapa : backLinks){
			String targetURL = (String)mapa.get("TargetURL");
			if (targetURL.endsWith(domain))
				refDomianHome++;
			if (targetURL.toLowerCase().contains("www"))
				www = "Y";
		}
		Map<String , String> result = new HashMap<String, String>();
		result.put("refDomianHome", String.valueOf(refDomianHome));
		result.put("www", www);
		return result;
	}
	
	public static List<Map<String, String>> getIndexItemInfo(List<Domain> domains) {
		Map parameters = new HashMap();
		
		parameters.put("items", domains.size() + "");
		
		for(int i = 0; i < domains.size(); i++) {
			parameters.put("item" + i, domains.get(i).name);
		}

		APIService service = new APIService("94E7D8E2D627A278CE7B07FA5AF06A5D", "http://developer.majesticseo.com/api_command");
		Response response = service.executeCommand("GetIndexItemInfo", parameters);
		
		return response.getTableForName("Results").getTableRows();
	}
}
