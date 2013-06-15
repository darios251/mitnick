package util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.hibernate.id.IdentityGenerator.GetGeneratedKeysDelegate;

import models.OutputResult;

public class FilterResultHelper {

	public static List<OutputDTO> addFilters(String inGoogle, String www, String pr, String refDomians, String refips, String refsubnet, String extlinksedu,
			String refdomainedu, String extbacklinksgov, String refdomiansgov, String refdomainshome, String percenttoh, String orderBy, String order) {
		OutputResult output = OutputResult.getInstance();
		List<OutputDTO> results = output.getResult();
		results = getInGoogle(results, inGoogle);
		results = getWww(results, www);
		results = getPr(results, pr);
		results = getRefDomians(results, refDomians);
		results = getRefips(results, refips);
		results = getRefsubnet(results, refsubnet);
		results = getExtlinksedu(results, extlinksedu);
		results = getRefdomainedu(results, refdomainedu);
		results = getExtbacklinksgov(results, extbacklinksgov);
		results = getRefdomiansgov(results, refdomiansgov);
		results = getRefdomainshome(results, refdomainshome);
		results = getPercenttoh(results, percenttoh);
		
		if (results!=null && !results.isEmpty()){
			if (orderBy == null)
				orderBy = "site";	
			if ("DESC".equals(order))
				orderDESC(results, orderBy);
			else
				orderASC(results, orderBy);
		}
		
		return results;
	}

	private static List<OutputDTO> getInGoogle(List<OutputDTO> result, String inGoogle){
		List<OutputDTO> filterResult = new ArrayList<OutputDTO>();
		if (inGoogle == null || "".equals(inGoogle) || play.i18n.Messages.get("result.filter.selectOption").equals(inGoogle))
			filterResult = result;
		else {
			for (OutputDTO output: result){
				if (inGoogle.equals(output.getInGoogle()))
					filterResult.add(output);
			}
		}
		return filterResult;
	}
	
	private static List<OutputDTO> getWww(List<OutputDTO> result, String www){
		List<OutputDTO> filterResult = new ArrayList<OutputDTO>();
		if (www == null || "".equals(www) || play.i18n.Messages.get("result.filter.selectOption").equals(www))
			filterResult = result;
		else {
			for (OutputDTO output: result){
				if (www.equals(output.getWww()))
					filterResult.add(output);
			}
		}
		return filterResult;
	}
	
	private static List<OutputDTO> getPr(List<OutputDTO> result, String pr){
		result = getGreatherPr(result, pr);
		result = getLessPr(result, pr);
		return result;
	}
	
	private static List<OutputDTO> getGreatherPr(List<OutputDTO> result, String pr){
		List<OutputDTO> filterResult = new ArrayList<OutputDTO>();
		BigDecimal greather = greaterThan(pr);
		if (greather == null)
			return result;
		
		if (greather!=null){
			for (OutputDTO output: result){
				if (greather.compareTo(output.getPr())<0)
					filterResult.add(output);
			}
		}
		
		return filterResult;
	}
	
	private static List<OutputDTO> getLessPr(List<OutputDTO> result, String pr){
		List<OutputDTO> filterResult = new ArrayList<OutputDTO>();
		BigDecimal less = lessThan(pr);
		if (less == null)
			return result;
		if (less!=null){
			for (OutputDTO output: result){
				if (less.compareTo(output.getPr())>0)
					filterResult.add(output);
			}
		}
		return filterResult;
	}
	
	private static List<OutputDTO> getRefDomians(List<OutputDTO> result, String refDomians){
		result = getGreatherRefDomians(result, refDomians);
		result = getLessRefDomians(result, refDomians);
		return result;
	}
	
	private static List<OutputDTO> getGreatherRefDomians(List<OutputDTO> result, String refDomians){
		List<OutputDTO> filterResult = new ArrayList<OutputDTO>();
		BigDecimal greather = greaterThan(refDomians);				
		if (greather == null)
			return result;
		if (greather!=null){
			for (OutputDTO output: result){
				if (greather.compareTo(output.getRefDomians())<0)
					filterResult.add(output);
			}
		}		
		
		return filterResult;
	}
	
	private static List<OutputDTO> getLessRefDomians(List<OutputDTO> result, String refDomians){
		List<OutputDTO> filterResult = new ArrayList<OutputDTO>();
		BigDecimal less = lessThan(refDomians);
		if (less == null)
			return result;
		if (less!=null){
			for (OutputDTO output: result){
				if (less.compareTo(output.getRefDomians())>0)
					filterResult.add(output);
			}
		}
		return filterResult;
	}
	
