package com.mitnick.business.servicios;

import java.util.List;

import com.mitnick.business.services.ServicioBase;
import com.mitnick.servicio.servicios.IProductoServicio;
import com.mitnick.servicio.servicios.dtos.ConsultaProductoDto;
import com.mitnick.servicio.servicios.dtos.ConsultaStockDto;
import com.mitnick.utils.dtos.MarcaDto;
import com.mitnick.utils.dtos.ProductoDto;
import com.mitnick.utils.dtos.TipoDto;

public class ProductoServicio extends ServicioBase implements IProductoServicio {

	@Override
	public List<ProductoDto> consultaProducto(ConsultaProductoDto filtro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductoDto altaProducto(ProductoDto producto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void bajaProducto(ProductoDto producto) {
		// TODO Auto-generated method stub

	}

	@Override
	public void modificarProducto(ProductoDto producto) {
		// TODO Auto-generated method stub

	}

	@Override
	public void modificarStock(ProductoDto producto, int cantidad) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<ProductoDto> obtenerStock(ConsultaStockDto filtro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TipoDto> obtenerTipos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MarcaDto> obtenerMarcas() {
		// TODO Auto-generated method stub
		return null;
	}

}
