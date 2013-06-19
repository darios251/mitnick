package controllers;

import play.*;
import play.mvc.*;

import java.io.IOException;
import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index() {
        Admin.index();
    }    

}