	private static List<OutputDTO> getRefips(List<OutputDTO> result, String refips){
		result = getGreatherRefips(result, refips);
		result = getLessRefips(result, refips);
		return result;
	}
	private static List<OutputDTO> getGreatherRefips(List<OutputDTO> result, String refips){
		List<OutputDTO> filterResult = new ArrayList<OutputDTO>();
		BigDecimal greather = greaterThan(refips);
		if (greather == null)
			return result;
		if (greather!=null){
			for (OutputDTO output: result){
				if (greather.compareTo(output.getRefIps())<0)
					filterResult.add(output);
			}
		}		
		
		return filterResult;
	}
	private static List<OutputDTO> getLessRefips(List<OutputDTO> result, String refips){
		List<OutputDTO> filterResult = new ArrayList<OutputDTO>();
		BigDecimal less = lessThan(refips);
		if (less == null)
			return result;
		if (less!=null){
			for (OutputDTO output: result){
				if (less.compareTo(output.getRefIps())>0)
					filterResult.add(output);
			}
		}
		return filterResult;
	}
	
	private static List<OutputDTO> getRefsubnet(List<OutputDTO> result, String refsubnet){
		result = getGreatherRefsubnet(result, refsubnet);
		result = getLessRefsubnet(result, refsubnet);
		return result;
	}
	private static List<OutputDTO> getGreatherRefsubnet(List<OutputDTO> result, String refsubnet){
		List<OutputDTO> filterResult = new ArrayList<OutputDTO>();
		BigDecimal greather = greaterThan(refsubnet);
		if (greather == null)
			return result;
		if (greather!=null){
			for (OutputDTO output: result){
				if (greather.compareTo(output.getRefSubNet())<0)
					filterResult.add(output);
			}
		}
		return filterResult;
	}
	private static List<OutputDTO> getLessRefsubnet(List<OutputDTO> result, String refsubnet){
		List<OutputDTO> filterResult = new ArrayList<OutputDTO>();
		BigDecimal less = lessThan(refsubnet);
		if (less == null)
			return result;
		if (less!=null){
			for (OutputDTO output: result){
				if (less.compareTo(output.getRefSubNet())>0)
					filterResult.add(output);
			}
		}
		return filterResult;
	}
	private static List<OutputDTO> getExtlinksedu(List<OutputDTO> result, String extlinksedu){
		result = getGreatherExtlinksedu(result, extlinksedu);
		result = getLessExtlinksedu(result, extlinksedu);
		return result;
	}
	private static List<OutputDTO> getGreatherExtlinksedu(List<OutputDTO> result, String extlinksedu){
		List<OutputDTO> filterResult = new ArrayList<OutputDTO>();
		BigDecimal greather = greaterThan(extlinksedu);
		if (greather == null)
			return result;
		if (greather!=null){
			for (OutputDTO output: result){
				if (greather.compareTo(output.getExtBackLnksEdu())<0)
					filterResult.add(output);
			}
		}
		return filterResult;
	}
	private static List<OutputDTO> getLessExtlinksedu(List<OutputDTO> result, String extlinksedu){
		List<OutputDTO> filterResult = new ArrayList<OutputDTO>();
		BigDecimal less = lessThan(extlinksedu);
		if (less == null)
			return result;
		if (less!=null){
			for (OutputDTO output: result){
				if (less.compareTo(output.getExtBackLnksEdu())>0)
					filterResult.add(output);
			}
		}
		return filterResult;
	}
	
	private static List<OutputDTO> getRefdomainedu(List<OutputDTO> result, String refdomainedu){
		result = getGreatherRefdomainedu(result, refdomainedu);
		result = getLessRefdomainedu(result, refdomainedu);
		return result;
	}
	private static List<OutputDTO> getGreatherRefdomainedu(List<OutputDTO> result, String refdomainedu){
		List<OutputDTO> filterResult = new ArrayList<OutputDTO>();
		BigDecimal greather = greaterThan(refdomainedu);
		if (greather == null)
			return result;
		if (greather!=null){
			for (OutputDTO output: result){
				if (greather.compareTo(output.getRefDomainsEdu())<0)
					filterResult.add(output);
			}
		}
		return filterResult;
	}
	private static List<OutputDTO> getLessRefdomainedu(List<OutputDTO> result, String refdomainedu){
		List<OutputDTO> filterResult = new ArrayList<OutputDTO>();
		BigDecimal less = lessThan(refdomainedu);
		if (less == null)
			return result;
		if (less!=null){
			for (OutputDTO output: result){
				if (less.compareTo(output.getRefDomainsEdu())>0)
					filterResult.add(output);
			}
		}
		return filterResult;
	}
	
