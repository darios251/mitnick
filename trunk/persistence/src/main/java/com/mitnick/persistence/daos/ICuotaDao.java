package com.mitnick.persistence.daos;

import java.util.List;

import org.appfuse.dao.GenericDao;

import com.mitnick.persistence.entities.Cuota;
import com.mitnick.utils.dtos.CuotaDto;

public interface ICuotaDao extends GenericDao<Cuota, Long>{
	
	public List<Cuota> getCuotaByClienteId(Long cliente);
	
	public void eliminarCuota(CuotaDto cuotaDto);
	
	public Cuota saveOrUpdate(Cuota cuota);

}
