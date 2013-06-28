package controllers;
 
import models.MajesticSeoKey;
import models.User;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class Admin extends Controller {
    
    @Before
    static void setConnectedUser() {
        if(Security.isConnected()) {
            User user = Security.getConnected();
            renderArgs.put("user", user.name);
        }
    }
 
    public static void index() {
        render();
    }
    
    public static void majesticSeoKey() {
    	MajesticSeoKey apikey = new MajesticSeoKey("Api Key not set");
    	try {
    		apikey = (MajesticSeoKey) MajesticSeoKey.findAll().get(0);
    	}
    	catch(Exception e) {}
    	renderArgs.put("apikey", apikey.apiKey);
    	render();
    }
    
    public static void saveMajesticSeoKey() {
    	MajesticSeoKey apikey = new MajesticSeoKey("Api Key not set");
    	try {
    		apikey = (MajesticSeoKey) MajesticSeoKey.findAll().get(0);
    	}
    	catch(Exception e) {}
    	apikey.apiKey = params.get("apiKey");
    	flash.success("The api key were changed successfully");
    	apikey._save();
    	redirect("/");
    }
    
}