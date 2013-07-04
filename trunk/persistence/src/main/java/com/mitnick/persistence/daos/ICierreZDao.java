package com.mitnick.persistence.daos;

import java.util.Date;

import org.appfuse.dao.GenericDao;

import com.mitnick.persistence.entities.CierreZ;


public interface ICierreZDao extends GenericDao<CierreZ, Long>{
	
	public CierreZ findByFecha(Date fecha, int numeroCaja);
}
