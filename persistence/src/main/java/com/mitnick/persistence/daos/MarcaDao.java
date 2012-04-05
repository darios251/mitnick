package com.mitnick.persistence.daos;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.mitnick.persistence.entities.Marca;

@Repository("marcaDao")
public class MarcaDao extends GenericDaoHibernate<Marca, Long>  implements IMarcaDao {

	 
	public MarcaDao(@Value("com.mitnick.persistence.entities.Marca") Class<Marca> persistentClass) {
		super(persistentClass);
	}

}
