package com.mitnick.business.servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mitnick.business.exceptions.BusinessException;
import com.mitnick.business.services.ServicioBase;
import com.mitnick.persistence.daos.IMarcaDao;
import com.mitnick.persistence.daos.IProductoDAO;
import com.mitnick.persistence.daos.ITipoDao;
import com.mitnick.persistence.entities.Marca;
import com.mitnick.persistence.entities.Movimiento;
import com.mitnick.persistence.entities.Producto;
import com.mitnick.persistence.entities.Tipo;
import com.mitnick.servicio.servicios.IProductoServicio;
import com.mitnick.servicio.servicios.dtos.ConsultaProductoDto;
import com.mitnick.servicio.servicios.dtos.ConsultaStockDto;
import com.mitnick.utils.Validator;
import com.mitnick.utils.VentaHelper;
import com.mitnick.utils.dtos.MarcaDto;
import com.mitnick.utils.dtos.ProductoDto;
import com.mitnick.utils.dtos.TipoDto;

@Service("productoServicio")
public class ProductoServicio extends ServicioBase implements IProductoServicio {

	@Autowired
	protected IProductoDAO productoDao;

	@Autowired
	protected IMarcaDao marcaDao;

	@Autowired
	protected ITipoDao tipoDao;

	@Transactional(readOnly=true)
	@Override
	public List<ProductoDto> consultaProducto(ConsultaProductoDto filtro) {
		List<ProductoDto> resultado = new ArrayList<ProductoDto>();
		try{
			for (Producto producto : productoDao.findByFiltro(filtro))
				resultado.add(entityDTOParser.getDtoFromEntity(producto));
		} catch (Exception e) {
			throw new BusinessException("error.persistence", "Error en capa de persistencia de  cliente", e);
		}

		return resultado;
	}

	@Transactional
	@Override
	public ProductoDto altaProducto(ProductoDto productoDto) {
		validar(productoDto);
		try {
			//TODO: validar el codigo de producto unico
			//se calcula el impuesto del producto
			productoDto.setIva(VentaHelper.CalcularImpuesto(productoDto));
			
			Producto producto = entityDTOParser.getEntityFromDto(productoDto);
			//se calcula la cantidad a ajustar
			Movimiento movimiento = new Movimiento();
			movimiento.setCantidad(productoDto.getStock());
			//el primer movimiento al dar de alta el producto es igual al stock ingresado
			movimiento.setStockAlaFecha(productoDto.getStock());
			movimiento.setFecha(new Date());
			movimiento.setTipo(Movimiento.AJUSTE);
			producto.addMovimientos(movimiento);
			
			producto = productoDao.saveOrUpdate(producto);
			productoDto.setId(producto.getId());
		} catch (Exception e) {
			throw new BusinessException("error.persistence", "Error en capa de persistencia de  cliente", e);
		}
		return productoDto;
	}

	@Transactional
	@Override
	public void bajaProducto(ProductoDto producto) {
		if (producto.getId() == null) {
			throw new BusinessException("error.productoServicio.id.nulo", "Se invoca la modificacion de un producto que no existe en la base de datos ya que no se brinda el ID");
		}
		try {
			productoDao.remove(producto.getId());
		} catch (Exception e) {
			throw new BusinessException("error.persistence", "Error en capa de persistencia de  cliente", e);
		}


	}

