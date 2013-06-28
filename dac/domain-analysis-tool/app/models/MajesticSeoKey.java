package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class MajesticSeoKey extends Model {
    
	public MajesticSeoKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String apiKey;
}
