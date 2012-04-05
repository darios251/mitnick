package com.mitnick.business.servicios;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitnick.business.exceptions.BusinessException;
import com.mitnick.business.services.ServicioBase;
import com.mitnick.persistence.daos.IMarcaDao;
import com.mitnick.persistence.daos.IProductoDao;
import com.mitnick.persistence.daos.ITipoDao;
import com.mitnick.persistence.entities.Marca;
import com.mitnick.persistence.entities.Movimiento;
import com.mitnick.persistence.entities.Producto;
import com.mitnick.persistence.entities.Tipo;
import com.mitnick.servicio.servicios.IProductoServicio;
import com.mitnick.servicio.servicios.dtos.ConsultaProductoDto;
import com.mitnick.servicio.servicios.dtos.ConsultaStockDto;
import com.mitnick.utils.Validator;
import com.mitnick.utils.dtos.MarcaDto;
import com.mitnick.utils.dtos.ProductoDto;
import com.mitnick.utils.dtos.TipoDto;

@Service("productoServicio")
public class ProductoServicio extends ServicioBase implements IProductoServicio {

	@Autowired
	IProductoDao productoDao;

	@Autowired
	IMarcaDao marcaDao;

	@Autowired
	ITipoDao tipoDao;

	@Override
	public List<ProductoDto> consultaProducto(ConsultaProductoDto filtro) {
		List<Producto> productos = null;
		try{
			productos = productoDao.findByCodigoDescripcion(filtro.getCodigo(), filtro.getDescripcion());
		} catch (Exception e) {
			throw new BusinessException("error.persistence", "Error en capa de persistencia de  cliente", e);
		}
		List<ProductoDto> resultado = new ArrayList<ProductoDto>();
		for (int i = 0; i < productos.size(); i++) {
			resultado.add(getProductoDtoFromProducto(productos.get(i)));
		}
		return resultado;
	}

	
	public ProductoDto altaProducto(ProductoDto productoDto) {
		validar(productoDto);
		Producto producto = getProductoFromProductoDto(productoDto);
		try {
			//se calcula la cantidad a ajustar
			Movimiento movimiento = new Movimiento();
			movimiento.setCantidad(productoDto.getStock());
			movimiento.setFecha(new Date());
			movimiento.setTipo(Movimiento.AJUSTE);
		
			producto.addMovimientos(movimiento);
			
			producto = productoDao.save(producto);
		} catch (Exception e) {
			throw new BusinessException("error.persistence", "Error en capa de persistencia de  cliente", e);
		}
		productoDto.setId(producto.getId());
		return productoDto;
	}

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

