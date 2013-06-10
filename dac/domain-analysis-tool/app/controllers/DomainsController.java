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
import play.data.binding.Binder;
import play.db.Model;
import play.exceptions.TemplateNotFoundException;
import play.mvc.With;
import util.DATConstants;

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
		
		Domain.deleteAll();
		
		importDomainNames((Domains) object);

		validation.valid(object);
		if (validation.hasErrors()) {
			renderArgs.put("error", play.i18n.Messages.get("crud.hasErrors"));
			try {
				render(request.controller.replace(".", "/") + "/blank.html", type, object);
			} catch (TemplateNotFoundException e) {
				render("CRUD/blank.html", type, object);
			}
		}

		object._save();
		
		flash.success(play.i18n.Messages.get("report.created", type.modelName));
		if (params.get("_save") != null) {
			redirect("Admin.index");
		}
		render("Admin.index");
	}

	private static void importDomainNames(Domains domains) {
		File f = domains.domainFile.getFile();
		BufferedReader entrada;
		try {
			entrada = new BufferedReader(new FileReader(f));
			String linea;
			while (entrada.ready()) {
				linea = entrada.readLine();
				Domain domain = new Domain();
				domain.name = linea.trim();
				domain._save();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
