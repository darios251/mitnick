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
		String retorno = "";
		List<Domain> domainNames = Domain.findAll();
		int cant = domainNames.size();
		int i = 0;
		while (i<3 && i<= cant){
			retorno = retorno.concat(domainNames.get(i).name).concat(", ");
			i++;
		}
		return retorno.concat("....");
	}
}
