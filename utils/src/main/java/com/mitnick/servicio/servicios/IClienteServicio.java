package com.mitnick.servicio.servicios;

import java.util.List;

import org.springframework.security.access.annotation.Secured;

import com.mitnick.servicio.servicios.dtos.ConsultaClienteDto;
import com.mitnick.utils.dtos.CiudadDto;
import com.mitnick.utils.dtos.ClienteDto;
import com.mitnick.utils.dtos.CuotaDto;
import com.mitnick.utils.dtos.PagoDto;
import com.mitnick.utils.dtos.ProvinciaDto;

public interface IClienteServicio {

	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	ClienteDto guardarCliente(ClienteDto cliente);
	
	@Secured(value={"ROLE_ADMIN"})
	void eliminarCliente(ClienteDto cliente);
	
	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	List<ClienteDto> consultarCliente(ConsultaClienteDto filtro);
	
	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	List<CiudadDto> obtenerCiudades(ProvinciaDto provincia);
	
	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	List<ProvinciaDto> obtenerProvincias();

	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	void cargarReporte();
	
	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	List<CuotaDto> obtenerCuotasPendientes(ClienteDto cliente);
	
	@Secured(value={"ROLE_ADMIN"})
	void eliminarCuota(CuotaDto cuota);
	
	@Secured(value={"ROLE_ADMIN"})
	void guardarCuotas(List<CuotaDto> cuotasDtos);
	
	@Secured(value={"ROLE_ADMIN"})
	public void guardarCuota(CuotaDto cuotaDto);

	@Secured(value={"ROLE_ADMIN"})
	public List<CuotaDto> quitarPago(PagoDto pago, List<CuotaDto> cuotas);
	
	@Secured(value={"ROLE_ADMIN"})
	public List<CuotaDto> agregarPago(PagoDto pago, List<CuotaDto> cuotas);
	
	@Secured(value={"ROLE_ADMIN"})
	public void comprobantePago(List<CuotaDto> cuotaDto);
	
	@Secured(value={"ROLE_ADMIN"})
	public void reporteMovimientosCliente(ClienteDto cliente);
	
}
