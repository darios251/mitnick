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
	
	/**
	 * Este método modifica la cantidad de stock del producto pasado como parámetro.
	 * A la cantidad del producto actual se le debe sumar la cantidad recibida por parámetro.
	 * @param producto
	 * @param cantidad puede ser negativa o postiva
	 */
	void modificarStock(ProductoDto producto, int cantidad);
	
	List<ProductoDto> obtenerStock(ConsultaStockDto filtro);
	
	List<TipoDto> obtenerTipos();
	
	List<MarcaDto> obtenerMarcas();

	List<ProductoDto> obtenerProductos();
}
