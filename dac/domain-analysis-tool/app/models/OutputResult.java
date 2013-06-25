package models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import play.Logger;

import util.OutputDTO;

import controllers.GoogleSearchAPIConnector;
import com.temesoft.google.pr.PageRankService;
import controllers.MajesticSEOConnector;

public class OutputResult {
	
	public static OutputResult output;
	
	public String fileName;
	
	public List<OutputDTO> results = new ArrayList<OutputDTO>();
	
	//filter search values
	private String inGoogle="";
	private String www="";
	private String pr="";
	private String refDomians="";
	private String refips="";
	private String refsubnet="";
	private String extlinksedu="";
	private String refdomainedu="";
	private String extbacklinksgov="";
	private String refdomiansgov="";
	private String refdomainshome="";
	private String percenttoh="";
	
	public List<OutputDTO> getResult() {
		return results;
	}
	
	public static OutputResult restartInstance(List<Domain> domains){
		output = new OutputResult();
		output.results = getOutputs(domains);
		return output;
	}
	
	public static OutputResult getInstance(){
		if (output==null)
			return new OutputResult();
		return output;
	}

	private static List<OutputDTO> getOutputs(List<Domain> domains){
		List<Map<String, String>> indexItemInfo = MajesticSEOConnector.getIndexItemInfo(domains);
		
		List<OutputDTO> outputs = new ArrayList<OutputDTO>();
		PageRankService pr = new PageRankService();
		for(int i = 0; i < indexItemInfo.size(); i++) {
				Map<String, String> itemInfo = indexItemInfo.get(i);
				Logger.debug("Analysing domain:" + itemInfo.get("Item"), new Object[]{});
				
				OutputDTO dto = new OutputDTO();
				
				String isInGoogle = GoogleSearchAPIConnector.isInGoogle(itemInfo.get("Item")) ? "YES" : "MAYBE";
				dto.setInGoogle(isInGoogle);
				
				dto.setSite(itemInfo.get("Item"));
				dto.setPr(new BigDecimal(pr.getPR(itemInfo.get("Item"))));
				
				int wwwBackLinks = 0;
				int noWwwBackLinks = 0;
				for(Map<String, String> itemIndex : MajesticSEOConnector.getTopPages(itemInfo.get("Item"))) {
					if(itemIndex.get("URL")!= null && itemIndex.get("URL").endsWith(itemInfo.get("Item"))) {
						if(itemIndex.get("URL").endsWith("://www." + itemInfo.get("Item")) || itemIndex.get("URL").endsWith("://www." + itemInfo.get("Item") + "/")) {
							Logger.debug("domain: " + itemIndex.get("URL") + " - refDomain: " + itemIndex.get("RefDomains") + " - wwwBackLink", new Object[]{});
							wwwBackLinks += Integer.parseInt(itemIndex.get("RefDomains"));
						}
						else if(itemIndex.get("URL").endsWith("://" + itemInfo.get("Item")) || itemIndex.get("URL").endsWith("://" + itemInfo.get("Item") + "/" )){
							Logger.debug("domain: " + itemIndex.get("URL") + " - refDomain: " + itemIndex.get("RefDomains")  + " - NoWwwBackLink", new Object[]{});
							noWwwBackLinks += Integer.parseInt(itemIndex.get("RefDomains"));
						}
					}
				}
				
				dto.setWww(wwwBackLinks > noWwwBackLinks ? "YES" : "NO");
				BigDecimal refDomianHome = new BigDecimal(wwwBackLinks + noWwwBackLinks);
				BigDecimal refTotals = new BigDecimal(itemInfo.get("RefDomains"));
				
				if(!refTotals.equals(BigDecimal.ZERO)) {
					BigDecimal percent = refDomianHome.multiply(new BigDecimal(100));
					percent = percent.divide(refTotals, 2, RoundingMode.HALF_UP);
					
					dto.setPercentToHome(percent);
				}
				else
					dto.setPercentToHome(BigDecimal.ZERO);
				
				dto.setRefDomianHome(refDomianHome);
				BigDecimal refDomians = new BigDecimal(itemInfo.get("RefDomains"));
				dto.setRefDomians(refDomians);
				dto.setRefIps(new BigDecimal(itemInfo.get("RefIPs")));
				dto.setRefSubNet(new BigDecimal(itemInfo.get("RefSubNets")));
				dto.setExtBackLinks(new BigDecimal(itemInfo.get("ExtBackLinks")));
				dto.setExtBackLnksEdu(new BigDecimal(itemInfo.get("ExtBackLinksEDU")));
				dto.setExtBackLinksGov(new BigDecimal(itemInfo.get("ExtBackLinksGOV")));
				dto.setRefDomainsEdu(new BigDecimal(itemInfo.get("RefDomainsEDU")));
				dto.setRefDomainsGov(new BigDecimal(itemInfo.get("RefDomainsGOV")));
				dto.setCitationFlow(new BigDecimal(itemInfo.get("CitationFlow")));
				dto.setTrustFlow(new BigDecimal(itemInfo.get("TrustFlow")));
				outputs.add(dto);
		}
		return outputs;
	}
	
	public static OutputResult getOutput() {
		return output;
	}

	public static void setOutput(OutputResult output) {
		OutputResult.output = output;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public List<OutputDTO> getResults() {
		return results;
	}

	public void setResults(List<OutputDTO> results) {
		this.results = results;
	}

	public String getInGoogle() {
		return inGoogle;
	}

	public void setInGoogle(String inGoogle) {
		this.inGoogle = inGoogle;
	}

	public String getWww() {
		return www;
	}

	public void setWww(String www) {
		this.www = www;
	}

	public String getPr() {
		return pr;
	}

	public void setPr(String pr) {
		this.pr = pr;
	}

	public String getRefDomians() {
		return refDomians;
	}

	public void setRefDomians(String refDomians) {
		this.refDomians = refDomians;
	}

	public String getRefips() {
		return refips;
	}

	public void setRefips(String refips) {
		this.refips = refips;
	}

	public String getRefsubnet() {
		return refsubnet;
	}

	public void setRefsubnet(String refsubnet) {
		this.refsubnet = refsubnet;
	}

	public String getExtlinksedu() {
		return extlinksedu;
	}

	public void setExtlinksedu(String extlinksedu) {
		this.extlinksedu = extlinksedu;
	}

	public String getRefdomainedu() {
		return refdomainedu;
	}

	public void setRefdomainedu(String refdomainedu) {
		this.refdomainedu = refdomainedu;
	}

	public String getExtbacklinksgov() {
		return extbacklinksgov;
	}

	public void setExtbacklinksgov(String extbacklinksgov) {
		this.extbacklinksgov = extbacklinksgov;
	}

	public String getRefdomiansgov() {
		return refdomiansgov;
	}

	public void setRefdomiansgov(String refdomiansgov) {
		this.refdomiansgov = refdomiansgov;
	}

	public String getRefdomainshome() {
		return refdomainshome;
	}

	public void setRefdomainshome(String refdomainshome) {
		this.refdomainshome = refdomainshome;
	}

	public String getPercenttoh() {
		return percenttoh;
	}

	public void setPercenttoh(String percenttoh) {
		this.percenttoh = percenttoh;
	}
	
}
