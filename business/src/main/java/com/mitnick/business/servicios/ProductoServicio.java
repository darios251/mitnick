package com.mitnick.business.servicios;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mitnick.exceptions.BusinessException;
import com.mitnick.exceptions.PersistenceException;
import com.mitnick.persistence.daos.IMarcaDao;
import com.mitnick.persistence.daos.IMovimientoDao;
import com.mitnick.persistence.daos.IParametroDAO;
import com.mitnick.persistence.daos.IProductoDAO;
import com.mitnick.persistence.daos.ITipoDao;
import com.mitnick.persistence.entities.Marca;
import com.mitnick.persistence.entities.Movimiento;
import com.mitnick.persistence.entities.Parametro;
import com.mitnick.persistence.entities.Producto;
import com.mitnick.persistence.entities.Tipo;
import com.mitnick.servicio.servicios.IProductoServicio;
import com.mitnick.servicio.servicios.dtos.ConsultaProductoDto;
import com.mitnick.util.EntityDTOParser;
import com.mitnick.utils.PropertiesManager;
import com.mitnick.utils.Validator;
import com.mitnick.utils.VentaHelper;
import com.mitnick.utils.dtos.MarcaDto;
import com.mitnick.utils.dtos.ProductoDto;
import com.mitnick.utils.dtos.ProductoNuevoDto;
import com.mitnick.utils.dtos.TipoDto;

@SuppressWarnings("rawtypes")
@Service("productoServicio")
public class ProductoServicio extends ServicioBase implements IProductoServicio {

	@Autowired
	protected IProductoDAO productoDao;

	@Autowired
	protected IMarcaDao marcaDao;

	@Autowired
	protected ITipoDao tipoDao;

	@Autowired
	private IMovimientoDao movimientoDao;

	@Autowired
	private IParametroDAO parametroDao;

	@Autowired
	private EntityDTOParser<Tipo, TipoDto> entityDTOParserTipo;

	@Autowired
	private EntityDTOParser<Marca, MarcaDto> entityDTOParserMarca;

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<ProductoDto> consultaProducto(ConsultaProductoDto filtro) {
		try {
			return entityDTOParser.getDtosFromEntities(productoDao.findByFiltro(filtro));
		} catch (PersistenceException e) {
			throw new BusinessException(e, "Error al intentar consultar productos");
		}
	}

	@Transactional(readOnly = true)
	@Override
	public void resetearMovimiento() {
		try {
			List<Producto> productos = productoDao.getAll();
			for (Producto producto : productos) {
				movimientoDao.removeAll(producto.getId());
				Movimiento movimiento = new Movimiento();
				movimiento.setStockAlaFecha(producto.getStock());
				movimiento.setFecha(new Date());
				movimiento.setTipo(Movimiento.CREACION);
				movimiento.setCantidad(producto.getStock());
				movimiento.setProducto(producto);
				movimientoDao.saveOrUpdate(movimiento);
			}

		} catch (PersistenceException e) {
			throw new BusinessException(e, "Error al intentar resetear los movimientos de los productos");
		}
	}

	@Transactional
	@Override
	public ProductoNuevoDto guardarProducto(ProductoNuevoDto productoDto) {
		validateEntity(productoDto);
		try {
			if (productoDto.getId() == null) {
				// si es un producto nuevo
				Producto p = productoDao.findByCode(productoDto.getCodigo());
				if (p != null)
					throw new BusinessException("error.productoServicio.codigo.duplicado",
							"Ya existe un producto con el c�digo ingresado");
			}

			productoDto.setIva("0"); // se setea para que no salga un error de
										// conversi�n luego se sobreescribe

			int stockOriginal = Integer.parseInt(productoDto.getStock());
			// si el producto existe en la base de datos
			if (Validator.isNotNull(productoDto.getId())) {
				Producto productoOriginal = productoDao.get(productoDto.getId());
				stockOriginal = productoOriginal.getStock();
			}

			@SuppressWarnings("unchecked")
			Producto producto = (Producto) entityDTOParser.getEntityFromDto(productoDto);

			int cantidad = producto.getStock() - stockOriginal;
			int cantidadValidar = Math.abs(cantidad);
			boolean validarCantidad = PropertiesManager.getPropertyAsBoolean("application.producto.edit.max.cantidad")
					.booleanValue();

			if (validarCantidad) {
				Parametro parConfigurable = parametroDao.getByName("producto.cantidad.warning");
				if (parConfigurable != null) {
					if (!productoDto.isConfirmado() && cantidadValidar > parConfigurable.getIntValor())
						throw new BusinessException("producto.edit.max.cantidad");
				}
			} else {
				productoDto.setConfirmado(true);
			}

			BigDecimal precioVenta = producto.getPrecioVenta();
			// se calcula el precio del producto sin el iva
			BigDecimal precioSinIva = VentaHelper.calcularPrecioSinIva(producto.getPrecioVenta());
			producto.setPrecioVenta(precioSinIva);
			producto.setIva(precioVenta.subtract(precioSinIva));

			if (stockOriginal != producto.getStock() || productoDto.getId() == null) {
				Movimiento movimiento = new Movimiento();
				movimiento.setStockAlaFecha(stockOriginal);
				movimiento.setFecha(new Date());
				movimiento.setTipo(Movimiento.AJUSTE);
				movimiento.setCantidad(cantidad);
				if (productoDto.getId() == null) {
					movimiento.setTipo(Movimiento.CREACION);
					movimiento.setCantidad(producto.getStock());
				}

				movimiento.setProducto(producto);
				movimientoDao.saveOrUpdate(movimiento);
			}

			producto = productoDao.saveOrUpdate(producto);
			productoDto.setId(producto.getId());
		} catch (PersistenceException e) {
			throw new BusinessException(e, "Error al intentar guardar el producto");
		}
		return productoDto;
	}

