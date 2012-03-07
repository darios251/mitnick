package com.mitnick.persistence.daos;

import org.appfuse.dao.hibernate.GenericDaoHibernate;

import com.mitnick.persistence.entities.Venta;

public class VentaDAO extends GenericDaoHibernate<Venta, Long>  implements IVentaDAO {

	public VentaDAO(Class<Venta> persistentClass) {
		super(persistentClass);
	}


}
