package com.mitnick.persistence.daos;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

import com.mitnick.persistence.entities.MedioPago;

@Repository("medioPagoDao")
public class MedioPagoDAO extends GenericDaoHibernate<MedioPago, Long>  implements IMedioPagoDAO {

	public MedioPagoDAO() {
		super(MedioPago.class);
	}


}
