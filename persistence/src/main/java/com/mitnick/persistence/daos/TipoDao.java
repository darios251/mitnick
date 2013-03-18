package com.mitnick.persistence.daos;

import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.mitnick.persistence.entities.Tipo;
import com.mitnick.utils.Validator;

@Repository("tipoDao")
public class TipoDao extends GenericDaoHibernate<Tipo, Long>  implements ITipoDao {

	public TipoDao() {
		super(Tipo.class);
	}

	
	@SuppressWarnings("unchecked")
	public Tipo findById(Long id) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Tipo.class);

		if (Validator.isNotNull(id)) {
			criteria.add(Restrictions.eq("id", id));
		}		
		List<Tipo> tipos = getHibernateTemplate()
				.findByCriteria(criteria);
		if (tipos != null && !tipos.isEmpty())
			return tipos.get(0);
		return null;
	}
	
}
