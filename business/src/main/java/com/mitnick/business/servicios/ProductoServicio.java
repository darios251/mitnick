package com.mitnick.business.servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mitnick.exceptions.BusinessException;
import com.mitnick.persistence.daos.IMarcaDao;
import com.mitnick.persistence.daos.IMovimientoDao;
import com.mitnick.persistence.daos.IProductoDAO;
import com.mitnick.persistence.daos.ITipoDao;
import com.mitnick.persistence.entities.Marca;
import com.mitnick.persistence.entities.Movimiento;
import com.mitnick.persistence.entities.Producto;
import com.mitnick.persistence.entities.Tipo;
import com.mitnick.servicio.servicios.IProductoServicio;
import com.mitnick.servicio.servicios.dtos.ConsultaProductoDto;
import com.mitnick.servicio.servicios.dtos.ConsultaStockDto;
import com.mitnick.util.EntityDTOParser;
import com.mitnick.utils.Validator;
import com.mitnick.utils.VentaHelper;
import com.mitnick.utils.dtos.MarcaDto;
import com.mitnick.utils.dtos.ProductoDto;
import com.mitnick.utils.dtos.TipoDto;

@Service("productoServicio")
public class ProductoServicio extends ServicioBase<Producto, ProductoDto> implements IProductoServicio {

	@Autowired
	protected IProductoDAO productoDao;

	@Autowired
	protected IMarcaDao marcaDao;

	@Autowired
	protected ITipoDao tipoDao;
	
	@Autowired
	private IMovimientoDao movimientoDao;
	
	@Autowired
	private EntityDTOParser<Tipo, TipoDto> entityDTOParserTipo;
	
	@Autowired
	private EntityDTOParser<Marca, MarcaDto> entityDTOParserMarca;

	@Transactional(readOnly=true)
	@Override
	public List<ProductoDto> consultaProducto(ConsultaProductoDto filtro) {
		try{
			return entityDTOParser.getDtosFromEntities(productoDao.findByFiltro(filtro));
		} catch (Exception e) {
			throw new BusinessException("error.persistence", "Error en capa de persistencia de  cliente", e);
		}
	}

	@Transactional
	@Override
	public ProductoDto guardarProducto(ProductoDto productoDto) {
		validar(productoDto);
		try {
			//TODO: validar el codigo de producto unico
			//se calcula el impuesto del producto
			productoDto.setIva(VentaHelper.CalcularImpuesto(productoDto));
			
			Producto producto = entityDTOParser.getEntityFromDto(productoDto);
			//se calcula la cantidad a ajustar
			//producto.addMovimientos(movimiento);
			
			producto = productoDao.saveOrUpdate(producto);
			productoDto.setId(producto.getId());
			
			Movimiento movimiento = new Movimiento();
			movimiento.setCantidad(productoDto.getStock());
			//el primer movimiento al dar de alta el producto es igual al stock ingresado
			movimiento.setStockAlaFecha(productoDto.getStock());
			movimiento.setFecha(new Date());
			movimiento.setTipo(Movimiento.AJUSTE);
			movimiento.setProducto(producto);
			
			movimientoDao.saveOrUpdate(movimiento);
		} catch (Exception e) {
			throw new BusinessException("error.persistence", "Error en capa de persistencia de  cliente", e);
		}
		return productoDto;
	}

	@Transactional
	@Override
	public void bajaProducto(ProductoDto productoDto) {
		if (productoDto.getId() == null) {
			throw new BusinessException("error.productoServicio.id.nulo", "Se invoca la modificacion de un producto que no existe en la base de datos ya que no se brinda el ID");
		}
		try {
			Producto producto = entityDTOParser.getEntityFromDto(productoDto);
			producto.setEliminado(true);
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
		
		producto.setStock(cantidad);
		
		try {
			productoDao.saveOrUpdate(producto);
			
			int stockOriginal = producto.getStock();
			
			int movimientoCantidad = cantidad - stockOriginal;
			
			//se calcula la cantidad a ajustar
			Movimiento movimiento = new Movimiento();
			movimiento.setCantidad(movimientoCantidad);
			movimiento.setFecha(new Date());
			movimiento.setTipo(Movimiento.AJUSTE);
			movimiento.setProducto(producto);
			
			movimientoDao.saveOrUpdate(movimiento);
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
			resultado.addAll(entityDTOParser.getDtosFromEntities(productoDao.findStockByFiltro(filtro)));
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
			resultado.addAll(entityDTOParserTipo.getDtosFromEntities(tipoDao.getAll()));
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
			resultado.addAll(entityDTOParserMarca.getDtosFromEntities(marcaDao.getAll()));
		} catch (Exception e) {
			throw new BusinessException("error.persistence", "Error en capa de persistencia de  cliente", e);
		}
		return resultado;
	}
	
	@Transactional(readOnly=true)
	@Override
	public List<ProductoDto> obtenerProductos() {
		List<ProductoDto> productos = new ArrayList<ProductoDto>();
		
		productos.addAll(entityDTOParser.getDtosFromEntities(productoDao.getAll()));
		
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

	public void setMovimientoDao(IMovimientoDao movimientoDao) {
		this.movimientoDao = movimientoDao;
	}

}
