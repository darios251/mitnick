package com.mitnick.business.servicios;

import java.util.List;

import com.mitnick.business.servicios.dtos.ConsultaProductoDto;
import com.mitnick.business.servicios.dtos.ConsultaStockDto;
import com.mitnick.utils.dtos.MarcaDto;
import com.mitnick.utils.dtos.ProductoDto;
import com.mitnick.utils.dtos.TipoDto;

public interface IProductoServicio {

	List<ProductoDto> consultaProducto(ConsultaProductoDto filtro);
	
	ProductoDto altaProducto(ProductoDto producto);
	
	void bajaProducto(ProductoDto producto);
	
	void modificarProducto(ProductoDto producto);
	
	/**
	 * Este método modifica la cantidad de stock del producto pasado como parámetro.
	 * 
	 * @param producto
	 * @param cantidad puede ser negativa o postiva
	 */
	void modificarStock(ProductoDto producto, int cantidad);
	
	List<ProductoDto> obtenerStock(ConsultaStockDto filtro);
	
	List<TipoDto> obtenerTipos();
	
	List<MarcaDto> obtenerMarcas();
}
