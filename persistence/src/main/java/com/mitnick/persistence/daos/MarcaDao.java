package com.mitnick.persistence.daos;

import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.mitnick.persistence.entities.Marca;
import com.mitnick.utils.Validator;

@Repository("marcaDao")
public class MarcaDao extends GenericDaoHibernate<Marca, Long>  implements IMarcaDao {

	public MarcaDao() {
		super(Marca.class);
	}

	@SuppressWarnings("unchecked")
	public Marca findById(Long id) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Marca.class);

		if (Validator.isNotNull(id)) {
			criteria.add(Restrictions.eq("id", id));
		}
		List<Marca> marcas = getHibernateTemplate()
				.findByCriteria(criteria);
		if (marcas != null && !marcas.isEmpty())
			return marcas.get(0);
		return null;
	}
	
}
