package com.mitnick.servicio.servicios;

import java.util.List;

import com.mitnick.servicio.servicios.dtos.ConsultaProductoDto;
import com.mitnick.servicio.servicios.dtos.ConsultaStockDto;
import com.mitnick.utils.dtos.MarcaDto;
import com.mitnick.utils.dtos.ProductoDto;
import com.mitnick.utils.dtos.TipoDto;

public interface IProductoServicio {

	List<ProductoDto> consultaProducto(ConsultaProductoDto filtro);
	
	ProductoDto guardarProducto(ProductoDto producto);
	
	void bajaProducto(ProductoDto producto);
	
	List<ProductoDto> obtenerStock(ConsultaStockDto filtro);
	
	List<TipoDto> obtenerTipos();
	
	List<MarcaDto> obtenerMarcas();

	List<ProductoDto> obtenerProductos();
}
