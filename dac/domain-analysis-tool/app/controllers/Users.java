package controllers;

import java.lang.reflect.Constructor;

import models.Permission;
import models.User;
import play.Play;
import play.data.binding.Binder;
import play.db.Model;
import play.exceptions.TemplateNotFoundException;
import play.libs.Crypto;
import play.mvc.*;

public class Users extends CRUD {

	public static void save(String id) throws Exception {
        ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        Model object = type.findById(id);
        notFoundIfNull(object);
        
        User user = ((User)object);
        
        Permission permission = user.permission;
        
        Binder.bindBean(params.getRootParamNode(), "object", user);
        
        validation.valid(user);
        if (validation.hasErrors()) {
            renderArgs.put("error", play.i18n.Messages.get("crud.hasErrors"));
            try {
                render(request.controller.replace(".", "/") + "/show.html", type, user);
            } catch (TemplateNotFoundException e) {
                render("CRUD/show.html", type, user);
            }
        }
        
        user.password = Crypto.encryptAES(user.password, Play.configuration.getProperty("application.privateKey"));
        
        user._save();
        
        flash.success(play.i18n.Messages.get("crud.saved", type.modelName));
        if (params.get("_save") != null) {
            redirect(request.controller + ".list");
        }
        redirect(request.controller + ".show", user._key());
    }
	
	public static void create() throws Exception {
		ObjectType type = ObjectType.get(getControllerClass());
		notFoundIfNull(type);
		Constructor<?> constructor = type.entityClass.getDeclaredConstructor();
		constructor.setAccessible(true);
		Model object = (Model) constructor.newInstance();
		
		User user = ((User)object);
		
		Binder.bindBean(params.getRootParamNode(), "object", object);

		validation.valid(object);
		if (validation.hasErrors()) {
			renderArgs.put("error", play.i18n.Messages.get("crud.hasErrors"));
			try {
				render(request.controller.replace(".", "/") + "/blank.html",
						type, object);
			} catch (TemplateNotFoundException e) {
				render("CRUD/blank.html", type, object);
			}
		}
		
		user.password = Crypto.encryptAES(user.password, Play.configuration.getProperty("application.privateKey"));
		
		object._save();

		flash.success(play.i18n.Messages.get("crud.created", type.modelName));
		if (params.get("_save") != null) {
			redirect(request.controller + ".list");
		}
		if (params.get("_saveAndAddAnother") != null) {
			redirect(request.controller + ".blank");
		}
		redirect(request.controller + ".show", object._key());
	}
	
	public static void show(String id) throws Exception {
        ObjectType type = ObjectType.get(getControllerClass());
        notFoundIfNull(type);
        Model object = type.findById(id);
        User user = (User) object;
        notFoundIfNull(user);
        if(user.permission == null)
        	user.permission = new Permission();
        
        user.password = Crypto.decryptAES(user.password, Play.configuration.getProperty("application.privateKey"));
        try {
            render(type, object);
        } catch (TemplateNotFoundException e) {
            render("CRUD/show.html", type, object);
        }
    }
}
