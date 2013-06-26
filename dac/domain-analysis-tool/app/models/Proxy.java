package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class Proxy extends Model {
	
	public String ip;
	
	public int port;
	
	public boolean needAuthentication;
	
	public String username;
	
	public String password;
	
	@Override
	public String toString() {
		return ip + ":" + port;
	}
    
}
