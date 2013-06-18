package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.data.validation.Required;
import play.db.jpa.Blob;
import play.db.jpa.Model;
import controllers.CRUD.Hidden;

@Entity
public class Domains extends Model {

	@Required
	public Blob domainFile;
	
	@Override
	public String toString() {
		return domainFile.toString();
	}
}