	@Override
	public void modificarProducto(ProductoDto productoDto) {
		if (productoDto.getId() == null) {
			throw new BusinessException("error.productoServicio.id.nulo", "Se invoca la modificacion de un producto que no existe en la base de datos ya que no se brinda el ID");
		}

		validar(productoDto);
		Producto producto = getProductoFromProductoDto(productoDto);
		try {
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
	
		producto.addMovimientos(movimiento);
		producto.setStock(cantidad);
		
		try {
			productoDao.saveOrUpdate(producto);
		} catch (Exception e) {
			throw new BusinessException("error.persistence", "Error en capa de persistencia de  cliente", e);
		}

		productoDto.setStock(cantidad);

	}

	@Override
	public List<ProductoDto> obtenerStock(ConsultaStockDto filtro) {
		List<Producto> productos = null;
		try {
			productos = productoDao
				.findByCodigoDescripcionTipoMarca(filtro.getCodigo(),
						filtro.getDescripcion(), filtro.getTipo(),
						filtro.getMarca());
		} catch (Exception e) {
			throw new BusinessException("error.persistence", "Error en capa de persistencia de  cliente", e);
		}

		List<ProductoDto> resultado = new ArrayList<ProductoDto>();
		for (int i = 0; i < productos.size(); i++) {
			resultado.add(getProductoDtoFromProducto(productos.get(i)));
		}
		return resultado;
	}

	@Override
	public List<TipoDto> obtenerTipos() {
		List<Tipo> tipos = null;
		try {
			tipos = tipoDao.getAll();
		} catch (Exception e) {
			throw new BusinessException("error.persistence", "Error en capa de persistencia de  cliente", e);
		}
		List<TipoDto> resultado = new ArrayList<TipoDto>();
		for (int i = 0; i < tipos.size(); i++) {
			resultado.add(getTipoDtoFromTipo(tipos.get(i)));
		}
		return resultado;
	}

	@Override
	public List<MarcaDto> obtenerMarcas() {
		List<Marca> marcas = null;
		try {
			marcas = marcaDao.getAll();
		} catch (Exception e) {
			throw new BusinessException("error.persistence", "Error en capa de persistencia de  cliente", e);
		}

		List<MarcaDto> resultado = new ArrayList<MarcaDto>();
		for (int i = 0; i < marcas.size(); i++) {
			resultado.add(getMarcaDtoFromMarca(marcas.get(i)));
		}
		return resultado;
	}

	private Producto getProductoFromProductoDto(ProductoDto productoDto) {
		Producto producto = null;
		if (Validator.isNotNull(productoDto.getId()))
			try {
				producto = productoDao.get(productoDto.getId());
			} catch (Exception e) {
				throw new BusinessException("error.persistence", "Error en capa de persistencia de  cliente", e);
			}

		else
			producto = new Producto();

		producto.setCodigo(productoDto.getCodigo());
		producto.setDescripcion(productoDto.getDescripcion());
		producto.setPrecio(new Long(productoDto.getPrecio().longValue()));
		producto.setStock(productoDto.getStock());
		producto.setStockMinimo(productoDto.getStockMinimo());

		try {
			Marca marca = marcaDao.get(new Long(productoDto.getMarca()
				.getId()));
			producto.setMarca(marca);
			
			Tipo tipo = tipoDao.get(new Long(productoDto.getTipo().getId()));
			producto.setTipo(tipo);
		} catch (Exception e) {
			throw new BusinessException("error.persistence", "Error en capa de persistencia de  cliente", e);
		}


		return producto;
	}

	private ProductoDto getProductoDtoFromProducto(Producto producto) {
		ProductoDto productoDto = new ProductoDto();

		productoDto.setId(producto.getId());

		productoDto.setCodigo(producto.getCodigo());
		productoDto.setDescripcion(producto.getDescripcion());
		productoDto.setPrecio(new BigDecimal(producto.getPrecio()));
		productoDto.setStock(producto.getStock());

		productoDto.setMarca(getMarcaDtoFromMarca(producto.getMarca()));

		productoDto.setTipo(getTipoDtoFromTipo(producto.getTipo()));

		return productoDto;
	}

	private TipoDto getTipoDtoFromTipo(Tipo tipo) {
		TipoDto tipoDto = new TipoDto();
		tipoDto.setDescripcion(tipo.getDescripcion());
		tipoDto.setId(tipo.getId());
		return tipoDto;
	}

	private MarcaDto getMarcaDtoFromMarca(Marca marca) {
		MarcaDto marcaDto = new MarcaDto();
		marcaDto.setDescripcion(marca.getDescripcion());
		marcaDto.setId(marca.getId());
		return marcaDto;
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

	public IProductoDao getProductoDao() {
		return productoDao;
	}

	public void setProductoDao(IProductoDao productoDao) {
		this.productoDao = productoDao;
	}

	public IMarcaDao getMarcaDao() {
		return marcaDao;
	}

	public void setMarcaDao(IMarcaDao marcaDao) {
		this.marcaDao = marcaDao;
	}

	public ITipoDao getTipoDao() {
		return tipoDao;
	}

	public void setTipoDao(ITipoDao tipoDao) {
		this.tipoDao = tipoDao;
	}

}
