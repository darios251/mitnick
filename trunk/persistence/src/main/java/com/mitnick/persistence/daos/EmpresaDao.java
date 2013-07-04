package com.mitnick.persistence.daos;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.mitnick.persistence.entities.Empresa;

@Repository("empresaDao")
public class EmpresaDao extends GenericDaoHibernate<Empresa, Long>  implements IEmpresaDao {

	public EmpresaDao() {
		super(Empresa.class);
	}

	@Override
	public Empresa getEmpresa() {
		DetachedCriteria criteria = DetachedCriteria
				.forClass(Empresa.class);
		criteria.add(Restrictions.idEq(new Long(1)));
		Empresa empresa = (Empresa) getHibernateTemplate().findByCriteria(criteria).get(0);
		
		return empresa;
	}
	
	@Override
	public Empresa saveOrUpdate(Empresa empresa) {
		getHibernateTemplate().saveOrUpdate(empresa);
		return empresa;
	}
}
