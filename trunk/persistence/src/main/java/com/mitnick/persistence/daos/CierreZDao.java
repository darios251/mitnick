package com.mitnick.persistence.daos;

import java.util.Date;
import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.mitnick.persistence.entities.CierreZ;

@Repository("cierreZDao")
public class CierreZDao extends GenericDaoHibernate<CierreZ, Long>  implements ICierreZDao {

	public CierreZDao() {
		super(CierreZ.class);
	}
	
	public CierreZ findByFecha(Date fecha, int numeroCaja){
		DetachedCriteria criteria = DetachedCriteria.forClass(CierreZ.class);
		criteria.add(Restrictions.eq("fecha", fecha));
		criteria.add(Restrictions.eq("numeroCaja", numeroCaja));
		@SuppressWarnings("unchecked")
		List<CierreZ> cierres = getHibernateTemplate().findByCriteria(criteria);
		if (cierres!=null && !cierres.isEmpty())
			return cierres.get(0);
		return null; 
	}
	
}
