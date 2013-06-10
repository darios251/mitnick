package controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.majesticseo.external.rpc.APIService;
import com.majesticseo.external.rpc.Response;

public class MajesticSEOConnector {

	public static List<Map<String, String>> getBackLinks() {
		Map parameters = new HashMap();
		parameters.put("item", "www.majesticseo.com");

		APIService service = new APIService("94E7D8E2D627A278CE7B07FA5AF06A5D", "http://developer.majesticseo.com/api_command");
		Response response = service.executeCommand("GetBackLinkData", parameters);
		
		return response.getTableForName("BackLinks").getTableRows();
	}
}