	private static List<OutputDTO> getExtbacklinksgov(List<OutputDTO> result, String extbacklinksgov){
		result = getGreatherExtbacklinksgov(result, extbacklinksgov);
		result = getLessExtbacklinksgov(result, extbacklinksgov);
		return result;
	}
	private static List<OutputDTO> getGreatherExtbacklinksgov(List<OutputDTO> result, String extbacklinksgov){
		List<OutputDTO> filterResult = new ArrayList<OutputDTO>();
		BigDecimal greather = greaterThan(extbacklinksgov);
		if (greather == null)
			return result;
		if (greather!=null){
			for (OutputDTO output: result){
				if (greather.compareTo(output.getExtBackLinksGov())<0)
					filterResult.add(output);
			}
		}
		return filterResult;
	}
	private static List<OutputDTO> getLessExtbacklinksgov(List<OutputDTO> result, String extbacklinksgov){
		List<OutputDTO> filterResult = new ArrayList<OutputDTO>();
		BigDecimal less = lessThan(extbacklinksgov);
		if (less == null)
			return result;
		if (less!=null){
			for (OutputDTO output: result){
				if (less.compareTo(output.getExtBackLinksGov())>0)
					filterResult.add(output);
			}
		}
		return filterResult;
	}
	
	private static List<OutputDTO> getRefdomiansgov(List<OutputDTO> result, String refdomiansgov){
		result = getGreatherRefdomiansgov(result, refdomiansgov);
		result = getLessRefdomiansgov(result, refdomiansgov);
		return result;
	}
	private static List<OutputDTO> getGreatherRefdomiansgov(List<OutputDTO> result, String refdomiansgov){
		List<OutputDTO> filterResult = new ArrayList<OutputDTO>();
		BigDecimal greather = greaterThan(refdomiansgov);
		if (greather == null)
			return result;
		if (greather!=null){
			for (OutputDTO output: result){
				if (greather.compareTo(output.getRefDomainsGov())<0)
					filterResult.add(output);
			}
		}
		return filterResult;
	}
	private static List<OutputDTO> getLessRefdomiansgov(List<OutputDTO> result, String refdomiansgov){
		List<OutputDTO> filterResult = new ArrayList<OutputDTO>();
		BigDecimal less = lessThan(refdomiansgov);
		if (less == null)
			return result;
		if (less!=null){
			for (OutputDTO output: result){
				if (less.compareTo(output.getRefDomainsGov())>0)
					filterResult.add(output);
			}
		}
		return filterResult;
	}
	
	private static List<OutputDTO> getRefdomainshome(List<OutputDTO> result, String refdomainshome){
		result = getGreatherRefdomainshome(result, refdomainshome);
		result = getLessRefdomainshome(result, refdomainshome);
		return result;
	}
	private static List<OutputDTO> getGreatherRefdomainshome(List<OutputDTO> result, String refdomainshome){
		List<OutputDTO> filterResult = new ArrayList<OutputDTO>();
		BigDecimal greather = greaterThan(refdomainshome);
		if (greather == null)
			return result;
		if (greather!=null){
			for (OutputDTO output: result){
				if (greather.compareTo(output.getRefDomianHome())<0)
					filterResult.add(output);
			}
		}
		return filterResult;
	}
	private static List<OutputDTO> getLessRefdomainshome(List<OutputDTO> result, String refdomainshome){
		List<OutputDTO> filterResult = new ArrayList<OutputDTO>();
		BigDecimal less = lessThan(refdomainshome);
		if (less == null)
			return result;
		if (less!=null){
			for (OutputDTO output: result){
				if (less.compareTo(output.getRefDomianHome())>0)
					filterResult.add(output);
			}
		}
		return filterResult;
	}
	
	private static List<OutputDTO> getPercenttoh(List<OutputDTO> result, String percenttoh){
		result = getGreatherPercenttoh(result, percenttoh);
		result = getLessPercenttoh(result, percenttoh);
		return result;
	}
	private static List<OutputDTO> getGreatherPercenttoh(List<OutputDTO> result, String percenttoh){
		List<OutputDTO> filterResult = new ArrayList<OutputDTO>();
		BigDecimal greather = greaterThan(percenttoh);
		if (greather == null)
			return result;
		if (greather!=null){
			for (OutputDTO output: result){
				if (greather.compareTo(output.getPercentToHome())<0)
					filterResult.add(output);
			}
		}
		return filterResult;
	}
	private static List<OutputDTO> getLessPercenttoh(List<OutputDTO> result, String percenttoh){
		List<OutputDTO> filterResult = new ArrayList<OutputDTO>();
		BigDecimal less = lessThan(percenttoh);
		if (less == null)
			return result;
		if (less!=null){
			for (OutputDTO output: result){
				if (less.compareTo(output.getPercentToHome())>0)
					filterResult.add(output);
			}
		}
		return filterResult;
	}
	
