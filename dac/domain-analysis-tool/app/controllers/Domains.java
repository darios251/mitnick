package controllers;


import play.mvc.With;
import util.DATConstants;

@Check(DATConstants.Constants.Roles.DOMIANS_ROLE)
@With(Secure.class)
public class Domains extends CRUD {

}
