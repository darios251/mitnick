package controllers;

import models.User;
import play.Play;
import play.libs.Crypto;

public class Security extends Secure.Security {
	
    static boolean authenticate(String username, String password) {
    	return User.connect(username, Crypto.encryptAES(password, Play.configuration.getProperty("application.privateKey"))) != null;
    }
    
    static void onAuthenticated() {
		Admin.index();
	}
    
    public static User getConnected(){
		return User.find("byUsername", connected()).<User>first();
	}
    
    public static void main(String[] args) {
    	 String password= Crypto.encryptAES("admin", "www.domainat.com");
    	 System.out.println(password);
	}
}