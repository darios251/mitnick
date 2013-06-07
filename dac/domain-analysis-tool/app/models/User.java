package models;

import javax.persistence.Entity;
import javax.persistence.Table;

import play.data.validation.Password;
import play.data.validation.Required;
import play.data.validation.Unique;
import play.db.jpa.Model;

@Entity
@Table(name = "Customer")
public class User extends Model {

	@Required
	@Unique
	public String username;

	@Required
	@Password
	public String password;

	@Required
	public String name;

	public static User connect(String username, String password) {
		return find("byUsernameAndPassword", username, password).first();
	}

	@Override
	public String toString() {
		return username;
	}

}
