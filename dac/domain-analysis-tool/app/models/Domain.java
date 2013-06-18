package models;

import javax.persistence.Entity;

import play.db.jpa.Model;


public class Domain {

	public String name;
	
	public String toString() {
		return name;
	}
}
