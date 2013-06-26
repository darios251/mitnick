package controllers;

import models.Proxy;
import play.mvc.With;
import util.DATConstants;

@Check(DATConstants.Constants.Roles.DOMIANS_ROLE)
@With(Secure.class)
@CRUD.For(value = Proxy.class)
public class Proxies extends CRUD {

}
