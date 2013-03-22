package com.mitnick.persistence.daos;

import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.mitnick.persistence.entities.Ciudad;
import com.mitnick.persistence.entities.Cliente;
import com.mitnick.persistence.entities.Provincia;

@Repository("ciudadDao")
public class CiudadDao extends GenericDaoHibernate<Ciudad, Long>  implements ICiudadDao {

	public CiudadDao() {
		super(Ciudad.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Ciudad> getByProvincia(Provincia provincia) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Ciudad.class);

		criteria.add(Restrictions.eq("provincia.id", provincia.getId()));
		
		criteria.addOrder(Order.desc("descripcion"));
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Ciudad> getByProvinciaPostalCode(Provincia provincia, String postal) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Ciudad.class);

		criteria.add(Restrictions.eq("provincia.id", provincia.getId()));
		criteria.add(Restrictions.eq("codigoPostal", postal));
		
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Ciudad> getByProvinciaDescription(Provincia provincia, String description) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Ciudad.class);

		criteria.add(Restrictions.eq("provincia.id", provincia.getId()));
		criteria.add(Restrictions.ilike("descripcion", description));
		
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Ciudad> getByDescripcion(String descripcion) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Ciudad.class);

		criteria.add(Restrictions.eq("descripcion", descripcion));
		
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Ciudad> getByPostal(String postal) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Ciudad.class);

		criteria.add(Restrictions.eq("codigoPostal", postal));
		
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Ciudad getById(Long id) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Ciudad.class);

		criteria.add(Restrictions.eq("id", id));
		
		List<Ciudad> ciudades = getHibernateTemplate().findByCriteria(criteria);
		if (ciudades != null && !ciudades.isEmpty())
			return ciudades.get(0);
		
		return null;
				
	}

}
