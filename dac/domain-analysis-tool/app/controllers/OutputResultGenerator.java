package controllers;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import models.OutputResult;
import play.data.binding.Binder;
import play.db.Model;
import play.exceptions.TemplateNotFoundException;
import play.mvc.Controller;
import play.mvc.With;
import util.DATConstants;
import util.FilterResultHelper;
import util.OutputDTO;
import util.XLSExportHelper;
import controllers.CRUD.ObjectType;

/**
 * This CRUD allow to export the results into a xls file.
 * 
 * @author Martin
 * 
 */
@Check(DATConstants.Constants.Roles.DOMIANS_ROLE)
@With(Secure.class)
public class OutputResultGenerator extends Controller {

	public static void listResult(String orderBy, String order, boolean delete) {
		OutputResult output = OutputResult.getInstance();
		String inGoogle = null;
		String www = null;
		String pr = null;
		String refDomians = null;
		String refips = null;
		String refsubnet = null;
		String extlinksedu = null;
		String refdomainedu = null;
		String extbacklinksgov = null;
		String refdomiansgov = null;
		String refdomainshome = null;
		String percenttoh = null;
		if (delete) {
			inGoogle = output.getInGoogle();
			www = output.getWww();
			pr = output.getPr();
			refDomians = output.getRefDomians();
			refips = output.getRefips();
			refsubnet = output.getRefsubnet();
			extlinksedu = output.getExtlinksedu();
			refdomainedu = output.getRefdomainedu();
			extbacklinksgov = output.getExtbacklinksgov();
			refdomiansgov = output.getRefdomiansgov();
			refdomainshome = output.getRefdomainshome();
			percenttoh = output.getPercenttoh();
		} else {
			inGoogle = params.get("inGoogle");
			www = params.get("www");
			pr = params.get("pr");
			refDomians = params.get("refDomians");
			refips = params.get("refips");
			refsubnet = params.get("refsubnet");
			extlinksedu = params.get("extlinksedu");
			refdomainedu = params.get("refdomainedu");
			extbacklinksgov = params.get("extbacklinksgov");
			refdomiansgov = params.get("refdomiansgov");
			refdomainshome = params.get("refdomainshome");
			percenttoh = params.get("percenttoh");

			output.setInGoogle(inGoogle);
			output.setWww(www);
			output.setPr(pr);
			output.setRefDomians(refDomians);
			output.setRefips(refips);
			output.setRefsubnet(refsubnet);
			output.setExtlinksedu(extlinksedu);
			output.setRefdomainedu(refdomainedu);
			output.setExtbacklinksgov(extbacklinksgov);
			output.setRefdomiansgov(refdomiansgov);
			output.setRefdomainshome(refdomainshome);
			output.setPercenttoh(percenttoh);
		}
		
		List<OutputDTO> results = FilterResultHelper.addFilters(inGoogle, www, pr, refDomians, refips, refsubnet, extlinksedu, refdomainedu, extbacklinksgov,
				refdomiansgov, refdomainshome, percenttoh, orderBy, order);
		
		render(results, orderBy, order, inGoogle, www, pr, refDomians, refips, refsubnet, extlinksedu, refdomainedu, extbacklinksgov, refdomiansgov, refdomainshome, percenttoh);
	}

	public static void createXLSFile() throws Exception {
		OutputResult output = OutputResult.getInstance();
		String inGoogle = output.getInGoogle();
		String www = output.getWww();
		String pr = output.getPr();
		String refDomians = output.getRefDomians();
		String refips = output.getRefips();
		String refsubnet = output.getRefsubnet();
		String extlinksedu = output.getExtlinksedu();
		String refdomainedu = output.getRefdomainedu();
		String extbacklinksgov = output.getExtbacklinksgov();
		String refdomiansgov = output.getRefdomiansgov();
		String refdomainshome = output.getRefdomainshome();
		String percenttoh = output.getPercenttoh();
		List<OutputDTO> results = FilterResultHelper.addFilters(inGoogle, www, pr, refDomians, refips, refsubnet, extlinksedu, refdomainedu, extbacklinksgov,
				refdomiansgov, refdomainshome, percenttoh, "site", "ASC");
		String temporalName = String.valueOf(System.currentTimeMillis());
		XLSExportHelper.write(temporalName, results);			
	}
	
	public static void delete(String orderBy, String order, String site) {
		OutputResult outputs = OutputResult.getInstance();
		List<OutputDTO> results = outputs.getResult();
		OutputDTO remove = null;
		for (OutputDTO output : results) {
			if (site != null && site.equals(output.getSite()))
				remove = output;
		}
		if (remove != null)
			outputs.getResult().remove(remove);

		listResult(orderBy, order, true);
	}

	public static void goodToBuy(String site) {
		OutputResult outputs = OutputResult.getInstance();
		List<OutputDTO> results = outputs.getResult();
		for (OutputDTO output : results) {
			if (site != null && site.equals(output.getSite()))
				output.setGoodToBuy(!output.isGoodToBuy());
		}
	}

	
	

}