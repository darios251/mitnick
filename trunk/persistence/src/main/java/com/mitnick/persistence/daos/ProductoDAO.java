package com.mitnick.persistence.daos;

import org.appfuse.dao.hibernate.GenericDaoHibernate;

import com.mitnick.persistence.entities.Producto;

public class ProductoDAO extends GenericDaoHibernate<Producto, Long>  implements IProductoDAO {

	public ProductoDAO(Class<Producto> persistentClass) {
		super(persistentClass);
	}

	
}
