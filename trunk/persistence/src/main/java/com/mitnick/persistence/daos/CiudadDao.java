package com.mitnick.persistence.daos;

import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.mitnick.persistence.entities.Ciudad;
import com.mitnick.persistence.entities.Provincia;
import com.mitnick.persistence.entities.Venta;

@Repository("ciudadDao")
public class CiudadDao extends GenericDaoHibernate<Ciudad, Long>  implements ICiudadDao {

	public CiudadDao() {
		super(Ciudad.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Ciudad> getByProvincia(Provincia provincia) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Venta.class);

		criteria.add(Restrictions.gt("provincia", provincia));
		
		criteria.addOrder(Order.desc("descripcion"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

}