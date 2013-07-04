package com.mitnick.persistence.daos;

import java.math.BigDecimal;
import java.util.List;

import org.appfuse.dao.GenericDao;

import com.mitnick.persistence.entities.Credito;
import com.mitnick.persistence.entities.Venta;
import com.mitnick.servicio.servicios.dtos.ReportesDto;
import com.mitnick.utils.dtos.CuotaDto;
import com.mitnick.utils.dtos.VentaDto;

public interface IVentaDAO extends GenericDao<Venta, Long>{

	List<Venta> findByFiltro(ReportesDto filtro, int numeroCaja);
	
	Venta saveOrUpdate(Venta venta);
	
	List<Venta> findByClient(Long cliente);
	
	public Venta findByNumeroFactura(String numeroTicket, int numeroCaja);
	
	public void usarCredito(String nroCredito, BigDecimal montoUsado);
	
	public Credito getCredito(String nroCredito);
	
	public void actualizarCreditos(VentaDto venta);
	
	public void actualizarCreditos(List<CuotaDto> cuotas);
	
	public Venta findTransactionByNumeroFactura(String numeroTicket, int numeroCaja);
	
	public Venta findLastByClient(Long cliente);
	
	public Venta findTransactionByNumeroTipoFactura(String numeroTicket, String tipo, String factura, int numeroCaja);
}
