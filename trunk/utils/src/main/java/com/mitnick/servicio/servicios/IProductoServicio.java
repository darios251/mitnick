package com.mitnick.servicio.servicios;

import java.util.List;

import org.springframework.security.access.annotation.Secured;

import com.mitnick.servicio.servicios.dtos.ConsultaProductoDto;
import com.mitnick.servicio.servicios.dtos.ConsultaStockDto;
import com.mitnick.utils.dtos.MarcaDto;
import com.mitnick.utils.dtos.ProductoDto;
import com.mitnick.utils.dtos.ProductoNuevoDto;
import com.mitnick.utils.dtos.TipoDto;

public interface IProductoServicio {

	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	List<ProductoDto> consultaProducto(ConsultaProductoDto filtro);
	
	@Secured(value={"ROLE_ADMIN"})
	ProductoNuevoDto guardarProducto(ProductoNuevoDto producto);
	
	@Secured(value={"ROLE_ADMIN"})
	void bajaProducto(ProductoDto producto);
	
	@Secured(value={"ROLE_ADMIN"})
	List<ProductoDto> obtenerStock(ConsultaStockDto filtro);
	
	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	List<TipoDto> obtenerTipos();
	
	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	List<MarcaDto> obtenerMarcas();

	@Secured(value={"ROLE_USER", "ROLE_ADMIN"})
	List<ProductoDto> obtenerProductos();
	
	@Secured(value={"ROLE_ADMIN"})
	ProductoNuevoDto getProductoNuevo(ProductoDto producto);
}
