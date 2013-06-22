package util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import models.Domain;
import play.db.jpa.Model;

public class OutputDTO implements Serializable {

	private String site;
	private BigDecimal pr;
	private String inGoogle;
	private String www;
	private BigDecimal percentToHome;
	private BigDecimal refDomianHome;
	private BigDecimal refDomians;
	private BigDecimal extBackLinks;
	private BigDecimal refIps;
	private BigDecimal refSubNet;
	private BigDecimal extBackLnksEdu;
	private BigDecimal extBackLinksGov;
	private BigDecimal refDomainsEdu;
	private BigDecimal refDomainsGov;
	private BigDecimal citationFlow;
	private BigDecimal trustFlow;
	private boolean goodToBuy = false;
	private boolean deleted = false;
	
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	public boolean isDeleted() {
		return deleted;
	}
	
	public boolean isGoodToBuy() {
		return goodToBuy;
	}

	public void setGoodToBuy(boolean goodToBuy) {
		this.goodToBuy = goodToBuy;
	}	

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public BigDecimal getPr() {
		return pr;
	}

	public void setPr(BigDecimal pr) {
		this.pr = pr;
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

	public BigDecimal getPercentToHome() {
		return percentToHome;
	}

	public void setPercentToHome(BigDecimal percentToHome) {
		this.percentToHome = percentToHome;
	}

	public BigDecimal getRefDomianHome() {
		return refDomianHome;
	}

	public void setRefDomianHome(BigDecimal refDomianHome) {
		this.refDomianHome = refDomianHome;
	}

	public BigDecimal getRefDomians() {
		return refDomians;
	}

	public void setRefDomians(BigDecimal refDomians) {
		this.refDomians = refDomians;
	}

	public BigDecimal getExtBackLinks() {
		return extBackLinks;
	}

	public void setExtBackLinks(BigDecimal extBackLinks) {
		this.extBackLinks = extBackLinks;
	}

	public BigDecimal getRefIps() {
		return refIps;
	}

	public void setRefIps(BigDecimal refIps) {
		this.refIps = refIps;
	}

	public BigDecimal getRefSubNet() {
		return refSubNet;
	}

	public void setRefSubNet(BigDecimal refSubNet) {
		this.refSubNet = refSubNet;
	}

	public BigDecimal getExtBackLnksEdu() {
		return extBackLnksEdu;
	}

	public void setExtBackLnksEdu(BigDecimal extBackLnksEdu) {
		this.extBackLnksEdu = extBackLnksEdu;
	}

	public BigDecimal getExtBackLinksGov() {
		return extBackLinksGov;
	}

	public void setExtBackLinksGov(BigDecimal extBackLinksGov) {
		this.extBackLinksGov = extBackLinksGov;
	}

	public BigDecimal getRefDomainsEdu() {
		return refDomainsEdu;
	}

	public void setRefDomainsEdu(BigDecimal refDomainsEdu) {
		this.refDomainsEdu = refDomainsEdu;
	}

	public BigDecimal getRefDomainsGov() {
		return refDomainsGov;
	}

	public void setRefDomainsGov(BigDecimal refDomainsGov) {
		this.refDomainsGov = refDomainsGov;
	}
	
	public void setCitationFlow(BigDecimal citationFlow) {
		this.citationFlow = citationFlow;
	}
	
	public BigDecimal getCitationFlow() {
		return citationFlow;
	}
	
	public void setTrustFlow(BigDecimal trustFlow) {
		this.trustFlow = trustFlow;
	}
	
	public BigDecimal getTrustFlow() {
		return trustFlow;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((citationFlow == null) ? 0 : citationFlow.hashCode());
		result = prime * result
				+ ((extBackLinks == null) ? 0 : extBackLinks.hashCode());
		result = prime * result
				+ ((extBackLinksGov == null) ? 0 : extBackLinksGov.hashCode());
		result = prime * result
				+ ((extBackLnksEdu == null) ? 0 : extBackLnksEdu.hashCode());
		result = prime * result + (goodToBuy ? 1231 : 1237);
		result = prime * result
				+ ((inGoogle == null) ? 0 : inGoogle.hashCode());
		result = prime * result
				+ ((percentToHome == null) ? 0 : percentToHome.hashCode());
		result = prime * result + ((pr == null) ? 0 : pr.hashCode());
		result = prime * result
				+ ((refDomainsEdu == null) ? 0 : refDomainsEdu.hashCode());
		result = prime * result
				+ ((refDomainsGov == null) ? 0 : refDomainsGov.hashCode());
		result = prime * result
				+ ((refDomianHome == null) ? 0 : refDomianHome.hashCode());
		result = prime * result
				+ ((refDomians == null) ? 0 : refDomians.hashCode());
		result = prime * result + ((refIps == null) ? 0 : refIps.hashCode());
		result = prime * result
				+ ((refSubNet == null) ? 0 : refSubNet.hashCode());
		result = prime * result + ((site == null) ? 0 : site.hashCode());
		result = prime * result
				+ ((trustFlow == null) ? 0 : trustFlow.hashCode());
		result = prime * result + ((www == null) ? 0 : www.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OutputDTO other = (OutputDTO) obj;
		if (citationFlow == null) {
			if (other.citationFlow != null)
				return false;
		} else if (!citationFlow.equals(other.citationFlow))
			return false;
		if (extBackLinks == null) {
			if (other.extBackLinks != null)
				return false;
		} else if (!extBackLinks.equals(other.extBackLinks))
			return false;
		if (extBackLinksGov == null) {
			if (other.extBackLinksGov != null)
				return false;
		} else if (!extBackLinksGov.equals(other.extBackLinksGov))
			return false;
		if (extBackLnksEdu == null) {
			if (other.extBackLnksEdu != null)
				return false;
		} else if (!extBackLnksEdu.equals(other.extBackLnksEdu))
			return false;
		if (goodToBuy != other.goodToBuy)
			return false;
		if (inGoogle == null) {
			if (other.inGoogle != null)
				return false;
		} else if (!inGoogle.equals(other.inGoogle))
			return false;
		if (percentToHome == null) {
			if (other.percentToHome != null)
				return false;
		} else if (!percentToHome.equals(other.percentToHome))
			return false;
		if (pr == null) {
			if (other.pr != null)
				return false;
		} else if (!pr.equals(other.pr))
			return false;
		if (refDomainsEdu == null) {
			if (other.refDomainsEdu != null)
				return false;
		} else if (!refDomainsEdu.equals(other.refDomainsEdu))
			return false;
		if (refDomainsGov == null) {
			if (other.refDomainsGov != null)
				return false;
		} else if (!refDomainsGov.equals(other.refDomainsGov))
			return false;
		if (refDomianHome == null) {
			if (other.refDomianHome != null)
				return false;
		} else if (!refDomianHome.equals(other.refDomianHome))
			return false;
		if (refDomians == null) {
			if (other.refDomians != null)
				return false;
		} else if (!refDomians.equals(other.refDomians))
			return false;
		if (refIps == null) {
			if (other.refIps != null)
				return false;
		} else if (!refIps.equals(other.refIps))
			return false;
		if (refSubNet == null) {
			if (other.refSubNet != null)
				return false;
		} else if (!refSubNet.equals(other.refSubNet))
			return false;
		if (site == null) {
			if (other.site != null)
				return false;
		} else if (!site.equals(other.site))
			return false;
		if (trustFlow == null) {
			if (other.trustFlow != null)
				return false;
		} else if (!trustFlow.equals(other.trustFlow))
			return false;
		if (www == null) {
			if (other.www != null)
				return false;
		} else if (!www.equals(other.www))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OutputDTO [site=" + site + ", pr=" + pr + ", inGoogle="
				+ inGoogle + ", www=" + www + ", percentToHome="
				+ percentToHome + ", refDomianHome=" + refDomianHome
				+ ", refDomians=" + refDomians + ", extBackLinks="
				+ extBackLinks + ", refIps=" + refIps + ", refSubNet="
				+ refSubNet + ", extBackLnksEdu=" + extBackLnksEdu
				+ ", extBackLinksGov=" + extBackLinksGov + ", refDomainsEdu="
				+ refDomainsEdu + ", refDomainsGov=" + refDomainsGov
				+ ", citationFlow=" + citationFlow + ", trustFlow=" + trustFlow
				+ ", goodToBuy=" + goodToBuy + "]";
	}
	
	
}
