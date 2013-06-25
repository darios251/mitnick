package controllers;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.dom4j.dom.DOMDocument;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import models.Domain;
import play.Play;
import play.libs.*;

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
		
		int limit = 1000;
		while(!"OK".equals(response.getResponseAttributes().get("Code")) || response.getTableForName("Results").getTableRows().size() == 0) {
			limit--;
			if(limit <=0 )
				break;
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
			
			response = service.executeCommand("GetIndexItemInfo", parameters);
		}
		
		
		return response.getTableForName("Results").getTableRows();
	}
	
	public static List<Map<String, String>> getTopPages(String domain) {
		Map parameters = new HashMap();
	
		parameters.put("datasource", "fresh");
		parameters.put("Query", domain);
		parameters.put("Count", "5");
	
		APIService service = new APIService(Play.configuration.getProperty("majesticseoapi.key"), Play.configuration.getProperty("majesticseoapi.url"));
		Response response = service.executeCommand("GetTopPages", parameters);
		
		int limit = 1000;
		while(!"OK".equals(response.getResponseAttributes().get("Code")) || response.getTableForName("Matches").getTableRows().size() == 0) {
			limit--;
			if(limit <= 0)
				break;
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
			response = service.executeCommand("GetTopPages", parameters);
		}
		
		return response.getTableForName("Matches").getTableRows();
	}
	
	public static List<Map<String, String>> analizeIndexItems(List<Domain> domains) {
		Map parameters = new HashMap();
	
		parameters.put("datasource", "fresh");
		parameters.put("items", domains.size() + "");
		
		for(int i = 0; i < domains.size(); i++) {
			parameters.put("item" + i, domains.get(i).name);
		}
	
		APIService service = new APIService(Play.configuration.getProperty("majesticseoapi.key"), Play.configuration.getProperty("majesticseoapi.url"));
		Response response = service.executeCommand("AnalyseIndexItem", parameters);
		
		int limit = 1000;
		while((!"OK".equals(response.getResponseAttributes().get("Code")) && !"JobDoneCheckDownloads".equals(response.getResponseAttributes().get("Code"))) || ("OK".equals(response.getResponseAttributes().get("Code")) && response.getTableForName("TargetURLs").getTableRows().size() == 0)) {
			limit--;
			if(limit <= 0)
				break;
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
			response = service.executeCommand("AnalyseIndexItem", parameters);
		}
		
		if("JobDoneCheckDownloads".equals(response.getResponseAttributes().get("Code")))
			return getDownloadsList(response.getParamForName("JobID"));
		
		return response.getTableForName("TargetURLs").getTableRows();
	}
	
	public static List<Map<String, String>> analizeIndexItem(String domain) {
		Map parameters = new HashMap();
	
		parameters.put("datasource", "fresh");
		parameters.put("items", "1");
		parameters.put("item0", domain);
	
		APIService service = new APIService(Play.configuration.getProperty("majesticseoapi.key"), Play.configuration.getProperty("majesticseoapi.url"));
		Response response = service.executeCommand("AnalyseIndexItem", parameters);
		
		int limit = 1000;
		while(!"OK".equals(response.getResponseAttributes().get("Code"))) {
			limit--;
			if(limit <= 0)
				break;
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
			response = service.executeCommand("AnalyseIndexItem", parameters);
		}
		
		return response.getTableForName("TargetURLs").getTableRows();
	}
	
	public static List<Map<String, String>> getDownloadsList(String jobID) {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		
		Map parameters = new HashMap();
		
		parameters.put("datasource", "fresh");
		parameters.put("DownloadJobID", jobID);
	
		APIService service = new APIService(Play.configuration.getProperty("majesticseoapi.key"), Play.configuration.getProperty("majesticseoapi.url"));
		Response response = service.executeCommand("GetDownloadsList", parameters);
		
		List<Map<String, String>> tableRows = response.getTableForName("Downloads").getTableRows();
		
		int limit = 1000;
		while(!"OK".equals(response.getResponseAttributes().get("Code")) || tableRows.size() == 0) {
			limit--;
			if(limit <= 0)
				break;
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
			response = service.executeCommand("GetDownloadsList", parameters);
			tableRows = response.getTableForName("Downloads").getTableRows();
		}
		
		Map<String, String> map = tableRows.get(0);
		String location = map.get("PublicDownloadLocation");
		
		try {
			URL url = new URL(location);
			URLConnection con = url.openConnection();
			GZIPInputStream in = new GZIPInputStream (con.getInputStream());
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		    factory.setNamespaceAware(true);
		    DocumentBuilder builder = factory.newDocumentBuilder();
		    
		    Document xmlDoc = builder.parse(in);
		    
		    for(Node dataTable : XPath.selectNodes("/Result/DataTables/DataTable[@Name=\"TargetURLs\"]", xmlDoc)) {
		    	String headers = dataTable.getAttributes().getNamedItem("Headers").getTextContent();
		    	
		    	String[] headersSplited = headers.split("\\|");
		    	
		    	for(Node row : XPath.selectNodes("/Result/DataTables/DataTable[@Name=\"TargetURLs\"]/Row", xmlDoc)) {
		    		Map<String, String> resultMap = new HashMap<String, String>();
		    		
		    		String content = row.getTextContent();
		    		String[] contentSplited = content.split("\\|");
		    		
		    		for(int index = 0; index < headersSplited.length; index++) {
		    			resultMap.put(headersSplited[index].trim(), contentSplited[index].trim());
		    		}
		    		
		    		result.add(resultMap);
		    	}
		    }
		    
//		    
		} catch (Exception e) {
		}
	    
		
		return result;
	}
}
