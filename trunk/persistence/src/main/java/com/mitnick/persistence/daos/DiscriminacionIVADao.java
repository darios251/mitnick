package com.mitnick.persistence.daos;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.mitnick.persistence.entities.DiscriminacionIVA;

@Repository("discriminacionIVADao")
public class DiscriminacionIVADao extends GenericDaoHibernate<DiscriminacionIVA, Long>  implements IDiscriminacionIVADao {

	public DiscriminacionIVADao() {
		super(DiscriminacionIVA.class);
	}

	@Override
	public DiscriminacionIVA findDiscriminacionIVAporCodigo(String codigo) {
		DetachedCriteria criteria = DetachedCriteria.forClass(DiscriminacionIVA.class);
		criteria.add(Restrictions.ilike("codigo", codigo));
		return (DiscriminacionIVA) getHibernateTemplate().findByCriteria(criteria).get(0);
	}

}
