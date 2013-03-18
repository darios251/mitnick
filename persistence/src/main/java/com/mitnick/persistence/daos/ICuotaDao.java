package com.mitnick.persistence.daos;

import java.math.BigDecimal;
import java.util.List;

import org.appfuse.dao.GenericDao;

import com.mitnick.persistence.entities.Cuota;
import com.mitnick.persistence.entities.Pago;
import com.mitnick.servicio.servicios.dtos.ReportesDto;
import com.mitnick.utils.dtos.CuotaDto;

public interface ICuotaDao extends GenericDao<Cuota, Long>{
	
	public List<Cuota> getCuotaByClienteId(Long cliente);
	
	public void eliminarCuota(CuotaDto cuotaDto);
	
	public Cuota saveOrUpdate(Cuota cuota);
	
	public List<Cuota> findByFiltro(ReportesDto filtro);
	
	public BigDecimal getSaldoPendiente(Long cliente);
	
	public List<Cuota> getCuotaByClient(Long cliente);
	
	public List<Pago> getPagosCuotas(ReportesDto filtro);

}