	public ProductoNuevoDto getProductoByCode(String code) {
		Producto producto = productoDao.findByStartCode(code);
		if (Validator.isNull(producto))
			throw new BusinessException("error.producto.buscarProducto.productoNoEncontrado", "El producto no se encuentra");
		ProductoNuevoDto productoDTO = (ProductoNuevoDto) entityDTOParser.getProductoNuevoDtoFromProducto(producto);
		return productoDTO;
	}

	@Transactional
	@Override
	public void bajaProducto(ProductoDto productoDto) {
		if (productoDto.getId() == null) {
			throw new BusinessException("error.productoServicio.id.nulo",
					"Se invoca la modificacion de un producto que no existe en la base de datos ya que no se brinda el ID");
		}
		try {
			@SuppressWarnings("unchecked")
			Producto producto = (Producto) entityDTOParser.getEntityFromDto(productoDto);
			producto.setEliminado(true);
			productoDao.saveOrUpdate(producto);
		} catch (PersistenceException e) {
			throw new BusinessException(e, "Error al intentar eliminar el producto");
		}
	}

	@Transactional(readOnly = true)
	@Override
	public List<TipoDto> obtenerTipos() {
		List<TipoDto> resultado = new ArrayList<TipoDto>();
		try {
			resultado.addAll(entityDTOParserTipo.getDtosFromEntities(tipoDao.getAll()));
		} catch (PersistenceException e) {
			throw new BusinessException(e, "Error al intentar obtener los tipos");
		}
		return resultado;
	}

	@Transactional(readOnly = true)
	@Override
	public void agregarNuevoTipo(String descripcion) {
		Tipo tipo = new Tipo();
		tipo.setDescripcion(descripcion);
		tipoDao.save(tipo);
	}

	@Transactional(readOnly = true)
	@Override
	public void agregarNuevaMarca(String descripcion) {
		Marca marca = new Marca();
		marca.setDescripcion(descripcion);
		marcaDao.save(marca);
	}

	@Transactional(readOnly = true)
	@Override
	public List<MarcaDto> obtenerMarcas() {
		List<MarcaDto> resultado = new ArrayList<MarcaDto>();
		try {
			resultado.addAll(entityDTOParserMarca.getDtosFromEntities(marcaDao.getAll()));
		} catch (PersistenceException e) {
			throw new BusinessException(e, "Error al intentar obtener las marcas");
		}
		return resultado;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<ProductoDto> obtenerProductos() {
		List<ProductoDto> productos = new ArrayList<ProductoDto>();
		productos.addAll(entityDTOParser.getDtosFromEntities(productoDao.getAll()));
		return productos;
	}

	@Transactional(readOnly = true)
	@Override
	public ProductoNuevoDto getProductoNuevo(ProductoDto productoDto) {
		if (Validator.isNull(productoDto) || Validator.isNull(productoDto.getId()))
			throw new BusinessException("error.producto.id.null", "El producto o el id es nulo");

		Producto producto = productoDao.get(productoDto.getId());

		return entityDTOParser.getProductoNuevoDtoFromProducto(producto);
	}

	@Transactional(readOnly = true)
	@Override
	public void updateCantidadMaximaWarning(String cantidad) {
		try {
			if (Validator.isBlankOrNull(cantidad) || !Validator.isNumeric(cantidad))
				throw new BusinessException("error.parametro.cantidad.validation", "El par�metro cantidad no es valido");
			Parametro parConfigurable = parametroDao.getByName("producto.cantidad.warning");
			parConfigurable.setValor(cantidad);
			parametroDao.save(parConfigurable);
		} catch (PersistenceException e) {
			throw new BusinessException(e, "Error al intentar modificar la cantidad m�xima sin alertas");
		}
	}
}
