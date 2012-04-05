package com.mitnick.persistence.daos;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

import com.mitnick.persistence.entities.Tipo;

@Repository("tipoDao")
public class TipoDao extends GenericDaoHibernate<Tipo, Long>  implements ITipoDao {

	public TipoDao(Class<Tipo> persistentClass) {
		super(persistentClass);
	}

}