	private static BigDecimal greaterThan(String value){
		if (value==null)
			return null;
		String mayor = "";
		int signoMayorIndex = value.indexOf(">");
		signoMayorIndex++;
		if (signoMayorIndex>=0){
			boolean cont = true;
			while (cont && signoMayorIndex<value.length()){				
				char a = value.charAt(signoMayorIndex);
				try {
					new Integer(String.valueOf(a));
					mayor = mayor.concat(String.valueOf(a));					
				} catch (Exception e){
					if ('.' == a || ','==a)
						mayor = mayor.concat(".");		
					else if ('>'==a || '<'==a)
						cont = false;
				}
				signoMayorIndex++;
				
			}
		}
		if (!"".equals(mayor))
			return new BigDecimal(mayor);
		return null;
	}

	private static BigDecimal lessThan(String value){
		if (value==null)
			return null;
		String mayor = "";
		int signoMayorIndex = value.indexOf("<");
		signoMayorIndex++;
		if (signoMayorIndex>=0){
			boolean cont = true;
			while (cont && signoMayorIndex<value.length()){				
				char a = value.charAt(signoMayorIndex);
				try {
					new Integer(String.valueOf(a));
					mayor = mayor.concat(String.valueOf(a));					
				} catch (Exception e){
					if ('.' == a || ','==a)
						mayor = mayor.concat(".");		
					else if ('>'==a || '<'==a)
						cont = false;
				}
				signoMayorIndex++;
				
			}
		}
		if (!"".equals(mayor))
			return new BigDecimal(mayor);
		return null;
	}

	@SuppressWarnings("unchecked")
	private static OutputDTO orderDESC(List<OutputDTO> result, String orderBy) {
		if ("site".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e2.getSite().compareTo(e1.getSite());
				}
			});
		}
		if ("pr".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e2.getPr().compareTo(e1.getPr());
				}
			});
		}
		if ("ingoogle".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e2.getInGoogle().compareTo(e1.getInGoogle());
				}
			});
		}
		if ("www".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e2.getWww().compareTo(e1.getWww());
				}
			});
		}
		if ("refdomain".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e2.getRefDomians().compareTo(e1.getRefDomians());
				}
			});
		}
		if ("refips".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e2.getRefIps().compareTo(e1.getRefIps());
				}
			});
		}
		if ("refsubnet".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e2.getRefSubNet().compareTo(e1.getRefSubNet());
				}
			});
		}
		if ("extlinksedu".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e2.getExtBackLnksEdu().compareTo(e1.getExtBackLnksEdu());
				}
			});
		}
		if ("refdomainedu".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e2.getRefDomainsEdu().compareTo(e1.getRefDomainsEdu());
				}
			});
		}
		if ("extbacklinksgov".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e2.getExtBackLinksGov().compareTo(e1.getExtBackLinksGov());
				}
			});
		}
		if ("refdomiansgov".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e2.getRefDomainsGov().compareTo(e1.getRefDomainsGov());
				}
			});
		}
		if ("refdomainshome".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e2.getRefDomianHome().compareTo(e1.getRefDomianHome());
				}
			});
		}
		if ("percenttoh".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e2.getPercentToHome().compareTo(e1.getPercentToHome());
				}
			});
		}
		return result.get(0);
	}

	@SuppressWarnings("unchecked")
	private static OutputDTO orderASC(List<OutputDTO> result, String orderBy) {
		if ("site".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e1.getSite().compareTo(e2.getSite());
				}
			});
		}
		if ("pr".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e1.getPr().compareTo(e2.getPr());
				}
			});
		}
		if ("ingoogle".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e1.getInGoogle().compareTo(e2.getInGoogle());
				}
			});
		}
		if ("www".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e1.getWww().compareTo(e2.getWww());
				}
			});
		}
		if ("refdomain".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e1.getRefDomians().compareTo(e2.getRefDomians());
				}
			});
		}
		if ("refips".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e1.getRefIps().compareTo(e2.getRefIps());
				}
			});
		}
		if ("refsubnet".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e1.getRefSubNet().compareTo(e2.getRefSubNet());
				}
			});
		}
		if ("extlinksedu".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e1.getExtBackLnksEdu().compareTo(e2.getExtBackLnksEdu());
				}
			});
		}
		if ("refdomainedu".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e1.getRefDomainsEdu().compareTo(e2.getRefDomainsEdu());
				}
			});
		}
		if ("extbacklinksgov".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e1.getExtBackLinksGov().compareTo(e2.getExtBackLinksGov());
				}
			});
		}
		if ("refdomiansgov".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e1.getRefDomainsGov().compareTo(e2.getRefDomainsGov());
				}
			});
		}
		if ("refdomainshome".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e1.getRefDomianHome().compareTo(e2.getRefDomianHome());
				}
			});
		}
		if ("percenttoh".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e1.getPercentToHome().compareTo(e2.getPercentToHome());
				}
			});
		}
		return result.get(0);
	}
	
}
