package controllers;

import models.User;
import play.Play;
import play.libs.Crypto;
import util.DATConstants;

public class Security extends Secure.Security {
	
    static boolean authenticate(String username, String password) {
    	return User.connect(username, Crypto.encryptAES(password, Play.configuration.getProperty("application.privateKey"))) != null;
    }
    
    static void onAuthenticated() {
    	boolean isAdmin = check(DATConstants.Constants.Roles.ADMIN_ROLE);
		if(isAdmin)
			Admin.index();
		else
			Application.index();
	}
    
    public static User getConnected(){
		return User.find("byUsername", connected()).<User>first();
	}
    
    public static void main(String[] args) {
    	 String password= Crypto.encryptAES("admin", "www.domainat.com");
    	 System.out.println(password);
	}
    
    static boolean check(String profile) {
		// the admin has all permissions
		if(getConnected().isAdmin)
			return true;
		if(DATConstants.Constants.Roles.DOMIANS_ROLE.equals(profile)) {
	    	return getConnected().permission.domainManager;
	    }
	    
	    return false;
	}
}