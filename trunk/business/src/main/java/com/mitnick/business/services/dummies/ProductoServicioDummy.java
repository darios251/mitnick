package com.mitnick.business.services.dummies;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mitnick.business.services.ServicioBase;
import com.mitnick.servicio.servicios.IProductoServicio;
import com.mitnick.servicio.servicios.dtos.ConsultaProductoDto;
import com.mitnick.servicio.servicios.dtos.ConsultaStockDto;
import com.mitnick.utils.dtos.MarcaDto;
import com.mitnick.utils.dtos.ProductoDto;
import com.mitnick.utils.dtos.TipoDto;

@Service("productoServicio")
public class ProductoServicioDummy extends ServicioBase implements
		IProductoServicio {

	@Override
	public List<ProductoDto> consultaProducto(ConsultaProductoDto filtro) {
		ProductoDto producto = new ProductoDto();
		producto.setId(1l);
		producto.setCodigo(filtro.getCodigo() != null ? filtro.getCodigo() : "12345");
		producto.setDescripcion(filtro.getDescripcion() != null ? filtro.getDescripcion() : "DescripcionDummy");
		producto.setMarca(new MarcaDto());
		producto.setPrecio(new BigDecimal(123));
		producto.setStock(21);
		producto.setTalle("L");
		producto.setTipo(new TipoDto());
		
		List<ProductoDto> productos = new ArrayList<ProductoDto>();
		productos.add(producto);
		return productos;

	}

	@Override
	public ProductoDto altaProducto(ProductoDto producto) {
		producto.setId(1l);
		return producto;
	}

	@Override
	public void bajaProducto(ProductoDto producto) {
		
	}

	@Override
	public void modificarProducto(ProductoDto producto) {

	}

	@Override
	public void modificarStock(ProductoDto producto, int cantidad) {

	}

	@Override
	public List<ProductoDto> obtenerStock(ConsultaStockDto filtro) {
		ProductoDto producto = new ProductoDto();
		producto.setId(1l);
		producto.setCodigo(filtro.getCodigo() != null ? filtro.getCodigo() : "12345");
		producto.setDescripcion(filtro.getDescripcion() != null ? filtro.getDescripcion() : "DescripcionDummy");
		producto.setMarca(new MarcaDto());
		producto.setPrecio(new BigDecimal(123));
		producto.setStock(21);
		producto.setTalle("L");
		producto.setTipo(new TipoDto());
		
		List<ProductoDto> productos = new ArrayList<ProductoDto>();
		productos.add(producto);
		return productos;
	}

	@Override
	public List<TipoDto> obtenerTipos() {
		List<TipoDto> tipos = new ArrayList<TipoDto>();
		TipoDto tipo = new TipoDto();
		tipo.setId(1l);
		tipo.setCodigo("tipo 1");
		tipo.setDescripcion("Descripción tipo 1");
		
		tipos.add(tipo);
		
		tipo = new TipoDto();
		tipo.setId(1l);
		tipo.setCodigo("tipo 2");
		tipo.setDescripcion("Descripción tipo 2");
		
		tipos.add(tipo);
		return tipos;
	}

	@Override
	public List<MarcaDto> obtenerMarcas() {
		List<MarcaDto> marcas = new ArrayList<MarcaDto>();
		MarcaDto marca = new MarcaDto();
		marca.setId(1l);
		marca.setCodigo("marca 1");
		marca.setDescripcion("Descripción marca 1");
		
		marcas.add(marca);
		
		marca = new MarcaDto();
		marca.setId(1l);
		marca.setCodigo("marca 2");
		marca.setDescripcion("Descripción marca 2");
		
		marcas.add(marca);
		return marcas;
	}

}
