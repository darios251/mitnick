package com.mitnick.persistence.daos;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

import com.mitnick.persistence.entities.DiscriminacionIVA;

@Repository("discriminacionIVADao")
public class DiscriminacionIVADao extends GenericDaoHibernate<DiscriminacionIVA, Long>  implements IDiscriminacionIVADao {

	public DiscriminacionIVADao() {
		super(DiscriminacionIVA.class);
	}

}
