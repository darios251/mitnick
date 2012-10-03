package com.mitnick.persistence.daos;

import java.util.List;

import org.appfuse.dao.GenericDao;

import com.mitnick.persistence.entities.MedioPago;

public interface IMedioPagoDAO extends GenericDao<MedioPago, Long>{
	
	public List<MedioPago> obtenerMediosPagosCuentaCorriente();

}
