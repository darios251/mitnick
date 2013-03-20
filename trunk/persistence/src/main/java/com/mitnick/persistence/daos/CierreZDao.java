package com.mitnick.persistence.daos;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

import com.mitnick.persistence.entities.CierreZ;

@Repository("cierreZDao")
public class CierreZDao extends GenericDaoHibernate<CierreZ, Long>  implements ICierreZDao {

	public CierreZDao() {
		super(CierreZ.class);
	}
	
}
