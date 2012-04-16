package com.mitnick.persistence.daos;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;
import com.mitnick.persistence.entities.Direccion;

@Repository("direccionDao")
public class DireccionDao extends GenericDaoHibernate<Direccion, Long>  implements IDireccionDao {

	public DireccionDao() {
		super(Direccion.class);
	}
	
	public Direccion saveOrUpdate(Direccion direccion){
		getHibernateTemplate().saveOrUpdate(direccion);
		return direccion;
	}

}
