package com.mitnick.persistence.daos;

import java.math.BigDecimal;
import java.util.List;

import org.appfuse.dao.GenericDao;

import com.mitnick.persistence.entities.Credito;
import com.mitnick.persistence.entities.Venta;
import com.mitnick.servicio.servicios.dtos.ReportesDto;
import com.mitnick.utils.dtos.VentaDto;

public interface IVentaDAO extends GenericDao<Venta, Long>{

	List<Venta> findByFiltro(ReportesDto filtro);
	
	Venta saveOrUpdate(Venta venta);
	
	void generarFactura(Venta venta);
	
	List<Venta> findByClient(Long cliente);
	
	public Venta findByNumeroFactura(String numeroTicket);
	
	public void usarCredito(String nroCredito, BigDecimal montoUsado);
	
	public Credito getCredito(String nroCredito);
	
	public void actualizarCreditos(VentaDto venta);
	
}
