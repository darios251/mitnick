package controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import controllers.CRUD.ObjectType;

import models.Domain;
import models.Domains;
import models.OutputResult;
import play.data.binding.Binder;
import play.db.Model;
import play.exceptions.TemplateNotFoundException;
import play.mvc.With;
import util.DATConstants;
import util.FilterResultHelper;
import util.OutputDTO;
import util.XLSExportHelper;

/**
 * This CRUD allow to import the Domain.txt file, to add the all domains into the system.
 * @author Agustina
 *
 */
@Check(DATConstants.Constants.Roles.DOMIANS_ROLE)
@With(Secure.class)
@CRUD.For(value = Domains.class)
public class DomainsController extends CRUD {

	public static void create() throws Exception {
		ObjectType type = ObjectType.get(getControllerClass());
		notFoundIfNull(type);
		Constructor<?> constructor = type.entityClass.getDeclaredConstructor();
		constructor.setAccessible(true);
		Model object = (Model) constructor.newInstance();
		Binder.bindBean(params.getRootParamNode(), "object", object);
		
		validation.valid(object);
		if (validation.hasErrors()) {
			renderArgs.put("error", play.i18n.Messages.get("crud.hasErrors"));
			try {
				render(request.controller.replace(".", "/") + "/blank.html", type, object);
			} catch (TemplateNotFoundException e) {
				render("CRUD/blank.html", type, object);
			}
		}
		
		List<Domain> domains = importDomainNames((Domains) object);
		OutputResult.restartInstance(domains);
		
		listResult("site", "ASC", false);
		
	}

	private static List<Domain> importDomainNames(Domains domains) {
		File f = domains.domainFile.getFile();
		List<Domain> domainsList = new ArrayList<Domain>();
		BufferedReader entrada;
		try {
			entrada = new BufferedReader(new FileReader(f));
			String linea;
			while (entrada.ready()) {
				linea = entrada.readLine();
				Domain domain = new Domain();
				domain.name = linea.trim();
				domainsList.add(domain);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return domainsList;

	}
	
	public static void list(int page, String search, String searchFields, String orderBy, String order) {
		listResult("site", "ASC", false);
    }
	
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
		File file = XLSExportHelper.write(temporalName, results);	
		
		renderBinary(file, "domainAnalisys.xls");
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
