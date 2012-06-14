package com.mitnick.servicio.servicios;

import java.util.List;

import com.mitnick.servicio.servicios.dtos.ConsultaProveedorDto;
import com.mitnick.utils.dtos.ProveedorDto;

public interface IProveedorServicio {

	ProveedorDto guardarProveedor(ProveedorDto proveedor);
	
	void bajaProveedor(ProveedorDto proveedor);
	
	List<ProveedorDto> obtenerProveedores();
	
	List<ProveedorDto> consultaProveedor(ConsultaProveedorDto dto);
}
