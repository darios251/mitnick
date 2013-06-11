package controllers;


import play.mvc.With;
import util.DATConstants;

/**
 * This CRUD just allow to show the Domain list. 
 * The user can not do any updates in Domains, just can see the list and search using filter name.
 * @author Agustina
 *
 */
@Check(DATConstants.Constants.Roles.DOMIANS_ROLE)
@With(Secure.class)
public class Domains extends CRUD {

}
