package com.mitnick.persistence.daos;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.mitnick.persistence.entities.Configuracion;

@Repository("configuracionDao")
public class ConfiguracionDAO extends GenericDaoHibernate<Configuracion, Long>  implements IConfiguracionDAO {

	public ConfiguracionDAO() {
		super(Configuracion.class);
	}

	@Override
	public Configuracion getConfiguracion() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Configuracion.class);
		return (Configuracion) getHibernateTemplate().findByCriteria(criteria).get(0);
	}
}
