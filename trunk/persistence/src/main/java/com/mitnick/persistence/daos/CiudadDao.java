package com.mitnick.persistence.daos;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

import com.mitnick.persistence.entities.Ciudad;

@Repository("ciudadDao")
public class CiudadDao extends GenericDaoHibernate<Ciudad, Long>  implements ICiudadDao {

	public CiudadDao(Class<Ciudad> persistentClass) {
		super(persistentClass);
	}

}
