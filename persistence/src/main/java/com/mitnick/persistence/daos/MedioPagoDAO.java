package com.mitnick.persistence.daos;

import org.appfuse.dao.hibernate.GenericDaoHibernate;

import com.mitnick.persistence.entities.MedioPago;

public class MedioPagoDAO extends GenericDaoHibernate<MedioPago, Long>  implements IMedioPagoDAO {

	public MedioPagoDAO(Class<MedioPago> persistentClass) {
		super(persistentClass);
	}


}
