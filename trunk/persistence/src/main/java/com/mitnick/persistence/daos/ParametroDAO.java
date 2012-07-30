package com.mitnick.persistence.daos;

import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.mitnick.persistence.entities.Parametro;
import com.mitnick.utils.Validator;

@Repository("parametroDao")
public class ParametroDAO extends GenericDaoHibernate<Parametro, Long>  implements IParametroDAO {

	public ParametroDAO() {
		super(Parametro.class);
	}

	@Override
	public Parametro getByName(String name) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Parametro.class);
		
		if(!Validator.isBlankOrNull(name)){
			criteria.add(Restrictions.ilike("nombre", name));
		}
		List<Parametro> parametros = getHibernateTemplate().findByCriteria(criteria);
		if (parametros.isEmpty())
			return null;
		else
			return parametros.get(0);
	}
	
}
