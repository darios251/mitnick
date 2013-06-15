package util;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import models.OutputResult;

public class FilterResultHelper {

	public static List<OutputDTO> addFilters(String inGoogle, String www, String pr, String refDomians, String refips, String refsubnet, String extlinksedu,
			String refdomainedu, String extbacklinksgov, String refdomiansgov, String refdomainshome, String percenttoh, String orderBy, String order) {
		OutputResult output = OutputResult.getInstance();
		List<OutputDTO> results = output.getResult();
		
		if (orderBy == null)
			orderBy = "site";	
		if ("DESC".equals(order))
			orderDESC(results, orderBy);
		else
			orderASC(results, orderBy);
		
		return results;
	}

	private BigDecimal greaterThan(String value){
		return null;
	}

	private BigDecimal lessThan(String value){
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
