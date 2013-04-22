package com.mitnick.persistence.daos;

import java.math.BigDecimal;
import java.util.List;

import org.appfuse.dao.GenericDao;

import com.mitnick.persistence.entities.Cliente;
import com.mitnick.persistence.entities.Comprobante;
import com.mitnick.servicio.servicios.dtos.ConsultaClienteDto;
import com.mitnick.utils.dtos.ClienteDto;
import com.mitnick.utils.dtos.CuotaDto;

public interface IClienteDao extends GenericDao<Cliente, Long>{

	List<Cliente> findByDocumento(String documento);
	
	List<Cliente> findByFiltro(ConsultaClienteDto filtro);
	
	Cliente saveOrUpdate(Cliente cliente);

	Comprobante generarComprobante(List<CuotaDto> cuotas);
	
	void reporteMovimientosCliente(ClienteDto cliente);
	
	Comprobante saveOrUpdate(Comprobante comprobante);
	
	Cliente findByDocumentoEq(String documento);
	
	Cliente findByCuitEq(String cuit);
	
	Comprobante findComprobanteByNumero(String nroComprobante);
	
	void eliminarComprobante(Comprobante comprobante);
	
	BigDecimal getSaldoDeudor(ClienteDto cliente);
	
	BigDecimal getSaldoFavor(ClienteDto cliente);
	
	
}
