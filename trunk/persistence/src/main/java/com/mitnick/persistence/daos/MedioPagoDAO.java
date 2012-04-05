package com.mitnick.persistence.daos;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

import com.mitnick.persistence.entities.MedioPago;

@Repository("medioPagoDao")
public class MedioPagoDao extends GenericDaoHibernate<MedioPago, Long>  implements IMedioPagoDao {

	public MedioPagoDao(Class<MedioPago> persistentClass) {
		super(persistentClass);
	}


}
