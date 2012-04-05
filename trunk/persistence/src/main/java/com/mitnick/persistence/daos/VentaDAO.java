package com.mitnick.persistence.daos;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

import com.mitnick.persistence.entities.Venta;

@Repository("ventaDao")
public class VentaDao extends GenericDaoHibernate<Venta, Long>  implements IVentaDao {

	public VentaDao(Class<Venta> persistentClass) {
		super(persistentClass);
	}


}
