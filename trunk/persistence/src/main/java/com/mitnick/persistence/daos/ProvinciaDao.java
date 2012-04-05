package com.mitnick.persistence.daos;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

import com.mitnick.persistence.entities.Provincia;

@Repository("provinciaDao")
public class ProvinciaDao extends GenericDaoHibernate<Provincia, Long>  implements IProvinciaDao {

	public ProvinciaDao(Class<Provincia> persistentClass) {
		super(persistentClass);
	}

}
