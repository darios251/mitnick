package controllers;

import play.*;
import play.mvc.*;

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

}