	@Transactional
	@Override
	public void modificarProducto(ProductoDto productoDto) {
		if (productoDto.getId() == null) {
			throw new BusinessException("error.productoServicio.id.nulo", "Se invoca la modificacion de un producto que no existe en la base de datos ya que no se brinda el ID");
		}

		validar(productoDto);
		try {
			//se calcula el impuesto del producto
			productoDto.setIva(VentaHelper.CalcularImpuesto(productoDto));
			Producto producto = entityDTOParser.getEntityFromDto(productoDto);
			productoDao.saveOrUpdate(producto);
		} catch (Exception e) {
			throw new BusinessException("error.persistence", "Error en capa de persistencia de  cliente", e);
		}

	}

	
	/**
	 * Este método modifica la cantidad de stock del producto pasado como
	 * parámetro. 
	 * 
	 * @param producto
	 * @param cantidad
	 *            puede ser negativa o postiva
	 */
	@Transactional
	@Override
	public void modificarStock(ProductoDto productoDto, int cantidad) {
		if (productoDto.getId() == null) {
			throw new BusinessException("error.productoServicio.id.nulo", "Se invoca la modificacion de un producto que no existe en la base de datos ya que no se brinda el ID");
		}
		Producto producto = null;
		try {
			
			producto = productoDao.get(productoDto.getId());
		} catch (Exception e) {
			throw new BusinessException("error.persistence", "Error en capa de persistencia de  cliente", e);
		}
		
		int stockOriginal = producto.getStock();
		
		int movimientoCantidad = cantidad - stockOriginal;
		
		//se calcula la cantidad a ajustar
		Movimiento movimiento = new Movimiento();
		movimiento.setCantidad(movimientoCantidad);
		movimiento.setFecha(new Date());
		movimiento.setTipo(Movimiento.AJUSTE);
		movimiento.setProducto(producto);
		producto.addMovimientos(movimiento);
		producto.setStock(cantidad);
		
		try {
			productoDao.saveOrUpdate(producto);
		} catch (Exception e) {
			throw new BusinessException("error.persistence", "Error en capa de persistencia de  cliente", e);
		}

		productoDto.setStock(cantidad);

	}

	@Transactional(readOnly=true)
	@Override
	public List<ProductoDto> obtenerStock(ConsultaStockDto filtro) {
		List<ProductoDto> resultado = new ArrayList<ProductoDto>();
		try {
			for (Producto producto : productoDao.findStockByFiltro(filtro))
				resultado.add(entityDTOParser.getDtoFromEntity(producto));
		} catch (Exception e) {
			throw new BusinessException("error.persistence", "Error en capa de persistencia de  cliente", e);
		}
		return resultado;
	}

	@Transactional(readOnly=true)
	@Override
	public List<TipoDto> obtenerTipos() {
		List<TipoDto> resultado = new ArrayList<TipoDto>();
		try {
			for (Tipo tipo : tipoDao.getAll())
				resultado.add(entityDTOParser.getDtoFromEntity(tipo));
		} catch (Exception e) {
			throw new BusinessException("error.persistence", "Error en capa de persistencia de  cliente", e);
		}
		return resultado;
	}

	@Transactional(readOnly=true)
	@Override
	public List<MarcaDto> obtenerMarcas() {
		List<MarcaDto> resultado = new ArrayList<MarcaDto>();
		try {
			for (Marca marca: marcaDao.getAll())
				resultado.add(entityDTOParser.getDtoFromEntity(marca));
		} catch (Exception e) {
			throw new BusinessException("error.persistence", "Error en capa de persistencia de  cliente", e);
		}
		return resultado;
	}
	
	@Transactional(readOnly=true)
	@Override
	public List<ProductoDto> obtenerProductos() {
		List<ProductoDto> productos = new ArrayList<ProductoDto>();
		
		for (Producto producto : productoDao.getAll()) {
			productos.add(entityDTOParser.getDtoFromEntity(producto));
		}
		
		return productos;
	}
	
	private void validar(ProductoDto productoDto) {
		if (Validator.isBlankOrNull(productoDto.getDescripcion()))
			throw new BusinessException("error.productoServicio.descripcion.nulo");	
		if (Validator.isBlankOrNull(productoDto.getCodigo()))
			throw new BusinessException("error.productoServicio.codigo.nulo");	
		if (Validator.isNull(productoDto.getMarca()))
			throw new BusinessException("error.productoServicio.marca.nulo");	
		if (Validator.isNull(productoDto.getTipo()))
			throw new BusinessException("error.productoServicio.tipo.nulo");	
		if (Validator.isNull(productoDto.getPrecio()))
			throw new BusinessException("error.productoServicio.precio.nulo");	
		if (Validator.isNull(productoDto.getStock()))
			throw new BusinessException("error.productoServicio.stock.nulo");	
	
	}

	public void setProductoDao(IProductoDAO productoDao) {
		this.productoDao = productoDao;
	}

	public void setMarcaDao(IMarcaDao marcaDao) {
		this.marcaDao = marcaDao;
	}

	public void setTipoDao(ITipoDao tipoDao) {
		this.tipoDao = tipoDao;
	}

}
