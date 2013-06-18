package controllers;

import play.*;
import play.mvc.*;

import java.io.IOException;
import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index() {
        render();
    }
    
    public static void getBackLinks() {
    	List<Map<String, String>> backLinks = MajesticSEOConnector.getBackLinks();
    	render(backLinks);
    }
    
    public static void getIndexItemInfo() {
    	List<Map<String, String>> results = MajesticSEOConnector.getIndexItemInfo();
    	render(results);
    }
    
    public static void searchInGoogle(String query) {
    	try {
			GoogleSearchAPIConnector.search(query);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	render();
    }

}